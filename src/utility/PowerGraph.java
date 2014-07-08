package utility;

import algorithm.bitset_impl.GraphBitSet;
import graph.GraphAL;
import graph.Vertex;

import java.util.BitSet;
import java.util.Stack;

public abstract class PowerGraph {

	/**
	 * Produces the kth power of the input graph. Uses breadth first search to find the k-neighbours of every vertex.
	 * Bitsets are used to represent the vertex sets.
	 * @param input	The input graph using a bitset representation.
	 * @param k The maximum allowed distance between vertices for them to be considered neighbours.
	 * @return The kth power graph.
	 */
	public static GraphBitSet raiseBFS (GraphBitSet input, int k) {
		GraphBitSet powerGraph = new GraphBitSet(input.size());

		for(int i = 0; i < input.size(); i++) {
			BitSet adjacency = powerGraph.neighbours(i);
			adjacency.set(i);
			BitSet next = new BitSet(input.size());
			next.set(i);

			for (int j = 0; j < k; j++) {
				BitSet temp = new BitSet(input.size());
				for (int l = next.nextSetBit(0); l > -1; l = next.nextSetBit(l + 1)) {
					temp.or(input.neighbours(l));
				}
				next = temp;
				next.andNot(adjacency);
				adjacency.or(next);
			}

			adjacency.clear(i);
		}

		return powerGraph;

	}

	/**
	 * Similar algorithm to {@link #raiseBFS(algorithm.bitset_impl.GraphBitSet, int)} but tries to speed up the process by utilising the
	 * rules of exponents, i.e. G^4 is calculated by calculating G^2 first and then (G^2)^2 instead of doing a breadth
	 * first search of depth 4.
	 * @param input	The input graph using a bitset representation.
	 * @param k The maximum allowed distance between vertices for them to be considered neighbours.
	 * @return The kth power graph.
	 */
	public static GraphBitSet raiseBFSv2(GraphBitSet input, int k) {
		String bin = new StringBuilder(Integer.toBinaryString(k)).reverse().toString();

		GraphBitSet squared = input;
		GraphBitSet result;

		if (bin.charAt(0) == '0')
			result = new GraphBitSet(input.size());
		else
			result = new GraphBitSet(input);


		for (int i = 1; i < bin.length(); i++) {
			squared = raiseToK(squared, squared);

			if (bin.charAt(i) == '1')
				result = raiseToK(result, squared);
		}

		return result;
	}

	/*
	Helper method for raiseBFSv2
	Produces the product of two power graphs - for example, if v1 and v2 are neighbours in graph1 and v2 and v3 are
	neighbours in graph2, then v1 and v3 are neighbours in the result graph.
	 */
	private static GraphBitSet raiseToK (GraphBitSet graph1, GraphBitSet graph2) {
		int size = graph1.size();
		GraphBitSet result = new GraphBitSet(graph1);

		for(int i = 0; i < size; i++) {
			BitSet adjacency = result.neighbours(i);
			adjacency.set(i);

			BitSet temp = new BitSet(size);
			for (int l = adjacency.nextSetBit(0); l > -1; l = adjacency.nextSetBit(l + 1)) {
				temp.or(graph2.neighbours(l));
			}

			adjacency.or(temp);
			adjacency.clear(i);
		}

		return result;

	}


	/**
	 * Produces the kth power of the input graph. Uses breadth first search to find the k-neighbours of every vertex.
	 * Used for graphs that use adjacency lists but the result still uses BitSets.
	 * @param graph A graph using adjacency list representation.
	 * @param k The maximum allowed distance between vertices for them to be considered neighbours.
	 * @return The kth power graph.
	 */
	public static GraphBitSet raiseAL(GraphAL graph, int k) {
		GraphBitSet powerGraph = new GraphBitSet(graph.size());

		for(int i = 0; i < graph.size(); i++) {
			BitSet adjacency = powerGraph.neighbours(i);

			Stack<Vertex>[] kNeighbours = new Stack[k];
			for (int j = 0; j < k; j++) kNeighbours[j] = new Stack<>();

			for(Vertex v : graph.getVertex(i).getAdjacencyList()) {
				adjacency.set(v.getIndex());
				kNeighbours[0].push(v);
			}

			for (int j = 0; j < k; j++){
				while (!kNeighbours[j].empty()) {
					Vertex vertex = kNeighbours[j].pop();
					adjacency.set(vertex.getIndex());

					for (Vertex v : vertex.getAdjacencyList()) {
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



	/**
	 * UNFINISHED CODE (Runs slow at the moment)
	 * Produces the kth power of the input graph. Tries to a kind of matrix multiplication to get the result.
	 * Bitsets are used to represent the vertex sets.
	 * @param input	The input graph using a bitset representation.
	 * @param k The maximum allowed distance between vertices for them to be considered neighbours.
	 * @return The kth power graph.
	 */
	public static GraphBitSet raiseMMult (GraphBitSet input, int k) {
		int size = input.size();

		GraphBitSet powerGraph = new GraphBitSet(input);
		BitSet[] matrix;
		BitSet[] transpose = null;

		for (int n = 1; n < k; n++) {
			matrix = new BitSet[size];

			for (int i = 0; i < size; i++) {
				matrix[i] = (BitSet) powerGraph.neighbours(i).clone();
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

	/*
	Helper method for raiseMMult.
	Produces the transpose of a binary matrix
	 */
	private static BitSet[] transpose(BitSet[] matrix) {
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


}
