package PLAYER;


public class HumanPlayer extends Player {

    public HumanPlayer(String playerName){
        this.playerName = playerName;
        if(playerName.contains("Black") || playerName.contains("black"))
            this.playerID = 1  ;
        else
            this.playerID = 0  ;
        
    }
    @Override
    public boolean isBot() { return false; }
}
