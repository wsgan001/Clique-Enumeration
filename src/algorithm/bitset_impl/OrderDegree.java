package algorithm.bitset_impl;

import graph.GraphBitSet;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Degree ordering of the vertices of a graph.
 */
public class OrderDegree implements Order {

	/**
	 * Sorts the vertices based on their degree.
	 * @param graph the graph.
	 * @return the vertices of the graph sorted by their degree.
	 */
	@Override
	public int[] order(GraphBitSet graph) {
		int n = graph.size();
		int[] vert = new int[n];
		int maxDegree = 0;

		// find max degre
		for (int i = 0; i < n; i++) {
			if (graph.degree(i) > maxDegree) maxDegree = graph.degree(i);
		}

		// create a list of stacks which will store all vertices of a given degree
		ArrayList<Stack<Integer>> degrees = new ArrayList<Stack<Integer>>(maxDegree + 1);
		for (int i = 0; i < maxDegree + 1; i++) {
			degrees.add(new Stack<Integer>());
		}

		// push every vertex to the right stack (based on their degree)
		for (int i = 0; i < n; i++) {
			degrees.get(graph.degree(i)).push(i);
		}

		// fill in the array of vertices in their degree order
		int counter = 0;
		for (int i = 0; i < maxDegree + 1; i++) {
			while (!degrees.get(i).empty())
				vert[counter++] = degrees.get(i).pop();
		}

		return vert;
	}
}
