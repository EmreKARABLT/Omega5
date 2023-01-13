package GAME;

import UI.Menu;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Board implements Cloneable{
    private int boardSize ;
    private ArrayList<Cell> cells ;
    /*
    TODO Everyone can change these OffsetX and OffsetY values -> Run the Show class and there will be a line which gives you offset values for your screen
         Replace the following line with the printed values on console
     */
    public static double RADIUS = 30;
    private int offsetX, offsetY;
    public Board(int boardSize){
        this.offsetX = 718 ;
        this.offsetY = 382;
        this.cells = new ArrayList<>();
        this.boardSize = boardSize;
        createBoard();

//        colorTheCellsRandomly();
    }
    public Board(int boardSize,int offsetX , int offsetY){

        this.cells = new ArrayList<>();
        this.boardSize = boardSize;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        createBoard();

//        colorTheCellsRandomly();
    }

    public Board(Board board){
        offsetX = Menu.WIDTH/2;
        offsetY = Menu.HEIGHT/2;
        this.boardSize = board.boardSize;
        this.cells = new ArrayList<>();
        createBoard();
        copyCells(board.getCells());
    }
    public void copyCells(ArrayList<Cell> cells){
        for (int i = 0; i < cells.size(); i++) {
            this.cells.get(i).setColor(cells.get(i).getColor());
        }
    }

    /**
     * Colors each cell to white or black
     * Implemented for testing purposes
     */
    public void colorTheCellsRandomly(){
        for (int i = 0 ; i < cells.size()-5 ; i++) {
            double random = Math.random();
            if(random < 0.5 )
                cells.get(i).setColor(0);
            else cells.get(i).setColor(1);
        }

    }
    /**
     * Creates all the cells in the board , assign coordinates on GUI (x,y) and q r s and ID variables
     */
    private void createCells(){
        int n = boardSize;
        int id = 0;
        if(cells.isEmpty()) {
            for (int q = -n; q <= n; q++) {
                int rMax = Math.max(-n, -q - n);
                int rMin = Math.min(n, -q + n);
                for (int r = rMax; r <= rMin; r++) {
                    Cell cell = new Cell(q, r, -q - r, id++);
                    cells.add(cell);
                }
            }
        }
    }

    /**
     * iterates over the cells in the board and creates neigborship ,( by using addNeighbor method from Cell class )
     */
    private void connectCells(){
        if(cells == null){
            createCells();
        }
        for (int i = 0; i < cells.size(); i++) {
            Cell c1 = cells.get(i);
            for (int j = 0; j < cells.size(); j++) {
                Cell c2 = cells.get(j);
                int abs_difference = Math.abs(c1.getQ() - c2.getQ()) + Math.abs(c1.getR() - c2.getR()) + Math.abs(c1.getS() - c2.getS());
                if (abs_difference == 2 && i != j ) {
                    c1.addNeighbor(c2);
                    c2.addNeighbor(c1);
                }
            }
        }
    }
    /**
     * will be called by constructor to create the board
     */
    private void createBoard(){
        if(this.cells == null){
            cells = new ArrayList<>();
        }
        RADIUS = (int)( (offsetY*2 - 50) / (boardSize*2 + 1) / 2 );
        createCells();
        connectCells();
        createCenters();
    }

    /**
     * This method will assign the center of each hexagon on the board , x and y values
     */
    public void createCenters(){
        for (Cell cell : cells) {
            RADIUS = (int)( (offsetY*2 - 50) / (boardSize*2 + 1) / 2 );
            int q = cell.getQ();
            int r = cell.getR();
            double sqrt3 = Math.sqrt(3);
            double x = RADIUS * (    3/2.d * q) +              offsetX;
            double y = RADIUS * (sqrt3/2.d * q  + sqrt3 * r ) +offsetY;

            cell.setX(x);
            cell.setY(y);
        }
    }

    /**
     * Finds the corresponding cell to x and y coordinates on screen
     * @param x x position on screen
     * @param y y position on screen
     * @return a cell which is located at x and y coordinates
     */
    public Cell getCellFromPosition(double x , double y ){
        double radius = RADIUS = (int)( (offsetY*2 - 50) / (boardSize*2 + 1) / 2 );
        double q =    2/3.d * (x - offsetX) / radius ;
        double r = ( -1/3.d * (x - offsetX) + Math.sqrt(3)/3 * (y - offsetY) )/ radius;

        int q_i = (q >= 0 ) ? (int)  (q + 0.5) : (int)(q - 0.5) ;
        int r_i = (r >= 0 ) ? (int)  (r + 0.5) : (int)(r - 0.5) ;

        List<Cell> match = cells.stream().filter(cell -> cell.getQ() == q_i && cell.getR() == r_i).toList();

        return (match.size() > 0 ) ? match.get(0) : null ;
    }

    /**
     * iterates over the cells to check how many cells are empty
     * @return the number of empty cells
     */
    public int getNumberOfEmptyCells(){
        return cells.stream().filter(cell -> cell.getColor()==-1).toList().size();
    }

    public ArrayList<Cell> getEmptyCells(){
        ArrayList<Cell> emptyCells= new ArrayList<>();
        for (Cell cell :
                cells) {
            if(cell.isEmpty())
                emptyCells.add(cell);
        }
        return emptyCells;
    }
    /**
     * Calculates the score of the given color
     * @param color is the color of the player
     * @return the score of the given color
     */
    public int scoreOfAPlayer(int color ){
        ArrayList<Integer> group = getGroupForColor(color);
        int scoreOfPlayer = multiplyTheGivenArrayList( group);
        setAllCellsToNotVisited();
        return scoreOfPlayer;
    }

    public ArrayList<Integer> getGroupForColor(int color){
        ArrayList<Integer> groups = new ArrayList<>();

        for(Cell startingCell : cells ){
            if(startingCell.getColor() == color && !startingCell.isVisited()){
                Integer numberOfPiecesConnectedToStartingCell = numberOfPiecesConnectedToCell(color , startingCell);
                if(numberOfPiecesConnectedToStartingCell != null)
                    groups.add(numberOfPiecesConnectedToStartingCell);
            }
        }
        return groups;
    }

    /**
     * this method counts how many pieces with the given color is connected , starts to search from given cell
     * @param color given color
     * @param startingCell the cell which we will start searching from
     * @return if the color doesnt match with the starting cell's color returns 0 , else returns the amount of cell with
     * the same color is connected to the provided cell
     */
    public Integer numberOfPiecesConnectedToCell(int color,Cell startingCell){

        if( startingCell.isVisited() && startingCell.getColor() != color )
            return null;

        int number_Of_pieces_in_group = 1;
        LinkedList<Cell> queue = new LinkedList<>();
        LinkedList<Cell> visited = new LinkedList<>();
        queue.add(startingCell);
        visited.add(startingCell);
        startingCell.setVisited(true);
        while(!queue.isEmpty()){
            Cell currentCell = queue.pollLast();
            for (Cell neighborCell :
                    currentCell.getNeighbors()) {
                if(!neighborCell.isVisited() && neighborCell.getColor() == color){
                    neighborCell.setVisited(true);
                    visited.add(neighborCell);
                    queue.add(neighborCell);
                    number_Of_pieces_in_group++;
                }
            }

        }
        return number_Of_pieces_in_group;
    }

    /**
     * multiplies all the integers in the given arrayList
     * @param integerList an arrayList consists integers
     * @return multiplication of elements in the provided list
     */
    public static int multiplyTheGivenArrayList(ArrayList<Integer> integerList){
        if(integerList.size() == 0 ) return 0;
        int multiplication = 1 ;

        for (int numberOfElementsInGroups : integerList) {
            if( numberOfElementsInGroups != 0 ) {
                multiplication *= numberOfElementsInGroups;
            }
        }
        return multiplication;
    }

    /**
     * sets all the visited variable of cells to False
     */
    public void setAllCellsToNotVisited(){
        for (Cell cell :
                cells) {
            cell.setVisited(false);
        }
    }

    public void clearBoard(){
        for (Cell cell : cells) {
            cell.reset();
        }
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

    public int getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Board)) return false;
        Board board = (Board) o;

        for (int i = 0; i < cells.size(); i++) {
            if(cells.get(i).getColor() != board.getCells().get(i).getColor() ){
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cells);
    }

    @Override
    public Board clone() {
        try {
            Board clone = (Board) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
    public String idString(){
        String idString = "";
        for (Cell cell :
                cells) {
            idString += cell.getId();
            idString += ",";
        }

        return idString;
    }
    @Override
    public String toString() {
        StringBuilder boardString = new StringBuilder();
        for (Cell cell : cells) {
            boardString.append(cell.getColor() + 1);
            boardString.append(",");
        }

        return boardString.toString();
    }
}
