package utility;

import graph.GraphBitSet;

public abstract class Utility {

	public static int countEdges(GraphBitSet graph) {
		int edges = 0;

		for (int i = 0; i < graph.size(); i++) {
			edges += graph.neighbours(i).cardinality();
		}

		return edges / 2;
	}
}
