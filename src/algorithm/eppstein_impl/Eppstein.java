
package algorithm.eppstein_impl;

import algorithm.CliqueAlgorithm;
import graph.GraphAL;
import graph.Vertex;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Algorithm of Eppstein, Loffler and Strash
 * Source: Eppstein, Loffler and Strash: Listing All Maximal Cliques in Sparse Graphs in Near-Optimal Time (2010)
 * Converted from their C code.
 */
public class Eppstein extends CliqueAlgorithm {

	private GraphAL graph;

	private int[] vertexSets;
	private int[] vertexLookup;

	private int[][] neighboursInP;
	private int[] numNeighbours;

	public Eppstein(GraphAL graph) {
		this(graph, false);
	}

	public Eppstein(GraphAL graph, boolean verbose) {
		super(verbose);
		this.graph = graph;
	}

	@Override
	public void run() {
		LinkedList<Integer>[] adjList = buildAdjList();
		int size = adjList.length;
		vertexSets = new int[size];
		vertexLookup = new int[size];
		neighboursInP = new int[size][];
		numNeighbours = new int[size];

		NeighbourListArray[] orderingArray = EppsteinHelper.computeDegeneracyOrder(adjList, size);

		for (int i = 0; i < size; i++) {
			vertexLookup[i] = i;
			vertexSets[i] = i;
			neighboursInP[i] = new int[1];
			numNeighbours[i] = 1;
		}

		PointerSet pointers = new PointerSet(0, 0, size);

		LinkedList<Integer> partialClique = new LinkedList<Integer>();

		for (int i = 0; i < size; i++) {
			int vertex = orderingArray[i].vertex;
			partialClique.addLast(vertex);

			PointerSet newPointers = new PointerSet();

			fillInPandXForRecursiveCall(i, vertex, orderingArray, pointers, newPointers);
			extend(partialClique, newPointers.X, newPointers.P, newPointers.R);

			pointers.R++;
			partialClique.removeLast();
		}


	}

	private void extend(LinkedList<Integer> partialClique, int beginX, int beginP, int beginR) {
		inc();
		if (beginX >= beginP && beginP >= beginR) {
			reportClique(partialClique);
			return;
		}

		if (beginP >= beginR) return;

		PointerSet pointers = new PointerSet(beginX, beginP, beginR);

		int[] numNonNeighbours = new int[1];
		int[] myCandidatesToIterateThrough = findPivot(numNonNeighbours, beginX, beginP, beginR);
		int numCandidatesToIterateThrough = numNonNeighbours[0];

		if (numCandidatesToIterateThrough != 0) {
			for (int i = 0; i < numCandidatesToIterateThrough; i++) {
				int vertex = myCandidatesToIterateThrough[i];

				PointerSet newPointers = new PointerSet(pointers.P, pointers.P, pointers.P);

				partialClique.addLast(vertex);

				moveToR(vertex, pointers, newPointers);

				extend(partialClique, newPointers.X, newPointers.P, newPointers.R);

				partialClique.removeLast();

				// Move vertex from R to X
				moveFromRToX(vertex, pointers);
			}

			for (int i = 0; i < numCandidatesToIterateThrough; i++) {
				int vertex = myCandidatesToIterateThrough[i];

				pointers.P--;

				swapTo(vertex, pointers.P);
			}
		}
	}

	/**
	 * Computes the pivot the Tomita way: the vertex from P union X that has the most neighbours in P.
	 * @param beginX The index where X begins in vertexSets.
	 * @param beginP The index where P begins in vertexSets.
	 * @param beginR The index where R begins in vertexSets.
	 * @return Pivot Tomita style.
	 */
	private int[] findPivot(int[] numNonNeighbours, int beginX, int beginP, int beginR) {

		// FINDING THE PIVOT
		int pivot = -1;
		int max = -1;

		// Iterate over each vertex in P union X to find the vertex with the most neighbours in P.
		for (int j = beginX; j < beginR; j++) {
			int vertex = vertexSets[j];
			int numPotentialNeighbours = min(beginR - beginP, numNeighbours[vertex]);

			int numNeighboursInP = 0;

			for (int k = 0; k < numPotentialNeighbours; k++) {
				int neighbour = neighboursInP[vertex][k];
				int location = vertexLookup[neighbour];

				if (location >= beginP && location < beginR) {
					numNeighboursInP++;
				}
				else break;
			}

			if (numNeighboursInP > max) {
				pivot = vertex;
				max = numNeighboursInP;
			}
		}

		// END OF FINDING PIVOT

		// COMPUTE P \ N(pivot)
		// initialise pivotNonNeighbours to P and the number of non-neighbours to the size of P
		numNonNeighbours[0] = beginR - beginP;
		int[] pivotNonNeighbours = Arrays.copyOfRange(vertexSets, beginP, beginR);

		// every neighbour is marked -1
		int numPivotNeighbours = min(numNonNeighbours[0], numNeighbours[pivot]);
		for (int j = 0; j < numPivotNeighbours; j++) {
			int neighbour = neighboursInP[pivot][j];
			int location = vertexLookup[neighbour];

			if (location >= beginP && location < beginR) {
				pivotNonNeighbours[location-beginP] = -1;
			}
			else break;
		}
		// neighbours are moved to the end of pivotNonNeighbours and numNonNeighbours is decremented accordingly
		int j = 0;
		while (j < numNonNeighbours[0]){

			int vertex = pivotNonNeighbours[j];

			if (vertex == -1) {
				numNonNeighbours[0]--;
				pivotNonNeighbours[j] = pivotNonNeighbours[numNonNeighbours[0]];
				continue;
			}

			j++;
		}
		// END OF COMPUTE P \ N(pivot)

		return pivotNonNeighbours;
	}

	// helper method to find the minimum of two integers
	private int min(int x, int y) {
		return (x > y) ? y : x;
	}


	private void fillInPandXForRecursiveCall(int vertex, int orderNumber, NeighbourListArray[] orderingArray,
													   PointerSet pointers, PointerSet newPointers) {
		// swap 'vertex' with the first vertex in R and decrement beginR (therefore moving 'vertex' to R)
		pointers.R--;
		swapTo(vertex, pointers.R);

		newPointers.R = pointers.R;
		newPointers.P = pointers.R;

		// swap later neighbours of 'vertex' into P section of vertexSets
		for (int j = 0; j < orderingArray[orderNumber].laterDegree; j++) {
			int neighbour = orderingArray[orderNumber].later[j];

			newPointers.P--;

			swapTo(neighbour, newPointers.P);
		}

		newPointers.X = newPointers.P;

		// swap earlier neighbours of 'vertex' into X section of vertexSets
		for (int j = 0; j < orderingArray[orderNumber].earlierDegree; j++) {
			int neighbour = orderingArray[orderNumber].earlier[j];

			newPointers.X--;

			swapTo(neighbour, newPointers.X);

			neighboursInP[neighbour] = new int[min(newPointers.R - newPointers.P, orderingArray[neighbour].laterDegree)];
			numNeighbours[neighbour] = 0;

			// fill in NeighboursInP
			for (int k = 0; k < orderingArray[neighbour].laterDegree; k++) {
				int laterNeighbour = orderingArray[neighbour].later[k];
				int laterLocation = vertexLookup[laterNeighbour];

				if (laterLocation >= newPointers.P && laterLocation < newPointers.R) {
					neighboursInP[neighbour][numNeighbours[neighbour]] = laterNeighbour;
					numNeighbours[neighbour]++;
				}
			}
		}

		// reset numNeighbours and neighboursInP for this vertex
		for (int j = newPointers.P; j < newPointers.R; j++) {
			int vertexInP = vertexSets[j];
			numNeighbours[vertexInP] = 0;
			neighboursInP[vertexInP] = new int[min(newPointers.R - newPointers.P,
					orderingArray[vertexInP].laterDegree + orderingArray[vertexInP].earlierDegree)];
		}

		// count neighbours in P and fill in array of neighbours in P
		for (int j = newPointers.P; j < newPointers.R; j++) {
			int vertexInP = vertexSets[j];

			for (int k = 0; k < orderingArray[vertexInP].laterDegree; k++) {
				int laterNeighbour = orderingArray[vertexInP].later[k];
				int laterLocation = vertexLookup[laterNeighbour];

				if (laterLocation >= newPointers.P && laterLocation < newPointers.R) {
					neighboursInP[vertexInP][numNeighbours[vertexInP]] = laterNeighbour;
					numNeighbours[vertexInP]++;
					neighboursInP[laterNeighbour][numNeighbours[laterNeighbour]] = vertexInP;
					numNeighbours[laterNeighbour]++;
				}
			}
		}

	}


	// move a vertex to R and readjust the pointers
	private void moveToR(int vertex, PointerSet pointers, PointerSet newPointers) {

		pointers.R--;
		swapTo(vertex, pointers.R);

		int sizeOfP = pointers.R - pointers.P;

		int j = pointers.X;
		while (j < newPointers.X) {
			int neighbour = vertexSets[j];

			boolean incrementJ = true;

			int numPotentialNeighbours = min(sizeOfP, numNeighbours[neighbour]);

			for (int k = 0; k < numPotentialNeighbours; k++) {

				if (neighboursInP[neighbour][k] == vertex) {
					newPointers.X--;
					swapTo(neighbour, newPointers.X);
					incrementJ = false;
				}

			}

			if (incrementJ) j++;
		}

		for (j = pointers.P; j < pointers.R; j++) {
			int neighbour = vertexSets[j];

			int numPotentialNeighbours = min(sizeOfP, numNeighbours[neighbour]);

			for (int k = 0; k < numPotentialNeighbours; k++) {

				if (neighboursInP[neighbour][k] == vertex) {
					swapTo(neighbour, newPointers.R);
					newPointers.R++;
				}

			}
		}

		for (j = newPointers.X; j < newPointers.R; j++) {
			int thisVertex = vertexSets[j];

			int numPotentialNeighbours = min(sizeOfP, numNeighbours[thisVertex]);

			int numNeighboursInP = 0;

			for (int k = 0; k < numPotentialNeighbours; k++) {
				int neighbour = neighboursInP[thisVertex][k];
				int neighbourLocation = vertexLookup[neighbour];

				if (neighbourLocation >= newPointers.P && neighbourLocation < newPointers.R) {
					neighboursInP[thisVertex][k] = neighboursInP[thisVertex][numNeighboursInP];
					neighboursInP[thisVertex][numNeighboursInP] = neighbour;
					numNeighboursInP++;
				}
			}
		}
	}

	private void moveFromRToX(int vertex, PointerSet pointers) {
		swapTo(vertex, pointers.P);
		pointers.P++; pointers.R++;
	}



	private void swapTo(int v, int position) {
		int location = vertexLookup[v];

		vertexSets[location] = vertexSets[position];
		vertexSets[position] = v;
		vertexLookup[v] = position;
		vertexLookup[vertexSets[location]] = location;
	}

	private LinkedList<Integer>[] buildAdjList() {

		LinkedList<Integer>[] adjList = new LinkedList[graph.size()];

		for (int i = 0; i < graph.size(); i++) {
			adjList[i] = new LinkedList<Integer>();

			for (Vertex v : graph.getVertex(i).getAdjacencyList()) {
				adjList[i].addLast(v.getIndex());
			}
		}

		return adjList;
	}


	private static class PointerSet {
		int X;
		int P;
		int R;

		public PointerSet() {
			X = P = R = 0;
		}

		public PointerSet(int X, int P, int R) {
			this.X = X;
			this.P = P;
			this.R = R;
		}
	}

}
