package PLAYER.RULE_BASED_BOT;

import GAME.Board;
import GAME.Cell;
import GAME.State;
import PLAYER.Player;

import java.util.*;

public class RuleBasedBot extends Player {


    public RuleBasedBot(String playerName){
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

        if(playerName.contains("Black") || playerName.contains("black")){
            move.add(a[0]);
            move.add(a[1]);

            a[0].setColor(0);
            a[1].setColor(1);
            state.addWhite(a[0]);
            state.addBlack(a[1]);
        }else{
            move.add(a[1]);
            move.add(a[0]);

            a[1].setColor(0);
            a[0].setColor(1);
            state.addWhite(a[1]);
            state.addBlack(a[0]);
        }

        state.getPlayers().get(0).setScore(state.getBoard().scoreOfAPlayer(0));
        state.getPlayers().get(1).setScore(state.getBoard().scoreOfAPlayer(1));
        state.nextTurn();
        state.nextTurn();

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
                    Rules.neigbourColors(emptyCells.get(i)) +
                    Rules.radius(emptyCells.get(i)) +
                    Rules.N_neibourgs(emptyCells.get(i));


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
