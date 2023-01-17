package TESTS;

import GAME.Board;
import GAME.Cell;
import GAME.State;
import PLAYER.Hybrid.Hybrid;
import PLAYER.MTS.MonteCarlo;
import PLAYER.MTS.SELECTION_HEURISTICS.UCB1;
import PLAYER.MTS.SELECTION_HEURISTICS.UCB1NN;
import PLAYER.MTS.SELECTION_HEURISTICS.UCT;

import PLAYER.Player;
import PLAYER.RandomBot;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TestBot implements Runnable {
    private final int numberOfTest;
    private final State state;
    private int numberOfGamesWhiteWon = 0;
    private int numberOfGamesBlackWon = 0;
    private int numberOfTies = 0;
    Player white, black;
    private ArrayList<Integer> scoresOfWhite = new ArrayList<>();
    private ArrayList<Integer> scoresOfBlack = new ArrayList<>();
    private ArrayList<Double> ratios = new ArrayList<>();
    private ArrayList<String> tempStringArr;
    public static StringBuilder s = new StringBuilder();

    public TestBot(int numberOfTest, Player white, Player black) {
        this.numberOfTest = numberOfTest;
        this.white = white;
        this.black = black;
        ArrayList<Player> playersList = new ArrayList<>();
        playersList.add(white);
        playersList.add(black);
        state = new State(new Board(3), playersList);
        run();

    }

    @Override
    public void run() {
        s.append(state.getBoard().idString());
        s.append("move,");
        s.append("bscore,");
        s.append("wscore,");
        s.append("bwin\n");
        for (int i = 0; i < numberOfTest; i++) {
            tempStringArr = new ArrayList<>();
            runTest();
            System.out.printf("\n*************   WHITE : %d - BLACK : %d   *************\n",white.getScore(),black.getScore());
            //in this part board will be full and any data can be derived
            state.restart();
        }
        PrintWriter writer = null;
        try {

            writer = new PrintWriter(String.format("dataset_m.csv"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert writer != null;
        writer.print(s.toString());
        writer.close();
    }

    public void runTest() {
        while (!state.isGameOver()) {
            state.getCurrentPlayer().getMoves(state);
            tempStringArr.add(state.getBoard().toString());
            //If you want to get the state of the board after each move it is where you need to derive the state of the board state.getBoard().getCells()
        }


        if (state.isGameOver()) {
            int scoreW = state.getPlayers().get(0).getScore();
            int scoreB = state.getPlayers().get(1).getScore();

            if (scoreW > scoreB) {
                numberOfGamesWhiteWon++;
            } else {
                numberOfGamesBlackWon++;
            }
        }
        int blacksScore = state.getPlayers().get(1).getScore();
        int whitesScore = state.getPlayers().get(0).getScore();

        String winLabel = "1";
        if (whitesScore > blacksScore) {
            winLabel = "0";
        }
        int i = 1;
        for (String tempString : tempStringArr) {
            tempString += i + ",";
            i++;
            tempString += blacksScore + ",";
            tempString += whitesScore + ",";
            tempString += winLabel;
            s.append(tempString);
            s.append("\n");
        }
    }

    public int getNumberOfGamesWhiteWon() {
        return numberOfGamesWhiteWon;
    }

    public int getNumberOfGamesBlackWon() {
        return numberOfGamesBlackWon;
    }

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
        Player white = new MonteCarlo("white", new UCB1());
        Player black = new Hybrid("black", new UCB1NN());
//        Player white = new RandomBot("white");
//        Player black = new RandomBot("black");
        // Random               ->  new RandomBot( "white"/"black")
        // RuleBased            ->  new RuleBasedBot( "white"/"black")
        // MonteCarlo PNaive    ->  new MonteCarlo( "white"/"black" , new Pnaive())
        // MonteCarlo UCT       ->  new MonteCarlo( "white"/"black" , new UCT() )
        // MonteCarlo UCB1      ->  new MonteCarlo( "white"/"black" , new USB1() )
        // Genetic Ruled Based  ->  new GeneticRuleBasedBot("white"/"black")
        double start = System.currentTimeMillis();
        int N = 1000;
        TestBot testBot = new TestBot(N, white, black);
        double end = System.currentTimeMillis();
        System.out.println("\nExecution time " + (end - start) / 1000.d + " seconds for " + N + " games");
        System.out.println("\nAverage execution time for each game " + (end - start) / N + " milliseconds \n");
        System.out.println("#############################\nWin Percentage of White Player: " + testBot.getWhitesWinPercentage() + "\n");
        double average = (end - start)/1000.d / N;
        System.out.println(3600 / average + " games will be generated in an hour");

    }
}
