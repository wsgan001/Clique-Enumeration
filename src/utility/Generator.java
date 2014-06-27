package utility;

import graph.*;

import java.util.*;

public abstract class Generator {

	public static GraphBS generateBS(int n, int m) {
		GraphBS graph = new GraphBS(n);
		generate(graph, n, m);
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
				i++;
			}
		}
	}
}