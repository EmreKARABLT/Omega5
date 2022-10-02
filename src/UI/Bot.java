package UI;

import GAME.Board;
import GAME.Cell;

import java.util.ArrayList;

public class Bot {

    public int color;
    public String brain;

    public Bot(int color, String brain){
        this.color = color;
        this.brain = brain;
    }

    public boolean isBotPlaying(int botColor){
        return this.color == botColor;
    }

    public Cell selectCell(Board board){
        if(brain == "ramdom"){
            return selectCellRandom(board);
        }
        return selectCellRandom(board);
    }

    public Cell selectCellRandom(Board board){
        ArrayList<Cell> cells = board.getCells();
        for (int i = 0; i < cells.size(); i++) {
            if(cells.get(i).getColor() == 0){
                return cells.get(i);
            }
        }
        return null;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
