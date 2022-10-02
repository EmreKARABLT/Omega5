package GAME;


public class Player {

    private String playerName;
    private int turn;
    private int playerID;

    public Player(String playerName,int playerID, int turn){
        this.playerName = playerName;
        this.turn = turn;
        this.playerID = playerID;
        
    }

    public int getTurn() {
        return turn;
    }

    public String isBot() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }
}
