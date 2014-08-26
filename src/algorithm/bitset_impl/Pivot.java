package algorithm.bitset_impl;

import graph.GraphBitSet;

import java.util.BitSet;

/**
 * Has a single method that selects a single pivot from the candidate or the not set.
 */
public interface Pivot {

	/**
	 * Selects a pivot.
	 * @param graph the graph processed by the clique algorithm.
	 * @param P	the CANDIDATE set.
	 * @param X	the NOT set.
	 * @return a pivot vertex.
	 */
	public int selectPivot(GraphBitSet graph, BitSet P, BitSet X);
}
