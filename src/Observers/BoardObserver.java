package Observers;

import GAME.Board;

public class BoardObserver implements Observer{
    private Board board ;
    public BoardObserver( Board board){
        this.board = board;
    }

    @Override
    public void update(){

    }
}
