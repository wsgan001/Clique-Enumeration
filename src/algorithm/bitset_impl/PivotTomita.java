package algorithm.bitset_impl;

import graph.GraphBitSet;

import java.util.BitSet;

/**
 * Pivot selector class that selects the pivot as in Bron-Kerbosch version 2 and Tomita's algorithm.
 */
public class PivotTomita implements Pivot {

	@Override
	public int selectPivot(GraphBitSet graph, BitSet P, BitSet X) {
		// Create union of P and X
		BitSet union = (BitSet)P.clone();
		union.or(X);

		int max = -1;
		int pivot = -1;
		// Search for the maximum
		for(int i = union.nextSetBit(0); i > -1; i = union.nextSetBit(i + 1)) {
			BitSet set = (BitSet)P.clone();
			set.and(graph.neighbours(i));

			if (set.cardinality() > max) {
				max = set.cardinality();
				pivot = i;
			}
		}

		return pivot;
	}
}
