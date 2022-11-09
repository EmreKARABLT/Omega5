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
    private Integer white ;
    private Integer black ;
    private int depth;
    private int numberOfSimulations;
    private int numberOfWins;
    private int numberOfChildren;
    private ArrayList<Cell> emptyCells;

    public Node(Node parent,State state,Integer white , Integer black){
        this.parent = parent;
        this.depth =  (parent != null)  ? parent.getDepth()+1 : 0;
        this.state = new State(state);
        this.white = white;
        this.black = black;
        if(white!= null) {
            this.state.getBoard().getCells().get(this.white).setColor(0);
            this.state.getBoard().getCells().get(this.black).setColor(1);
        }
        this.emptyCells = this.state.getBoard().getEmptyCells();
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
        if(children.size()==0)
            return null;
        return children.get((int)(Math.random()*children.size()));

    }

    public boolean terminalNode(){
        return state.isGameOver();
    }

    public int numberOfPossibleMoves(){
        int n = this.state.getBoard().getNumberOfEmptyCells();
        int k = 2;
        return binomCoef(n,k);
    }


    public int binomCoef (int n , int k) {
        int mult = 1;
        if(n-k <= 0)
            return mult;
        for (int i = n-k+1; i <= n; i++) {
            mult*=i;
        }
        return mult;
    }


    public boolean duplicatedRandomMove(State state){

        for (Node child : children) {
            if (child.getState().equals(state)) {
                return false;
            }
        }
        return true;
    }
    //TODO fix all children
    public void allChildren(){

        int desiredSize = 15;
        //TODO fix Number of possible moves !!
        int numberOfPossibleMoves = numberOfPossibleMoves();
//        System.out.println(numberOfPossibleMoves);

        //TODO add if statement to create nodes in order if number of possible moves is smaller than possible states
        if(numberOfPossibleMoves <= desiredSize ){
            for (int i = 0; i < emptyCells.size(); i++) {
                for (int j = i+1; j < emptyCells.size(); j++) {
                    int c1= emptyCells.get(i).getId();
                    int c2= emptyCells.get(j).getId();
                    if(!this.doesContain(c1,c2))
                        children.add(new Node(this,state,c1,c2));
                    if(!this.doesContain(c2,c1))
                        children.add(new Node(this,state,c2,c1));
                }
            }
        }else {
            while (children.size() < desiredSize) {
                ArrayList<Cell> temp = state.getBoard().getEmptyCells();
                Cell cell_w = temp.get((int) (Math.random() * temp.size()));
                temp.remove(cell_w);
                Cell cell_b = temp.get((int) (Math.random() * temp.size()));
                int c1 = cell_w.getId();
                int c2 = cell_b.getId();
                if (c1 != c2 && !this.doesContain(c1, c2))
                    children.add(new Node(this, state, c2, c1));
                else
                    System.out.println(c1+" "+c2 +" duplicated");
            }
        }
    }

    public ArrayList<Integer[]> conbinations(){

        ArrayList<Integer[]> conbinations = new ArrayList<>();
        ArrayList<Cell> emptyCells = state.getBoard().getEmptyCells();

        for (int i = 0; i < state.getBoard().getNumberOfEmptyCells(); i++) {
            for (int j = 0; i < state.getBoard().getNumberOfEmptyCells(); j++) {
                if(i != j){
                    Integer[] cells = new Integer[2];
                    cells[0] = emptyCells.get(i).getId();
                    cells[1] = emptyCells.get(j).getId();
                    conbinations.add(cells);
                }


                /*
                cells[1] = emptyCells.get(i);
                cells[0] = emptyCells.get(j);
                conbinations.add(cells);
                */
            }
        }
        return conbinations;
    }

    public void get_N_Children(int N){

        ArrayList<Integer[]> child = conbinations();

        for (int i = 0; i < N; i++) {
            int index = ((int) (Math.random() * child.size()));
            Integer[] cells = child.get(index);
            Integer a = cells[0];
            Integer b = cells[1];
            children.add(new Node(this, state, a, b));
            child.remove(index);
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
    public Integer getBlack() {return black;}
    public void setBlack(Integer black) {this.black = black;}
    public ArrayList<Cell> getEmptyCells() {return emptyCells;}
    public void setEmptyCells(ArrayList<Cell> emptyCells) {this.emptyCells = emptyCells;}
    public Integer getWhite() {return white;}
    public void setWhite(Integer white) {this.white = white;}
    public int getDepth() {return depth;}
    public void setDepth(int depth) {this.depth = depth;}
    public int getNumberOfChildren() {return numberOfChildren;}
    public void setNumberOfChildren(int numberOfChildren) {this.numberOfChildren = numberOfChildren;}

    public boolean doesContain(Integer white ,Integer black) {
        for (Node node :
                children) {
            if (Objects.equals(node.getWhite(), white) && Objects.equals(node.getBlack(),black)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent, state, white, black, depth);
    }

    @Override
    public String toString() {
        return "Node " + state.toString() + " {w,b} " + white + " " + black ;
    }

    public static void main(String[] args) {
        ArrayList<Player> players = new ArrayList<>(){};
        players.add(new HumanPlayer("White") );
        players.add(new RandomBot("Black") );
        State state = new State(3 , players);

        Cell white = state.getBoard().getCells().get(0);
        Cell black = state.getBoard().getCells().get(1);
        Cell black2 = state.getBoard().getCells().get(2);
        Node node1 = new Node(null,state,white.getId(),black.getId() );
        Node node2 = new Node(null,state,white.getId(),black.getId() );
        System.out.println(node1.equals(node2));
    }
}

