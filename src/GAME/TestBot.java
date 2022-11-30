package GAME;

import PLAYER.MTS.SELECTION_HEURISTICS.RAVE;
import PLAYER.MTS.SELECTION_HEURISTICS.UCB1;
import PLAYER.MTS.SELECTION_HEURISTICS.UCT;
import PLAYER.MonteCarlo;
import PLAYER.P_BOT.PBot;
import PLAYER.RULE_BASED_BOT.RuleBasedBot;
import PLAYER.RandomBot;
import PLAYER.Player;

import java.util.ArrayList;

public class TestBot {
    private final int numberOfTest;
    private final State state;
    private int numberOfGamesWhiteWon = 0;
    private int numberOfGamesBlackWon = 0;
    private int ties = 0;

    public TestBot(int numberOfTest, Player bot1, Player bot2) {
        this.numberOfTest = numberOfTest;
        ArrayList<Player> playersList = new ArrayList<>();
        playersList.add(bot1);
        playersList.add(bot2);
        state = new State(new Board(2), playersList);

        for (int i = 0; i < numberOfTest; i++) {
            runTest();
            System.out.println("White : " + state.getPlayers().get(0).getScore() + " Black : " + state.getPlayers().get(1).getScore());
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

    }

    public double getWhitesWinPercentage() {
        return numberOfGamesWhiteWon / (double) numberOfTest * 100;
    }

    public double getBlacksWinPercentage() {
        return numberOfGamesBlackWon / (double) numberOfTest * 100;
    }

    public double getTiesPercentage() {
        return ties / (double) numberOfTest * 100;
    }

    public static void main(String[] args) {

        Player[] whiteBots =
                {new MonteCarlo("White", new UCT()),
                        new MonteCarlo("White", new UCB1()),
                        new MonteCarlo("White", new RAVE()),
                        new RuleBasedBot("White"),
                        //new PBot("White"),
                        new RandomBot("White")};

        Player[] blackBots =
                {new MonteCarlo("Black", new UCT()),
                        new MonteCarlo("Black", new UCB1()),
                        new MonteCarlo("Black", new RAVE()),
                        new RuleBasedBot("Black"),
                        //new PBot("Black"),
                        new RandomBot("Black")};

        double start = System.currentTimeMillis();
        for (Player bot1 : whiteBots) {
            for (Player bot2 : blackBots) {
                String bot1Name = "White "+bot1.getHeuristicName();
                String bot2Name = "Black "+bot2.getHeuristicName();
                System.out.println("START MATCH : "+ bot1Name +" vs "
                        + bot2Name +"\n--------------------------");

                TestBot testBot = new TestBot(2, bot1, bot2);

                System.out.println("win rate : "+ bot1Name +" = "+ testBot.getWhitesWinPercentage()
                        + "\nwin rate: "+ bot2Name + " = " +testBot.getBlacksWinPercentage());
                System.out.println("Tie percentage: " + testBot.getTiesPercentage());
                System.out.println("END MATCH \n--------------------------");
            }
        }
        double end = System.currentTimeMillis();
    }
}
