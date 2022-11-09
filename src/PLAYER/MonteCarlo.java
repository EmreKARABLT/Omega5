package PLAYER;

import GAME.Board;
import GAME.Cell;
import GAME.State;
import PLAYER.MTS.MonteCarloTreeSearch;

import java.util.ArrayList;

public class MonteCarlo extends Player{

    MonteCarloTreeSearch montecarlo;

    public MonteCarlo(String playerName){
        this.playerName = playerName;
        this.playerID = counterForIDs % 2;
        counterForIDs++;

    }

    @Override
    public boolean isBot(){return true;};

    public ArrayList<Cell> getMoves(State state){

        if(montecarlo == null){
            montecarlo = new MonteCarloTreeSearch(state);
            return montecarlo.bestMove(state);
        }

        return montecarlo.bestMove(state);

    }

    public static class RuleBasedBot2 extends Player{

        public RuleBasedBot2(String playerName){
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
            Board board = state.getBoard();

            return move;
        };



    }
}
