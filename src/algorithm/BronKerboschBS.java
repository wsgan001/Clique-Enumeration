package algorithm;

import graph.GraphBS;

import java.util.BitSet;

public class BronKerboschBS extends AlgorithmBS {

	public BronKerboschBS(GraphBS graph) {
		super(graph);
	}

	@Override
	protected void extend(BitSet R, BitSet P, BitSet X) {
		if(P.cardinality() == 0 && X.cardinality() == 0) {
			printClique(R, ++cliqueNumber);
		}

		int v = 0;
		while(P.cardinality() != 0) {
			// find next vertex in P
			v = P.nextSetBit(v);

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
