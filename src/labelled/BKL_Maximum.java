package labelled;

import java.util.BitSet;
import java.util.Stack;

/**
 * Modified version of {@link labelled.BKL_LabelsToR} that enumerates the maximum solutions rather than the maximal ones
 * to verify the algorithm against Ciaran's results.
 */
public class BKL_Maximum {

	LabelledGraph graph;
	int budget;
	int calls;
	int cliques = 0;
	int max = 0;
	int cost =100;
	Stack<BitSet> maximumCliques;

	public BKL_Maximum(LabelledGraph graph, int budget) {
		this.graph = graph;
		this.budget = budget;
		maximumCliques = new Stack<BitSet>();
	}

	public void start() {
		BitSet[] LabelsToR = new BitSet[graph.n];

		for (int i = 0; i < graph.n; i++) {
			LabelsToR[i] = new BitSet(graph.l);
		}

		BitSet P = new BitSet(graph.n);
		P.set(0, graph.n);

		BitSet R = new BitSet(graph.n);
		BitSet X = new BitSet(graph.n);

			extend(R, P, X, LabelsToR, -1);
	}

	public void extend(BitSet R, BitSet P, BitSet X, BitSet[] LabelsToR, int last) {
		calls++;
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
				nLabelsToR[v] = (BitSet)LabelsToR[v].clone();
				for (int i = newP.nextSetBit(0); i > -1; i = newP.nextSetBit(i + 1)) {
					BitSet bs = (BitSet) LabelsToR[i].clone();
					bs.set(graph.label(v, i) - 1);
					bs.or(LabelsToR[v]);
					if (bs.cardinality() > budget) {
						newP.clear(i);
					}
					nLabelsToR[i] = bs;
				}

				for (int i = newX.nextSetBit(0); i > -1; i = newX.nextSetBit(i + 1)) {
					BitSet bs = (BitSet) LabelsToR[i].clone();
					bs.set(graph.label(v, i) - 1);
					bs.or(LabelsToR[v]);
					if (bs.cardinality() > budget) {
						newX.clear(i);
					}
					nLabelsToR[i] = bs;
				}

				// call recursion
				extend(newR, newP, newX, nLabelsToR, v);

				// remove v from candidates
				P.flip(v);

				// add v to X
				X.set(v);
			}
		}
		// If maximal clique is found, print it and return
		else if (X.nextSetBit(0) == -1) {
			cliques++;
			int card = R.cardinality();
			if (card > max) {
				maximumCliques = new Stack<BitSet>();
				maximumCliques.push((BitSet)R.clone());
				max = card;
				cost = LabelsToR[last].cardinality();
			}
			else if (card == max) {
				if (LabelsToR[last].cardinality() < cost) {
					maximumCliques = new Stack<BitSet>();
					cost = LabelsToR[last].cardinality();
					maximumCliques.push((BitSet)R.clone());
				}
				else if (LabelsToR[last].cardinality() == cost) maximumCliques.push((BitSet)R.clone());
			}
		}
	}
}
