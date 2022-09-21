package UI;

import GAME.Board;
import GAME.Cell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.JPanel;
import java.awt.*;

public class Grid extends JPanel{


    private LinkedList<HexCoord> setOfCoordinates;
    private LinkedList<Polygon> setOfPolygons;
    private LinkedList<Hex> setOfHexagons;
    private HashMap<HexCoord, Hex> mapOfHex;
    private ArrayList<Cell> setOfCells;
    private final double RADIUS = 25.5;
    private final int WIDTH  = 1000;
    private final int HEIGHT = 1000;

    public Grid(int radius){
        Board board = new Board(radius);
        this.setOfCells = board.getCells();
        this.setOfCoordinates = createCoordinates(setOfCells);

        this.setOfHexagons = createHex(3);
        this.setOfPolygons = createPoly();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    public LinkedList<HexCoord> createCoordinates(ArrayList<Cell> boardCells){
        setOfCoordinates = new LinkedList<>();
        for (Cell cell :
                boardCells) {
            HexCoord coordenadas = new HexCoord(cell.getX(), cell.getY());
            setOfCoordinates.add(coordenadas);
        }
        return setOfCoordinates;
    }

    public LinkedList<Polygon> createPoly(){

        LinkedList<Polygon> setOfPolygons = new LinkedList<>();

        for (int i = 0; i < setOfCells.size(); i++) {
            setOfPolygons.add(setOfHexagons.get(i).getPolygon());
        }

        return setOfPolygons;
    }

    public LinkedList<Hex> createHex(int color){

        LinkedList<Hex> setOfHexagons = new LinkedList<>();

        for (int i = 0; i < setOfCells.size(); i++) {

            Hex hex = new Hex(setOfCells.get(i).getX(),setOfCells.get(i).getY(), color);
            hex.setQRS(setOfCells.get(i).getQRSasArray());
            setOfHexagons.add(hex);
        }

        return setOfHexagons;
    }

    @Override
    public void paintComponent(Graphics g){

        super.paintComponent(g);

        for (int i = 0; i < setOfHexagons.size(); i++) {
            g.setColor(setOfHexagons.get(i).getColor());
            g.fillPolygon(setOfHexagons.get(i).getPolygon());
            g.setColor(Color.BLACK);
            g.drawPolygon(setOfHexagons.get(i).getPolygon());
        }

        for (int i = 0; i < setOfCells.size(); i++) {
            //g.drawString((int)setOfCells.get(i).getX() + " " + (int)setOfCells.get(i).getY(), (int)setOfCells.get(i).getX(), (int)setOfHexagons.get(i).getCoordinates().getY());
            //g.drawString(setOfCells.get(i).toString(), (int)setOfCells.get(i).getX(), (int)setOfHexagons.get(i).getCoordinates().getY());
            //System.out.println(setOfCells.get(i).toString() + " " + (int)setOfCells.get(i).getX() + " " + (int)setOfCells.get(i).getY());
        }

        //repaint(1);

    }

    public Hex getNeearestHexagon2(HexCoord pointerCoordinates){
        return mapOfHex.get(pointerCoordinates);
    }

    public Hex getNeearestHexagon(HexCoord pointerCoordinates){

        double X2 = pointerCoordinates.getX();
        double Y2 = pointerCoordinates.getY();

        for (int i = 0; i < setOfCoordinates.size(); i++) {
            double X1 = setOfCoordinates.get(i).getX();
            double Y1 = setOfCoordinates.get(i).getY();

            int distance = (int) Math.sqrt((X2 - X1) * (X2 - X1) + (Y2 - Y1) * (Y2 - Y1));

            if(distance < RADIUS){
                return setOfHexagons.get(i);
            }
        }

        return setOfHexagons.get(0);
    }


    public LinkedList<HexCoord> getSetOfCoordinates() {
        return setOfCoordinates;
    }

    public void setSetOfCoordinates(LinkedList<HexCoord> setOfCoordinates) {
        this.setOfCoordinates = setOfCoordinates;
    }

    public LinkedList<Polygon> getSetOfPolygons() {
        return setOfPolygons;
    }

    public void setSetOfPolygons(LinkedList<Polygon> setOfPolygons) {
        this.setOfPolygons = setOfPolygons;
    }

    public LinkedList<Hex> getSetOfHexagons() {
        return setOfHexagons;
    }

    public void setSetOfHexagons(LinkedList<Hex> setOfHexagons) {
        this.setOfHexagons = setOfHexagons;
    }

    public HashMap<HexCoord, Hex> getMapOfHex() {
        return mapOfHex;
    }

    public void setMapOfHex(HashMap<HexCoord, Hex> mapOfHex) {
        this.mapOfHex = mapOfHex;
    }

    public ArrayList<Cell> getSetOfCells() {
        return setOfCells;
    }

    public void setSetOfCells(ArrayList<Cell> setOfCells) {
        this.setOfCells = setOfCells;
    }

    public double getRADIUS() {
        return RADIUS;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }
}

