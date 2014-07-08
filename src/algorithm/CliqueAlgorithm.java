package algorithm;

import graph.Vertex;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.LinkedList;

public abstract class CliqueAlgorithm {

	private int cliqueCounter = 0;
	private boolean verbose;

	public CliqueAlgorithm(boolean verbose) {
		this.verbose = verbose;
	}

	public int getNumberOfCliques() {
		return cliqueCounter;
	}

	public void measureTime() {
		long start = System.nanoTime();
		System.out.println("Starting algorithm...");
		run();
		System.out.println("Done (executed in " + (System.nanoTime()-start)/1000000 + " ms).");
	}

	public abstract void run();

	public void reportClique(BitSet clique) {
		cliqueCounter++;
		if (verbose)
			System.out.println("Clique " + cliqueCounter + ": " + clique);
	}

	public void reportClique(ArrayList<Vertex> clique) {
		cliqueCounter++;
		if (verbose) {
			System.out.print("Clique " + cliqueCounter + ": {");

			if (!clique.isEmpty()) {
				Iterator<Vertex> it = clique.iterator();
				System.out.print(it.next().getIndex());
				while (it.hasNext()) {
					System.out.print(", " + it.next().getIndex());
				}
			}
			System.out.println("}");
		}
	}

	public void reportClique(int[] compsub, int c) {
		cliqueCounter++;
		if (verbose) {
			System.out.print("clique" + cliqueCounter + " = ");
			for (int k = 0; k < c; k++) {
				System.out.print(compsub[k] + " ");
			}
			System.out.println();
		}
	}

	public void reportClique(LinkedList<Integer> clique) {
		cliqueCounter++;
		if (verbose) {
			System.out.println("clique" + cliqueCounter + " =");
			for (int k : clique)
				System.out.println(" " + k);
		}
	}

}
