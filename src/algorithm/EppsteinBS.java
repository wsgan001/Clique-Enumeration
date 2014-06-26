package algorithm;

import graph.VertexBS;
import graph.GraphBS;

import java.util.Arrays;
import java.util.BitSet;

public class EppsteinBS extends TomitaBS {


	public EppsteinBS(GraphBS graph) {
		super(graph);
	}

	@Override
	public void run() {
		int[] degeneracy = order();

		long start = System.nanoTime();
		BitSet P = new BitSet(size);
		P.set(0, size);

		BitSet R = new BitSet(size);
		BitSet X = new BitSet(size);

		for(int v : degeneracy) {
			// Add v to R
			BitSet newR = (BitSet)R.clone();
			newR.set(v);

			// intersection of P and the neighbourhood of v
			BitSet newP = (BitSet)P.clone();
			newP.and(graph.getNeighbours(v));

			// intersection of X and the neighbourhood of v
			BitSet newX = (BitSet)X.clone();
			newX.and(graph.getNeighbours(v));

			// call recursion
			extend(newR, newP, newX);

			// remove v from candidates
			P.flip(v);

			// add v to X
			X.set(v);
		}

		System.out.println("Takes " + (System.nanoTime() - start)/1000000 + " ms without ordering.");

	}

	private int[] order() {
		int size = graph.size();

		VertexBS[] vertices = new VertexBS[size];
		System.arraycopy(graph.getVertexSet(), 0, vertices, 0, size);
		Arrays.sort(vertices);

		for(int i = 0; i < size; i++) {
			for(int j = i; j < size; j++) {
				if(graph.isAdjacent(vertices[i].getIndex(), vertices[j].getIndex())) {
					int k = j;
					while(vertices[j].getDegree() <= vertices[k].getDegree() && k > i) k--;
					VertexBS temp = vertices[j];
					vertices[j] = vertices[k+1];
					temp.decrementDegree();
					vertices[k+1] = temp;
				}
			}

		}

		int[] ordered = new int[size];
		for(int i = 0; i < size; i++) ordered[i] = vertices[i].getIndex();
		return ordered;
	}
}
