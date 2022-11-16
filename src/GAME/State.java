package GAME;

import PLAYER.Player;
import UI.Grid;


import java.util.ArrayList;
import java.util.Objects;

public class State implements Cloneable{
    private int id ;
    private static int idCounter = 0;
    private Board board;
    private int numberOfPlayers ;
    private int numberOfAIPlayers = 0;
    private Player currentPlayer ;
    private int currentColor ;
    private ArrayList<Player> players = new ArrayList<>();
    int boardSize;
    private ArrayList<Cell> whites = new ArrayList<>();
    private ArrayList<Cell> blacks = new ArrayList<>();


    /**
     * Creates the board with given board size and as many players as desired
     *
     * @param board_size desired board size (3, 5 ,7)
     */
    public State(int board_size, ArrayList<Player> playersList) {
        this.boardSize = board_size;

        this.numberOfPlayers = playersList.size();
        players = playersList;
        id = idCounter++;
        this.currentPlayer = players.get(0);
        this.currentColor = 0 ;
//        this.table = new Table(players);
        this.board = new Board(board_size);
        restart();
//        if(playersList.get(0).isBot() && playersList.get(1).isBot()){
//            gameLoop();
//        }


    }

    public void resetBoard(){
        this.board = new Board(boardSize);
    }

//    public void gameLoop(){
//
//            while (!isGameOver()){
//                ArrayList<Cell> moves = getCurrentPlayer().getMoves(this);
//
//                moves.get(0).setColor(0);
//                moves.get(1).setColor(1);
//                this.nextTurn();
//                this.nextTurn();
//            }
//                this.getPlayers().get(0).setScore(this.getBoard().scoreOfAPlayer(0));
//                this.getPlayers().get(1).setScore(this.getBoard().scoreOfAPlayer(1));
//
//            resetBoard();
//
//    }
    public State(State state) {
        this.numberOfPlayers = state.getPlayers().size();
        players = state.getPlayers();
        id = idCounter++;
        this.currentPlayer = state.getCurrentPlayer();
        this.currentColor = state.getCurrentColor();
        this.board = new Board(state.getBoard());
    }
    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public int getCurrentColor(){
        return currentPlayer.getCurrentPieceID();
    }

    public void nextTurn(){
        currentColor = (currentColor + 1) % numberOfPlayers;
        int nextColor = currentColor;
        currentPlayer.setCurrentPieceID(nextColor);
        if(nextColor == 0 ){
            currentPlayer = players.get( (currentPlayer.getPlayerID()+1) % numberOfPlayers);
        }
    }
    public void previousTurn(){
        currentColor = Math.abs(currentColor - 1);
        int previousColor = currentColor;
        if(previousColor == 1)
            currentPlayer = players.get( Math.abs(currentPlayer.getPlayerID() - 1));
        currentPlayer.setCurrentPieceID(previousColor);
    }
    public void updatePlayerScores(){
        players.get(0).setScore(board.scoreOfAPlayer(0));
        players.get(1).setScore(board.scoreOfAPlayer(1));
    }

    public boolean isGameOver() {
        return board.getNumberOfEmptyCells() <= board.getCells().size() % (numberOfPlayers * numberOfPlayers);
    }
    public void colorWhite(Cell cell){
        board.getCells().get(cell.getId()).setColor(0);
    }
    public void colorBlack(Cell cell){
        board.getCells().get(cell.getId()).setColor(1);
    }
    public void uncolor(Cell cell){
        board.getCells().get(cell.getId()).setColor(-1);
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getNumberOfAIPlayers() {
        return numberOfAIPlayers;
    }

    public void setNumberOfAIPlayers(int numberOfAIPlayers) {
        this.numberOfAIPlayers = numberOfAIPlayers;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
    public Player getWinner(){
        if(players.get(0).getScore() == players.get(1).getScore())
            return null;
        return (players.get(0).getScore()>players.get(1).getScore()) ? players.get(0) : players.get(1);
    }
    public Player getLoser(){
        if(players.get(0).getScore() == players.get(1).getScore())
            return null;
        return (players.get(1).getScore()<players.get(0).getScore()) ? players.get(1) : players.get(0);
    }
    public void addBlack(Cell black){
        blacks.add(black);
    }
    public void addWhite(Cell white){
        whites.add(white);
    }

    public ArrayList<Cell> getBlacks() {
        return blacks;
    }

    public ArrayList<Cell> getWhites() {
        return whites;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void restart(){

        for (Player player: players) {
            player.reset();
        }
        currentColor = 0 ;
        currentPlayer = players.get(0);
        board.clearBoard();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;
        State state = (State) o;
        return Objects.equals(board, state.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(board);
    }

    @Override
    public State clone() {
        try {
            State clone = (State) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String toString() {
        return id + "";
    }
}
