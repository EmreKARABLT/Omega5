package GAME;

import java.util.LinkedList;

public class Cell {
    private int q , r , s ; // indices / coordinates
    private int color ; // 0:empty 1:white 2:black 3:red 4:blue
    private boolean visited ; // will be used to count groups
    private LinkedList<Cell> neighbors ;

    private double x,y ; // coordinates in GUI
    private double offset;

    public Cell(int q , int r , int s){
        this.neighbors = new LinkedList<>();
        this.color = 0 ;
        this.visited = false ;
        this.q = q;
        this.r = r;
        this.s = s;
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

    public double getOffset() {
        return offset;
    }

    public void setOffset(double offset) {
        this.offset = offset;
    }
}
