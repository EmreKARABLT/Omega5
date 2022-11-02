package GAME;

import PLAYER.Player;


import java.util.ArrayList;

public class State{
    private Board board;
    private int numberOfPlayers ;
//    private Table table;
    private int numberOfAIPlayers = 0;
    private boolean isGameOver;
    private Player currentPlayer ;
    private int currentColor ;
    private ArrayList<Player> players = new ArrayList<>();

    /**
     * Creates the board with given board size and as many players as desired
     *
     * @param board_size desired board size (3, 5 ,7)
     */
    public State(int board_size, ArrayList<Player> playersList) {
        this.numberOfPlayers = playersList.size();
        players = playersList;

        this.currentPlayer = players.get(0);
        this.currentColor = 0 ;
//        this.table = new Table(players);
        this.board = new Board(board_size);
        this.isGameOver = true;

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

    public boolean isGameOver() {
        return currentPlayer.getPlayerID() == 0 && currentColor == 0 && board.getNumberOfEmptyCells() < numberOfPlayers * numberOfPlayers;
    }
    public void setGameOver(boolean isGameOver){
        this.isGameOver = isGameOver;
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
        return (players.get(0).getScore()>players.get(1).getScore()) ? players.get(0) : players.get(1);
    }
    public void restart(){

        for (Player player: players) {
            player.reset();
        }
        currentColor = 0 ;
        currentPlayer = players.get(0);
        board.clearBoard();
    }

}
