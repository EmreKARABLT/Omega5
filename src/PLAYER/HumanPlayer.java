package PLAYER;


public class HumanPlayer extends Player {

    public HumanPlayer(String playerName){
        this.playerName = playerName;
        this.playerID = counterForIDs % 2 ;
        counterForIDs++;
        
    }
    @Override
    public boolean isBot() { return false; }
}
