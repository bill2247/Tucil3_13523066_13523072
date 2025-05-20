import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InputFile {
    private final String filePath;
    private final BoardState boardState;

    public InputFile(String filePath) {
        this.filePath = filePath;
        this.boardState = new BoardState(0, 0);
        loadBoardFromFile();
    }

    public BoardState getBoardState() {
        return boardState;
    }

    private void addPiecesLocation() {
        Map<Character, Coordinate> piecesLocation = new HashMap<>();
        for (Map.Entry<Character, Piece> entry : BoardState.pieces.entrySet()) {
            char id = entry.getKey();
            Piece piece = entry.getValue();
            Coordinate coordinate = piece.getUpLeft();
            piecesLocation.put(id, coordinate);
        }
        boardState.setPiecesLocation(piecesLocation);
    }

    private void loadBoardFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            int rows = 0, cols = 0;
            int expectedPieces;
            List<Character> foundPieceIds = new ArrayList<>();

            // Parse header lines
            String line = reader.readLine();
            int[] dims = parseDimensions(line);
            rows = dims[0];
            cols = dims[1];

            line = reader.readLine();
            expectedPieces = parseExpectedPieceCount(line);

            // Prepare board matrix
            char[][] matrix = new char[rows][cols];

            boolean exitFound = parseTopExit(line = reader.readLine(), matrix, foundPieceIds);

            // Read remaining rows
            exitFound |= parseBoardRows(reader, matrix, foundPieceIds, rows, cols, exitFound);

            validateMatrixSize(matrix, rows, cols);
            validateUniquePieces(foundPieceIds, expectedPieces);

            // If exit still not found, check last line
            if (!exitFound) {
                line = reader.readLine();
                if (line != null) {
                    parseBottomExit(line, rows - 1);
                } else {
                    throw new IllegalArgumentException("Exit point not found");
                }
            }

            // Finalize board state
            BoardState.rows = rows;
            BoardState.cols = cols;
            boardState.setBoard(matrix);
            populateBoardState(matrix, rows, cols);
            addPiecesLocation();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int[] parseDimensions(String header) {
        String[] parts = header.split(" ");
        int r = Integer.parseInt(parts[0]);
        int c = Integer.parseInt(parts[1]);
        return new int[]{r, c};
    }

    private int parseExpectedPieceCount(String line) {
        String[] parts = line.split(" ");
        return Integer.parseInt(parts[0]);
    }

    private boolean parseTopExit(String line, char[][] matrix, List<Character> pieceIds) {
        char[] cells = line.toCharArray();
        
        for (int j = 0; j < cells.length; j++) {
            if (cells[j] == 'K') {
                boardState.setExitPoint(new Coordinate(-1, (j + 1) / 2));
                return true;
            }
        }
        // Also process row 0 for pieces
        extractRowData(cells, matrix, 0, pieceIds);
        return false;
    }

    private boolean parseBoardRows(BufferedReader reader, char[][] matrix, List<Character> pieceIds, int totalRows, int totalCols, boolean exitAlreadyFound)throws IOException {
        boolean exitFound = exitAlreadyFound;
        int up = totalRows;
        int down = 1;
        if (exitAlreadyFound) {
            // up = totalRows + 1;
            down = 0;
        }
        for (int i = down; i < up; i++) {
            String line = reader.readLine();
            
            if (line == null) break;

            char[] cells = line.toCharArray();
            // Check side exits
            if (!exitFound) {
                if (cells[0] == 'K') {
                    boardState.setExitPoint(new Coordinate(i, -1));
                    exitFound = true;
                } else if (cells[cells.length - 1] == 'K') {
                    boardState.setExitPoint(new Coordinate(i, totalCols));
                    exitFound = true;
                }
            }
            extractRowData(cells, matrix, i, pieceIds);
        }
        return exitFound;
    }

    private void extractRowData(char[] cells, char[][] matrix, int rowIndex, List<Character> pieceIds) {
        int colIndex = 0;
        for (char cell : cells) {
            if (cell != ' ' && cell != 'K') {
                if (colIndex >= matrix[rowIndex].length) {
                    colIndex = matrix[rowIndex].length - 1;
                }
                matrix[rowIndex][colIndex] = cell;
                if (!pieceIds.contains(cell)) {
                    pieceIds.add(cell);
                }
                colIndex++;
            }
        }
    }

    private void parseBottomExit(String line, int bottomRow) {
        char[] cells = line.toCharArray();
        
        for (int j = 0; j < cells.length; j++) {
            if (cells[j] == 'K') {
                boardState.setExitPoint(new Coordinate(bottomRow+1, (j + 1) / 2));
                break;
            }
        }
    }

    private void validateMatrixSize(char[][] matrix, int rows, int cols) {
        if (matrix.length != rows || matrix[0].length != cols) {
            throw new IllegalArgumentException("Invalid matrix size");
        }
    }

    private void validateUniquePieces(List<Character> pieceIds, int expectedCount) {
        Set<Character> unique = new HashSet<>(pieceIds);
        // exclude '.' and 'P'
        unique.remove('.');
        unique.remove('P');

        if (unique.size() != expectedCount) {
            throw new IllegalArgumentException(
                "Invalid number of pieces, expected: " + expectedCount + ", found: " + unique.size()
                + " elements: " + unique);
        }
    }

    private void populateBoardState(char[][] matrix, int rows, int cols) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char cell = matrix[i][j];
                boolean valid = boardState.addCell(cell, i, j);
                if (!valid) {
                    System.out.println("Invalid cell: " + cell + " at (" + i + ", " + j + ")");
                }
            }
        }
    }
}
