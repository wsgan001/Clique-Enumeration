package algorithm;

import graph.GraphBS;

import java.util.BitSet;

public class BS_Tomita extends BS_WithPivot {

	public BS_Tomita(GraphBS graph) {
		super(graph);
	}

	public BS_Tomita(GraphBS graph, boolean verbose) {
		super(graph, verbose);
	}

	@Override
	protected int selectPivot(BitSet P, BitSet X) {

		// Create union of P and X
		BitSet union = (BitSet)P.clone();
		union.or(X);

		int max = 0;
		int pivot = -1;
		// Search for the maximum
		for(int i = union.nextSetBit(0); i > -1; i = union.nextSetBit(i + 1)) {
			BitSet set = (BitSet)P.clone();
			set.and(graph.getNeighbours(i));

			if (set.cardinality() >= max) {
				max = set.cardinality();
				pivot = i;
			}
		}

		return pivot;
	}
}
