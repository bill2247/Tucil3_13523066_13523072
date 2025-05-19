public class Piece {
    public char id;
    public Orientation orientation;
    public int length;
    public Coordinate upLeft;

    public Piece(char id, Orientation orientation, int length, Coordinate upLeft) {
        this.id = id;
        this.orientation = orientation;
        this.length = length;
        this.upLeft = upLeft;
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
    public Coordinate getUpLeft() {
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
    public void setUpLeft(Coordinate upLeft) {
        this.upLeft = upLeft;
    }
    public void addLength(int length) {
        this.length += length;
    }
    public void printPiece() {
        System.out.println("Piece ID: " + id);
        System.out.println("Orientation: " + orientation);
        System.out.println("Length: " + length);
        System.out.println("UpLeft Coordinate: (" + upLeft.x + ", " + upLeft.y + ")");
    }
}

