package UI;


import GAME.Cell;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Hex extends JPanel {

    private Color color;
    private final Color EMPTY_CELL_COLOR = new Color(232,201,116);
    private double radius ;
    private Polygon polygon;
    private final Cell cell ;


    public Hex(Cell cell) {
        this.cell = cell;
        this.radius = cell.getRADIUS();
        this.polygon = createPolygon();
        changeColor(0 );
    }


    /**
     * Create the shape of hexagon using 6 points shifted by a certain degree
     *
     * @return a new Polygon object
     */

    public Polygon createPolygon() {

        Polygon p = new Polygon();

        for (int i = 0; i < 6; i++) {
            p.addPoint(
                    (int) (cell.getX() + Cell.RADIUS * Math.cos(i * 2 * Math.PI / 6)),
                    (int) (cell.getY() + Cell.RADIUS * Math.sin(i * 2 * Math.PI / 6))
            );
        }
        return p;
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.setColor(getColor());
        g.fillPolygon(getPolygon());
        g.setColor(Color.BLACK);
        g.drawPolygon(getPolygon());
        repaint(1);
    }

    public void changeColor(int colorID) {

        if (colorID == 1) {
            this.cell.setColor(colorID);
            this.color = Color.WHITE;
        }
        if (colorID == 2) {
            this.cell.setColor(colorID);
            this.color = Color.BLACK;
        }
        if (colorID == 3) {
            this.cell.setColor(colorID);
            this.color = Color.RED;
        }
        if (colorID == 4) {
            this.cell.setColor(colorID);
            this.color = Color.BLUE;
        }
        if (colorID == 0) {
            this.cell.setColor(colorID);
            this.color = EMPTY_CELL_COLOR ;
        }
    }

//    public int calculateDistanceToCell(Cell cell) {
//
//        double X1 = this.cell.getX();
//        double Y1 = this.cell.getY();
//
//        double X2 = cell.getX();
//        double Y2 = cell.getY();
//
//        return (int) Math.sqrt((X2 - X1) * (X2 - X1) + (Y2 - Y1) * (Y2 - Y1));
//    }

    public void setColor(Color color) {

    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Color getColor() {
        return color;
    }

    public int getColorID() {

        if (this.color == Color.WHITE) {
            return 1;
        }
        if (this.color == Color.BLACK) {
            return 2;
        }
        if (this.color == Color.RED) {
            return 3;
        }
        if (this.color == Color.BLUE) {
            return 4;
        }
        if (this.color == EMPTY_CELL_COLOR ) {
            return 0;
        }
        return -1;
    }


    public double getRadius() {
        return radius;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public int[] getQRS() {
        return cell.getQRSasArray();
    }

    @Override
    public String toString() {

        return "(" + Arrays.toString(cell.getQRSasArray()) + ")";
    }


}