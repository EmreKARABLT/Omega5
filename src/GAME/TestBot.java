package GAME;

import PLAYER.MonteCarlo;
import PLAYER.RandomBot;
import PLAYER.Player;

import java.util.ArrayList;

public class TestBot {
    private final int numberOfTest ;
    private final State state ;
    private int numberOfGamesWhiteWon = 0 ;
    private int numberOfGamesBlackWon = 0 ;

    public TestBot(int numberOfTest , Player bot1 , Player bot2){
        this.numberOfTest = numberOfTest;
        ArrayList<Player> playersList = new ArrayList<>();
        playersList.add( bot1 );
        playersList.add( bot2 );
        state = new State(3, playersList);

        for (int i = 0; i < numberOfTest; i++) {
            runTest();
            state.restart();
        }
        System.out.println(numberOfGamesWhiteWon + " " + numberOfGamesBlackWon);

    }
    public void runTest(){

        while( !state.isGameOver() ){
            ArrayList<Cell> moves = state.getCurrentPlayer().getMoves(state);
            moves.get(0).setColor(0);
            moves.get(1).setColor(1);
            state.getPlayers().get(0).setScore(state.getBoard().scoreOfAPlayer(0));
            state.getPlayers().get(1).setScore(state.getBoard().scoreOfAPlayer(1));
            state.nextTurn();
            state.nextTurn();
        }

        if(state.isGameOver()) {

            if(state.getWinner().getPlayerName().equals("Black")){
                numberOfGamesBlackWon++;
            }else
                numberOfGamesWhiteWon++;
        }
    }
    public double getWhitesWinPercentage(){
        return  numberOfGamesWhiteWon / (double) numberOfTest * 100;
    }
    public double getBlacksWinPercentage() {
        return  numberOfGamesBlackWon / (double) numberOfTest * 100;
    }
    public static void main(String[] args) {
        double start = System.currentTimeMillis();
        Player bot1 = new RandomBot("Black");
        Player bot2 = new MonteCarlo("White");
        TestBot testBot = new TestBot(100000,bot1,bot2);
        System.out.println(testBot.getWhitesWinPercentage() + " " + testBot.getBlacksWinPercentage());
        double end = System.currentTimeMillis();
        System.out.println((end - start)/1000.d);
    }

}
