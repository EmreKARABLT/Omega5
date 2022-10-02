package GAME;

import PLAYER.HumanPlayer;
import PLAYER.Player;

import java.util.ArrayList;

public class State {
    private Board board ;
    private ArrayList<Player> players ;
    private int numberOfPlayers = 2 ;
    private Table table ;
    private int numberOfAIPlayers =  0;
    private boolean isRunning ;
    /**
     * Creates the board with given board size and as many players as desired
     * @param board_size desired board size (3, 5 ,7)
     */
    public State(int board_size, ArrayList<String> playersList) {

        this.table = new Table(playersList);

        this.board = new Board(board_size);
        this.isRunning = true ;
    }

    public boolean isEnoughSpaces(){
        return board.getNumberOfEmptyCells() >= numberOfPlayers * numberOfPlayers;
    }
    /**
     * Creates the board with given board size and 2 human players as default
     * @param board_size desired board size
     */
    public State(int board_size ){
        this.board = new Board(board_size);
        for (int i = 1; i <= 2; i++) {
            players.add( new HumanPlayer(i) );
        }
    }

    /**
     * Checks if there is a cell at given coordinates ( will be used for mouse-listeners )
     * @param x x-coordinate on screen
     * @param y y-coordinate on screen
     * @return returns to the cell corresponding to the given coordinates
     * (LEA)
     */
//    private Cell isPieceInBoard(double x , double y){
//        if(true){
//            return new Cell(0,0,0,0);
//        }
//        return null;
//    }

    /**
     * Checks if the cell is available for a player to place a piece
     * @param cell a cell
     * @return true if the cell is coloured
     */
    public boolean isCellColoured(Cell cell){
        return (cell.getColor()) != 0 ;
    }
    /**
     * At the end of each round this method checks if there are enough cells for every player to play
     * @return true if there is no enough space for everyone else false
     */
    public boolean isGameOver(){
        return false;
    }
    public void setPlayersScore(int color_id , int score){
        players.stream().filter(player -> player.getPieceColor() == color_id).toList().get(0).setScore(score);
    }
    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
    public Player getPlayerWithColor(int color){
        if(color == 0 && color > numberOfPlayers)
            return null;
        return players.stream().filter(player -> player.getPlayersColor()==color).toList().get(0);
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public int getNumberOfAIPlayers() {
        return numberOfAIPlayers;
    }

    public void setNumberOfAIPlayers(int numberOfAIPlayers) {
        this.numberOfAIPlayers = numberOfAIPlayers;
    }
}
