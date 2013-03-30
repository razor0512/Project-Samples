/**
 * This class represents a node in the state space graph of the sliding puzzle.
 * This class is provided as a convenient way of representing a node in the
 * state space graph. This class provides an easy way to access the state
 * contained in the node, the parent node of this node, and the move from the
 * parent node to this node that generated the state contained in this node.
 * 
 * @author Harchris
 */
public class SlidingNineNodeHW3 implements Comparable<SlidingNineNodeHW3> {
	// the following fields are assumed to be immutable

	// the field that contains the reference to the parent of this node
	private final SlidingNineNodeHW3 _parent;

	// the field that contains the reference to the state contained by this node
	private final SlidingNineStateHW3 _state;

	// the field that contains the move that generated the state in this node
	// from the state of the parent of this node
	private final char _move;

	/**
	 * This is the class constructor that takes two parameters. The first
	 * parameter is a reference to the parent of this node and the second
	 * parameter is a reference to the state that will be contained in this
	 * node.
	 * 
	 * @param parent
	 *            The reference to the parent of this node.
	 * @param state
	 *            The state that will be contained in this node.
	 */
	public SlidingNineNodeHW3(SlidingNineNodeHW3 parent, SlidingNineStateHW3 state) {
		_parent = parent;
		_state = state;
		_move = ' ';
	}

	/**
	 * This class constructor takes three parameters. The first parameter is a
	 * reference to the parent of this node, the second parameter is a reference
	 * to the state that will be contained in this node, and the last parameter
	 * contains the move that generated the state in the second parameter from
	 * the state of the parent of this node.
	 * 
	 * @param parent
	 *            The reference to the parent of this node.
	 * @param state
	 *            The state that will be contained in this node.
	 * @param move
	 *            The move that generated the state in the second parameter from
	 *            the state contained in the parent of this node.
	 */
	public SlidingNineNodeHW3(SlidingNineNodeHW3 parent,
			SlidingNineStateHW3 state, char move) {
		_parent = parent;
		_state = state;
		_move = move;
	}

	/**
	 * This method returns a reference to the parent node of this node.
	 * 
	 * @return A reference to the parent node of this node.
	 */
	public SlidingNineNodeHW3 getParent() {
		return _parent;
	}

	/**
	 * This method returns a reference to the state contained in this node.
	 * 
	 * @return A reference to the state contained in this node.
	 */
	public SlidingNineStateHW3 getState() {
		return _state;
	}

	/**
	 * This method returns the move that generated the state contained in this
	 * node from the state contained in the parent of this node.
	 * 
	 * @return The move that generated the state contained in this node from the
	 *         state contained in the parent of this node.
	 */
	public char getMove() {
		return _move;
	}

	/*
	 * This method returns a string representation of the state contained in
	 * this node.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return _state.toString();
	}

	/*
	 * This method compares the state contained in this node with the state
	 * contained in the argument node.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(SlidingNineNodeHW3 arg0) {
		return _state.compareTo(arg0._state);
	}
}
