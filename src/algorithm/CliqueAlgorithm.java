package algorithm;

import graph.Vertex;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * The base class for all algorithms.
 *
 * Stores statistics about the algorithm's run, such as the time it took, the number of cliques it found and the nodes
 * it visited to enumerate all of them.
 */
public abstract class CliqueAlgorithm {

	private long cliqueCounter;
	private boolean verbose;
	private long time;
	private long calls;

	/**
	 * Sets the verbosity.
	 * @param verbose true, if cliques are to be printed one by one.
	 */
	public CliqueAlgorithm(boolean verbose) {
		this.verbose = verbose;
	}

	/**
	 * Return the number of cliques found.
	 * @return the number of cliques.
	 */
	public long getNumberOfCliques() {
		return cliqueCounter;
	}

	/**
	 * Starts the algorithm, resetting every value to zero before executing it.
	 */
	public void execute() {
		long start = System.nanoTime();
		calls = 0;
		cliqueCounter = 0;
		run();
		time = (System.nanoTime() - start) / 1000000;
	}

	public long getTime() { return time; }

	public long getCalls() { return calls; }

	protected void inc() { calls++; }

	public abstract void run();

	/**
	 * Print clique from BitSet data.
	 * @param clique the clique.
	 */
	public void reportClique(BitSet clique) {
		cliqueCounter++;
		if (verbose)
			System.out.println("Clique " + cliqueCounter + ": " + clique);
	}

	/**
	 * Print clique from ArrayList data.
	 * @param clique the clique.
	 */
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

	/**
	 * Report clique from BK original format.
	 * @param compsub the array containing the vertices.
	 * @param c marks the end of the clique.
	 */
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

	/**
	 * Report a clique from a LinkedList
	 * @param clique the clique.
	 */
	public void reportClique(LinkedList<Integer> clique) {
		cliqueCounter++;
		if (verbose) {
			System.out.println("clique" + cliqueCounter + " =");
			for (int k : clique)
				System.out.println(" " + k);
		}
	}

}
