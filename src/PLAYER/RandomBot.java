package PLAYER;

import GAME.Cell;
import GAME.State;

import java.util.ArrayList;

public class RandomBot extends Player{

    public RandomBot(String playerName){
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

    public ArrayList<Cell> getMoves(State state){
        ArrayList<Cell> move = new ArrayList<>();
        ArrayList<Cell> emptyCells = state.getBoard().getEmptyCells();

        int r1 = (int) (Math.random() * emptyCells.size());
        Cell cell1 = emptyCells.remove(r1);
        if(emptyCells.isEmpty()){
            System.out.println();
        }
        int r2 = (int) (Math.random() * emptyCells.size());
        Cell cell2 = emptyCells.remove(r2);

        move.add(cell1);
        move.add(cell2);

        return move;
    };


    public String getName() {
        return "Random";
    }
}
