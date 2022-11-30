package PLAYER.RULE_BASED_BOT;

import GAME.Board;
import GAME.Cell;
import GAME.State;
import PLAYER.Player;

import java.util.*;

public class RuleBasedBot extends Player {


    public RuleBasedBot(String playerName){
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
                    this.getW()[2] * Rules.neigbourColors(emptyCells.get(i)) +
                    this.getW()[3] * Rules.radius(emptyCells.get(i)) +
                    this.getW()[4] * Rules.N_neibourgs(emptyCells.get(i));


            coeficients.add(formula);

            hash.put(formula, emptyCells.get(i));
        }

        state.getBoard().setAllCellsToNotVisited();

        return new Cell[] {hash.get(Collections.max(coeficients)), hash.get(Collections.min(coeficients))};

    }
    @Override
    public String getHeuristicName() {
        return "Rule Based Bot";
    }
}
