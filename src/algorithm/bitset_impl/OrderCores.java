package algorithm.bitset_impl;

import graph.GraphBitSet;

import java.util.BitSet;

/**
 * Reference: Vladimir Batagelj, Matjaz Zaversnik: An O(m) Algorithm for Cores Decomposition of Networks (2006)
 */
public class OrderCores implements Order {

	/**
	 * An implementation of Batagelj's core decomposition algorithm using a BitSet array adjacency matrix representation
	 * of the input graph.
	 * @param graph The input graph.
	 * @return A degeneracy ordering of the graphs vertices.
	 */
	public int[] order(GraphBitSet graph) {
		int n = graph.size();

		int[] pos = new int[n];		// keeps tracks of the positions of the vertices in 'vert'
		int[] vert = new int[n]; 	// set of vertices initially sorted by their degrees
		int[] deg = new int[n];		// the degree for all vertices
		int maxDegree = 0;			// maximum degree in the graph

		// START ORDERING

		// calculate the degree array and remember the maximum degree simultaneously.
		for (int i = 0; i < graph.size(); i++) {
			deg[i] = graph.degree(i);
			if (deg[i] > maxDegree) maxDegree = deg[i];
		}

		int[] bin = new int[maxDegree + 1];	// bin[i] keeps track of the position of the first vertex of degree 'i' in 'vert'

		// calculate the number of vertices of a given degree
		int k = 0;
		for (int i = 0; i < graph.size(); i++)
			bin[graph.degree(i)]++;

		// calculate the correct values in bin
		int start = 0;
		for (int d = 0; d < bin.length; d++) {
			int num = bin[d];
			bin[d] = start;
			start += num;
		}

		// order the vertices
		for (int i = 0; i < n; i++) {
			pos[i] = bin[graph.degree(i)];
			vert[pos[i]] = i;
			bin[graph.degree(i)]++;
		}

		// reset bin (as it the ordering above messes it up)
		System.arraycopy(bin, 0, bin, 1, maxDegree);
		bin[0] = 0;

		// END ORDERING

		// START CORE DECOMPOSITION
		for (int i = 0; i < n; i++) {
			int v = vert[i];

			if (deg[v] > k) k = deg[v];

			// For all neighbours, we the neighbour and the first vertex in the same bin.
			// In pos we swap their position and finally we increment the starting position of the bin.
			BitSet neigbours = graph.neighbours(v);
			for (int u = neigbours.nextSetBit(0); u > -1; u = neigbours.nextSetBit(u + 1)) {
				if (deg[u] > deg[v]) {
					// swap u and the vertex its bin points to
					int du = deg[u];	int pu = pos[u];
					int pw = bin[du];		int w = vert[pw];

					if (u != w) {
						pos[u] = pw;
						vert[pu] = w;
						pos[w] = pu;
						vert[pw] = u;
					}
					bin[du]++;
					deg[u]--;
				}
			}
		}

		// System.out.println("Core " + k);
		return vert;
	}
}
