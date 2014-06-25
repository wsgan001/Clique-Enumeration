package algorithm;

import graph.GraphBS;

import java.util.BitSet;

public class TomitaBS extends AlgorithmBS {

	public TomitaBS(GraphBS graph) {
		super(graph);
	}

	@Override
	protected void extend(BitSet R, BitSet P, BitSet X) {
		// If maximal clique is found, print it and return
		if(P.cardinality() == 0 && X.cardinality() == 0) {
			printClique(R, ++cliqueNumber);
			return;
		}

		// Tomita pivot selection: maximise the size of the intersection of P and N(pivot)

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

		// Create P - N(pivot)
		BitSet S = (BitSet)P.clone();
		S.andNot(graph.getNeighbours(pivot));

		for (int v = S.nextSetBit(0); v > -1; v = S.nextSetBit(v + 1)){
			// Add v to R
			BitSet newR = (BitSet)R.clone();
			newR.set(v);

			// intersection of P and the neighbourhood of v
			BitSet newP = (BitSet)P.clone();
			newP.and(graph.getNeighbours(v));

			// intersection of X and the neighbourhood of v
			BitSet newX = (BitSet)X.clone();
			newX.and(graph.getNeighbours(v));

			// call recursion
			extend(newR, newP, newX);

			// remove v from candidates
			P.flip(v);

			// add v to X
			X.set(v);
		}
	}
}
