package TIME_COMPLEXITY_ANALISIS;

import GAME.TestBot;
import PLAYER.MTS.MCTS_TimeComplexity;
import PLAYER.MTS.SELECTION_HEURISTICS.UCB1;
import PLAYER.Player;
import PLAYER.RandomBot;

public class TimeComplexity {


    public static int calculateMoves(int r){
        int count = 1;

        for (int i = 1; i <= r; i++) {
            count = count + (6*i);
        }

        return count;
    }

    public static void createArrayX(int r){

        int n = calculateMoves(r);

        System.out.print("{");

        for (int i = n; i >= 0; i--) {
            System.out.print(n + ", ");
            n -=4;
            if(n < 0){break;}
        }
        System.out.print("};");


    }

    public static void main(String[] args) {


        int r = 12;
        System.out.print("{");
        Player white = new MCTS_TimeComplexity("white", new UCB1());
        Player black = new RandomBot("black");
        TestBot testBot = new TestBot(1,white,black,r);
        testBot.run();
        System.out.print("};");

        System.out.println();

        createArrayX(r);

    }
}
