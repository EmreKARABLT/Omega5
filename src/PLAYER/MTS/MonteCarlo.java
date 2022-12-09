package PLAYER.MTS;

import GAME.Cell;
import GAME.State;
import PLAYER.MTS.SELECTION_HEURISTICS.Heuristics;
import PLAYER.Player;
import UI.Grid;

import java.util.ArrayList;

public class MonteCarlo extends Player{



    private final double SEARCHTIME = 250;
    private final double MAX_SIMULATION = 1;
    Tree tree = null;
    public static double winProb = 0;

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

    @Override
    public ArrayList<Cell> getMoves(State state){
        ArrayList<Cell> move = new ArrayList<>();
        if(tree == null ){
            this.tree = new Tree(state, this.heuristics);
        }else
            this.tree.setRoot(state, state.getWhites(), state.getBlacks());

        double a = 0;
        double numberOfSim = 0;

        while ( a < SEARCHTIME){
            double start = System.currentTimeMillis();

            tree.simulation();
            numberOfSim++;
            double finish = System.currentTimeMillis();
            a += finish-start;
        }
//        System.out.println("\nEvaluation for " + numberOfSim + " simulations took " + a/Math.pow(10,9) + " seconds");


        Node winner = tree.getBest(tree.root);
        tree.setLastMove(winner);
        move.add(winner.getWhite());
        move.add(winner.getBlack());
//        System.out.println(winRateCalculator(winner));
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
        int numberOfNonRootNodes = (tree.getNumberOfNodesDiscovered()-1);
        int numberOfNonLeafNodes = tree.getNumberOfNodesDiscovered() - tree.getNumberOfLeafNodes();

        double averageBranchingFactor = (numberOfNonRootNodes * 1.0) / numberOfNonLeafNodes;
        winProb = winRate;
        return  "\n" +
                "[Player = " + winner.getState().getCurrentPlayer().getPlayerName() + " \n" +
                "Number Of Discovered Nodes " + tree.getNumberOfNodesDiscovered() + "\n" +
                "Number Of Leaf Nodes " + tree.getNumberOfLeafNodes() + "\n" +
                "Average Branching Factor " + averageBranchingFactor + "\n" +
                "Heuristic = " + winner.getState().getCurrentPlayer() + " \n" +
                "wins : " +  winner.getNumberOfWins() + " sims: " + winner.getNumberOfSimulations() + " \n" +
                "Win Rate = " + winRate + " %" + "\n" +
                "Your chances of victory = " + (100 - winRate) + " %]";

    }
}
