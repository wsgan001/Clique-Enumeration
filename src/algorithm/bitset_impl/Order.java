package algorithm.bitset_impl;

import graph.GraphBitSet;

/**
 * Has a single method that produces a vertex order from the input Graph.
 */
public interface Order {

	/**
	 * Orders the vertices of the graph.
	 * @param graph the graph.
	 * @return an ordering.
	 */
	public int[] order(GraphBitSet graph);
}
