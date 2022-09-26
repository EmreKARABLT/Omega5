package Observers;

import GAME.Board;

import java.util.ArrayList;
import java.util.HashMap;

public class GameLoopObserver implements Observer{
    private Board board ;
    private HashMap< Integer , ArrayList<Observer> > observers = new HashMap<>();


    public GameLoopObserver(Board board){
        this.board = board;
    }

    @Override
    public void update(){

    }
}
