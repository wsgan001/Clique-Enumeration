package algorithm;

import graph.GraphBS;

import java.util.BitSet;

public abstract class AlgorithmBS {

	protected final GraphBS graph;
	protected final int size;
	protected int cliqueNumber;

	public AlgorithmBS(GraphBS graph) {
		this.graph = graph;
		this.size = graph.size();
		this.cliqueNumber = 0;
	}

	public void run() {
		BitSet P = new BitSet(size);
		P.set(0, size);

		extend(new BitSet(size), P, new BitSet(size));
	}

	protected abstract void extend(BitSet R, BitSet P, BitSet X);

	public static void printClique(BitSet clique, int no) {
		System.out.println("Clique " + no + ": " + clique);
	}
}