package algorithm;

import graph.GraphBS;

import java.util.BitSet;

public class BS_Basic extends CliqueAlgorithm {

	protected final GraphBS graph;
	protected final int size;

	public BS_Basic(GraphBS graph, boolean verbose) {
		super(verbose);
		this.graph = graph;
		this.size = graph.size();
	}

	public void run() {
		BitSet P = new BitSet(size);
		P.set(0, size);

		extend(new BitSet(size), P, new BitSet(size));
	}

	protected void extend(BitSet R, BitSet P, BitSet X) {
		if(P.cardinality() == 0 && X.cardinality() == 0) {
			reportClique(R);
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
