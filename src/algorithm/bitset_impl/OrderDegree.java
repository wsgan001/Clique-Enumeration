package algorithm.bitset_impl;

import java.util.ArrayList;
import java.util.Stack;

public class OrderDegree implements Order {

	@Override
	public int[] order(GraphBitSet graph) {
		int n = graph.size();
		int[] vert = new int[n];
		int maxDegree = 0;

		for (int i = 0; i < n; i++) {
			if (graph.degree(i) > maxDegree) maxDegree = graph.degree(i);
		}

		ArrayList<Stack<Integer>> degrees = new ArrayList<>(maxDegree + 1);
		for (int i = 0; i < maxDegree + 1; i++) {
			degrees.add(new Stack<Integer>());
		}

		for (int i = 0; i < n; i++) {
			degrees.get(graph.degree(i)).push(i);
		}

		int counter = 0;
		for (int i = 0; i < maxDegree + 1; i++) {
			while (!degrees.get(i).empty())
				vert[counter++] = degrees.get(i).pop();
		}

		return vert;
	}
}
