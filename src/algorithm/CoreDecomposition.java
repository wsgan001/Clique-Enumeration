package algorithm;

import graph.GraphBS;
import graph.VertexBS;
import java.util.BitSet;

public abstract class CoreDecomposition {

	public static int[] cores(GraphBS graph) {
		int n = graph.size();

		// keeps tracks of the positions of the vertices
		int[] pos = new int[n];
		int[] vert = new int[n];
		// Store the degree of all vertices in the graph (e.g. degrees[i] shows the degree of the vertex with index 'i')
		int[] degrees = new int[n];
		// maximum degree in the graph

		// START ORDERING
		int maxDegree = 0;
		for (int i = 0; i < graph.size(); i++) {
			degrees[i] = graph.getVertex(i).getDegree();
			if (degrees[i] > maxDegree) maxDegree = degrees[i];
		}

		int[] bin = new int[maxDegree + 1];

		// calculate the number of vertices of a given degree
		int k = 0;
		for (VertexBS v : graph.getVertexSet())
			bin[v.getDegree()]++;

		// calculate the starting point for every degree
		int start = 0;
		for (int d = 0; d < bin.length; d++) {
			int num = bin[d];
			bin[d] = start;
			start += num;
		}

		// order the vertices
		for (VertexBS v : graph.getVertexSet()) {
			pos[v.getIndex()] = bin[v.getDegree()];
			vert[pos[v.getIndex()]] = v.getIndex();
			bin[v.getDegree()]++;
		}

		for (int d = maxDegree; d > 0; d--) {
			bin[d] = bin[d-1];
		}
		bin[0] = 0;

		// END ORDERING

		// START CORE DECOMPOSITION
		for (int i = 0; i < n; i++) {
			int v = vert[i];

			/*
			For all neighbours, we the neighbour and the first vertex in the same bin.
			In pos we swap their position and finally we increment the starting position of the bin.
			 */
			BitSet neigbours = graph.getNeighbours(v);
			for (int u = neigbours.nextSetBit(0); u > -1; u = neigbours.nextSetBit(u + 1)) {
				if (degrees[u] > degrees[v]) {
					int du = degrees[u];	int pu = pos[u];
					int pw = bin[du];		int w = vert[pw];

					if (u != w) {
						pos[u] = pw;
						vert[pu] = w;
						pos[w] = pu;
						vert[pw] = u;
					}
					bin[du]++;
					degrees[u]--;
				}
			}
		}

		return vert;
	}
}
