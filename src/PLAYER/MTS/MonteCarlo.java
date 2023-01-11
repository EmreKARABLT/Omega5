package PLAYER.MTS;

import GAME.Cell;
import GAME.State;
import PLAYER.MTS.SELECTION_HEURISTICS.Heuristics;
import PLAYER.MTS.SELECTION_HEURISTICS.RAVE;
import PLAYER.MTS.SELECTION_HEURISTICS.UCB1;
import PLAYER.MTS.SELECTION_HEURISTICS.UCT;
import PLAYER.Player;
import UI.Grid;

import javax.swing.*;
import java.util.ArrayList;

public class MonteCarlo extends Player{



    private final double SEARCHTIME = 150;
    private final double MAX_SIMULATION = 1000;
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
//        if(tree == null ){
//            this.tree = new Tree(state, this.heuristics,playerID);
//
//        }else
//            this.tree.setRoot(state, state.getWhites(), state.getBlacks());
        this.tree = new Tree(state, this.heuristics,playerID);
        double a = 0;
        double numberOfSim = 0;

        while ( a < SEARCHTIME){
            double start = System.currentTimeMillis();

            tree.simulation();
            numberOfSim++;
            double finish = System.currentTimeMillis();
            a += finish-start;
//            a++;
        }



        Node winner = tree.getBest(tree.root);
        tree.setLastMove(winner);
        ArrayList<Cell> moves = new ArrayList<>();

//        System.out.println(winRateCalculator(winner));

        moves.add(winner.getWhite());
        moves.add(winner.getBlack());
        winner.getWhite().setColor(0);
        winner.getBlack().setColor(1);
        state.addWhite(winner.getWhite());
        state.addBlack(winner.getBlack());
        state.getPlayers().get(0).setScore(state.getBoard().scoreOfAPlayer(0));
        state.getPlayers().get(1).setScore(state.getBoard().scoreOfAPlayer(1));
        state.nextTurn();
        state.nextTurn();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if(GUI!=null) {
                    GUI.updateScores();
                    GUI.updateColors();
                    GUI.updateScores();
                    GUI.updateColorBars();
                    GUI.endFrame();
                    GUI.repaint();
                }
            }
        });

        return moves;
    }

    public Tree getTree() {
        return tree;
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
        double winRateAmaf = (double) Math.round(winner.getNumberOfWinsAMAF()/winner.getNumberOfSimulationsAMAF()*100 * decimals) / decimals;
        int numberOfNonRootNodes = (tree.getNumberOfNodesDiscovered()-1);
        int numberOfNonLeafNodes = tree.getNumberOfNodesDiscovered() - tree.getNumberOfLeafNodes();

        double averageBranchingFactor = (numberOfNonRootNodes * 1.0) / numberOfNonLeafNodes;
        winProb = winRate;
        return  "\n" +
                "[Player = " + winner.getState().getCurrentPlayer().getPlayerName() + " \n" +
                "Number Of Discovered Nodes " + tree.getNumberOfNodesDiscovered() + "\n" +
                "Number Of Leaf Nodes " + tree.getNumberOfLeafNodes() + "\n" +
//                "Average Branching Factor " + averageBranchingFactor + "\n" +
                "Total Number Of Simulations = " + tree.root.getNumberOfSimulations() + " \n"+
                "Heuristic = " + winner.getState().getCurrentPlayer() + " \n" +
                "Wins(U) : " +  winner.getNumberOfWins() + " sims: " + winner.getNumberOfSimulations() + " \n" +
//                "Wins(A) : " +  winner.getNumberOfWinsAMAF() + " sims: " + winner.getNumberOfSimulationsAMAF() + " \n" +
                "Win Rate (U)= " + winRate + " %" + "\n" +
//                "Win Rate (A)= " + winRateAmaf + " %" + "\n" +
//                "Is AMAF MORE IMPORTANT = " + (winner.getNumberOfSimulationsAMAF() >3* winner.getNumberOfSimulations()) + "\n"+
                "Your chances of victory = " + (100 - winRate) + " %]";

    }
}
