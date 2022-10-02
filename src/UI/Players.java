package UI;

import GAME.Cell;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Players {

    public static int total = 0;
    public int[][] turn;
    public Human humans;
    public Bot bots;
    int currentTurn;
    int currentPlayer;
    int totalPlayers;
    int color = 1;
    private boolean ronda = true;
    public String[] colors = {"RED", "BLUE", "GREEN", "YELLOW"};


    private ArrayList<LinkedList<Integer>> scores = new  ArrayList<>();

    private ArrayList<Integer> finalScore = new ArrayList<>();
    private  ArrayList<Cell> board;


    public Players(int numberHumans, int numberBot, Human human, Bot bot){
        currentPlayer = 0;
        currentTurn = 0;
        totalPlayers = numberHumans + numberBot;
        this.bots = bot;
        this.humans = human;
        turn = new int[totalPlayers][totalPlayers];

        fillTurn();
        finalScore.add(1);
        finalScore.add(1);
        finalScore.add(1);
        finalScore.add(1);
    }

    public boolean gameOver(int cells){
        return (totalPlayers*totalPlayers) >= cells;
    }

    public boolean botTurn(){
        return currentPlayer+1 == bots.getColor();
    }

    public void fillTurn(){

        for (int i = 0; i < turn.length; i++) {
            for (int j = 0; j < turn.length; j++) {
                turn[i][j] = j + 1;
                System.out.println(j+1);
            }
        }
    }

    public void update(){
        ronda = false;
        if(currentTurn >= totalPlayers) {
            currentTurn = 0;
            if(currentPlayer >= totalPlayers) {
                currentPlayer =0;
            }else{
                currentPlayer++;
            }
        }

        if(currentPlayer >= totalPlayers){currentPlayer =0;}
        System.out.println("Player number " + currentPlayer + " Turn number " + currentTurn);
        color = turn[currentPlayer][currentTurn];
        currentTurn++;

    }

    public boolean getRonda(){return ronda;}

    public int getCurrentPlayer() {return currentPlayer;}

    public int getCurrentTurn() {return currentTurn;}

    public int getColor() {return color;}

    public ArrayList<Integer> getFinalScore() {
        return finalScore;
    }

    public int getScore(int i){
        return finalScore.get(i);
    }


}
