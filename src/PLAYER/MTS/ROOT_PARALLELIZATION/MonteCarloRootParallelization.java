package PLAYER.MTS.ROOT_PARALLELIZATION;

import GAME.Cell;
import GAME.State;
import PLAYER.MTS.Node;
import PLAYER.MTS.SELECTION_HEURISTICS.Heuristics;
import PLAYER.MTS.SELECTION_HEURISTICS.UCB1;
import PLAYER.Player;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.CountDownLatch;

public class MonteCarloRootParallelization extends Player implements Runnable{



	public static double SEARCHTIME = 100;
	private final double MAX_SIMULATION = 1;
	boolean isRave = false ;
	public static double winProb = 0;
	public TreeParallel finalTree ;

	public MonteCarloRootParallelization(String playerName, Heuristics heuristics){
		this.heuristics = heuristics;
		this.playerName = playerName;
		this.heuristicName = heuristics.getName();
		if(playerName.contains("Black") || playerName.contains("black"))
			this.playerID = 1  ;
		else
			this.playerID = 0  ;
	}

	@Override
	public boolean isBot(){return true;};

	@Override
	public ArrayList<Cell> getMoves(State state){
		int n = 7;
		CountDownLatch finalTreeLatch = new CountDownLatch(n);
		finalTree = new TreeParallel(finalTreeLatch,state.clone(),new UCB1());
		finalTree.rootExpansion();

		CountDownLatch latch = new CountDownLatch(n);
		Thread[] threads = new Thread[n];
		TreeParallel[] trees = new TreeParallel[n];
		for (int i = 0; i < threads.length; i++) {
			TreeParallel tree= new TreeParallel(latch , new State(state) , heuristics);
			trees[i] = tree;
			threads[i] = new Thread(tree);
		}
		for (int i = 0; i < threads.length; i++) {
			threads[i].start();
		}
		try{
			latch.await();
		}catch (Exception e){
			e.printStackTrace();
		};

		ArrayList<Node> finalChildren = finalTree.root.getChildren();

		for (int i = 0; i < trees.length; i++) {
			TreeParallel tree = trees[i];
			finalTree.root.concat(tree.root);

			for (int j = 0; j < finalChildren.size() ; j++) {
				Node node = finalChildren.get(j);
				ArrayList<Node> childrenOfEachTree = tree.root.getChildren();

				for (int k = 0; k < childrenOfEachTree.size() ; k++) {
					node.concat(childrenOfEachTree.get(k));
				}
			}
		}

		Node winner = finalChildren.get(0);
		double max = winner.getNaive();
		for (Node node :
				finalTree.root.getChildren()){
			if(node.getNaive() > max){
				max = node.getNaive();
				winner = node;
			}
		}

//		Node winner = finalChildren.get(0);
//		double max = winner.getNaive();
//		for (Node node :
//				finalTree.root.getChildren()){
//			if(node.getNaive() > max){
//				max = node.getNaive();
//				winner = node;
//			}
//		}
		winRateCalculator(winner);
		winner.getWhite().setColor(0);
		winner.getBlack().setColor(1);
		state.addWhite(winner.getWhite());
		state.addBlack(winner.getBlack());
		state.getPlayers().get(0).setScore(state.getBoard().scoreOfAPlayer(0));
		state.getPlayers().get(1).setScore(state.getBoard().scoreOfAPlayer(1));
		state.nextTurn();
		state.nextTurn();

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if(GUI!=null) {
					GUI.updateScores();
					GUI.updateColors();
					GUI.updateScores();
					GUI.updateColorBars();
					GUI.endFrame();
					GUI.repaint();
				}
			}
		});
//		System.out.println(treePP.getHash_AMAF().isEmpty());
		return moves;
	}
	public void reset(){
		this.score = 0 ;
		this.currentPiecesID = 0 ;
	}

	public String winRateCalculator(Node winner){
		double rate = 0;
		rate = (winner.getNumberOfWins() /  winner.getNumberOfSimulations()) * 100;
		int decimals = 100000;
		double winRate = (double) Math.round(rate * decimals) / decimals;
		double winRateAmaf = (double) Math.round(winner.getNumberOfWinsAMAF()/winner.getNumberOfSimulationsAMAF()*100 * decimals) / decimals;
		int numberOfNonRootNodes = (finalTree.getNumberOfNodesDiscovered()-1);
		int numberOfNonLeafNodes = finalTree.getNumberOfNodesDiscovered() - finalTree.getNumberOfLeafNodes();

		double averageBranchingFactor = (numberOfNonRootNodes * 1.0) / numberOfNonLeafNodes;
		winProb = winRate;
		return  "\n" +
				"[8Player = " + ((winner.getPlayerID()==0)?"White":"Black") + " \n" +
//                "Number Of Discovered Nodes " + tree.getNumberOfNodesDiscovered() + "\n" +
//                "Number Of Leaf Nodes " + tree.getNumberOfLeafNodes() + "\n" +
//                "Average Branching Factor " + averageBranchingFactor + "\n" +
				"Total Number Of Simulations = " + finalTree.root.getNumberOfSimulations() + " \n"+
				"Ratio Of Explored Children = " + finalTree.root.ratioOfDiscovered() +"\n"+
//				"Heuristic = " + winner.getState().getCurrentPlayer() + " \n" +
				"Wins(U) : " +  winner.getNumberOfWins() + " sims: " + winner.getNumberOfSimulations() + " \n" +
//				"Wins(A) : " +  winner.getNumberOfWinsAMAF() + " sims: " + winner.getNumberOfSimulationsAMAF() + " \n" +
				"Win Rate (U)= " + winRate + " %" + "\n" +
//				"Win Rate (A)= " + winRateAmaf + " %" + "\n" +
//				"Is AMAF MORE IMPORTANT = " + (winner.getNumberOfSimulationsAMAF() >3* winner.getNumberOfSimulations()) + "\n"+
				"Your chances of victory = " + (100 - winRate) + " %]";

	}



	@Override
	public void run() {
		this.moves = getMoves(state);
	}
}
