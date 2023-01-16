package PLAYER.RULE_BASED_BOT;

import GAME.Board;
import GAME.Cell;
import GAME.State;
import PLAYER.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class GeneticRuleBasedBot extends Player {

    public double[] w = {0.49729562743801226, 0.8678861928805042, 0.8745639482995891, 0.9787807452781794, 0.9397878224169762};

    public GeneticRuleBasedBot(String playerName){
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
        Cell[] a = evaluation(state);

        if(playerName.equals("Black")){
            move.add(a[0]);
            move.add(a[1]);
        }else{
            move.add(a[1]);
            move.add(a[0]);
        }


        return move;
    };

    public Cell[] evaluation(State state){
        Board board = state.getBoard();
        ArrayList<Cell> emptyCells = board.getEmptyCells();
        ArrayList<Double> coeficients = new ArrayList<>();
        HashMap<Double, Cell> hash = new HashMap<>();

        for (int i = 0; i < emptyCells.size(); i++) {

            Double formula =

                    //this.getW()[0] * Rules.clusters(emptyCells.get(i), 1) +
                    //this.getW()[1] * Rules.Nclusters(emptyCells.get(i), 1, board.getCells()) +
                            w[2] * Rules.neigbourColors(emptyCells.get(i)) +
                            w[3] * Rules.radius(emptyCells.get(i)) +
                            w[4] * Rules.N_neibourgs(emptyCells.get(i));


            coeficients.add(formula);

            hash.put(formula, emptyCells.get(i));
        }

        state.getBoard().setAllCellsToNotVisited();

        return new Cell[] {hash.get(Collections.max(coeficients)), hash.get(Collections.min(coeficients))};

    }
    public void setPlayerName(String PlayerName){
        this.playerName = playerName;
        if(playerName.contains("Black") || playerName.contains("black"))
            this.playerID = 1  ;
        else
            this.playerID = 0  ;
    }
}
