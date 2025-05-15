public class Piece {
    public char id;
    public Orientation orientation;
    public int length;
    public Coordinat upLeft;

    public Piece(char id, Orientation orientation, int length) {
        this.id = id;
        this.orientation = orientation;
        this.length = length;
    }

    public char getId() {
        return id;
    }
    public Orientation getOrientation() {
        return orientation;
    }
    public int getLength() {
        return length;
    }
    public Coordinat getUpLeft() {
        return upLeft;
    }
    public void setId(char id) {
        this.id = id;
    }
    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }
    public void setLength(int length) {
        this.length = length;
    }
    public void setUpLeft(Coordinat upLeft) {
        this.upLeft = upLeft;
    }
}

