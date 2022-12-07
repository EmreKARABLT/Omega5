package GAME;

import PLAYER.MTS.MonteCarlo;
import PLAYER.MTS.SELECTION_HEURISTICS.Pnaive;
import PLAYER.MTS.SELECTION_HEURISTICS.UCB1;
import PLAYER.RULE_BASED_BOT.GeneticRuleBasedBot;
import PLAYER.RandomBot;
import PLAYER.Player;

import java.util.ArrayList;

public class TestBot implements Runnable {
    private final int numberOfTest;
    private final State state;
    private int numberOfGamesWhiteWon = 0;
    private int numberOfGamesBlackWon = 0;
    private int ties = 0;
    private ArrayList<Integer> scoresOfBlack = new ArrayList<>();
    private ArrayList<Integer> scoresOfWhite = new ArrayList<>();
    private ArrayList<Double> ratios = new ArrayList<>();

    public TestBot(int numberOfTest, Player bot1, Player bot2) {
        this.numberOfTest = numberOfTest;
        ArrayList<Player> playersList = new ArrayList<>();
        playersList.add(bot1);
        playersList.add(bot2);
        state = new State(new Board(3), playersList);
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfTest; i++) {
            runTest();
//            System.out.println("White : " + state.getPlayers().get(0).getScore() + " Black : " + state.getPlayers().get(1).getScore());
            //in this part board will be full and any data can be derived
            // state.getBoard().getCells();// with this line you can get the cells of the board (END GAME )
            state.restart();
        }
    }

    public void runTest() {

        while (!state.isGameOver()) {
            ArrayList<Cell> moves = state.getCurrentPlayer().getMoves(state);
            moves.get(0).setColor(0);
            moves.get(1).setColor(1);
            state.getPlayers().get(0).setScore(state.getBoard().scoreOfAPlayer(0));
            state.getPlayers().get(1).setScore(state.getBoard().scoreOfAPlayer(1));
            state.nextTurn();
            state.nextTurn();
            //If you want to get the state of the board after each move it is where you need to derive the state of the board state.getBoard().getCells()
        }

        if (state.isGameOver()) {
            Player winner = state.getWinner();
            Player loser = state.getLoser();

            if (winner == null || loser == null) {
                ties++;
                return;
            }
            if (winner.getPlayerID() == 1) {
                numberOfGamesBlackWon++;
            } else if (winner.getPlayerID() == 0)
                numberOfGamesWhiteWon++;
        }
        int blacks = state.getPlayers().get(1).getScore();
        int whites = state.getPlayers().get(0).getScore();
        scoresOfBlack.add(blacks);
        scoresOfWhite.add(whites);
        ratios.add((blacks*1.0)/whites);


    }
    public int getNumberOfGamesWhiteWon(){  return numberOfGamesWhiteWon; }
    public int getNumberOfGamesBlackWon(){  return numberOfGamesBlackWon; }
    public double getWhitesWinPercentage() {
        return numberOfGamesWhiteWon / (double) numberOfTest * 100;
    }

    public double getBlacksWinPercentage() {
        return numberOfGamesBlackWon / (double) numberOfTest * 100;
    }

    public double getTiesPercentage() {
        return ties / (double) numberOfTest * 100;
    }

    public ArrayList<Double> getRatios() {
        return ratios;
    }

    public ArrayList<Integer> getScoresOfBlack() {
        return scoresOfBlack;
    }
    public ArrayList<Integer> getScoresOfWhite() {
        return scoresOfWhite;
    }


    public static double inverseStudentT(double p, int df) {
        double t = 0;
        double dt = 0.5;
        while (dt > 1e-6) {
            t += dt * (studentT(t, df) - p);
            dt *= 0.5;
        }
        return t;
    }
    // Function to calculate the cumulative distribution function (CDF)
    // of the Student's t-distribution
    public static double studentT(double t, int df) {
        double c = df / (df + t * t);
        double sum = 0;
        for (int i = df; i > 0; i--) {
            sum = 1 + sum * c;
        }
        return 1 - 0.5 * Math.pow(1 - c, sum);
    }

    public static void main(String[] args) {
        Player white = new MonteCarlo("white", new Pnaive());
        Player black = new GeneticRuleBasedBot("black");
        // Random               ->  new RandomBot( "white"/"black")
        // RuleBased            ->  new RuleBasedBot( "white"/"black")
        // MonteCarlo PNaive    ->  new MonteCarlo( "white"/"black" , new Pnaive())
        // MonteCarlo UCT       ->  new MonteCarlo( "white"/"black" , new UCT() )
        // MonteCarlo UCB1      ->  new MonteCarlo( "white"/"black" , new USB1() )
        // Genetic Ruled Based  ->  new GeneticRuleBasedBot("white"/"black")
        TestBot testBot = new TestBot(100,white,black);
        testBot.run();
        System.out.println("Win Percentage of White Player: " + testBot.getWhitesWinPercentage());



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


//        Player[] whiteBots =
//                {new MonteCarlo("White", new UCT()),
//                        new MonteCarlo("White", new UCB1()),
//                        new MonteCarlo("White", new RAVE()),
//                        new RuleBasedBot("White"),
//                        //new PBot("White"),
//                        new RandomBot("White")};
//
//        Player[] blackBots =
//                {new MonteCarlo("Black", new UCT()),
//                        new MonteCarlo("Black", new UCB1()),
//                        new MonteCarlo("Black", new RAVE()),
//                        new RuleBasedBot("Black"),
//                        //new PBot("Black"),
//                        new RandomBot("Black")};
//
//        double start = System.currentTimeMillis();
//        for (Player bot1 : whiteBots) {
//            for (Player bot2 : blackBots) {
//                String bot1Name = "White "+bot1.getHeuristics().getName();
//                String bot2Name = "Black "+bot2.getHeuristics().getName();
//                System.out.println("START MATCH : "+ bot1Name +" vs "
//                        + bot2Name +"\n--------------------------");
//
//                TestBot testBot = new TestBot(2, bot1, bot2);
//
//                System.out.println("win rate : "+ bot1Name +" = "+ testBot.getWhitesWinPercentage()
//                        + "\nwin rate: "+ bot2Name + " = " +testBot.getBlacksWinPercentage());
//                System.out.println("Tie percentage: " + testBot.getTiesPercentage());
//                System.out.println("END MATCH \n--------------------------");
//            }
//        }
//        double end = System.currentTimeMillis();


    }
}
