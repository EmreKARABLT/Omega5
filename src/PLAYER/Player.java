package PLAYER;

import GAME.Cell;

public interface Player {

    /**
     * Changes the color of the piece the player is going to place
     */
    public void changeToNextPieceColor();

    /**
     * Places the piece of the current color onto the selected cell
     * @param cell the cell where the piece will be placed
     */
    public void placeCurrentPieceOnCell(Cell cell);

    /**
     * sets the color the player represents
     * @param playersColor an integer representing color the player will change to
     */
    public void setPlayersColor(int playersColor);

    /**
     * gets the color the player represents
     * @return the color the player represents
     */
    public int getPlayersColor();

    /**
     * sets color of the current piece in play
     * @param pieceColor an integer representing the color you want the piece to change to
     */
    public void setPieceColor(int pieceColor);

    /**
     * gets the color of the current piece
     * @return an integer representing the color of the piece
     */
    public int getPieceColor();

    /**
     * changes the score of the player
     * @param score the int the players score will change to
     */
    public void setScore(int score);

    /**
     * gets the current score of the player
     * @return the current score of the player
     */
    public int getScore();

}
