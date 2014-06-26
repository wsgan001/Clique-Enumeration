package algorithm;

import graph.GraphAL;
import graph.VertexAL;
import utility.SetOperation;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Iterator;

public class TomitaAL {
	private int cliqueNumber = 0;
	private final int size;
	private GraphAL graph;

	public TomitaAL(GraphAL graph) {
		this.size = graph.size();
		this.graph = graph;
	}

	public void run() {
		ArrayList<VertexAL> P = new ArrayList<>(Arrays.asList(graph.getVertices()));
		extend(new ArrayList<VertexAL>(), P, new ArrayList<VertexAL>());
	}

	
	private void extend(ArrayList<VertexAL> R, ArrayList<VertexAL> P, ArrayList<VertexAL> X) {
		
		if (P.isEmpty() && X.isEmpty()) printClique(R, ++cliqueNumber);
		
		else {
			// Find the pivot
			ArrayList<VertexAL> union = new ArrayList<>(P);
			union.addAll(X);
			VertexAL pivot = null;
			int max = -1;
			for (VertexAL v : union) {
				ArrayList<VertexAL> set = SetOperation.intersect(P, v.getAdjacencyList());
				if (set.size() > max) {
					max = set.size();
					pivot = v;
					if (max == P.size()) break;
				}
			}

			ArrayList<VertexAL> disconnected = new ArrayList<>(P);
			disconnected.removeAll(pivot.getAdjacencyList());

			for (VertexAL v : disconnected){
				extend(SetOperation.addToSet(R, v), SetOperation.intersect(P, v.getAdjacencyList()), SetOperation.intersect(X, v.getAdjacencyList()));
			
				P.remove(v);
				X.add(v);
			}
		}
	}

	private void printClique(ArrayList<VertexAL> clique, int n) {
		System.out.print("Clique " + n + ": {");

		if (!clique.isEmpty()) {
			Iterator<VertexAL> it = clique.iterator();
			System.out.print(it.next().getIndex());
			while (it.hasNext()) {
				System.out.print(", " + it.next().getIndex());
			}
		}
		System.out.println("}");
	}
}
