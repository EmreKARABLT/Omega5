package GAME;

public class Chairs {
    
    private Player player;
    private Chairs nextChairs;
    
    public Chairs(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Chairs getNextChairs() {
        return nextChairs;
    }

    public void setNextChairs(Chairs nextChairs) {
        this.nextChairs = nextChairs;
    }
}
