package algorithm.bitset_impl;

import algorithm.CliqueAlgorithm;
import graph.GraphBitSet;

import java.util.BitSet;

/**
 * BitSet implementation of the Bron-Kerbosch algorithm with pivot selection and (optionally) initial vertex ordering.
 */
public class AlgorithmBS extends CliqueAlgorithm {

	private final GraphBitSet graph;
	private final int size;
	private Order order;
	private Pivot selector;

	/**
	 *
	 * @param graph a BitSet graph for which maximal cliques are to be enumerated
	 * @param order vertex ordering for outer loop
	 * @param selector	pivot selector
	 */
	public AlgorithmBS(GraphBitSet graph, Order order, Pivot selector) {
		this(graph, order, selector, false);
	}

	/**
	 *
	 * @param graph a BitSet graph for which maximal cliques are to be enumerated
	 * @param order vertex ordering for outer loop
	 * @param selector pivot selector
	 * @param verbose if set to true, every maximal clique is printed to stdout (used mainly for testing)
	 */
	public AlgorithmBS(GraphBitSet graph, Order order, Pivot selector, boolean verbose) {
		super(verbose);
		this.graph = graph;
		this.size = graph.size();
		this.order = order;
		this.selector = selector;
	}

	/**
	 * Runs the algorithm.
	 */
	@Override
	public void run() {
		int[] order = this.order.order(graph);

		BitSet P = new BitSet(size);
		P.set(0, size);

		BitSet R = new BitSet(size);
		BitSet X = new BitSet(size);

		if (order == null) {
			extend(R, P, X);
			return;
		}

		for(int v : order) {
			// Add v to R
			BitSet newR = (BitSet)R.clone();
			newR.set(v);

			// intersection of P and the neighbourhood of v
			BitSet newP = (BitSet)P.clone();
			newP.and(graph.neighbours(v));

			// intersection of X and the neighbourhood of v
			BitSet newX = (BitSet)X.clone();
			newX.and(graph.neighbours(v));

			// call recursion (Tomita's recursive call, implemented in its superclass)
			extend(newR, newP, newX);

			// remove v from candidates
			P.flip(v);

			// add v to X
			X.set(v);
		}
	}

	private void extend(BitSet R, BitSet P, BitSet X) {
		inc();
		// If the candidate set is not empty, continue the algorithm
		if (P.nextSetBit(0) > -1) {

			// Tomita pivot selection: maximise the size of the intersection of P and N(pivot)
			int pivot = selector.selectPivot(graph, P, X);

			// Create P - N(pivot)
			BitSet S = (BitSet) P.clone();
			S.andNot(graph.neighbours(pivot));

			for (int v = S.nextSetBit(0); v > -1; v = S.nextSetBit(v + 1)) {
				// Add v to R
				BitSet newR = (BitSet) R.clone();
				newR.set(v);

				// intersection of P and the neighbourhood of v
				BitSet newP = (BitSet) P.clone();
				newP.and(graph.neighbours(v));

				// intersection of X and the neighbourhood of v
				BitSet newX = (BitSet) X.clone();
				newX.and(graph.neighbours(v));

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
}
