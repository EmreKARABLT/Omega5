package TESTS;

import GAME.Board;
import GAME.Cell;
import GAME.State;
import PLAYER.MTS.MonteCarlo;
import PLAYER.MTS.SELECTION_HEURISTICS.UCT;
import PLAYER.RandomBot;
import PLAYER.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class RunTimeTest implements Runnable {
    private final int numberOfTest;
    private final State state;
    private int numberOfGamesWhiteWon = 0;
    private int numberOfGamesBlackWon = 0;
    private int ties = 0;
    private ArrayList<Integer> scoresOfBlack = new ArrayList<>();
    private ArrayList<Integer> scoresOfWhite = new ArrayList<>();
    private ArrayList<Double> ratios = new ArrayList<>();

    public RunTimeTest(int boardSize ,int numberOfTest, Player bot1, Player bot2) {
        this.numberOfTest = numberOfTest;
        ArrayList<Player> playersList = new ArrayList<>();
        playersList.add(bot1);
        playersList.add(bot2);
        state = new State(new Board(boardSize), playersList);
        run();
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfTest; i++) {
            runTest();
            System.out.printf(i+", ");
//            System.out.println("White : " + state.getPlayers().get(0).getScore() + " Black : " + state.getPlayers().get(1).getScore());
            //in this part board will be full and any data can be derived
            // state.getBoard().getCells();// with this line you can get the cells of the board (END GAME )
            state.restart();
        }
    }

    public void runTest() {

        while (!state.isGameOver()) {
            ArrayList<Cell> moves = state.getCurrentPlayer().getMoves(state);

            //If you want to get the state of the board after each move it is where you need to derive the state of the board state.getBoard().getCells()
        }
    }
    public int getNumberOfCells(){
        return this.state.getBoard().getCells().size();
    }


    public static void main(String[] args) {
        int[] boardSizes = new int[]{1,2,3,4,5,6,7,8,9};
        double[] runTimes = new double[boardSizes.length];
        int[] numberOfCells = new int[boardSizes.length];

        for (int i = 0; i < boardSizes.length; i++) {
            double start = System.currentTimeMillis();
            Player white = new MonteCarlo("white", new UCT());
            Player black = new RandomBot("black");
            // Random               ->  new RandomBot( "white"/"black")
            // RuleBased            ->  new RuleBasedBot( "white"/"black")
            // MonteCarlo PNaive    ->  new MonteCarlo( "white"/"black" , new Pnaive())
            // MonteCarlo UCT       ->  new MonteCarlo( "white"/"black" , new UCT() )
            // MonteCarlo UCB1      ->  new MonteCarlo( "white"/"black" , new USB1() )
            // Genetic Ruled Based  ->  new GeneticRuleBasedBot("white"/"black")

            RunTimeTest testBot = new RunTimeTest(boardSizes[i],10,white,black);
            numberOfCells[i] = testBot.getNumberOfCells();
            double finish = System.currentTimeMillis();
            runTimes[i] = (finish-start)/10000.d;
        }
        System.out.println("\nx=" + Arrays.toString(boardSizes));
        System.out.println("y=" + Arrays.toString(runTimes));
        System.out.println("N=" + Arrays.toString(numberOfCells));





    }
}
