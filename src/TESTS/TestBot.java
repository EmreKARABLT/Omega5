package TESTS;

import GAME.Board;
import GAME.Cell;
import GAME.State;
import PLAYER.MTS.MonteCarlo;
import PLAYER.MTS.ROOT_PARALLELIZATION.MonteCarloRootParallelization;
import PLAYER.MTS.SELECTION_HEURISTICS.Pnaive;
import PLAYER.MTS.SELECTION_HEURISTICS.UCB1;
import PLAYER.MTS.SELECTION_HEURISTICS.UCT;
import PLAYER.MTS.Tree;
import PLAYER.Player;
import PLAYER.RandomBot;

import java.util.ArrayList;
import java.util.List;

public class TestBot implements Runnable {
    private final int numberOfTest;
    private final State state;
    private int numberOfGamesWhiteWon = 0;
    private int numberOfGamesBlackWon = 0;
    private int numberOfTies = 0;
    Player white ,black;
    private int optimalPlayForWhite= 0 ;
    private ArrayList<Integer> scoresOfWhite                = new ArrayList<>();
    private ArrayList<Integer> numberOfDiscoveredWhite      = new ArrayList<>();
    private ArrayList<Integer> numberOfDiscoveredLeafWhite  = new ArrayList<>();
    private ArrayList<Integer> numberOfTotalWinsWhite       = new ArrayList<>();
    private ArrayList<Integer> numberOfSimulationsWhite     = new ArrayList<>();
    private ArrayList<Integer> scoresOfBlack                = new ArrayList<>();
    private ArrayList<Integer> numberOfDiscoveredBlack      = new ArrayList<>();
    private ArrayList<Integer> numberOfDiscoveredLeafBlack  = new ArrayList<>();
    private ArrayList<Integer> numberOfTotalWinsBlack       = new ArrayList<>();
    private ArrayList<Integer> numberOfSimulationsBlack     = new ArrayList<>();
    private ArrayList<Double> ratios = new ArrayList<>();


    public TestBot(int numberOfTest, Player white, Player black) {
        this.numberOfTest = numberOfTest;
        this.white = white;
        this.black = black;
        ArrayList<Player> playersList = new ArrayList<>();
        playersList.add(white);
        playersList.add(black);
        state = new State(new Board(1), playersList);
        run();
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfTest; i++) {
            System.out.print(i+",");
            runTest();

            //in this part board will be full and any data can be derived
            state.restart();
        }
    }

    public void runTest() {

        while (!state.isGameOver()) {
            state.getCurrentPlayer().getMoves(state);

            //If you want to get the state of the board after each move it is where you need to derive the state of the board state.getBoard().getCells()
        }
        if (state.isGameOver()) {
            Player winner = state.getWinner();
            int scoreW = state.getPlayers().get(0).getScore();
            int scoreB = state.getPlayers().get(1).getScore();

            Cell center = state.getBoard().getCells().stream().filter(cell -> cell.getQ() == 0 && cell.getR() == 0).toList().get(0);
            if(center.getColor()==0){
                optimalPlayForWhite++;
            }
            System.out.println(state.getPlayers().get(0).getScore() +" - "+ state.getPlayers().get(1).getScore() + " - " + (center.getColor()==0));
            if(scoreW==scoreB){
                numberOfTies++;
            }else
            if (scoreW>scoreB) {
                numberOfGamesWhiteWon++;
            } else {
                numberOfGamesBlackWon++;
            }
        }
        int blacks = state.getPlayers().get(1).getScore();
        int whites = state.getPlayers().get(0).getScore();
        scoresOfBlack.add(blacks);
        scoresOfWhite.add(whites);
        ratios.add((blacks*1.0)/whites);

        if(white.tree!=null) {
            Tree whiteTree =white.getTree();
            numberOfDiscoveredWhite.add(whiteTree.getNumberOfNodesDiscovered());
            numberOfDiscoveredLeafWhite.add(whiteTree.getNumberOfLeafNodes());
            numberOfTotalWinsWhite.add(whiteTree.getNumberOfTotalWins());
            numberOfSimulationsWhite.add(whiteTree.getSimNumber());
        }

        if(black.tree!=null) {
            Tree blackTree =black.getTree();
            numberOfDiscoveredBlack.add(blackTree.getNumberOfNodesDiscovered());
            numberOfDiscoveredLeafBlack.add(blackTree.getNumberOfLeafNodes());
            numberOfTotalWinsBlack.add(blackTree.getNumberOfTotalWins());
            numberOfSimulationsBlack.add(blackTree.getSimNumber());
        }
    }
    public int getNumberOfGamesWhiteWon(){  return numberOfGamesWhiteWon; }
    public int getNumberOfGamesBlackWon(){  return numberOfGamesBlackWon; }
    public double getWhitesWinPercentage() {
        return numberOfGamesWhiteWon / (double) numberOfTest * 100;
    }

    public double getBlacksWinPercentage() {
        return numberOfGamesBlackWon / (double) numberOfTest * 100;
    }


    public ArrayList<Integer> getScoresOfBlack() {
        return scoresOfBlack;
    }
    public ArrayList<Integer> getScoresOfWhite() {
        return scoresOfWhite;
    }


    public static void main(String[] args) {
        Player white = new MonteCarlo("white",new UCB1());
        Player black = new MonteCarlo("black", new UCT());
        // Random               ->  new RandomBot( "white"/"black")
        // RuleBased            ->  new RuleBasedBot( "white"/"black")
        // MonteCarlo PNaive    ->  new MonteCarlo( "white"/"black" , new Pnaive())
        // MonteCarlo UCT       ->  new MonteCarlo( "white"/"black" , new UCT() )
        // MonteCarlo UCB1      ->  new MonteCarlo( "white"/"black" , new USB1() )
        // Genetic Ruled Based  ->  new GeneticRuleBasedBot("white"/"black")
        double start = System.currentTimeMillis();
        int N = 10000;
        TestBot testBot = new TestBot(N,white,black);
        double end = System.currentTimeMillis();
        System.out.println("\nAverage execution time for each game "+ (end-start)/N/1000.d +" seconds \n");
        System.out.println("#############################\nWin Percentage of White Player: " + testBot.getWhitesWinPercentage() +"\n");
        System.out.println("#############################\nPercentage of Ties Player: " + (testBot.numberOfTies*100.d)/N +"\n");
        System.out.println("Number of optimal moves from white : " + testBot.optimalPlayForWhite  );
//        System.out.println("--WHITE--");
//        System.out.println("Number of games won = " +testBot.numberOfTotalWinsWhite      );
//        System.out.println("Number of discovered nodes  = " +testBot.numberOfDiscoveredWhite     );
//        System.out.println("Number of discovered leaf nodes = " +testBot.numberOfDiscoveredLeafWhite );
//        System.out.println("Number of simulations = " +testBot.numberOfSimulationsWhite    );
//
//        System.out.println("--BLACK--");
//        System.out.println("Number of games won = " +testBot.numberOfTotalWinsBlack      );
//        System.out.println("Number of discovered nodes  = " +testBot.numberOfDiscoveredBlack     );
//        System.out.println("Number of discovered leaf nodes = " +testBot.numberOfDiscoveredLeafBlack );
//        System.out.println("Number of simulations = " +testBot.numberOfSimulationsBlack    );


//        // Confidence level
//        double confidenceLevel = 0.95;
//
//        // Calculate mean and sample variance
//        double mean = 0.0;
//        double variance = 0.0;
//        for (double sample : samples) {
//            mean += sample;
//            variance += Math.pow(sample - mean, 2);
//
//        }
//        mean /= samples.size();
//        variance /= samples.size() - 1;
//        System.out.println("Mean : " + mean + " Variance : " + variance);
//
//// Calculate standard deviation
//        double stdDev = Math.sqrt(variance);
//
//// Calculate the critical value
//        double criticalValue = inverseStudentT(confidenceLevel, samples.size() - 1);
//
//// Calculate the confidence interval
//        double marginOfError = criticalValue * stdDev / Math.sqrt(samples.size());
//        double lowerBound = mean - marginOfError;
//        double upperBound = mean + marginOfError;
//
//// Print the confidence interval
//        System.out.println("Confidence interval: [" + lowerBound + ", " + upperBound + "]");
//        int numberOfSamplesInCI = 0 ;
//        for(double sample : samples){
//            if(sample>= lowerBound && sample<=upperBound)
//                numberOfSamplesInCI++;
//        }
//        System.out.println(samples);
//        System.out.println((numberOfSamplesInCI*1.0)/samples.size());

    }
}
