package UI;

public class HexCoord {

    private int X;
    private int Y;

    public HexCoord(int X, int Y){
        this.X = X;
        this.Y = Y;
    }

    public int getX() {
        return X;
    }
    public int getY() {
        return Y;
    }
    public void setX(int x) {
        X = x;
    }
    public void setY(int y) {
        Y = y;
    }

    @Override
    public String toString() {
        return "Coordinates: " + X + " || " + Y;
    }
}
