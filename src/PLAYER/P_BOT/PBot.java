package PLAYER.P_BOT;

import GAME.Cell;
import GAME.State;
import PLAYER.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class PBot extends Player {

    public PBot(String playerName){
        this.playerName = playerName;
        if(playerName.contains("Black") || playerName.contains("black"))
            this.playerID = 1  ;
        else
            this.playerID = 0  ;

    }
    @Override
    public boolean isBot() {
        return true;
    }

    ArrayList<Integer> value = new ArrayList<>();
    HashMap<Integer, Cell> hashMap= new HashMap<>();

    public ArrayList<Cell> getMoves(State state){
        ArrayList<Cell> move = new ArrayList<>();
        ArrayList<Cell> emptyCells = state.getBoard().getEmptyCells();

        for (int i = 0; i < emptyCells.size(); i++) {
            emptyCells.get(i).setColor(0);
            value.add(state.getBoard().scoreOfAPlayer(0));
            hashMap.put(state.getBoard().scoreOfAPlayer(0), emptyCells.get(i));
            emptyCells.get(i).setColor(1);
            value.add(state.getBoard().scoreOfAPlayer(1));
            hashMap.put(state.getBoard().scoreOfAPlayer(0), emptyCells.get(i));
            emptyCells.get(i).setColor(-1);
        }

        move.add(hashMap.get(Collections.min(value)));
        move.add(hashMap.get(Collections.max(value)));

        return move;
    };
}
