package GAME;
import java.util.*;

 class ScoreCount {
     private ArrayList<LinkedList<Integer>> scores =new  ArrayList<>();//ArrayList to store scores for all colors

     private HashMap<String,Integer> totalScore = new HashMap<>();
     private  ArrayList<Cell> board;//?


     ScoreCount(ArrayList<Cell> board){
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

         for(int i=0;i<board.size();i++){
             int color = board.get(i).getColor();
             if(color!=0)
             {
                 scores.get(color).add(countingColorSetSizeBFS(board.get(i)));//add size of a current colorSet to score

             }
         }
     }

     /**
      *  static method that creates easy-to-read paires of color/score
      * @return HashMap of color/score pairs
      */
    public HashMap<String,Integer> getCurrentScore(){
        for(int i = 1; i<scores.size();i++){//iterate through all colors of a board
            int sum = 0;
            for(int k : scores.get(i)){//iterate through LinkedList of colorSets sizes
                sum += k ;
            }
            totalScore.put("color#"+i,sum);//create a record of type ("color#1",34)
        }
        return totalScore;
    }

 }
