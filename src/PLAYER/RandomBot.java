package PLAYER;

import GAME.Cell;
import GAME.State;

import java.util.ArrayList;

public class RandomBot extends Player{

    public RandomBot(String playerName){
        this.playerName = playerName;
        this.playerID = counterForIDs % 2;
        counterForIDs++;
    }
    @Override
    public boolean isBot() {
        return true;
    }

    public ArrayList<Cell> getMoves(State state){
        ArrayList<Cell> move = new ArrayList<>();
        ArrayList<Cell> emptyCells = state.getBoard().getEmptyCells();

        int r1 = (int) (Math.random() * emptyCells.size());
        Cell cell1 = emptyCells.remove(r1);

        int r2 = (int) (Math.random() * emptyCells.size());
        Cell cell2 = emptyCells.remove(r2);

        move.add(cell1);
        move.add(cell2);

        return move;
    };

}
