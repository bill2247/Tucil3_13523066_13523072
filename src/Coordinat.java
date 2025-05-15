public class Coordinat {
    public int x;
    public int y;
    public Coordinat(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Coordinat() {
        this.x = 0;
        this.y = 0;
    }
    public Coordinat(Coordinat c) {
        this.x = c.x;
        this.y = c.y;
    }
}
