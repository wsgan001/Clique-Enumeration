package algorithm.bitset_impl;

import graph.GraphBitSet;

import java.util.BitSet;

/**
 * Arbitrary pivot selector class. Selects the first vertex in {@code P} set as pivot.
 */
public class PivotNone implements Pivot {

	/**
	 * Selects the first vertex in {@code P} as pivot.
	 * @param graph the graph processed by the clique algorithm.
	 * @param P    the CANDIDATE set.
	 * @param X    the NOT set.
	 * @return the first vertex in {@code P}.
	 */
	@Override
	public int selectPivot(GraphBitSet graph, BitSet P, BitSet X) {
		return P.nextSetBit(0);
	}
}
