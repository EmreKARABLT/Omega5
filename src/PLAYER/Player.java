package PLAYER;

import GAME.Cell;
import GAME.State;
import PLAYER.MTS.SELECTION_HEURISTICS.Heuristics;
import PLAYER.MTS.SELECTION_HEURISTICS.UCB1;
import PLAYER.MTS.SELECTION_HEURISTICS.UCT;

import java.util.ArrayList;

public abstract class Player {
    //protected static State state;
    double[] w = {Math.random(),Math.random(),Math.random(),Math.random(),Math.random()};
    protected String playerName;
    int currentPiecesID = 0 ;
    int score = 0 ;
    protected int playerID ;
    public static int counterForIDs = 0;
    public Heuristics heuristic = new UCB1();

    public boolean isBot(){return false;};
    public ArrayList<Cell> getMoves(State state){
        return null;
    };
    public ArrayList<Cell> getMoves(State state,ArrayList<Cell> whites ,ArrayList<Cell> blacks){
        return null;
    };



    public String getPlayerName(){return playerName;};
    public int getPlayerID(){return playerID;};

    public int getScore(){return score;};
    public void setScore(int score){ this.score = score;};

    public int getCurrentPieceID(){return currentPiecesID;};
    public void setCurrentPieceID(int currentPieceID){ this.currentPiecesID = currentPieceID;};

    public void reset(){
        score = 0 ;
        currentPiecesID = 0 ;
    }

    public void setW(double[] w){
        this.w = w;
    }
    public double[] getW(){
        return this.w;
    }

    public Heuristics getHeuristic() {
        return heuristic;
    }
}
