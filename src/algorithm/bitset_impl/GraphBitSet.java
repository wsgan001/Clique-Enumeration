package algorithm.bitset_impl;

import graph.Graph;
import graph.GraphAL;
import graph.Vertex;

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
		StringBuilder s = new StringBuilder("Graph size: " + size + '\n');
		for(int i = 0; i < size; i++) {
			s.append("Vertex ").append(i).append(" neighbours: ").append(adjacency[i]).append('\n');
		}

		return s.toString();
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
