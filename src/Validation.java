import java.util.Map;

public class Validation {
    private BoardState boardState;
    private Piece primaryPiece;
    private boolean isExitPointHorizontal;

    public Validation(BoardState boardState) {
        this.boardState = boardState;
        this.primaryPiece = BoardState.primaryPiece;
        this.isExitPointHorizontal = BoardState.exitCol == 0 || BoardState.exitCol == BoardState.cols - 1;
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
    // private boolean validation2() {
    //     char[][] matrix = boardState.getBoard();

    //     if (primaryPiece.getOrientation() == Orientation.HORIZONTAL) {
    //         int row = primaryPiece.getUpLeft().c;
    //         int startCol = primaryPiece.getUpLeft().r + primaryPiece.getLength();
    //         // jika cell yang dicek hanya 1, lewati
    //         if (startCol == BoardState.cols - 1) {
    //             return true;
    //         }
    //         for (int col = startCol; col < BoardState.cols; col++) {
    //             if (matrix[row][col] != '.') {
    //                 throw new IllegalStateException("There is a block in the way of the primary piece (horizontal).");
    //             }
    //         }
    //     } else {
    //         int col = primaryPiece.getUpLeft().r;
    //         int startRow = primaryPiece.getUpLeft().c + primaryPiece.getLength();
    //         // jika cell yang dicek hanya 1, lewati
    //         if (startRow == BoardState.rows - 1){
    //             return true;
    //         }
    //         for (int row = startRow; row < BoardState.rows; row++) {
    //             if (matrix[row][col] != '.') {
    //                 throw new IllegalStateException("There is a block in the way of the primary piece (vertical).");
    //             }
    //         }
    //     }
    //     return true;
    // }

    private boolean validation2(){
            boolean res = true; // tidak ada blok kosong
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
    
            if (ex < pry){
                char[][] matrix = boardState.getBoard();
                for (int i = ex+2; i < pry; i ++){
                    if (matrix[i][prx] == matrix[i-1][prx]){
                        throw new NullPointerException("There is a block (same orientation with piece) in the way of primary piece to exit point");
                        // return false;
                    }
                }
            } else {
                char[][] matrix = boardState.getBoard();
                for (int i = pry+len+1; i <= ex; i ++){
                    if (matrix[i][prx] == matrix[i-1][prx]){
                        throw new NullPointerException("There is a block (same orientation with piece) in the way of primary piece to exit point");
                        // return false;
                    }
                }
            }
        } else {
            int ex = BoardState.exitRow;
            int pry = BoardState.primaryPiece.upLeft.c;
            int prx = BoardState.primaryPiece.upLeft.r;
            int len = BoardState.primaryPiece.length;
    
            if (ex < pry){
                char[][] matrix = boardState.getBoard();
                for (int i = ex+2; i < pry; i ++){
                    if (matrix[i][prx] == matrix[i-1][prx]){
                        throw new NullPointerException("There is a block (same orientation with piece) in the way of primary piece to exit point");
                        // return false;
                    }
                }
            } else {
                char[][] matrix = boardState.getBoard();;
                for (int i = pry+len+1; i <= ex; i ++){
                    if (matrix[i][prx] == matrix[i-1][prx]){
                        throw new NullPointerException("There is a block (same orientation with piece) in the way of primary piece to exit point");
                        // return false;
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
        
        Map<Character, Piece> pieces = BoardState.pieces;
        for (Map.Entry<Character, Piece> entry : pieces.entrySet()) {
            Piece piece = entry.getValue();
            if (piece.getOrientation() != orientation) {
                if (piece.getLength() >= h) {
                    found = true;
                    break;
                }
            }
        }
        if (found) {
            throw new NullPointerException("There is a block (different orientation with piece) that is too long to exit point");
            // return false;
        }
        return true;
    }
}