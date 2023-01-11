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
        ArrayList<Cell> moves = new ArrayList<>();
        ArrayList<Cell> emptyCells = state.getBoard().getEmptyCells();

        int r1 = (int) (Math.random() * emptyCells.size());
        Cell cell1 = null;
        try {
            cell1 = emptyCells.remove(r1);
        }catch (Exception e){
            System.out.println();
        }

        int r2 = (int) (Math.random() * emptyCells.size());
        Cell cell2 = emptyCells.remove(r2);

        moves.add(cell1);
        moves.add(cell2);

        cell1.setColor(0);
        cell2.setColor(1);
        state.addWhite(cell1);
        state.addBlack(cell2);
        state.getPlayers().get(0).setScore(state.getBoard().scoreOfAPlayer(0));
        state.getPlayers().get(1).setScore(state.getBoard().scoreOfAPlayer(1));
        state.nextTurn();
        state.nextTurn();
        return moves;
    };

    public ArrayList<Cell> randomMoves(State state){
        ArrayList<Cell> moves = new ArrayList<>();
        ArrayList<Cell> emptyCells = state.getBoard().getEmptyCells();

        int r1 = (int) (Math.random() * emptyCells.size());
        Cell cell1 = null;
        try {
            cell1 = emptyCells.remove(r1);
        }catch (Exception e){
            System.out.println();
        }

        int r2 = (int) (Math.random() * emptyCells.size());
        Cell cell2 = emptyCells.remove(r2);

        moves.add(cell1);
        moves.add(cell2);

        return moves;
    }


    public String getName() {
        return "Random";
    }
}
