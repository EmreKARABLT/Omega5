package GAME;

import java.util.LinkedList;

public class Cell implements Cloneable{
    private static int counter = 0;
    private int q , r , s , id ; // indices / coordinates
    private int color ; // -1:empty 0:white 1:black 2:red 3:blue
    private boolean visited ; // will be used to count groups
    private LinkedList<Cell> neighbors ;
    private double x,y ; // coordinates in GUI
    public static double RADIUS = 30;

    public Cell(int q , int r , int s , int id){
        this.neighbors = new LinkedList<>();
        this.color = -1 ;
        this.visited = false ;
        this.q = q;
        this.r = r;
        this.s = s;
        this.id = id;
    }
    public Cell(Cell cell){
        this.neighbors = new LinkedList<>();
        this.color = cell.getColor();
        this.visited = cell.visited;
        this.q = cell.q;
        this.r = cell.r;
        this.s = cell.s;
        this.id = cell.id;
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

    public boolean isEmpty(){return color == -1 ;}

    public int getQ() {return q;}

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

    public boolean setColor(int color) {
        this.color = color;
        return this.color==color;
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

    public void reset(){
        this.visited = false;
        this.color =-1;
    }
    @Override
    public String toString() {
        return ""+color +" ";
    }

    @Override
    public Cell clone() {
        try {
            Cell clone = (Cell) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
