package UI;


import java.awt.*;

public class Hex {

    private HexCoord coordinates;
    private Color color ;
    private int radius = 30;
    private Polygon polygon;
    private int[] QRS = new int[3];

    public Hex(double x, double y, int color){

        this.coordinates = new HexCoord(x, y);
        this.polygon = createPolygon();
        QRS = coordinates.getQRS();

        changeColor(color);
    }


    public Polygon createPolygon(){

        Polygon p = new Polygon();

        for (int i = 0; i < 6; i++){
            p.addPoint(
                    (int) (coordinates.getX() + radius * Math.cos(i * 2 * Math.PI / 6)),
                    (int) (coordinates.getY() + radius * Math.sin(i * 2 * Math.PI / 6))
            );
        }
        return p;
    }

    public void changeColor(int colorID) {

        if(colorID == 1){
            this.color = Color.WHITE;
        }
        if(colorID == 2){
            this.color = Color.BLACK;
        }
        if(colorID == 3){
            this.color = Color.RED;
        }
        if(colorID == 4){
            this.color = Color.BLUE;
        }
        if(colorID == 0){
            this.color = Color.DARK_GRAY;
        }
    }

    public int calculateDistance(HexCoord pointerCoordinates){

        double X1 = coordinates.getX();
        double Y1 = coordinates.getY();

        double X2 = pointerCoordinates.getX();
        double Y2 = pointerCoordinates.getY();

        return (int) Math.sqrt((X2 - X1) * (X2 - X1) + (Y2 - Y1) * (Y2 - Y1));
    }

    public void setCoordinates(HexCoord coordinates) {
        this.coordinates = coordinates;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Color getColor() {
        return color;
    }

    public HexCoord getCoordinates() {
        return coordinates;
    }

    public int getRadius() {
        return radius;
    }
    public Polygon getPolygon() {
        return polygon;
    }
    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }
    public int[] getQRS() {
        return QRS;
    }
    public void setQRS(int[] qRS) {
        QRS = qRS;
    }
    @Override
    public String toString() {

        return "(" + QRS[0] + ", " + QRS[1] + ", " +QRS[2] + ")" ;
    }
}
