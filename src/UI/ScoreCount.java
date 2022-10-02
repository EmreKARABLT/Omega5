package UI;

import GAME.Board;
import GAME.Cell;

import javax.swing.*;
import java.util.*;

public class ScoreCount {
    private ArrayList<LinkedList<Integer>> scores =new  ArrayList<>();//ArrayList to store scores for all colors

    private ArrayList<Integer> finalScore = new ArrayList<>();//array of all color scores
    private  ArrayList<Cell> board;


    public ScoreCount(ArrayList<Cell> board){
        this.board = board;

    }
    /**
     * given a starting point traverse colorSet to which starting cell belongs. Count number of cells in colorSet
     * @params a Cell in a colorSet
     * @return  int size of current colorSet
     */
    public int countingColorSetSizeBFS(Cell s){
        int size = 1; // to accumulate # of cells in current colorSet
        int color = s.getColor(); //color of current colorSet
        LinkedList<Cell> colorSet = new LinkedList<>();
        s.setVisited(true);
        colorSet.add(s);
        while (colorSet.size()!=0)
        {
            //dequeue the cell
            Cell currentCell = colorSet.poll();
            //check neighbors of currentCell, if not visited , add to the colorSet
            Iterator<Cell> it = currentCell.getNeighbors().iterator();
            while(it.hasNext())
            {
                Cell nextCell = it.next();
                if(!nextCell.isVisited() && nextCell.getColor() == color )
                {
                    size += 1;//number of same color cells increased
                    nextCell.setVisited(true);
                    colorSet.add(nextCell);
                }
            }
        }
        return size;
    }

    /**
     * taverses the game board, calls counting method on all Cells of the board
     * @params a board object ArrayList<Cell>
     *
     */
    public void traverseBoard(ArrayList<Cell> board){
        //
        for (int i = 1; i < 5; i++) {
            scores.add(new LinkedList<Integer>());
        }
        for(int i=0;i<board.size();i++){
            int color = board.get(i).getColor() - 1;
            System.out.println(color);
            if(color > 0)
            {
                //scores.get(color).add(countingColorSetSizeBFS(board.get(i)));//add size of a current colorSet to score
                LinkedList<Integer> list = scores.get(color);//list to store size of current colorsSet
                list.add(countingColorSetSizeBFS(board.get(i)));//stores size of current colorSet
            }
        }
    }

    /**
     *  static method that creates easy-to-read paires of color/score
     * @return ArrayList of color/score pairs
     */
    public ArrayList<Integer> getCurrentScore(){
        for(int i = 0; i<scores.size();i++){//iterate through all colors of a board
            int mult = 1;
            for(int k : scores.get(i)){//iterate through LinkedList of colorSets sizes
                mult = k * mult;
            }
            finalScore.add(mult);
        }
        return finalScore;
    }

    public static void main(String[] args){

        Board board = new Board(3);
        ScoreCount a = new ScoreCount(board.getCells());

        for(int i=0;i<board.getCells().size()/2;i++){
            board.getCells().get(i).setColor(2);
        }
        for(int i =board.getCells().size()/2;i<board.getCells().size();i++){
            board.getCells().get(i).setColor(1);
        }


        a.traverseBoard(board.getCells());
        ArrayList<Integer> res = a.getCurrentScore();
        for(int i=0;i<res.size();i++) {

            System.out.println("score of color " + i + " is " + res.get(i));
        }
    }

}