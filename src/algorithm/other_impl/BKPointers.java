package algorithm.other_impl;

import algorithm.CliqueAlgorithm;
import graph.GraphBitSet;
import java.util.BitSet;

/**
 * Bron-Kerbosch algorithm implementation. Conversion from the original Algol code.
 * Reference: C. Bron and J. Kerbosch: Finding All Cliques of an Undirected Graph (1973)
 */
public class BKPointers extends CliqueAlgorithm {
	private BitSet[] connected;
	private int[] all;
	private int n;

	private int[] compsub;
	private int c = 0;

	/**
	 * Create the algorithm instance.
	 * @param graph the graph that stores the adjacency matrix.
	 */
	public BKPointers(GraphBitSet graph) {
		this(graph, false);
	}

	/**
	 * Create the algorithm instance.
	 * @param graph the graph that stores the adjacency matrix.
	 * @param verbose true, if all cliques are to be printed.
	 */
	public BKPointers(GraphBitSet graph, boolean verbose) {
		super(verbose);
		n = graph.size();
		connected = graph.adjacencyMatrix();
	}

	@Override
	public void run() {
		// Set diagonal to 1 as required by the algorithm
		for (int i = 0; i < n; i++) connected[i].set(i);

		all = new int[n];
		compsub = new int[n];

		for(int i = 0; i < n; i++) {
			all[i] = i;
		}

		extend(all, 0, all.length);

		// Set the diagonal back to 0
		for (int i = 0; i < n; i++) connected[i].clear(i);
	}

	// recursive call
	private void extend(int[] oldSet, int ne, int ce) {
		inc(); // increment the number of recursive calls

		int[] newSet = new int[ce];
		int fixp = 0; // although it's called fix point here, this is the pivot

		int newne, newce, p, sel;

		// candidate position
		int s = 0;

		// temporary number of disconnections (name kept from the original BK algorithm, but it does not store the
		// minimum anymore
		int minnod = ce;
		// position of first candidate
		int pos = 0;
		// number of disconnections
		int nod = 0;


		// DETERMINE EACH COUNTER VALUE AND LOOK FOR MINIMUM
		for(int i = 0; i < ce && minnod != 0; i++) {

			// currently tested vertex
			p = oldSet[i];

			int count = 0;

			// COUNT DISCONNECTIONS
			// 'count <= minnod' ensures the loop breaks when count is over the previous minimum
			// 'count' keeps track of the number of disconnections
			for(int j = ne; j < ce && count < minnod; j++) {
				if (!connected[p].get(oldSet[j])) {
					count++;
					// SAVE POSITION OF POTENTIAL CANDIDATE
					pos = j;
				}
			}

			// TEST NEW MINIMUM
			if (count < minnod) {
				fixp = p;

				// set minimum to the new value
				minnod = count;

				// if minimum is in NOT
				if (i < ne) s = pos;

					// if minimum is in CAND
				else {
					s = i;
					nod = 1;
				}
			}

		}


		// Iterate through the intersection of P and N(pivot)
		// if fixed point initially chosen from CAND then number of disconnections will be preincreased by one
		for (nod = minnod + nod; nod >= 1; nod--) {

			// INTERCHANGE
			p = oldSet[s]; 				// store the candidate temporarily
			oldSet[s] = oldSet[ne]; 	// move next vertex to candidate's position
			oldSet[ne] = p; 			// move candidate to next position

			sel = oldSet[ne];			// candidate

			// FILL NEW SET NOT
			newne = 0;
			for(int i = 0; i < ne; i++) {
				// moving all vertices in old NOT which are connected to candidate to new NOT
				if (connected[sel].get(oldSet[i])) {
					newSet[newne++] = oldSet[i];
				}
			}

			// FILL NEW SET CAND
			newce = newne;
			for(int i = ne + 1; i < ce; i++) {
				// moving all vertices in old CAND which are connected to candidate to new CAND
				if(connected[sel].get(oldSet[i])) {
					newSet[newce++] = oldSet[i];
				}
			}

			// ADD TO COMPSUB
			compsub[c++] = sel;

			// print compsub if it's a maximal clique
			if (newce == 0) {
				inc();
				reportClique(compsub, c);
			}

			else if (newne < newce) extend(newSet, newne, newce);

			c--;
			ne++;

			if (nod > 1) {
				// SELECT A CANDIDATE DISCONNECTED TO THE FIXED POINT
				s = ne;

				// LOOK FOR A CANDIDATE
				while(connected[fixp].get(oldSet[s])) s++;
			}

		}

	}
}
