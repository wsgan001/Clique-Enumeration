package labelled;

import algorithm.CliqueAlgorithm;

import java.util.BitSet;

/**
 * <p>
 * Labelled clique algorithm alternative version.
 * Differs in the way c(v) is calculated.
 * </p>
 * <p>
 * Takes a labelled graph and a budget as arguments in the constructor.
 * Should be started by calling {@link algorithm.CliqueAlgorithm#execute()} instead of calling run directly. </p>
 */
public class BKL_LabelsToR extends CliqueAlgorithm {

	LabelledGraph graph;
	int budget;

	/**
	 * Constructor.
	 * @param graph the input graph.
	 * @param budget a maximum allowed number of different labels in the cliques.
	 * @param verbose set this to true if all cliques should be printed.
	 */
	public BKL_LabelsToR(LabelledGraph graph, int budget, boolean verbose) {
		super(verbose);
		this.graph = graph;
		this.budget = budget;
	}

	/**
	 * Alternative constructor with verbosity set to false.
	 * @param graph the input graph.
	 * @param budget a maximum allowed number of different labels in the cliques.
	 */
	public BKL_LabelsToR(LabelledGraph graph, int budget) {
		this(graph, budget, false);
	}

	@Override
	public void run() {
		BitSet[] LabelsToR = new BitSet[graph.n];

		for (int i = 0; i < graph.n; i++) {
			LabelsToR[i] = new BitSet(graph.l);
		}

		BitSet P = new BitSet(graph.n);
		P.set(0, graph.n);

		BitSet R = new BitSet(graph.n);
		BitSet X = new BitSet(graph.n);

		extend(R, P, X, LabelsToR);
	}

	private void extend(BitSet R, BitSet P, BitSet X, BitSet[] LabelsToR) {
		inc();
		int firstSet = P.nextSetBit(0);
		// If the candidate set is not empty, continue the algorithm
		if (firstSet != -1) {
			for (int v = P.nextSetBit(firstSet); v > -1; v = P.nextSetBit(v + 1)) {
				// Add v to R
				BitSet newR = (BitSet) R.clone();
				newR.set(v);

				// intersection of P and the neighbourhood of v
				BitSet newP = (BitSet) P.clone();
				newP.and(graph.neighbourhood(v));

				// intersection of X and the neighbourhood of v
				BitSet newX = (BitSet) X.clone();
				newX.and(graph.neighbourhood(v));

				BitSet[] nLabelsToR = new BitSet[graph.n];
				// fill in labels for vertices in the candidate set and remove those over the budget
				fillInLabels(newP, LabelsToR, nLabelsToR, v);

				// fill in labels for vertices in the NOT set and remove those over the budget
				fillInLabels(newX, LabelsToR, nLabelsToR, v);

				// call recursion
				extend(newR, newP, newX, nLabelsToR);

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

	private void fillInLabels(BitSet set, BitSet[] oldLabelsToR, BitSet[] newLabelsToR, int vertex) {
		for (int i = set.nextSetBit(0); i > -1; i = set.nextSetBit(i + 1)) {
			BitSet bs = (BitSet) oldLabelsToR[i].clone();
			bs.set(graph.label(vertex, i) - 1);
			bs.or(oldLabelsToR[vertex]);
			if (bs.cardinality() > budget) {
				set.clear(i);
			}
			newLabelsToR[i] = bs;
		}

	}

}
