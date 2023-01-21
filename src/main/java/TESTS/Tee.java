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

public class Tee implements Runnable {
    private final int numberOfTest;
    private final State state;
    private int numberOfGamesWhiteWon = 0;
    private int numberOfGamesBlackWon = 0;
    private int numberOfTies = 0;
    private Player white, black;
    private ArrayList<Integer> scoresOfWhite = new ArrayList<>();
    private ArrayList<Integer> scoresOfBlack = new ArrayList<>();
    private ArrayList<Double> ratios = new ArrayList<>();
    private ArrayList<String> tempStringArr;
    public static StringBuilder s = new StringBuilder();
    private static int time = 1;

    public Tee(int numberOfTest, Player white, Player black) {
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
//        s.append("Time (m/s),");
//        s.append("MCTS Winrate\n");
        for (int i = 0; i < numberOfTest; i++) {
            tempStringArr = new ArrayList<>();
            runTest();
            //in this part board will be full and any data can be derived
            state.restart();
        }
        s.append(time + "," + (double) numberOfGamesBlackWon / numberOfTest + "\n");

        PrintWriter writer = null;
        try {

            writer = new PrintWriter(String.format("time_set.csv"), StandardCharsets.UTF_8);
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
        for (time = 10; time <= 1010; time += 100) {
            Player white = new Hybrid("white", new UCB1NN(), 100);
            Player black = new MonteCarlo("black", new UCB1(), time);
            double start = System.currentTimeMillis();
            int N = 100;
            Tee testBot = new Tee(N, white, black);
//            double end = System.currentTimeMillis();
//            System.out.println("\nExecution time " + (end - start) / 1000.d + " seconds for " + N + " games");
//            System.out.println("\nAverage execution time for each game " + (end - start) / N + " milliseconds \n");
//            System.out.println("#############################\nWin Percentage of White Player: " + testBot.getWhitesWinPercentage() + "\n");
//            double average = (end - start) / N;
//            System.out.println(3600 * 1000 / average + " games will be generated in an hour");
        }
    }
}
