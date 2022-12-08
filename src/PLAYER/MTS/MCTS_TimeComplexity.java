package PLAYER.MTS;

import GAME.Cell;
import GAME.State;
import PLAYER.MTS.SELECTION_HEURISTICS.Heuristics;
import PLAYER.Player;

import java.util.ArrayList;

public class MCTS_TimeComplexity extends Player {


    Tree tree = null;
    public static double winProb = 0;
    public static int a = 0;
    public MCTS_TimeComplexity(String playerName, Heuristics heuristics){

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

    @Override
    public ArrayList<Cell> getMoves(State state){
        ArrayList<Cell> move = new ArrayList<>();
        if(tree == null ){
            this.tree = new Tree(state, this.heuristics);
        }else
            this.tree.setRoot(state, state.getWhites(), state.getBlacks());

        a = 0;
        int times = 0;

        while (times < 100){
            double start = System.currentTimeMillis();

            tree.run();

            double finish = System.currentTimeMillis();
            a += finish-start;
            times++;
        }

        System.out.print((int)a + ", ");

        Node winner = tree.getBest(tree.root);

        move.add(winner.getWhite());
        move.add(winner.getBlack());
        //System.out.println(winRateCalculator(winner));
        return move;
    }
    public void reset(){
        this.score = 0 ;
        this.currentPiecesID = 0 ;
        this.tree = null;
    }

    public String winRateCalculator(Node winner){
        double rate = 0;
        rate = (winner.getNumberOfWins() /  winner.getNumberOfSimulations()) * 100;
        int decimals = 100000;
        double winRate = (double) Math.round(rate * decimals) / decimals;
        winProb = winRate;
        return  "\n" +
                "[Player = " + winner.getState().getCurrentPlayer().getPlayerName() + " \n" +
                "Heuristic = " + winner.getState().getCurrentPlayer() + " \n" +
                "wins : " +  winner.getNumberOfWins() + " sims: " + winner.getNumberOfSimulations() + " \n" +
                "Win Rate = " + winRate + " %" + "\n" +
                "Your chances of victory = " + (100 - winRate) + " %]";

    }
}
