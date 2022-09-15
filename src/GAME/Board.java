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
    private void createCells(){}

    /**
     * iterates over the cells in the board and creates neigborship ,( by using addNeighbor method from Cell class)
     */
    private void connectCells(){}
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
    /**
     *
     */






}
