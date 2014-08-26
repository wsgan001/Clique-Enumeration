package graph;

import java.util.BitSet;

public class GraphBitSet implements Graph {

	private BitSet[] adjacency;
	private final int size;
	private int[] degree;

	public GraphBitSet(int size) {
		this.size = size;
		degree = new int[size];

		adjacency = new BitSet[size];
		for(int i = 0; i < size; i++) {
			adjacency[i] = new BitSet(size);
		}
	}

	public GraphBitSet(GraphBitSet other) {
		size = other.size();
		degree = new int[size];

		adjacency = new BitSet[size];

		for (int i = 0; i < size; i++) {
			adjacency[i] = (BitSet)other.neighbours(i).clone();
		}
	}

	public GraphBitSet(GraphAL graph) {
		size = graph.size();
		degree = new int[size];

		adjacency = new BitSet[size];

		for(int i = 0; i < size; i++) {
			adjacency[i] = new BitSet(size);

			for (Vertex v : graph.getVertex(i).getAdjacencyList()) {
				addEdge(i, v.getIndex());
			}
		}
	}

	@Override
	public void addEdge(int v, int w) {
		adjacency[v].set(w);
		degree[v]++;
	}

	@Override
	public int size() {
		return size;
	}

	public int countEdges() {
		int c = 0;
		for (BitSet b : adjacency)
			c += b.cardinality();

		return c / 2;
	}

	@Override
	public boolean isAdjacent(int v, int w) {
		return adjacency[v].get(w);
	}

	public BitSet[] adjacencyMatrix() {
		return adjacency;
	}

	public int degree(int v) {
		return degree[v];
	}

	public BitSet neighbours(int v) {
		return adjacency[v];
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder(size + "\n" + countEdges() + "\n");
		for(int i = 0; i < size; i++) {
			for (int j = adjacency[i].nextSetBit(0); j > -1; j = adjacency[i].nextSetBit(j + 1)) {
				s.append(i).append(' ').append(j).append('\n');
			}
		}

		return s.toString();
	}

	public void print() {
		System.out.println(size + "\n" + countEdges());

		for(int i = 0; i < size; i++) {
			for (int j = adjacency[i].nextSetBit(0); j > -1; j = adjacency[i].nextSetBit(j + 1)) {
				System.out.printf("%d,%d\n", i, j);
			}
		}
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof GraphBitSet) {
			GraphBitSet other = (GraphBitSet) o;

			if (this.size != other.size())
				return false;

			for (int i = 0; i < size; i++) {
				if (!adjacency[i].equals(other.neighbours(i))) {
					return false;
				}
			}
		}
		else return false;

		return true;
	}

}
