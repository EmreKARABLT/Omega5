package GAME;

import java.util.LinkedList;

public class Cell {
    private int q , r , s , id ; // indices / coordinates
    private int color ; // 0:empty 1:white 2:black 3:red 4:blue
    private boolean visited ; // will be used to count groups
    private LinkedList<Cell> neighbors ;
    private double x,y ; // coordinates in GUI
    public static final double RADIUS = 30;

    public Cell(int q , int r , int s , int id){
        this.neighbors = new LinkedList<>();
        this.color = 0 ;
        this.visited = false ;
        this.q = q;
        this.r = r;
        this.s = s;
        this.id = id;
        cellToPixel();
    }

    /**
     * Calculates the coordinates of a cell on screen by using q, r and s/id
     * LEA
     */
    private void cellToPixel(){

    }
    /**
     * Checks if the provided cell is neighbor of the current cell
     * @param cell a cell
     * @return true if 2 cells are neighbors else returns false
     */
    public boolean isNeighbors(Cell cell){
        return this.neighbors.contains(cell);
    }

    /**
     * Adds a cell to neighbor list of the given cell
     * @param cell the cell which will be added to neighbor list
     */
    public void addNeighbor(Cell cell){
        if( neighbors != null){
            if( !neighbors.contains(cell))
                neighbors.add(cell);
        }
    }


    public int getQ() {
        return q;
    }

    public void setQ(int q) {
        this.q = q;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getS() {
        return s;
    }

    public void setS(int s) {
        this.s = s;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public LinkedList<Cell> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(LinkedList<Cell> neighbors) {
        this.neighbors = neighbors;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getRADIUS() {
        return RADIUS;
    }


    public int[] getQRSasArray(){
        return new int[]{ q, r, s };
    }

    @Override
    public String toString() {
        return  q + " " + r + " " + s;
    }
}
