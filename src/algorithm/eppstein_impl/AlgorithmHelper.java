package algorithm.eppstein_impl;

import java.util.LinkedList;

public class AlgorithmHelper {

	public static NeighbourListArray[] computeDegeneracyOrder(LinkedList<Integer>[] adjList, int size) {
		NeighbourList[] ordering = new NeighbourList[size];

		int degeneracy = 0;

		LinkedList<Integer>[] verticesByDegree = new LinkedList[size];

		int[] degree = new int[size];

		for (int i = 0; i < size; i++) {
			verticesByDegree[i] = new LinkedList<>();
			ordering[i] = new NeighbourList();
		}

		for (int i = 0; i < size; i++) {
			degree[i] = adjList[i].size();
			verticesByDegree[degree[i]].addFirst(i);
		}

		int currentDegree = 0;
		int numVerticesRemoved = 0;
		while (numVerticesRemoved < size) {
			if (!verticesByDegree[currentDegree].isEmpty()) {
				degeneracy = max(degeneracy, currentDegree);

				int vertex = verticesByDegree[currentDegree].getFirst();
				verticesByDegree[currentDegree].removeFirst();

				ordering[vertex].vertex = vertex;
				ordering[vertex].orderNumber = numVerticesRemoved;

				// visited vertices are marked -1 (they are removed from the subgraph)
				degree[vertex] = -1;

				for (Integer neighbour : adjList[vertex]) {

					if (degree[neighbour] != -1) {
						verticesByDegree[degree[neighbour]].remove(neighbour);
						ordering[vertex].later.addLast(neighbour);

						degree[neighbour]--;
						if (degree[neighbour] != -1) {
							verticesByDegree[degree[neighbour]].addFirst(neighbour);
						}
					}
					else {
						ordering[vertex].earlier.addLast(neighbour);
					}
				}

				numVerticesRemoved++;
				currentDegree = 0;
			}
			else currentDegree++;
		}

		// COPY ORDERING TO ARRAY REPRESENTATION
		NeighbourListArray[] orderingArray = new NeighbourListArray[size];

		for (int i = 0; i < size; i++) {
			orderingArray[i] = new NeighbourListArray();
			orderingArray[i].vertex = ordering[i].vertex;
			orderingArray[i].orderNumber = ordering[i].orderNumber;

			orderingArray[i].laterDegree = ordering[i].later.size();
			orderingArray[i].earlierDegree = ordering[i].earlier.size();

			orderingArray[i].later = new int[orderingArray[i].laterDegree];
			orderingArray[i].earlier = new int[orderingArray[i].earlierDegree];

			int j = 0;
			for (Integer k : ordering[i].later) {
				orderingArray[i].later[j] = k;
				j++;
			}

			j = 0;
			for (Integer k : ordering[i].earlier) {
				orderingArray[i].earlier[j] = k;
				j++;
			}

		}

		return orderingArray;
	}

	private static int max(int x, int y) {
		int max = x > y ? x : y;
		return max;
	}
}
