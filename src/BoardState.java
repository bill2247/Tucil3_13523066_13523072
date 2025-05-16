import java.util.*;

public class BoardState {
    public static int rows, cols;
    public static Map<Character, Piece> pieces = new HashMap<>();
    public static int exitRow, exitCol;
    public static Piece primaryPiece = null;
    public List<char[]> board = new ArrayList<>();
    // add: map of character to top-left-index's coordinate, non static
    
    public static Map<Integer, ArrayList<Integer>> idealHPieceCoordinates; // lokasi ideal piece horizontal
    public static Map<Integer, ArrayList<Integer>> idealVPieceCoordinates; // lokasi ideal piece vertikal

    public BoardState(int rows, int cols) {
        BoardState.rows = rows;
        BoardState.cols = cols;
    }

    public void setExitPoint(int r, int c) {
        BoardState.exitRow = r;
        BoardState.exitCol = c;
    }

    public void addCell(char id, int r, int c) {
        if (id == '.') return;
        if (id == 'K') {
            setExitPoint(r, c);
            return;
        }
        if (id == 'S') {
            if (primaryPiece != null) {
                primaryPiece = new Piece(id, Orientation.VERTICAL, 1);
                pieces.put(id, primaryPiece);
                return;
            } 
            primaryPiece.setLength(primaryPiece.getLength() + 1);
        }
        if (pieces.containsKey(id)) {
            Piece p = pieces.get(id);
            p.setLength(p.getLength() + 1);
            if (r == p.getUpLeft().x){
                p.setOrientation(Orientation.HORIZONTAL);
            } else {
                p.setOrientation(Orientation.VERTICAL);
            }
        } else {
            Piece p = new Piece(id, Orientation.HORIZONTAL, 1);
            pieces.put(id, p);
        }
    }

    public void deleteCell(char id){
        // ...
    }

    // public ArrayList<Map<Piece, Coordinate>> generateLegalMoves(){
    // ongoing, ghif
    // }
}
