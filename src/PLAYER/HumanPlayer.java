package PLAYER;

import GAME.Cell;

public class HumanPlayer implements Player{
    private int score;
    private int playerColor;
    private int pieceColor;

    public HumanPlayer(int playerColor){
        this.playerColor = playerColor;
        this.pieceColor = playerColor;
        this.score = 0;
    }

    @Override
    public void changeToNextPieceColor() {

    }

    @Override
    public void placeCurrentPieceOnCell(Cell cell) {

    }

    @Override
    public boolean turnIsOver() {
        return false;
    }

    @Override
    public void setPlayersColor(int playersColor) {
        this.playerColor = playersColor;
    }

    @Override
    public int getPlayersColor() {
        return playerColor;
    }

    @Override
    public void setPieceColor(int pieceColor) {
        this.pieceColor = pieceColor;
    }

    @Override
    public int getPieceColor() {
        return pieceColor;
    }

    @Override
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int getScore() {
        return score;
    }
}
