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

/**
 * This class represents a state of the sliding tile puzzle. A state is encoded
 * as an array of integers (ints) with values corresponding to the labels of the
 * tiles. The value 0 represents to blank space on the board.
 */
public class SlidingNineStateHW3 implements Comparable<SlidingNineStateHW3> {
	// the field that holds the encoded sliding puzzle state of an instance of
	// this class
	private final int[] _state;

	// an integer that contains the index of 0 (the blank space) in the _state
	// field. this is provided to make computing the index of 0 in _state
	// faster.
	private final int _blankIndex;

	/**
	 * This is a class constructor that takes an integer array encoding of a
	 * sliding tile puzzle state.
	 * 
	 * @param state
	 *            The integer array encoding of a sliding tile puzzle state.
	 */
	public SlidingNineStateHW3(int[] state) {
		// parameters are not validated (assumed to be correct)
		int i;

		// clone the array since a different array object is required
		_state = state.clone();

		// find the index of 0 in the array
		for (i = 0; i < _state.length; ++i) {
			if (_state[i] == 0) {
				break;
			}
		}

		// assign the index of 0 to the _blankIndex field
		_blankIndex = i;
	}

	/**
	 * This method returns a new state that is derived from this state by moving
	 * the blank space up. If the move is invalid, this method returns null.
	 * 
	 * @return The derived state or null if the move is invalid.
	 */
	public SlidingNineStateHW3 moveBlankUp() {
		int[] temparr = (int[])_state.clone();                       //clone the state attribute to a temporary array
		if(_blankIndex == 0 || _blankIndex == 1 || _blankIndex == 2) //i.e. elements in the first row
			return null;
		else {                                                       //switch values of the upper element and _blankIndex and return the new state
			int temp = temparr[_blankIndex - 3];  
			temparr[_blankIndex - 3] = temparr[_blankIndex];
			temparr[_blankIndex] = temp;
			return new SlidingNineStateHW3(temparr) ;
		}
	}

	/**
	 * This method returns a new state that is derived from this state by moving
	 * the blank space down. If the move is invalid, this method returns null.
	 * 
	 * @return The derived state or null if the move is invalid.
	 */
	public SlidingNineStateHW3 moveBlankDown() {
		int[] temparr = (int[])_state.clone();                       //clone the state attribute to a temporary array
		if(_blankIndex == 6 || _blankIndex == 7 || _blankIndex == 8) //i.e. elements in the last row
			return null;
		else {                                                       //switch values of the bottom element and _blankIndex and return the new state
			int temp = temparr[_blankIndex + 3];  
			temparr[_blankIndex + 3] = temparr[_blankIndex];
			temparr[_blankIndex] = temp;
			return new SlidingNineStateHW3(temparr) ;
		}
	}

	/**
	 * This method returns a new state that is derived from this state by moving
	 * the blank space left. If the move is invalid, this method returns null.
	 * 
	 * @return The derived state or null if the move is invalid.
	 */
	public SlidingNineStateHW3 moveBlankLeft() {
		int[] temparr = (int[])_state.clone();                       //clone the state attribute to a temporary array
		if(_blankIndex == 0 || _blankIndex == 3 || _blankIndex == 6) //i.e. elements in the leftmost column
			return null;
		else {                                                       //switch values of the left element and _blankIndex and return the new state
			int temp = temparr[_blankIndex - 1];  
			temparr[_blankIndex - 1] = temparr[_blankIndex];
			temparr[_blankIndex] = temp;
			return new SlidingNineStateHW3(temparr);
		}
	}

	/**
	 * This method returns a new state that is derived from this state by moving
	 * the blank space right. If the move is invalid, this method returns null.
	 * 
	 * @return The derived state or null if the move is invalid.
	 */
	public SlidingNineStateHW3 moveBlankRight() {
		int[] temparr = (int[])_state.clone();                       //clone the state attribute to a temporary array
		if(_blankIndex == 2 || _blankIndex == 5 || _blankIndex == 8) //i.e. elements in the rightmost column
			return null;
		else {                                                       //switch values of the right element and _blankIndex and return the new state
			int temp = temparr[_blankIndex + 1];  
			temparr[_blankIndex + 1] = temparr[_blankIndex];
			temparr[_blankIndex] = temp;
			return new SlidingNineStateHW3(temparr);
		}
	}

	/*
	 * This method compare the current instance to the argument instance for
	 * equality.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object arg0) {
		SlidingNineStateHW3 right;

		right = (SlidingNineStateHW3) arg0;
		return Arrays.equals(_state, right._state)
				&& (_blankIndex == right._blankIndex);
	}

	/*
	 * This method compares the current instance to the argument instance for
	 * lexicographic ordering. This is required if a collection of states needs
	 * to be sorted.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(SlidingNineStateHW3 arg0) {
		int i;

		for (i = 0; i < 9; ++i) {
			if (_state[i] > arg0._state[i]) {
				return 1;
			}
			if (_state[i] < arg0._state[i]) {
				return -1;
			}
		}

		return 0;
	}

	/*
	 * This method returns a string representation of the current instance.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		int i;
		String result;

		result = "[";
		for (i = 0; i < (_state.length - 1); ++i) {
			result += _state[i] + " ";
		}
		result += _state[i] + "]";

		return result;
	}

	/**
	 * This is the main method of this class. This method can be used for
	 * testing the implementations of the methods in this class.
	 * 
	 * @param args
	 *            The command-line arguments passed to this program during
	 *            program invocation.
	 */
	public static void main(String[] args) {

		//
		// insert any testing code here
		//

		System.out.println("Done.");
	}
}
