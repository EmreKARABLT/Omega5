package PLAYER.MTS;

import GAME.Cell;
import GAME.State;
import PLAYER.HumanPlayer;
import PLAYER.Player;
import PLAYER.RandomBot;

import java.util.ArrayList;
import java.util.Objects;

public class Node {
    //TODO create the clone of the state all the time.

    private Node parent;
    private Node root;
    private State state;
    private ArrayList<Node> children;
    private Cell white ;
    private Cell black ;
    private int depth;
    private int numberOfSimulations;
    private int numberOfWins;
    private int numberOfChildren;
    private ArrayList<Cell> emptyCells;

    public Node(Node parent,State state,Cell white , Cell black){
        this.parent = parent;
        this.state = state;
        this.white = white;
        this.black = black;
        try {
            this.state = (State) (state.clone());
        }catch (Exception e){
            e.printStackTrace();
        }
        if(white != null) {
            state.getBoard().getCells().get(white.getId()).setColor(0);
            state.getBoard().getCells().get(black.getId()).setColor(1);
            state.updatePlayerScores();
        }
        this.emptyCells = state.getBoard().getEmptyCells();
        numberOfWins = 0;
        numberOfChildren = 0;
        numberOfSimulations = 0;
        children = new ArrayList<>();
    }

    public boolean isRoot(){
        return parent == null;
    }
    public Node addChild(Node child){
        for (Node node: children) {
            if(child.equals(node)){
                return node;
            }
        }
        children.add(child);
        numberOfChildren++;
        return child;
    }

    public Node getRandomChild(){
        //TODO correct this method
        if(children.size()==0)
            return null;
        return children.get((int)(Math.random()*children.size()));

    }

    public boolean terminalNode(){
        return state.isGameOver();
    }

    public int numberOfPossibleMoves(){
        int n = state.getBoard().getNumberOfEmptyCells();
        int k = 2;
        return factorial(n)/(factorial(n-k));
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
        if (number <= 1){
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

    public void allChildrens(){

        int desiredSize = 100;
        int numberOfPossibleMoves = numberOfPossibleMoves();
        System.out.println(numberOfPossibleMoves);
        outer:
        for (int i = 0; i < emptyCells.size(); i++) {

            for (int j = i + 1; j < emptyCells.size(); j++) {

                Cell c1 = emptyCells.get(i);
                Cell c2 = emptyCells.get(j);
                c1.setColor(0);
                c2.setColor(1);
                if(!this.doesContain(c1,c2))
                    children.add(new Node(this, state, c1, c2));
                if(!this.doesContain(c2,c1))
                    children.add(new Node(this, state, c2, c1));

                if(children.size() >= Math.min(desiredSize,numberOfPossibleMoves)){
                    break outer;
                }
//                break outer;
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
    public Node getRoot() {return root;}
    public void setRoot(Node root) {this.root = root;}
    public int getNumberOfSimulations() {return numberOfSimulations;}
    public void setNumberOfSimulations(int numberOfSimulations) {this.numberOfSimulations = numberOfSimulations;}
    public int getNumberOfWins() {return numberOfWins;}
    public void setNumberOfWins(int numberOfWins) {this.numberOfWins = numberOfWins;}
    public Cell getBlack() {return black;}
    public void setBlack(Cell black) {this.black = black;}
    public ArrayList<Cell> getEmptyCells() {return emptyCells;}
    public void setEmptyCells(ArrayList<Cell> emptyCells) {this.emptyCells = emptyCells;}
    public Cell getWhite() {return white;}
    public void setWhite(Cell white) {this.white = white;}
    public int getDepth() {return depth;}
    public void setDepth(int depth) {this.depth = depth;}
    public int getNumberOfChildren() {return numberOfChildren;}
    public void setNumberOfChildren(int numberOfChildren) {this.numberOfChildren = numberOfChildren;}

    public boolean doesContain(Cell white ,Cell black) {
        for (Node node :
                children) {
            if (node.getWhite().getId() == white.getId() && node.getBlack().getId() == black.getId()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent, state, white, black, depth);
    }

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

