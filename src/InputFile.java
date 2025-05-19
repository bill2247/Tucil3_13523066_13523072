import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputFile {
    private String filePath;
    private BoardState boardState;

    public InputFile(String filePath) {
        this.filePath = filePath;
        this.boardState = new BoardState(0, 0);
        readFile();
    }
    public BoardState getBoardState() {
        return boardState;
    }

    private void readFile(){
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // membaca line pertama untuk row dan col
            String line;
            line = br.readLine();
            String[] rowCol = line.split(" ");
            int row = Integer.parseInt(rowCol[0]);
            int col = Integer.parseInt(rowCol[1]);
            // membaca line kedua untuk jumlah piece
            line = br.readLine();
            // menghitung jumlah piece dan array of id piece untuk handling kasus numPiece != jumlah piece yang ada. 
            String[] row2 = line.split(" ");
            int numPieces = Integer.parseInt(row2[0]);
            List<Character> idPieces = new ArrayList<>();
            // membaca line ketiga untuk mengenali exit point 
            char[][] matrixBoard = new char[row][col];
            boolean isExitPoint = false;
            line = br.readLine();
            char[] rowContent = line.toCharArray();
            for (int i = 0; i < rowContent.length; i++) {
                char cell = rowContent[i];
                if (cell == 'K') {
                    boardState.setExitPoint(new Coordinate((i+1)/2, 0));
                    isExitPoint = true;
                    break;
                }
            }
            // membaca line berikutnya untuk mnegenali exit point dan menyimpan point ke dalam matrixBoard
            int i = 0;
            // if (isExitPoint){
            //     i = 1;
            // }
            while (i < row) {
                if (!(i == 0 && !isExitPoint)) {
                    line = br.readLine();
                } 
                if (line == null) {
                    break;
                }
                rowContent = line.toCharArray();
                if (rowContent[0] == 'K') {
                    boardState.setExitPoint(new Coordinate(i, 0));
                    isExitPoint = true;
                }
                if (rowContent[rowContent.length - 1] == 'K') {
                    boardState.setExitPoint(new Coordinate(i, col - 1));
                    isExitPoint = true;
                }
                int colCounter = 0;
                for (int j = 0; j < rowContent.length; j++) {
                    char cell = rowContent[j];
                    if (cell != ' ' && cell != 'K') {
                        if (colCounter >= col) {
                            colCounter = col - 1;
                        }
                        if (cell != 'K'){
                            matrixBoard[i][colCounter] = cell;
                        }
                        // jika id piece sudah ada di dalam list, maka tidak perlu ditambahkan
                        if (!idPieces.contains(cell)) {
                            idPieces.add(cell);
                        }
                        colCounter++;
                    }
                }
                i++;
            }
            if (row != matrixBoard.length || col != matrixBoard[0].length) {
                throw new IllegalArgumentException("Invalid matrix size");
            }
            // ambil id piece yang unik dan hitung jumlah piece
            List<Character> uniquePieces = new ArrayList<>();
            for (char id : idPieces) {
                if (!uniquePieces.contains(id)) {
                    uniquePieces.add(id);
                }
            }
            // jika jumlah piece tidak sesuai dengan yang ada di file
            if (uniquePieces.size() != numPieces + 2) { // 2 adalah piece '.' dan 'P'
                throw new IllegalArgumentException("Invalid number of pieces, expected: " + numPieces + ", found: " + uniquePieces.size() + " thoose elements are: " + uniquePieces);
            }
            // membaca line terakhir untuk mengenali exit point jika belum ditemukan
            if (!isExitPoint){
                line = br.readLine();
                rowContent = line.toCharArray();
                for (int j = 0; j < rowContent.length; j++) {
                    char cell = rowContent[j];
                    if (cell == 'K') {
                        boardState.setExitPoint(new Coordinate((j+1)/2, row - 1));
                        break;
                    }
                }
            }
            // menyimpan matrixBoard ke dalam boardState
            BoardState.cols = col;
            BoardState.rows = row;
            boardState.setBoard(matrixBoard);
            for (int j = 0; j < row; j++) {
                for (int k = 0; k < col; k++) {
                    char cell = matrixBoard[j][k];
                    boolean h = boardState.addCell(cell, j, k);
                    if (!h) {
                        System.out.println("Invalid cell: " + cell + " at (" + j + ", " + k + ")");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
