package labelled;

import algorithm.bitset_impl.OrderNone;
import algorithm.bitset_impl.PivotTomita;
import graph.GraphBitSet;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Stack;

/**
 * Inefficient algorithm for labelled clique enumeration. Reuses the original Bron-Kerbosch algorithm implemented in
 * {@link labelled.FindAllCliques} to enumerate cliques in the combination graphs and then gets rid of non-maximal and
 * duplicate solutions.
 */
public class BKL_Combinations {

	private LabelledGraph graph;
	private Stack<BitSet> combs;
	private int budget;

	private BitSet[] cliques;

	public BKL_Combinations(LabelledGraph graph, int budget) {
		this.graph = graph;
		this.budget = budget;
	}

	public void run() {
		combs = new Stack<BitSet>();
		combinations(new BitSet(graph.l), 0);
		Stack<BitSet> allCliques = new Stack<BitSet>();

		for (BitSet combination : combs) {
			GraphBitSet combGraph = LabelledTools.getGraphFromGivenLabels(graph, combination);
			FindAllCliques finder = new FindAllCliques(combGraph, new OrderNone(), new PivotTomita());
			finder.execute();

			allCliques.addAll(finder.cliqueStack);
		}

		cliques = allCliques.toArray(new BitSet[allCliques.size()]);
		cliques = clear(cliques);
	}

	private void combinations(BitSet set, int index) {
		if (set.cardinality() == budget) {
			combs.push((BitSet)set.clone());
		}
		else if (index < graph.l) {
			combinations(set, index + 1);
			set.set(index);
			combinations(set, index + 1);
			set.clear(index);
		}
	}

	private static BitSet[] clear(BitSet[] cliques) {
		int length = cliques.length;
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				if (i != j) {
					BitSet bs = (BitSet) cliques[i].clone();
					bs.andNot(cliques[j]);
					if (bs.cardinality() == 0) {
						cliques[i] = cliques[--length];
						i--;
						break;
					}
				}
			}
		}
		return Arrays.copyOf(cliques, length);
	}

	public BitSet[] getCliques() { return cliques; }
}
