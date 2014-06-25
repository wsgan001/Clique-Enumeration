package utility;

import graph.GraphAL;
import graph.GraphBS;
import graph.VertexAL;
import graph.VertexBS;

import java.util.BitSet;
import java.util.Random;
import java.util.Stack;

public abstract class Utility {

	public static long measure(Measurable m) {
		long start = System.nanoTime();
		m.run();
		long end = System.nanoTime();

		return (end - start) / 1000000;
	}

	public static GraphBS raiseToK(GraphAL graph, int k) {
		GraphBS powerGraph = new GraphBS(graph.size());

		for(int i = 0; i < graph.size(); i++) {
			BitSet adjacency = powerGraph.getNeighbours(i);

			Stack<VertexAL>[] kNeighbours = new Stack[k];
			for (int j = 0; j < k; j++) kNeighbours[j] = new Stack<>();

			for(VertexAL v : graph.getVertex(i).getAdjacencyList()) {
				adjacency.set(v.getIndex());
				kNeighbours[0].push(v);
			}

			for (int j = 0; j < k; j++){
				while (!kNeighbours[j].empty()) {
					VertexAL vertex = kNeighbours[j].pop();
					adjacency.set(vertex.getIndex());

					for (VertexAL v : vertex.getAdjacencyList()) {
						if ( !adjacency.get(v.getIndex()) && (j < k-1) ) {
							adjacency.set(v.getIndex());
							kNeighbours[j+1].push(v);
						}
					}
				}
			}

			adjacency.clear(graph.getVertex(i).getIndex());
		}

		return powerGraph;
	}

	public static GraphBS raiseToKv2 (GraphBS graph, int k) {
		int size = graph.size();

		GraphBS powerGraph = new GraphBS(graph);
		BitSet[] matrix;
		BitSet[] transpose = null;

		for (int n = 1; n < k; n++) {
			matrix = new BitSet[size];

			for (int i = 0; i < size; i++) {
				matrix[i] = (BitSet) powerGraph.getNeighbours(i).clone();
			}

			if(n == 1)
				transpose = transpose(matrix);

			for (int i = 0; i < size; i++) {
				for (int j = i + 1; j < size; j++) {
					BitSet bs = (BitSet) matrix[i].clone();
					bs.and(transpose[j]);
					if (bs.nextSetBit(0) > -1) {
						powerGraph.addEdge(i, j);
						powerGraph.addEdge(j, i);
					}
				}
			}
		}

		return powerGraph;
	}

	public static BitSet[] transpose(BitSet[] matrix) {
		BitSet[] transpose = new BitSet[matrix.length];
		for (int i = 0; i < matrix.length; i++) {
			BitSet bs = new BitSet(matrix.length);

			for (int j = 0; j < matrix.length; j++) {
				if (matrix[j].get(i))
					bs.set(j);
			}

			transpose[i] = bs;
		}

		return transpose;
	}


	public static GraphBS raiseToK (GraphBS graph, int k) {
		GraphBS powerGraph = new GraphBS(graph.size());

		for(int i = 0; i < graph.size(); i++) {
			BitSet adjacency = powerGraph.getNeighbours(i);
			adjacency.set(i);
			BitSet next = new BitSet(graph.size());
			next.set(i);

			for (int j = 0; j < k; j++) {
				BitSet temp = new BitSet(graph.size());
				for (int l = next.nextSetBit(0); l > -1; l = next.nextSetBit(l + 1)) {
					temp.or(graph.getVertex(l).getAdjacency());
				}
				next = temp;
				next.andNot(adjacency);
				adjacency.or(next);
			}

			adjacency.clear(i);
		}

		return powerGraph;

	}

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