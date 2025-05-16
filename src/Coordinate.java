public class Coordinate {
    public int x;
    public int y;
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Coordinate() {
        this.x = 0;
        this.y = 0;
    }
    public Coordinate(Coordinate c) {
        this.x = c.x;
        this.y = c.y;
    }
}
