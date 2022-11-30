package PLAYER.MTS;

import GAME.Board;
import GAME.Cell;
import GAME.State;
import PLAYER.MTS.MonteCarloTreeSearch;
import PLAYER.MTS.SELECTION_HEURISTICS.Heuristics;
import PLAYER.Player;

import java.util.ArrayList;

public class MonteCarlo extends Player {


    MonteCarloTreeSearch montecarlo;

//    public MonteCarlo(String playerName){
//        this.playerName = playerName;
//        if(playerName.contains("Black") || playerName.contains("black"))
//            this.playerID = 1  ;
//        else
//            this.playerID = 0  ;
//
//    }
    public MonteCarlo(String playerName, Heuristics heuristics){
        this.heuristics = heuristics;
        this.playerName = playerName;
        this.heuristicName = heuristics.getName();
        if(playerName.contains("Black") || playerName.contains("black"))
            this.playerID = 1  ;
        else
            this.playerID = 0  ;

    }

    @Override
    public boolean isBot(){return true;};

    public ArrayList<Cell> getMoves(State state){

        if(montecarlo == null){
            montecarlo = new MonteCarloTreeSearch(state , heuristics);
            return montecarlo.bestMove(state);
        }

        return montecarlo.bestMove(state);

    }
}
