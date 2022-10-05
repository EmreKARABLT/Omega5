package PLAYER;


public class HumanPlayer implements Player {

    private final String playerName;
    private int currentPiecesID = 0 ;
    private int score = 0 ;
    private final int playerID;

    public HumanPlayer(String playerName, int playerID){
        this.playerName = playerName;
        this.playerID = playerID;
        
    }
    @Override
    public int getCurrentPieceID() {
        return currentPiecesID;
    }

    @Override
    public boolean isBot() { return "human".equalsIgnoreCase(playerName); }


    @Override
    public void setCurrentPieceID(int currentPieceID) {
        this.currentPiecesID = currentPieceID;
    }

    @Override
    public int getPlayerID() {
        return playerID;
    }

    @Override
    public String getPlayerName() { return playerName; }

    @Override
    public int getScore() { return score; }

    @Override
    public void setScore(int score) { this.score = score; }
}
