package utility;

import graph.GraphBitSet;
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
	public static GraphBitSet raiseBBS(GraphBitSet input, int k) {
		GraphBitSet powerGraph = new GraphBitSet(input.size());

		for(int i = 0; i < input.size(); i++) {
			// neighbours of vertex i
			BitSet adjacency = powerGraph.neighbours(i);
			adjacency.set(i);
			// vertices at the next distance (initially the original neighbours)
			BitSet next = (BitSet)adjacency.clone();

			for (int j = 1; j < k; j++) {
				BitSet temp = new BitSet(input.size());
				for (int l = next.nextSetBit(0); l > -1; l = next.nextSetBit(l + 1)) {
					temp.or(input.neighbours(l)); // add the neighbours of vertex l to the temporary neigbourhood
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
	 * Similar algorithm to {@link #raiseBBS(graph.GraphBitSet, int)} but tries to speed up the process by utilising the
	 * rules of exponents, i.e. G^4 is calculated by calculating G^2 first and then (G^2)^2 instead of doing a breadth
	 * first search of depth 4.
	 * @param input	The input graph using a bitset representation.
	 * @param k The maximum allowed distance between vertices for them to be considered neighbours.
	 * @return The kth power graph.
	 */
	public static GraphBitSet raiseGMA(GraphBitSet input, int k) {
		String bin = new StringBuilder(Integer.toBinaryString(k)).reverse().toString();

		GraphBitSet squared = input;
		GraphBitSet result;

		if (bin.charAt(0) == '0')
			result = new GraphBitSet(input.size());
		else
			result = new GraphBitSet(input);


		for (int i = 1; i < bin.length(); i++) {
			squared = multiply(squared, squared);

			if (bin.charAt(i) == '1')
				result = multiply(result, squared);
		}

		return result;
	}

	/*
	Helper method for raiseGM algorithms.
	Produces the product of two power graphs - for example, if v1 and v2 are neighbours in graph1 and v2 and v3 are
	neighbours in graph2, then v1 and v3 are neighbours in the result graph.
	 */
	private static GraphBitSet multiply(GraphBitSet graph1, GraphBitSet graph2) {
		int size = graph1.size();
		GraphBitSet result = new GraphBitSet(size);

		for(int i = 0; i < size; i++) {
			BitSet adjacency = result.neighbours(i);

			BitSet adjacencyG1 = graph1.neighbours(i);
			adjacencyG1.set(i);

			for (int l = adjacencyG1.nextSetBit(0); l > -1; l = adjacencyG1.nextSetBit(l + 1)) {
				adjacency.or(graph2.neighbours(l));
				adjacency.set(l);
			}

			adjacency.clear(i);
			adjacencyG1.clear(i);
		}

		return result;
	}

	public static GraphBitSet raiseGMB(GraphBitSet input, int k) {
		GraphBitSet result = new GraphBitSet(input);


		for (int i = 1; i < k; i++) {
				result = multiply(input, result);
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
			boolean[] visited = new boolean[graph.size()];
			BitSet adjacency = powerGraph.neighbours(i);

			Stack<Vertex>[] kNeighbours = new Stack[k];

			// these stacks store the neighbours at distance j
			for (int j = 0; j < k; j++) kNeighbours[j] = new Stack<Vertex>();

			// push the vertex itself at 0 distance
			for(Vertex v : graph.getVertex(i).getAdjacencyList()) {
				adjacency.set(v.getIndex());
				kNeighbours[0].push(v);
			}

			// repeat until a depth of k
			for (int j = 0; j < k; j++){

				// until are done with all vertices at a given distance
				while (!kNeighbours[j].empty()) {
					// get a next vertex
					Vertex vertex = kNeighbours[j].pop();
					// add this vertex as neighbour
					adjacency.set(vertex.getIndex());

					for (Vertex v : vertex.getAdjacencyList()) {
						if ( !visited[v.getIndex()] && !adjacency.get(v.getIndex()) && (j < k-1) ) {
							adjacency.set(v.getIndex());
							visited[v.getIndex()] = true;
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
	 * Produces the kth power of the input graph by matrix multiplication to get the result.
	 * Bitsets are used to represent the vertex sets.
	 * @param input	The input graph using a bitset representation.
	 * @param k The maximum allowed distance between vertices for them to be considered neighbours.
	 * @return The kth power graph.
	 */
	public static GraphBitSet raiseMM(GraphBitSet input, int k) {
		int size = input.size();

		GraphBitSet powerGraph = new GraphBitSet(input);
		GraphBitSet temporary;
		for (int n = 1; n < k; n++) {
			temporary = new GraphBitSet(powerGraph);
			for (int i = 0; i < size; i++) {
				for (int j = i + 1; j < size; j++) {
					BitSet bs = (BitSet) input.neighbours(i).clone();
					bs.and(temporary.neighbours(j));
					if (bs.nextSetBit(0) > -1) {
						powerGraph.addEdge(i, j);
						powerGraph.addEdge(j, i);
					}
				}
			}
		}

		return powerGraph;
	}

}
