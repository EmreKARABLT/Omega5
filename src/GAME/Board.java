package GAME;

import java.util.ArrayList;
import java.util.LinkedList;

public class Board {
    private int boardSize ;
    private ArrayList<Cell> cells ;
    
    public Board( int boardSize){
        /*
        counting
        finding a cell in given coordinates
        checking neighbors
        copying the whole board for searching algorithms

         */
        this.cells = new ArrayList<>();
        this.boardSize = boardSize;
        createBoard();
    }

    /**
     * creates all the cells in the board , assign coordinates on GUI (x,y) and q r s variables
     */
    private void createCells(){
        int n = boardSize;
        int id = 0 ;
        for (int q = -n; q <= n ; q++) {
            int rMax = Math.max(-n , -q-n);
            int rMin = Math.min( n , -q+n);
            for( int r = rMax ; r<=rMin ; r++){
                cells.add( new Cell( q, r,-q-r ,id++));
            }

        }
    }

    /**
     * iterates over the cells in the board and creates neigborship ,( by using addNeighbor method from Cell class)
     */
    private void connectCells(){
        if(cells == null){
            System.out.println("Cells list is empty");
            return;
        }
        for (int i = 0; i < cells.size()-1; i++) {
            Cell c1 = cells.get(i);
            for (int j = i+1; j < cells.size(); j++) {
                Cell c2 = cells.get(j);
                int abs_difference = Math.abs(c1.getQ() - c2.getQ()) + Math.abs(c1.getR() - c2.getR()) + Math.abs(c1.getS() - c2.getS());
                if(abs_difference == 2 ){
                    c1.addNeighbor(c2);
                    c2.addNeighbor(c1);
                }
            }
        }


    }
    /**
     * will be called by constructor to create the board
     */
    private void createBoard(){
        createCells();
        connectCells();
    }

    /**
     * Calculates the score of the given color
     * @param color is the color of the player
     * @return the score of the given color
     */
    private int countScores(int color){
        return 0 ;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }

    public void setCells(ArrayList<Cell> cells) {
        this.cells = cells;
    }

    /**
     *
     */

    public static void main(String[] args) {
        Board board = new Board(3);
        for (Cell c : board.getCells()) {
            for (Cell cell: c.getNeighbors()) {
                System.out.println(cell.getQ() +" "+ cell.getR() +" "+ cell.getS() +" "+ cell.getId() );

            }
        }
    }





}
