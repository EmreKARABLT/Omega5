package PLAYER.Hybrid;

import GAME.Cell;
import GAME.HelloTensorFlow;
import GAME.State;
import PLAYER.MTS.Node;
import PLAYER.MTS.SELECTION_HEURISTICS.Heuristics;
import PLAYER.MTS.SELECTION_HEURISTICS.UCB1;
import PLAYER.MTS.Tree;
import PLAYER.MTS.TuplePieces;
import PLAYER.Player;
import PLAYER.RandomBot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class TreeHybrid extends Tree {




	public int simNumber = 0;
	Node root;
	private State state;
	private final Boolean ACTIONS = true;
	HashMap<TuplePieces, Node> Hash_AMAF;
	public int counter = 0;
	public RandomBot randomBot = new RandomBot("white");
	public Heuristics heuristics ;
	public int treeID ;

	public Node lastMove = null;

	public TreeHybrid(State state, Heuristics heuristics, int treeID){
		super(state,heuristics,treeID);
		this.heuristics = heuristics;
		this.state = state;
		root = new Node(null, state, null, null );
		this.treeID = treeID;
		root.setPlayerID(treeID);
		Hash_AMAF = new HashMap<>();
	}


	public Node selection(Node node ){

		Node selected ;
		//TODO: find the ideal max
		int max = Math.min( node.numberOfPossibleMoves() , 300 );
//        int max = node.getNumberOfSimulations() /300 + 5 ;
//        boolean unprune = node.getNumberOfSimulations()%300 == 0 ;
//        String name = (node.getPlayerID() == 0) ? "white" : "black";
//        System.out.print(name + " ->");

		if(node.getChildren().size() <= max ){

//			if(node == root ){
////
//				int random = (int) (Math.random() * node.getBetterChildren().size());
//				selected = node.getBetterChildren().get(random);
////
//			}else {
			ArrayList<Cell> moves = randomBot.randomMoves(node.getState());
			selected = new Node(node, state, moves.get(0), moves.get(1));
			selected = node.addChild(selected);
//			selected.color();
//			double prediction = model.predict(state.getBoard().getCells());
//			selected.uncolor();
//
//			int iter = 0 ;
//			while(prediction<0.4 && iter < 100){
//				moves = randomBot.randomMoves(node.getState());
//				selected = new Node(node, state, moves.get(0), moves.get(1));
//				selected = node.addChild(selected);
//				selected.color();
//				prediction = model.predict(state.getBoard().getCells());
//				selected.uncolor();
//				iter++;
//
//			}
				//			}
			return selected;

		} else if(node.getPlayerID()==treeID) {
			selected = getBest(node);
		}
		else{
			selected = getWorst(node);
		}

		return selected;

	}

	public void simulation(){

		Node tempRoot = root;
		Node node = root;
		simNumber=counter++;
		if (root.getBetterChildren() == null) {
			root.setBetterChildren(evaluateNodes(root));
//				System.out.println("Evaluating..");
		}
		while(!state.isGameOver() ){

			Node nextNode = selection(node);
			nextNode.color();

			node =nextNode;

			if(node.getDepth() - root.getDepth() >= 13)
				break;

		}

		double win =  1;
		if(state.isGameOver()) {

			node.getState().updatePlayerScores();
			Player winner = node.getState().getWinner();
			if (winner.getPlayerID() != treeID)
				win = 0;
		}else{

			double prediction = model.predict(state.getBoard().getCells());
			if(prediction<0.3)
				win = 0;
		}
		node.setNumberOfWins(node.getNumberOfWins() + win);
		node.setNumberOfSimulations(node.getNumberOfSimulations() + 1);
		node.add(win);

		backpropagation(tempRoot, node, win);
	}
	public void backpropagation(Node root, Node node, double win){

		while (!node.equals(root) ){
			Node parent = node.getParent();

			parent.setNumberOfWins(parent.getNumberOfWins() + win );
			parent.setNumberOfSimulations(parent.getNumberOfSimulations() + 1);
			parent.add(win);
			node.uncolor();
			node = parent;
		}
	}

	public void setRoot(Node node){

		this.root = node;
		this.root.setPlayerID(treeID);
		Hash_AMAF = new HashMap<>();
	}

	public void setRoot(State state, ArrayList<Cell> whites , ArrayList<Cell> blacks) {
		int n = whites.size();
		this.state = state;
		Node opponentsMove = new Node(null, state, whites.get(n - 1), blacks.get(n - 1));
		for (Node child : lastMove.getChildren()) {
			if (child.getWhite().getId() == opponentsMove.getWhite().getId() && child.getBlack().getId() == opponentsMove.getBlack().getId()) {
				opponentsMove = child;
				break;
			}
		}

		setRoot(opponentsMove);

	}
	public Node getRoot(){
		return this.root;
	}


	public void setLastMove(Node lastMove) {
		this.lastMove = lastMove;
	}

	public ArrayList<Node> evaluateNodes(Node parent){
		ArrayList<Cell> emptyCells = state.getBoard().getEmptyCells();
		ArrayList<Node> allPossibilities = new ArrayList<>();
		for (int i = 0; i < emptyCells.size(); i++) {
			for (int j = i+1; j < emptyCells.size(); j++) {
				Node node1 = new Node(parent , state,emptyCells.get(i),emptyCells.get(j));
				node1.color();
				node1.setAnnPrediction(model.predict(state.getBoard().getCells()));
				node1.uncolor();
				Node node2 = new Node(parent , state,emptyCells.get(j),emptyCells.get(i));
				node2.color();
				node2.setAnnPrediction(model.predict(state.getBoard().getCells()));
				node2.uncolor();
				allPossibilities.add(node1);
				allPossibilities.add(node2);
			}
		}
		Collections.sort(allPossibilities);
		ArrayList<Node> betterMoves = new ArrayList<>();
		for (int i = 0; i < Math.min(150, allPossibilities.size()); i++) {
			betterMoves.add(allPossibilities.get(i));
		}
//		for (int i = 0; i < betterMoves.size(); i++) {
//			System.out.print(betterMoves.get(i).getAnnPrediction() + " , ");
//		}
//		System.out.println("\n=====");
		return betterMoves;
	}

	public Node getBest(Node node){
		if(node.getChildren().size() == 0){
			return null;
		}
		node = this.heuristics.bestNode(node);
		return node;
	}
	public Node getWorst(Node node){
		if(node.getChildren().size() == 0){
			return null;
		}
		node = this.heuristics.worstNode(node);
		return node;
	}


	public HelloTensorFlow getModel() {
		return model;
	}
}
