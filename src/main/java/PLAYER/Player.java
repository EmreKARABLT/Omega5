package PLAYER;

import GAME.Cell;
import GAME.State;
import PLAYER.Hybrid.TreeHybrid;
import PLAYER.MTS.SELECTION_HEURISTICS.Heuristics;
import PLAYER.MTS.Tree;
import UI.Grid;
import UI.Hex;
import UI.Menu;
import UI.Show;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public abstract class Player implements Runnable{
    double[] w = {Math.random(),Math.random(),Math.random(),Math.random(),Math.random()};
    protected String playerName;
    protected String heuristicName;
    public int currentPiecesID = 0 ;
    public int score = 0 ;
    protected int playerID ;
    public static int counterForIDs = 0;
    public Heuristics heuristics = null;
    public ArrayList<Cell> moves = new ArrayList<>();
    public State state;
    public Grid GUI ;
    public Tree tree ;

    public boolean isBot(){return false;};
    public ArrayList<Cell> getMoves(State state){
        return null;
    };
    public ArrayList<Cell> getMoves(State state, ArrayList<Cell> whites , ArrayList<Cell> blacks){
        return null;
    };


    public Heuristics getHeuristics() {
        return heuristics;
    }

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
    public void setState(State state) {
        this.state = state;
    }
    public State getState(){
        return this.state;
    }

    public void setGUI(Grid GUI) {
        this.GUI = GUI;
    }

    public Tree getTree() {return tree;}

    @Override
    public void run() {

    }

}
