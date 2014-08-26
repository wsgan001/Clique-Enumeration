package labelled;

import java.util.BitSet;

/**
 * Labelled graph data structure. Stores the labels as a 2D byte array (assuming that the cardinality of the label set
 * is less than 256). The label between non-neighbours is a 0. Despite this, the neighbourhood of is also stored as a
 * BitSet array, to speed up the algorithms.
 */
public class LabelledGraph {

	BitSet[] adjacency;
	byte[][] labels;
	int n;
	int l;

	public LabelledGraph(int n, int l) {
		labels = new byte[n][n];
		adjacency = new BitSet[n];
		this.l = l;
		this.n = n;

		for (int i = 0; i < n; i++) {
			adjacency[i] = new BitSet(n);
		}
	}

	public void addEdge(int v, int w, int label) {
		adjacency[v].set(w);
		adjacency[w].set(v);
		labels[v][w] = (byte)label;
		labels[w][v] = (byte)label;
	}

	public int label(int v, int w) {
		return labels[v][w];
	}

	public BitSet neighbourhood(int v) {
		return adjacency[v];
	}

	public boolean adjacent(int v, int w) {
		return adjacency[v].get(w);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("%d\n%d\n", n, l));

		for (int i = 0; i < adjacency.length; i++) {
			for (int j = adjacency[i].nextSetBit(i + 1); j > -1; j = adjacency[i].nextSetBit(j + 1)) {
				builder.append(String.format("%d,%d,%d\n", i, j, labels[i][j]));
			}
		}

		return builder.toString();
	}


}
