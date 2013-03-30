/**
 * CSC 171 	Assignment No. 3
 * Submitted by Kevin Eric R. Siangco 
 * 2010-7171
 * To compile:
 * 
 * javac -cp HW3.jar;. BFSSlidingPuzzleSearcherHW3.java (Windows)
 * javac -cp HW3.jar:. BFSSlidingPuzzleSearcherHW3.java (Linux/Mac)
 
 * To run:
 * 
 * java -cp HW3.jar;. BFSSlidingPuzzleSearcherHW3 (Windows)
 * java -cp HW3.jar:. BFSSlidingPuzzleSearcherHW3 (Linux/Mac)
 
 *(Tested and Working)
 */

import java.util.*;
import java.util.LinkedList;
import java.util.TreeSet;

public class BFSSlidingPuzzleSearcherHW3 implements
		BFSSlidingPuzzleSearcherInterface {

	/**
	 * This is a utility method that you can use to enqueue the successor nodes
	 * of the state contained in the node parameter into the queue parameter.
	 * 
	 * @param queue
	 *            The queue where the successor nodes will be enqueued.
	 * @param node
	 *            The node whose successors will be enqueued.
	 */
	static void enqueueSuccessors(LinkedList<SlidingNineNodeHW3> queue,
			SlidingNineNodeHW3 node) {
		SlidingNineStateHW3 state, temp;

		// state contains the state contained in node
		state = node.getState();
		if ((temp = state.moveBlankUp()) != null) {
			// moving the blank up is a valid move starting from state state

			// enqueue a new node containing the resultant state and the up move
			queue.addLast(new SlidingNineNodeHW3(node, temp, 'U'));
		}
		if ((temp = state.moveBlankDown()) != null) {
			// moving the blank down is a valid move starting from state state

			// enqueue a new node containing the resultant state and the down
			// move
			queue.addLast(new SlidingNineNodeHW3(node, temp, 'D'));
		}
		if ((temp = state.moveBlankLeft()) != null) {
			// moving the blank left is a valid move starting from state state

			// enqueue a new node containing the resultant state and the left
			// move
			queue.addLast(new SlidingNineNodeHW3(node, temp, 'L'));
		}
		if ((temp = state.moveBlankRight()) != null) {
			// moving the blank right is a valid move starting from state state

			// enqueue a new node containing the resultant state and the right
			// move
			queue.addLast(new SlidingNineNodeHW3(node, temp, 'R'));
		}
	}

	/*
	 * This method implements the memorizing breadth-first search of the state
	 * space graph of the sliding tile puzzle. The search starts from the state
	 * startState with goals of either state goalState1 or state goalState2. The
	 * fourth parameter gameSize indicates the size of the board (between 3 to 8
	 * inclusive). This method should only solve games of size 3 and should
	 * return null for games of other sizes. A state passed as a parameter is
	 * encoded as an array of 9 integers (ints) with values corresponding to the
	 * labels of the tiles. The blank space is represented by the value 0.
	 * 
	 * @param startState The starting state of the search encoded as an array of
	 * 9 ints.
	 * 
	 * @param goalState1 The first goal state of the search encoded as an array
	 * of 9 ints.
	 * 
	 * @param goalState2 The second goal state of the search encoded as an array
	 * of 9 ints.
	 * 
	 * @param gameSize The size of the puzzle. This is fixed to 3 for this
	 * homework.
	 * 
	 * @return The sequence of moves represented as a String object that solves
	 * the given puzzle if the size is 3 or null if the size of the puzzle is
	 * not 3.
	 */
	public String solveSlidingPuzzleBFS(int[] startState, int[] goalState1,
			int[] goalState2, int gameSize) {
		long startTime = System.currentTimeMillis();
		if(gameSize > 3)
			return null;
		// the structure for storing the list of open (unvisited) nodes		
		LinkedList<SlidingNineNodeHW3> open = new LinkedList<SlidingNineNodeHW3>(); 

		// the structure for storing the list of visited states
		TreeSet<SlidingNineStateHW3> closed = new TreeSet<SlidingNineStateHW3>();  //I modified it to store a list of <SlidingNineStateHW3> instead of <SlidingNineNodeHW3> since my implementation compares the nodes' state and not the node itself

		// the variable holding a reference to the current node being processed
		SlidingNineNodeHW3 current;
		
		//variables to store list of moves to make
		String movelist = "";
		
		SlidingNineStateHW3 goal1 = new SlidingNineStateHW3(goalState1);  //state to store the first goal
		SlidingNineStateHW3 goal2 = new SlidingNineStateHW3(goalState2);  //state to store the second goal
		SlidingNineNodeHW3 startnode = new SlidingNineNodeHW3(null, new SlidingNineStateHW3(startState));  //instantiate the starting node with a null parent and an instance of the start state
		current = startnode; 	//set the current node being processed to reference the starting node
		open.add(current); 	//add current(i.e. the starting node)node to the list of unvisited nodes

		//this loop is my implementation of the breadth first search algorithm 
		while(current.getState().compareTo(goal1) != 0  && current.getState().compareTo(goal2) != 0){  //while current state is not equal to either of the two goals..
			if(!closed.contains(current.getState())) {   //if the state of the current node being processed is not contained in the list of visited states
				enqueueSuccessors(open, current);        //enqueue the sucessors
				closed.add(current.getState());          //add the current state to the list of visited states to prevent it from coming back to this state
				open.remove(); 					         //dequeue the current node being processed
				current = open.getFirst(); 		         //set the current node to point to the new head of the queue;
			}
			else {	//if the current state has already been visited
				open.remove();							 //dequeue the the current node being processed
				current = open.getFirst();               //set the current node to point to the new head of the queue; 
			}
		}
		
		SlidingNineNodeHW3 pathfinder = current;         //this node is a reference to the last node processed(i.e. the node which contains the reached goal state) 
		//this loop gets the path from the reached goal state back to the start state
		while(pathfinder.getParent() != null) {			 //while the parent of the node is not null..
			movelist = movelist + pathfinder.getMove();	 //concatenate the node's 'Move' attribute to the movelist variable
			pathfinder = pathfinder.getParent();		 //set the pathfinder node to reference the parent of the current node
		}
		movelist = new StringBuffer(movelist).reverse().toString();   //reverse the string since the string was originally concatenated from successor to parent
		long duration = System.currentTimeMillis() - startTime;
		System.out.println("Program executed for " + duration + " milliseconds");
		return movelist;                                              //return the list of moves to be used by the jar file in solving the puzzle
	}

	/**
	 * This is the main function that starts the graphical user interface of the
	 * application. The solveSlidingPuzzleBFS method of this class is called
	 * from inside the GUI application. Nothing needs to be done in this method.
	 * 
	 * @param args
	 *            The command-line arguments passed during the invocation of
	 *            this program (not used).
	 */
	public static void main(String[] args) {
		BFSSlidingPuzzleHW3.runApplication(new BFSSlidingPuzzleSearcherHW3());
	}
}
