package GAME;

import java.util.ArrayList;

public class Table {
    
    private Chairs nextPlayer = null;
    private Chairs previousPlayer = null;


    public Table(ArrayList<String> players){
        for (int i = 0; i < players.size(); i++) {
            for (int j = 0; j < players.size(); j++) {
                addChair(new Player(players.get(i) ,i+1 , j+1));
            }
        }
    }

    public void addChair(Player player) {
        
        Chairs chair = new Chairs(player);
    
        if (nextPlayer == null)
            nextPlayer = chair;
        else
            previousPlayer.setNextChairs(chair);

    
        previousPlayer = chair;
        previousPlayer.setNextChairs(nextPlayer);

    }

    public Chairs traverseTable(int i) {
        Chairs currentNode = nextPlayer;
        int counter = 0;
        if (nextPlayer != null) {
            do {
                System.out.println(currentNode.getPlayer().getTurn() + " " + currentNode.getPlayer().getPlayerID());
                currentNode = currentNode.getNextChairs();
                counter++;
            } while (counter <= i);
        }
        return currentNode;
    }

    public static void main(String[] args) {
        System.out.println();
        ArrayList<String> b = new ArrayList<>();
        b.add("GA");
        b.add("human");
        b.add("NN");
        b.add("MT");
        Table a = new Table(b);
        a.traverseTable(17);


    }

}
