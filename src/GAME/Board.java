package GAME;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Board {
    private int boardSize ;
    private ArrayList<Cell> cells ;
    private int offsetX = 718 , offsetY =382 ;
    public Board(int boardSize, int offset_x, int offset_y){
        /*
        counting
        copying the whole board for searching algorithms
         */
        this.offsetX = offset_x;
        this.offsetY = offset_y;
        this.cells = new ArrayList<>();
        this.boardSize = boardSize;
        createBoard();
    }
    public Board(int boardSize){
        this.cells = new ArrayList<>();
        this.boardSize = boardSize;
        createBoard();
        for (Cell cell :
            cells) {
            cell.setColor( (Math.random() < 0.5) ? 1 : 2 );
        }
    }

    /**
     * creates all the cells in the board , assign coordinates on GUI (x,y) and q r s variables
     */
    private void createCells(){
        int n = boardSize;
        int id = 0 ;
        for (int q = -n; q <= n ; q++) {
            int rMax = Math.max(-n , -q-n);
            int rMin = Math.min( n , -q+n);
            for( int r = rMax ; r<=rMin ; r++){
                cells.add( new Cell( q, r,-q-r ,id++));
            }
        }
    }

    /**
     * iterates over the cells in the board and creates neigborship ,( by using addNeighbor method from Cell class)
     */
    private void connectCells(){
        if(cells == null){
            System.out.println("Cells list is empty");
            return;
        }
        for (int i = 0; i < cells.size()-1; i++) {
            Cell c1 = cells.get(i);
            for (int j = i+1; j < cells.size(); j++) {
                Cell c2 = cells.get(j);
                int abs_difference = Math.abs(c1.getQ() - c2.getQ()) + Math.abs(c1.getR() - c2.getR()) + Math.abs(c1.getS() - c2.getS());
                if(abs_difference == 2 ){
                    c1.addNeighbor(c2);
                    c2.addNeighbor(c1);
                }
            }
        }
    }

    /**
     * This method will assign the center of each hexagon on the board
     */
    public void createCenters(){
        for (Cell cell : cells) {
            double radius = cell.getRADIUS();
            int q = cell.getQ();
            int r = cell.getR();
            double sqrt3 = Math.sqrt(3);
            double x = radius * (    3/2.d * q) + offsetX;
            double y = radius * (sqrt3/2.d * q + sqrt3 * r ) +offsetY;

            cell.setX(x);
            cell.setY(y);
        }
    }
    /**
     * will be called by constructor to create the board
     */
    private void createBoard(){
        createCells();
        connectCells();
        createCenters();
    }

    public Cell getCellFromPosition(double x , double y ){
        double radius = cells.get(0).getRADIUS();
        double q = 2/3.d * (x - offsetX) / radius ;
        double r = ( -1/3.d * (x-offsetX) + Math.sqrt(3)/3 * (y - offsetY) )/ radius;

        int q_i = (q >= 0 ) ? (int)  (q + 0.5) : (int)(q - 0.5) ;
        int r_i = (r >= 0 ) ? (int)  (r + 0.5) : (int)(r - 0.5) ;

        List<Cell> match = cells.stream().filter(cell -> cell.getQ() == q_i && cell.getR() == r_i).toList();

        return (match.size() > 0 ) ? match.get(0) : null ;
    }

    public int getNumberOfEmptyCells(){
        return cells.stream().filter(cell -> cell.getColor()==0).toList().size();
    }


    /**
     * Calculates the score of the given color
     * @param color is the color of the player
     * @return the score of the given color
     */
    public int scoreOfAPlayer(int color ){
        ArrayList<Integer> groups = new ArrayList<>();
        for(Cell startingCell : cells ){
            int numberOfPiecesConnectedToNotVisitedCell = numberOfPiecesConnectedToCell(color , startingCell);
            if(numberOfPiecesConnectedToNotVisitedCell > 0 ){
                groups.add(numberOfPiecesConnectedToNotVisitedCell);
            }
        }
        int multiplication = 0 ;
        if(groups.size() > 0 )
            multiplication = 1 ;
        System.out.println(color + " " + groups );
        for (int i : groups) {
            if(i != 0 )
                multiplication *= i;
        }
        for (Cell cell :
                cells) {
            cell.setVisited(false);
        }
        return multiplication;
    }
    public int numberOfPiecesConnectedToCell(int color,Cell startingCell){
        if( startingCell.isVisited() )
            return 0;
        int number_Of_pieces_in_group = (startingCell.getColor() == color) ? 1 : 0 ;

        LinkedList<Cell> queue = new LinkedList<>();
        queue.add(startingCell);
        startingCell.setVisited(true);
        while(!queue.isEmpty()){
            Cell currentCell = queue.pollLast();
            for (Cell neighborCell :
                    currentCell.getNeighbors()) {
                if(!neighborCell.isVisited() && neighborCell.getColor() == color){
                    neighborCell.setVisited(true);
                    queue.add(neighborCell);
                    number_Of_pieces_in_group++;
                }
            }
        }
        return number_Of_pieces_in_group;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }

    public void setCells(ArrayList<Cell> cells) {
        this.cells = cells;
    }


    public static void main(String[] args) {
        Board board = new Board(3);
        for (int i = 0 ; i < 37 ; i++){
            board.getCells().get(i).setColor(1);
        }
        System.out.println(board.getNumberOfEmptyCells());

    }





}
