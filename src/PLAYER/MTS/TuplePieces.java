package PLAYER.MTS;

import GAME.Cell;

public class TuplePieces {

    private Cell white;
    private Cell black;

    public TuplePieces(Cell white, Cell black){
        this.white = white;
        this.black = black;
    }

    public Cell getWhite() {
        return white;
    }

    public void setWhite(Cell white) {
        this.white = white;
    }

    public Cell getBlack() {
        return black;
    }

    public void setBlack(Cell black) {
        this.black = black;
    }
}
