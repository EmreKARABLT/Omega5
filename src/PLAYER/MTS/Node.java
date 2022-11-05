package PLAYER.MTS;

import GAME.Board;
import GAME.Cell;
import GAME.State;
import PLAYER.HumanPlayer;
import PLAYER.Player;
import PLAYER.RandomBot;

import java.util.ArrayList;
import java.util.Objects;

public class Node {

    private Node parent;
    private Node root;
    private State state;
    private ArrayList<Node> children;
    private Cell white ;
    private Cell black ;
    private int depth;
    private int numberOfSimulations;
    private int numberOfWins = 0;
    private int numberOfChildren;
    private ArrayList<Cell> emptyCells;

    public Node(Node parent,State state,Cell white , Cell black){
        this.parent = parent;
        this.state = state;
        this.white = white;
        this.black = black;
        this.emptyCells = state.getBoard().getEmptyCells();
        numberOfChildren = 0;
    }

    public boolean isRoot(){
        return parent == null;
    }
    public Node addChild(Node child){
        for (Node node: children) {
            if( child.equals(node) )
                return node;
        }
        children.add(child);
        numberOfChildren++;
        return child;
    }

    public State getRandomState(){

        ArrayList<Cell> emptyCells = state.getBoard().getEmptyCells();

        int r1 = (int) (Math.random() * emptyCells.size());
        Cell white = emptyCells.remove(r1);

        int r2 = (int) (Math.random() * emptyCells.size());
        Cell black = emptyCells.remove(r2);

        State newState = new State(state.getBoard().getBoardSize(), state.getPlayers());
        Board board = state.getBoard();

        board.getCells().add(white);
        board.getCells().add(black);
        newState.setBoard(board);

        return newState;

    }

    public boolean terminalNode(){
        return state.isGameOver();
    }

    public int numberOfPossibleMoves(){
        int n = state.getBoard().getNumberOfEmptyCells();
        int k = n - 2;
        return factorial(n)/(2 * factorial(k));
    }

    public boolean Do_I_Have_More_Moves(){
        int n = state.getBoard().getNumberOfEmptyCells();
        int k = n - 2;
        int posibleMoves = factorial(n)/(2 * factorial(k));
        if(posibleMoves <= numberOfChildren){
            return false;
        }
        return true;
    }

    public int factorial (int number) {

        if (number == 0){
            return 1;
        } else{
            return number * factorial(number - 1);
        }

    }


    public boolean duplicatedRandomMove(State state){

        for (Node child : children) {
            if (child.getState().equals(state)) {
                return false;
            }
        }
        return true;
    }

    public void possibleStates(){

        for (int i = 0; i < emptyCells.size(); i++) {

            for (int j = i + 1; j < emptyCells.size(); j++) {

                Cell c1 = emptyCells.get(i);
                Cell c2 = emptyCells.get(j);
                c1.setColor(0);
                c2.setColor(1);

                children.add(new Node(this, state, c1, c2));
            }
        }
    }


    public double winningProbability(){
        if(numberOfSimulations > 0 )
            return numberOfWins/(double)numberOfSimulations;
        return 0 ;
    }

    public Node getParent() {
        return parent;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Node> children) {
        this.children = children;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public State getState() {return state;}
    public void setState(State state) {this.state = state;}

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public int getNumberOfSimulations() {
        return numberOfSimulations;
    }

    public void setNumberOfSimulations(int numberOfSimulations) {
        this.numberOfSimulations = numberOfSimulations;
    }

    public int getNumberOfWins() {
        return numberOfWins;
    }

    public void setNumberOfWins(int numberOfWins) {
        this.numberOfWins = numberOfWins;
    }

    public Cell getBlack() {
        return black;
    }

    public void setBlack(Cell black) {
        this.black = black;
    }

    public ArrayList<Cell> getEmptyCells() {
        return emptyCells;
    }

    public void setEmptyCells(ArrayList<Cell> emptyCells) {
        this.emptyCells = emptyCells;
    }

    public Cell getWhite() {
        return white;
    }

    public void setWhite(Cell white) {
        this.white = white;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node node = (Node) o;
        return Objects.equals(state, node.state) && Objects.equals(white, node.white) && Objects.equals(black, node.black);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent, state, white, black, depth);
    }


    /*
        public Node(State state, Node parent, Cell cellWhite, Cell cellBlack){
        this.state = state;
        this.parent = parent;
        this.white = cellWhite;
        this.black = cellBlack;
    }
    */
    public static void main(String[] args) {
        ArrayList<Player> players = new ArrayList<>(){};
        players.add(new HumanPlayer("White") );
        players.add(new RandomBot("Black") );
        State state = new State(3 , players);

        Cell white = state.getBoard().getCells().get(0);
        Cell black = state.getBoard().getCells().get(1);
        Cell black2 = state.getBoard().getCells().get(2);
        Node node1 = new Node(null,state,white,black );
        Node node2 = new Node(null,state,white,black );
        System.out.println(node1.equals(node2));
    }
}

