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
    private LinkedList<int[]> setOfQRS;
    private HashMap<HexCoord, Hex> mapOfHex;
    private final int [][] QRS_SYSTEM  = {{1,-1,0}, {0,-1,1}, {-1,0,1}, {-1,1,0}, {0,1,-1}, {1,0,-1}};
    private final int [][] QRS_SYSTEM2 = {{-1,0,1}, {-1,1,0}, {0,1,-1}, {1,0,-1}, {1,-1,0}, {0,-1,1}};



    private final double PI = Math.PI;
    private final double RADIUS;
    private final HexCoord SCREEN_CENTER;
    public final int TOTAL_OF_HEX;
    private int radius;
    private final int LADOS = 6;
    private final int WIDTH;
    private final int HEIGHT;


    public Grid(int width, int heigth, int radius){
        HEIGHT = heigth;
        WIDTH = width;
        this.radius = radius;
        this.RADIUS = 25.5;
        this.SCREEN_CENTER = new HexCoord((width) / 2.d, (heigth) / 2.d);
        this.TOTAL_OF_HEX = calculateTotal();

        this.setOfCoordinates = createCoordinates(radius);
        setOfQRS = createQRS(setOfCoordinates);
        this.setOfHexagons = createHex(3);
        this.mapOfHex = mappingHexagons();
        setPreferredSize(new Dimension(width, heigth));
        this.setOfPolygons = createPoly();

    }

    public Grid(int radius){
        Board board = new Board(radius);

        this.HEIGHT = 1000;
        this.WIDTH = 1000;
        this.SCREEN_CENTER = new HexCoord((WIDTH) / 2.d, (HEIGHT) / 2.d);
        this.RADIUS = 25.5;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        ArrayList<Cell> setOfCells = board.getCells();
        this.TOTAL_OF_HEX = setOfCells.size();
        this.setOfCoordinates = createCoordinates(radius);
        setOfQRS = createQRS(setOfCoordinates);
        this.setOfHexagons = createHex(3);
        fromCellsToCoord(setOfCells);
        this.setOfPolygons = createPoly();
        this.mapOfHex = mappingHexagons();

    }

    public Grid(LinkedList<HexCoord> coordinates, int width, int heigth, int radius){
        HEIGHT = heigth;
        WIDTH = width;
        this.radius = radius;
        this.RADIUS = 26;
        this.SCREEN_CENTER = new HexCoord((width+100) / 2.d, heigth / 2.d);
        this.TOTAL_OF_HEX = calculateTotal();
        this.setOfCoordinates = coordinates;
        this.setOfHexagons = createHex(3);
        setPreferredSize(new Dimension(width, heigth));
        this.setOfPolygons = createPoly();


    }

    public void fromCellsToCoord(ArrayList<Cell> setOfCells){
        for (int i = 0; i < setOfCells.size(); i++) {
            int[] qrs = setOfCells.get(i).getQRSasArray();
            if(qrs[0] == setOfQRS.get(i)[0] || qrs[1] == setOfQRS.get(i)[1] || qrs[2] == setOfQRS.get(i)[2]){
                setOfCells.get(i).setX(setOfCoordinates.get(i).getX());
                setOfCells.get(i).setY(setOfCoordinates.get(i).getY());
            }

        }
    }

    public void createKDTree(LinkedList<HexCoord> coordinates){}

    public HashMap<HexCoord, Hex> mappingHexagons(){
        HashMap<HexCoord, Hex> myMap = new HashMap<>();
        for (int i = 0; i < setOfCoordinates.size(); i++) {
            myMap.put(setOfCoordinates.get(i), setOfHexagons.get(i));
            System.out.println(setOfCoordinates.get(i).toString());
        }
        return myMap;
    }


    public LinkedList<int[]> createQRS(LinkedList<HexCoord> coord){

        LinkedList<int[]> QRS = new LinkedList<>();
        for (int i = 0; i < coord.size(); i++) {
            QRS.add(coord.get(i).getQRS());
        }
        return QRS;

    }
    public LinkedList<HexCoord> createCoordinates(int radius){

        LinkedList<HexCoord> setOfCoordinates = new LinkedList<>();
        setOfCoordinates.add(SCREEN_CENTER);
        setOfCoordinates.get(0).putQRS(0, 0, 0);
        int times = 2;

        for (int i = 1; i < radius + 1; i++) {

            for (int j = 0; j < LADOS; j++) {

                double x = (SCREEN_CENTER.getX() + (2 * i) * RADIUS * Math.cos(j * 2 * PI / (LADOS) + (PI / LADOS)));
                double y = (SCREEN_CENTER.getY() + (2 * i) * RADIUS * Math.sin(j * 2 * PI / (LADOS) + (PI / LADOS)));

                HexCoord coordenadas = new HexCoord(x, y);
                coordenadas.putQRS(i * QRS_SYSTEM[j][0], i * QRS_SYSTEM[j][1], i * QRS_SYSTEM[j][2]);
                setOfCoordinates.add(coordenadas);

                if(i >= 2){

                    int desviation = j + 3;

                    for (int k = 1; k < times; k++) {

                        double Xnew = x;
                        double Ynew = y;
                        int[] c = coordenadas.getQRS();

                        x = (Xnew + (2) * RADIUS * Math.cos(desviation * 2 * PI / (LADOS) - (PI / LADOS)));
                        y = (Ynew + (2) * RADIUS * Math.sin(desviation * 2 * PI / (LADOS) - (PI / LADOS)));
                        coordenadas = new HexCoord(x, y);
                        coordenadas.putQRS(QRS_SYSTEM2[j][0] + c[0], QRS_SYSTEM2[j][1] + c[1], QRS_SYSTEM2[j][2] + c[2]);
                        setOfCoordinates.add(coordenadas);
                    }
                }

            }

            times++;
        }
        return setOfCoordinates;
    }

    public LinkedList<Polygon> createPoly(){

        LinkedList<Polygon> setOfPolygons = new LinkedList<>();

        for (int i = 0; i < setOfCoordinates.size(); i++) {
            setOfPolygons.add(setOfHexagons.get(i).getPolygon());
        }

        return setOfPolygons;
    }

    public LinkedList<Hex> createHex(int color){

        LinkedList<Hex> setOfHexagons = new LinkedList<>();

        for (int i = 0; i < setOfCoordinates.size(); i++) {

            Hex hex = new Hex(setOfCoordinates.get(i).getX(),setOfCoordinates.get(i).getY(), color);
            hex.setQRS(setOfCoordinates.get(i).getQRS());
            setOfHexagons.add(hex);
        }

        return setOfHexagons;
    }

    public int calculateTotal(){
        int total = 1;

        for (int i = 1; i <= radius; i++) {
            total += LADOS * i;
        }

        return total;
    }


    public double calculateRadius(){
        double r = 0;

        if (WIDTH > HEIGHT) {
            r = (WIDTH - 200) / (radius * 2);
        }else{
            r = (HEIGHT - 200) / (radius * 2);
        }
        return r;
    }

    @Override
    public void paintComponent(Graphics g){

        super.paintComponent(g);

        for (int i = 0; i < setOfHexagons.size(); i++) {
            g.setColor(setOfHexagons.get(i).getColor());
            g.fillPolygon(setOfHexagons.get(i).getPolygon());
            g.setColor(Color.BLACK);
            g.drawPolygon(setOfHexagons.get(i).getPolygon());

            //g.drawString(setOfHexagons.get(i).toString(), (int)setOfHexagons.get(i).getCoordinates().getX(), (int)setOfHexagons.get(i).getCoordinates().getY());
        }

        repaint(1);

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
    public void setSetOfHexagons(LinkedList<Hex> setOfHexagons) {
        this.setOfHexagons = setOfHexagons;
        updateUI();
    }
    public LinkedList<Hex> getSetOfHexagons() {
        return setOfHexagons;
    }
    public void setSetOfPolygons(LinkedList<Polygon> setOfPolygons) {
        this.setOfPolygons = setOfPolygons;
    }
    public LinkedList<Polygon> getSetOfPolygons() {
        return setOfPolygons;
    }
    public void setMapOfHex(HashMap<HexCoord, Hex> mapOfHex) {
        this.mapOfHex = mapOfHex;
    }
    public HashMap<HexCoord, Hex> getMapOfHex() {
        return mapOfHex;
    }
}

