import java.util.*;

public class BoardState {
    public int rows, cols;
    public Map<Character, Piece> pieces = new HashMap<>();
    public int exitRow, exitCol;
    public List<char[]> board = new ArrayList<>();
    public Piece primaryPiece = null;

    public BoardState(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    public void setExitPoint(int r, int c) {
        this.exitRow = r;
        this.exitCol = c;
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
}
