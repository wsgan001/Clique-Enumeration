package labelled;

import algorithm.CliqueAlgorithm;

import java.util.BitSet;

/**
 * Labelled clique algorithm final version. Takes a labelled graph and a budget as arguments in the constructor.
 * Should be started by calling {@link algorithm.CliqueAlgorithm#execute()} instead of calling run directly.
 */
public class BKL_PostX extends CliqueAlgorithm {

	LabelledGraph graph;
	int budget;

	/**
	 * Constructor.
	 * @param graph the input graph.
	 * @param budget a maximum allowed number of different labels in the cliques.
	 * @param verbose set this to true if all cliques should be printed.
	 */
	public BKL_PostX(LabelledGraph graph, int budget, boolean verbose) {
		super(verbose);
		this.graph = graph;
		this.budget = budget;
	}

	/**
	 * Alternative constructor with verbosity set to false.
	 * @param graph the input graph.
	 * @param budget a maximum allowed number of different labels in the cliques.
	 */
	public BKL_PostX(LabelledGraph graph, int budget) {
		this(graph, budget, false);
	}

	@Override
	public void run() {
		BitSet labels = new BitSet(graph.l);
		BitSet P = new BitSet(graph.n);
		P.set(0, graph.n);

		BitSet R = new BitSet(graph.n);
		BitSet X = new BitSet(graph.n);

		extend(R, P, X, labels);
	}

	private void extend(BitSet R, BitSet P, BitSet X, BitSet labels) {
		inc();
		int firstSet = P.nextSetBit(0);
		// If the candidate set is not empty, continue the algorithm
		if (firstSet != -1) {
			for (int v = P.nextSetBit(firstSet); v > -1; v = P.nextSetBit(v + 1)) {
				BitSet newLabels = (BitSet)labels.clone();
				updateLabels(R, v, newLabels);

				// Add v to R
				BitSet newR = (BitSet) R.clone();
				newR.set(v);

				// intersection of P and the neighbourhood of v
				BitSet newP = (BitSet) P.clone();
				newP.and(graph.neighbourhood(v));

				// intersection of X and the neighbourhood of v
				BitSet newX = (BitSet) X.clone();
				newX.and(graph.neighbourhood(v));

				// fill in labels for vertices in the candidate set and remove those over the budget
				updateSet(newR, newP, newLabels);

				// call recursion
				extend(newR, newP, newX, newLabels);

				// remove v from candidates
				P.flip(v);

				// add v to X
				X.set(v);
			}
		}
		// If maximal clique is found, report it and return
		else {
			updateSet(R, X, labels);
			if (X.nextSetBit(0) == -1) {
				reportClique(R);
			}
		}
	}

	private void updateSet(BitSet R, BitSet set, BitSet labels) {
		for (int i = set.nextSetBit(0); i > -1; i = set.nextSetBit(i + 1)) {
			BitSet bs = (BitSet)labels.clone();
			for (int j = R.nextSetBit(0); j > -1; j = R.nextSetBit(j + 1)) {
				if (i != j)	{ bs.set(graph.label(i, j) - 1); }
			}
			if (bs.cardinality() > budget)
				set.clear(i);
		}
	}

	private void updateLabels(BitSet R, int v, BitSet labels) {
		for (int i = R.nextSetBit(0); i > -1; i = R.nextSetBit(i + 1)) {
			labels.set(graph.label(i, v) - 1);
		}
	}

}
