public class Coordinate {
    public int r;
    public int c;
    public Coordinate(int r, int c) {
        this.r = r;
        this.c = c;
    }
    public Coordinate() {
        this.r = 0;
        this.c = 0;
    }
    public Coordinate(Coordinate c) {
        this.r = c.r;
        this.c = c.c;
    }
}
