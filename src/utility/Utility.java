package utility;

import graph.GraphAL;
import graph.GraphBS;
import graph.VertexAL;
import graph.VertexBS;

import java.util.BitSet;
import java.util.Random;
import java.util.Stack;

public abstract class Utility {

	public static GraphAL generateSparseAL(int n, int m) {
		GraphAL graph = new GraphAL(n);

		Random rnd = new Random();

		for (int i = 0; i < m;) {
			int v = rnd.nextInt(n);
			int w = rnd.nextInt(n);

			if (v != w && !graph.isAdjacent(v, w)) {
				graph.addEdge(v, w);
				i++;
			}
		}

		return graph;
	}
}