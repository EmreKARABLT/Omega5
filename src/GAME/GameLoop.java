package GAME;

import PLAYER.HumanPlayer;
import PLAYER.Player;

import java.util.ArrayList;

public class GameLoop {
    private Board board ;
    private ArrayList<Player> players ;
    private int numberOfPlayerss = 4; //Temporary 
    private int numOfCells = board.getNumberOfCells()+1;
    private int totalTurnCounter = 0; 
    private int currentPlayersTurn = 0; 



    /**
     * Creates the board with given board size and as many players as desired
     * @param board_size desired board size (3, 5 ,7)
     * @param numberOfPlayers number of players
     */
    public GameLoop(int board_size , int numberOfPlayers){
        this.board = new Board(board_size);
        for (int i = 0; i < Math.min( numberOfPlayers , 4 ); i++) {
            players.add(new HumanPlayer(i));
        }
    }

    /**
     * Creates the board with given board size and 2 human players as default
     * @param board_size desired board size
     */
    public GameLoop(int board_size ){
        this.board = new Board(board_size);
        for (int i = 0; i < 2; i++) {
            players.add(new HumanPlayer(i));
        }
    }

    /**
     * Checks if there is a cell at given coordinates ( will be used for mouse-listeners )
     * @param x x-coordinate on screen
     * @param y y-coordinate on screen
     * @return returns to the cell corresponding to the given coordinates
     * (LEA)
     */
    private Cell isPieceInBoard(double x , double y){
        if(true){
            return new Cell(0,0,0,0);
        }
        return Cell
    }

    /**
     * Checks if the cell is available for a player to place a piece
     * @param cell a cell
     * @return true if the cell is coloured
     */
    private boolean isCellColoured(Cell cell){
        return ((cell.getColor()) != 0) ;
    }
    /**
     * At the end of each round this method checks if there are enough cells for every player to play
     * @return true if there is no enough space for everyone else false
     */
    private boolean isGameOver(){
         int totalTurnNum = (int) (numOfCells/Math.pow(numberOfPlayerss, 2)); 

         return (totalTurnCounter == totalTurnNum);

    }

    private void nextTurn(){
        if(currentPlayersTurn != numberOfPlayerss){
            currentPlayersTurn++;
        }
        else{
            currentPlayersTurn=0;
            totalTurnCounter++;
            }

    }



    /**
     * (Can be changed by anyone who will code this method , can be split several methods )
     * Controls game flow, checks which players turn, which color will be placed, updates scores and ends the game when game is over
     */
    private void gameFlow(){
    }
    




}
