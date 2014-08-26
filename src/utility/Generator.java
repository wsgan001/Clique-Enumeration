package utility;

import graph.GraphBitSet;
import graph.*;

import java.util.*;

public abstract class Generator {

	public static GraphBitSet generateBS(int n, int m) {
		GraphBitSet graph = new GraphBitSet(n);
		generate(graph, n, m);
		return graph;
	}

	public static GraphBitSet generateBS(int n, double p) {
		GraphBitSet graph = new GraphBitSet(n);

		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				if (Math.random() < p) {
					graph.addEdge(i, j);
					graph.addEdge(j, i);
				}
			}
		}

		return graph;
	}

	public static GraphAL generateAL(int n, int m) {
		GraphAL graph = new GraphAL(n);
		generate(graph, n, m);
		return graph;
	}

	private static void generate(Graph graph, int n, int m) {

		Random rnd = new Random();

		for (int i = 0; i < m;) {
			int v = rnd.nextInt(n);
			int w = rnd.nextInt(n);

			if (v != w && !graph.isAdjacent(v, w)) {
				graph.addEdge(v, w);
				graph.addEdge(w, v);
				i++;
			}
		}
	}
}