package algorithm;

import graph.GraphBS;
import java.util.BitSet;

public class BS_WithPivot extends BS_Basic {
	public BS_WithPivot(GraphBS graph) {
		super(graph, false);
	}

	public BS_WithPivot(GraphBS graph, boolean verbose) {
		super(graph, verbose);
	}

	@Override
	protected void extend(BitSet R, BitSet P, BitSet X) {
		// If the candidate set is not empty, continue the algorithm
		if (P.nextSetBit(0) > -1) {

			// Tomita pivot selection: maximise the size of the intersection of P and N(pivot)
			int pivot = selectPivot(P, X);

			// Create P - N(pivot)
			BitSet S = (BitSet) P.clone();
			S.andNot(graph.getNeighbours(pivot));

			for (int v = S.nextSetBit(0); v > -1; v = S.nextSetBit(v + 1)) {
				// Add v to R
				BitSet newR = (BitSet) R.clone();
				newR.set(v);

				// intersection of P and the neighbourhood of v
				BitSet newP = (BitSet) P.clone();
				newP.and(graph.getNeighbours(v));

				// intersection of X and the neighbourhood of v
				BitSet newX = (BitSet) X.clone();
				newX.and(graph.getNeighbours(v));

				// call recursion
				extend(newR, newP, newX);

				// remove v from candidates
				P.flip(v);

				// add v to X
				X.set(v);
			}
		}
		// If maximal clique is found, print it and return
		else if (X.nextSetBit(0) == -1) {
			reportClique(R);
		}
	}

	protected int selectPivot(BitSet P, BitSet X) {
		return P.nextSetBit(0);
	}
}
