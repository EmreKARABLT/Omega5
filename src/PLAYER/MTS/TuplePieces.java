package PLAYER.MTS;

import GAME.Cell;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TuplePieces that = (TuplePieces) o;
        return Objects.equals(white, that.white) && Objects.equals(black, that.black);
    }

    @Override
    public int hashCode() {
        return Objects.hash(white, black);
    }
}
