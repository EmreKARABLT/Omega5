package UI;
public class HexCoord {

    private double X;
    private double Y;
    private int[] QRS = new int[3];

    public HexCoord(double X, double Y){
        this.X = X;
        this.Y = Y;
    }

    public double getX() {
        return X;
    }
    public double getY() {
        return Y;
    }
    public void setX(double x) {
        X = x;
    }
    public void setY(double y) {
        Y = y;
    }
    public void putQRS(int q, int r, int s){
        QRS[0] = q;
        QRS[1] = r;
        QRS[2] = s;
    }
    public void setQRS(int[] qRS) {
        QRS = qRS;
    }
    public int[] getQRS() {
        return QRS;
    }

    @Override
    public String toString() {
        return "Coordinates: " + X + " || " + Y + " _(" + QRS[0] + ", " + QRS[1] + ", " +QRS[2] + ")" ;
    }

    public String toQRS() {
        return "(" + QRS[0] + ", " + QRS[1] + ", " +QRS[2] + ")" ;
    }


}
