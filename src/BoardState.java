import java.util.*;

public class BoardState {
    public static int rows, cols;
    public static Map<Character, Piece> pieces = new HashMap<>();
    public static int exitRow, exitCol;
    public static Piece primaryPiece = new Piece('P', Orientation.HORIZONTAL, 0, null);
    private char[][] board;
    private Map<Character, Coordinate> piecesLocation = new HashMap<>();
    
    public static Map<Integer, ArrayList<Integer>> idealHPieceCoordinates; // lokasi ideal piece horizontal
    public static Map<Integer, ArrayList<Integer>> idealVPieceCoordinates; // lokasi ideal piece vertikal

    public BoardState(int rows, int cols) {
        BoardState.rows = rows;
        BoardState.cols = cols;
    }

    public Map<Character, Coordinate> getPiecesLocation(){return this.piecesLocation;}
    public void setPiecesLocation(Map<Character, Coordinate> piecesLocation){this.piecesLocation = piecesLocation;}

    public void setExitPoint(Coordinate c) {
        BoardState.exitRow = c.r;
        BoardState.exitCol = c.c;
    }

    // public Coordinate getExitPoint() {
    //     return new Coordinate(exitRow, exitCol);
    // }

    public BoardState cloneBoardState(){
        BoardState newState = new BoardState(rows, cols);
        newState.board = new char[rows][];
        for(int i=0;i<rows;i++){
            newState.board[i] = board[i].clone();
        }

        for(Map.Entry<Character, Coordinate> entry : this.piecesLocation.entrySet()){
            Character id = entry.getKey();
            Coordinate coor = entry.getValue();
            newState.piecesLocation.put(id, new Coordinate(coor.r, coor.c));
        }

        return newState;
    }

    public boolean addCell(char id, int r, int c) {
        if (id == 'K' && (id < 'A' || id > 'Z' || id != '.')) {
            return false;
        }
        if (id == '.') return true;
        if (id == 'P') {
            if (pieces.containsKey(id)) {
                if (primaryPiece.getUpLeft().r == r){
                    primaryPiece.setOrientation(Orientation.HORIZONTAL);
                } else if (primaryPiece.getUpLeft().c == c) {
                    primaryPiece.setOrientation(Orientation.VERTICAL);
                } else {
                    return false;
                }
                // primaryPiece.setLength(primaryPiece.getLength() + 1);
                pieces.get(id).addLength(1);
                System.out.println("len added: " + primaryPiece.getLength());
            } else {
                pieces.put(id, primaryPiece);
                primaryPiece.setUpLeft(new Coordinate(r, c));
                pieces.get(id).addLength(1);
            }

            return true;
        }
        if (pieces.containsKey(id)) {
            pieces.get(id).addLength(1);
            Coordinate p = pieces.get(id).getUpLeft();
            if (r == p.r){
                pieces.get(id).setOrientation(Orientation.HORIZONTAL);
            } else if (c == p.c) {
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
        this.board = new char[rows][];
        for (int i = 0; i < rows; i++) {
            this.board[i] = board[i].clone();
        }
    }

    public char[][] getBoard() {
        return board;
    }

    public void addPiece(char id, int r, int c){
        // menambahkan piece ke papan diberikan id dan lokasi kiri-atas nya
        if(board[r][c] != '.' || (r==-1 && c==-1)){
            return;
        }
        Piece currentPiece = pieces.get(id);
        if(currentPiece.getOrientation() == Orientation.HORIZONTAL){
            for(int i=0;i<currentPiece.getLength();i++){
                board[r][c+i] = id;
            }
        } else{
            for(int i=0;i<currentPiece.getLength();i++){
                board[r+i][c] = id;
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
        int r = currentCoordinate.r;
        int c = currentCoordinate.c;
        if(currentPiece.getOrientation() == Orientation.HORIZONTAL){
            for(int i=0;i<currentPiece.getLength();i++){
                board[r][c+i] = '.';
            }
        } else{
            for(int i=0;i<currentPiece.getLength();i++){
                board[r+i][c] = '.';
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
            int r = currCoordinate.r;
            int c = currCoordinate.c;
            int i;
            if(currPiece.getOrientation() == Orientation.HORIZONTAL){
                // cek legal move sebelah kiri
                for(i=0;c-1-i>=0;i++){
                    char checkId = board[r][c-1-i];
                    if(checkId!='.'){break;}
                    Coordinate checkCoordinate = new Coordinate(r, c-1-i);
                    addMove(res, currId, checkCoordinate);
                }
                if(currId=='P' && c-1-i == exitCol){
                    addMove(res, currId, new Coordinate(-1, -1));
                }
                // cek legal move sebelah kanan
                for(i=0;c+currPiece.getLength()+i<cols;i++){
                    char checkId = board[r][c+currPiece.getLength()+i];
                    if(checkId!='.'){break;}
                    Coordinate checkCoordinate = new Coordinate(r, c+i+1);
                    addMove(res, currId, checkCoordinate);
                }
                if(currId=='P' && c+currPiece.getLength()+i == exitCol){
                    addMove(res, currId, new Coordinate(-1, -1));
                }
            } else{
                // cek legal move sebelah atas
                for(i=0;r-1-i>=0;i++){
                    char checkId = board[r-1-i][c];
                    if(checkId!='.'){break;}
                    Coordinate checkCoordinate = new Coordinate(r-1-i, c);
                    addMove(res, currId, checkCoordinate);
                }
                if(currId=='P' && r-1-i == exitRow){
                    addMove(res, currId, new Coordinate(-1, -1));
                }
                // cek legal move sebelah bawah
                for(i=0;r+currPiece.getLength()+i<rows;i++){
                    char checkId = board[r+currPiece.getLength()+i][c];
                    if(checkId!='.'){break;}
                    Coordinate checkCoordinate = new Coordinate(r+i+1, c);
                    addMove(res, currId, checkCoordinate);
                }
                if(currId=='P' && r+currPiece.getLength()+i == exitRow){
                    addMove(res, currId, new Coordinate(-1, -1));
                }
            }
        };

        return res;
    }
    
    public void printAllPieces() {
        for (Map.Entry<Character, Piece> entry : pieces.entrySet()) {
            entry.getValue().printPiece();
        }
    }

    public void printGameState() {
        // System.out.println("Rows: " + rows);
        // System.out.println("Cols: " + cols);
        // System.out.println("Exit Row: " + exitRow);
        // System.out.println("Exit Col: " + exitCol);

        // System.out.println("Primary Piece: " + (primaryPiece != null ? primaryPiece : "null"));

        // System.out.println("\nPieces:");
        // for (Map.Entry<Character, Piece> entry : pieces.entrySet()) {
        //     System.out.println("  " + entry.getKey() + ": " + entry.getValue());
        // }

        // System.out.println("\nPieces Location:");
        // for (Map.Entry<Character, Coordinate> entry : piecesLocation.entrySet()) {
        //     System.out.println("  " + entry.getKey() + ": " + entry.getValue());
        // }

        System.out.println("\nBoard:");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}