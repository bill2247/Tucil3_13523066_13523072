import java.util.*;

public class BoardState {
    public static int rows, cols;
    public static Map<Character, Piece> pieces = new HashMap<>();
    public static int exitRow, exitCol;
    public static Piece primaryPiece = null;
    private List<char[]> board = new ArrayList<>();
    // add: map of character to top-left-index's coordinate, non static
    public Map<Character, Coordinate> piecesLocation = new HashMap<>();
    
    public static Map<Integer, ArrayList<Integer>> idealHPieceCoordinates; // lokasi ideal piece horizontal
    public static Map<Integer, ArrayList<Integer>> idealVPieceCoordinates; // lokasi ideal piece vertikal

    public BoardState(int rows, int cols) {
        BoardState.rows = rows;
        BoardState.cols = cols;
    }

    public void setExitPoint(Coordinate c) {
        BoardState.exitRow = c.x;
        BoardState.exitCol = c.y;
        if (BoardState.exitCol == 0 || BoardState.exitCol == cols) {
            BoardState.primaryPiece = new Piece('P', Orientation.HORIZONTAL, 0, null);
        } else {
            BoardState.primaryPiece = new Piece('P', Orientation.VERTICAL, 0, null);
        }
    }

    // public Coordinate getExitPoint() {
    //     return new Coordinate(exitRow, exitCol);
    // }

    public BoardState cloneBoardState(){
        BoardState newState = new BoardState(rows, cols);
        for(char[] row : this.board){
            newState.board.add(Arrays.copyOf(row, row.length));
        }
        for(Map.Entry<Character, Coordinate> entry : this.piecesLocation.entrySet()){
            Character id = entry.getKey();
            Coordinate coor = entry.getValue();
            newState.piecesLocation.put(id, new Coordinate(coor.x, coor.y));
        }

        return newState;
    }

    public boolean addCell(char id, int r, int c) {
        if (id == 'K' && (id < 'A' || id > 'Z' || id != '.')) {
            return false;
        }
        if (id == '.') return true;
        if (id == 'P') {
            primaryPiece.setLength(primaryPiece.getLength() + 1);
            primaryPiece.setUpLeft(new Coordinate(r, c));
            return true;
        }
        if (pieces.containsKey(id)) {
            pieces.get(id).addLength(1);
            Coordinate p = pieces.get(id).getUpLeft();
            if (r == p.x){
                pieces.get(id).setOrientation(Orientation.HORIZONTAL);
            } else if (c == p.y) {
                pieces.get(id).setOrientation(Orientation.VERTICAL);
            } else {
                return false;
            }
        } else {
            Piece p = new Piece(id, Orientation.HORIZONTAL, 1, new Coordinate(r, c));
            pieces.put(id, p);
            return true;
        }
        return true;
    }

    public void setBoard(char[][] board) {
        for (int i = 0; i < rows; i++) {
            this.board.add(board[i]);
        }
    }
    public char[][] getBoard() {
        char[][] boardArray = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            boardArray[i] = this.board.get(i);
        }
        return boardArray;
    }

    public void addPiece(char id, int r, int c){
        // menambahkan piece ke papan diberikan id dan lokasi kiri-atas nya
        if(board.get(r)[c] == '.' || (r==-1 && c==-1)){
            return;
        }
        Piece currentPiece = pieces.get(id);
        if(currentPiece.getOrientation() == Orientation.HORIZONTAL){
            for(int i=0;i<currentPiece.getLength();i++){
                board.get(r)[c+i] = id;
            }
        } else{
            for(int i=0;i<currentPiece.getLength();i++){
                board.get(r+i)[c] = id;
            }
        }
        Coordinate currentCoordinate = new Coordinate(r, c);
        piecesLocation.put(id, currentCoordinate);
    }

    public void deletePiece(char id){
        // menghapus piece dari papan diberikan id nya
        if(!piecesLocation.containsKey(id)){
            return;
        }
        Coordinate currentCoordinate = piecesLocation.get(id);
        Piece currentPiece = pieces.get(id);
        int r = currentCoordinate.y;
        int c = currentCoordinate.x;
        if(currentPiece.getOrientation() == Orientation.HORIZONTAL){
            for(int i=0;i<currentPiece.getLength();i++){
                board.get(r)[c+i] = '.';
            }
        } else{
            for(int i=0;i<currentPiece.getLength();i++){
                board.get(r+i)[c] = '.';
            }
        }
        piecesLocation.remove(id);
    }

    private void addMove(Map<Character, ArrayList<Coordinate>> res, char id, Coordinate coor){
        res.computeIfAbsent(id, unused -> new ArrayList<>()).add(coor);
    }

    public Map<Character, ArrayList<Coordinate>> generateLegalMoves(){
        // coordinate of (-1, -1) means a primary piece is going outside the board (hence the puzzle is solved)
        Map<Character, ArrayList<Coordinate>> res = new HashMap<>();

        // cek setiap piece di dalam papan
        for(Map.Entry<Character, Coordinate> entry : piecesLocation.entrySet()){
            char currId = entry.getKey();
            Piece currPiece = pieces.get(currId);
            Coordinate currCoordinate = piecesLocation.get(currId);
            int r = currCoordinate.y;
            int c = currCoordinate.x;
            int i;
            if(currPiece.getOrientation() == Orientation.HORIZONTAL){
                // cek legal move sebelah kiri
                for(i=0;c-1-i>=0;i++){
                    char checkId = board.get(r)[c-1-i];
                    if(checkId!='.'){break;}
                    Coordinate checkCoordinate = new Coordinate(c-1-i, r);
                    addMove(res, currId, checkCoordinate);
                }
                if(currId=='P' && c-1-i == exitCol){
                    addMove(res, currId, new Coordinate(-1, -1));
                }
                // cek legal move sebelah kanan
                for(i=0;c+currPiece.getLength()+i<cols;i++){
                    char checkId = board.get(r)[c+currPiece.getLength()+i];
                    if(checkId!='.'){break;}
                    Coordinate checkCoordinate = new Coordinate(c+currPiece.getLength()+i, r);
                    addMove(res, currId, checkCoordinate);
                }
                if(currId=='P' && c+currPiece.getLength()+i == exitCol){
                    addMove(res, currId, new Coordinate(-1, -1));
                }
            } else{
                // cek legal move sebelah atas
                for(i=0;r-1-i>=0;i++){
                    char checkId = board.get(r-1-i)[c];
                    if(checkId!='.'){break;}
                    Coordinate checkCoordinate = new Coordinate(c, r-1-i);
                    addMove(res, currId, checkCoordinate);
                }
                if(currId=='P' && r-1-i == exitRow){
                    addMove(res, currId, new Coordinate(-1, -1));
                }
                // cek legal move sebelah bawah
                for(i=0;r+currPiece.getLength()+i<rows;i++){
                    char checkId = board.get(r+currPiece.getLength()+i)[c];
                    if(checkId!='.'){break;}
                    Coordinate checkCoordinate = new Coordinate(c, r+currPiece.getLength()+i);
                    addMove(res, currId, checkCoordinate);
                }
                if(currId=='P' && r+currPiece.getLength()+i == exitRow){
                    addMove(res, currId, new Coordinate(-1, -1));
                }
            }
        };

        return res;
    }
    // public ArrayList<Map<Piece, Coordinate>> generateLegalMoves(){
    // ongoing, ghif
    // }
    public void printAllPieces() {
        for (Map.Entry<Character, Piece> entry : pieces.entrySet()) {
            entry.getValue().printPiece();
        }
    }
}
