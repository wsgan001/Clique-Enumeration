package algorithm;

import graph.VertexBS;
import graph.GraphBS;

import java.util.Arrays;
import java.util.BitSet;

public class BS_Eppstein extends BS_Tomita {


	public BS_Eppstein(GraphBS graph) {
		super(graph);
	}

	@Override
	public void run() {
		int[] degeneracy = order();

		BitSet P = new BitSet(size);
		P.set(0, size);

		BitSet R = new BitSet(size);
		BitSet X = new BitSet(size);

		for(int v : degeneracy) {
			// Add v to R
			BitSet newR = (BitSet)R.clone();
			newR.set(v);

			// intersection of P and the neighbourhood of v
			BitSet newP = (BitSet)P.clone();
			newP.and(graph.getNeighbours(v));

			// intersection of X and the neighbourhood of v
			BitSet newX = (BitSet)X.clone();
			newX.and(graph.getNeighbours(v));

			// call recursion (Tomita's recursive call, implemented in its superclass)
			extend(newR, newP, newX);

			// remove v from candidates
			P.flip(v);

			// add v to X
			X.set(v);
		}
	}

	private int[] order() {
		return CoreDecomposition.cores(graph);
	}
}
