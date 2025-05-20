import java.util.Map;

public class Validation {
    private BoardState boardState;
    private Piece primaryPiece;

    public Validation(BoardState boardState) {
        this.boardState = boardState;
        this.primaryPiece = BoardState.primaryPiece;
    }
    public void validation(){
        validation1();
        validation2();
        validation3();
        validation4();
    }

    /**
     * Validasi 1: Pastikan exit point berada satu baris/kolom dengan primary piece.
     * Jika primary piece horizontal → exitCol harus satu baris dengan piece.
     * Jika vertical → exitRow harus satu kolom dengan piece.
     */
    private boolean validation1() {
        if (primaryPiece.getOrientation() == Orientation.HORIZONTAL) {
            if (primaryPiece.getUpLeft().r != BoardState.exitRow) {
                throw new IllegalStateException("Exit point is not in the same row as the primary piece.");
            }
        } else {
            if (primaryPiece.getUpLeft().c != BoardState.exitCol) {
                throw new IllegalStateException("Exit point is not in the same column as the primary piece.");
            }
        }
        return true;
    }

    /**
     * Validasi 2: Cek apakah jalur menuju exit point kosong.
     * Jika horizontal, periksa semua sel di kanan piece sampai exit.
     * Jika vertical, periksa semua sel di bawah piece sampai exit.
     */
    private boolean validation2(){
            boolean res = true; 
            if (primaryPiece.getOrientation() == Orientation.HORIZONTAL) {
                for (int i = primaryPiece.getUpLeft().c + 1; i < boardState.cols; i++) {
                    for (int j = primaryPiece.getUpLeft().r; j < primaryPiece.getUpLeft().r + primaryPiece.getLength(); j++) {
                        if (boardState.getBoard()[j][i] != '.') {
                            res = false;
                            break;
                        }
                    }
                }
            } else {
                for (int i = primaryPiece.getUpLeft().r + 1; i < boardState.rows; i++) {
                    if (boardState.getBoard()[i][primaryPiece.getUpLeft().c] != '.') {
                        for (int j = primaryPiece.getUpLeft().c; j < primaryPiece.getUpLeft().c + primaryPiece.getLength(); j++) {
                            if (boardState.getBoard()[i][j] != '.') {
                                res = false;
                                break;
                            }
                        }
                    }
                }
            }
            if (res) {
                throw new NullPointerException("There is a block (opposite orientation with piece) in the way of primary piece");
            }
            return true;
        }

    /**
     * Validasi 3: Pastikan tidak ada block dengan orientasi sama menghalangi dari ujung primary ke exit.
     */
    private boolean validation3() {
         if (BoardState.primaryPiece.orientation == Orientation.HORIZONTAL){
            int ex = BoardState.exitCol;
            int pry = BoardState.primaryPiece.upLeft.c;
            int prx = BoardState.primaryPiece.upLeft.r;
            int len = BoardState.primaryPiece.length;
            
            System.out.println("orientation: " + BoardState.primaryPiece.orientation);
            if (ex < pry){
                char[][] matrix = boardState.getBoard();
                for (int i = ex+2; i < pry; i ++){
                    if (matrix[prx][i] == matrix[prx][i-1]){
                        System.out.println("a");
                        throw new NullPointerException("There is a block (same orientation with piece) in the way of primary piece to exit point: " + matrix[i][prx]);
                    }
                }
            } else {
                char[][] matrix = boardState.getBoard();
                for (int i = pry+len+1; i < ex; i ++){
                    if (matrix[prx][i] == matrix[prx][i-1]){
                        System.out.println("b");
                        throw new NullPointerException("There is a block (same orientation with piece) in the way of primary piece to exit point: " + matrix[i][prx]);
                    }
                }
            }
        } else {
            int ex = BoardState.exitRow;
            int pry = BoardState.primaryPiece.upLeft.c;
            int prx = BoardState.primaryPiece.upLeft.r;
            int len = BoardState.primaryPiece.length;
    
            if (ex < prx){
                char[][] matrix = boardState.getBoard();
                for (int i = ex+2; i < pry; i ++){
                    if (matrix[i][pry] == matrix[i-1][pry]){
                        System.out.println("c");
                        throw new NullPointerException("There is a block (same orientation with piece) in the way of primary piece to exit point: " + matrix[i][pry]);
                    }
                }
            } else {
                char[][] matrix = boardState.getBoard();
                for (int i = pry+len+1; i < ex; i ++){
                    if (matrix[i][pry] == matrix[i-1][pry]){
                        System.out.println("d");
                        throw new NullPointerException("There is a block (same orientation with piece) in the way of primary piece to exit point: " + matrix[i][pry]);
                    }
                }
            }
        }
        return true;
    }

    /***
     * Validasi 4: misal koordinat exit point adalah (i,0) atau (0,j) maka h = max(i, row - i) atau max(col - j, j)
     * akan di cek seluruh piece yang berbeda orientasi dengan primary piece, apakah ada yang lebih dari h, jika iya maka board tidak valid
     */
    private boolean validation4() {
        boolean found = false;
        Orientation orientation = BoardState.primaryPiece.getOrientation();
        int h;
        if (BoardState.primaryPiece.getOrientation() == Orientation.HORIZONTAL){
            h = Math.max(BoardState.exitRow, BoardState.rows - BoardState.exitRow);
        } else {
            h = Math.max(BoardState.exitCol, BoardState.cols - BoardState.exitCol);
        }
        System.out.println("h = " + h);
        
        Map<Character, Piece> pieces = BoardState.pieces;
        for (Map.Entry<Character, Piece> entry : pieces.entrySet()) {
            Piece piece = entry.getValue();
            if (piece.getOrientation() != orientation) {
                if (piece.getLength() > h) {
                    int rowPiece = piece.getUpLeft().r;
                    int colPiece = piece.getUpLeft().c;
                    int rowExit = BoardState.exitRow;
                    int colExit = BoardState.exitCol;
                    int rowPrimary = BoardState.primaryPiece.getUpLeft().r;
                    int colPrimary = BoardState.primaryPiece.getUpLeft().c;
                    boolean isBetween = (
                        (rowPiece >= Math.min(rowExit, rowPrimary) && rowPiece <= Math.max(rowExit, rowPrimary)) ||
                        (colPiece >= Math.min(colExit, colPrimary) && colPiece <= Math.max(colExit, colPrimary))
                    );
                    if (isBetween) {
                        found = true;
                    }
                    break;
                }
            }
        }
        if (found) {
            throw new NullPointerException("There is a block (different orientation with piece) that is too long to exit point");
        }
        return true;
    }
}