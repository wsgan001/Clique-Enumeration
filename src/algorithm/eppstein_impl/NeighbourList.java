package algorithm.eppstein_impl;

import java.util.LinkedList;

/**
 * Same function as {@link algorithm.eppstein_impl.NeighbourListArray} but instead of arrays, vertices that come before
 * and after a vertex are stored in a linked list.
 *
 * Used to temporarily store data in
 * {@link algorithm.eppstein_impl.EppsteinHelper#computeDegeneracyOrder(java.util.LinkedList[], int)}
 */
public class NeighbourList {

	int vertex;
	LinkedList<Integer> earlier;
	LinkedList<Integer> later;
	int orderNumber;

	public NeighbourList() {
		earlier = new LinkedList<Integer>();
		later = new LinkedList<Integer>();
	}

}
