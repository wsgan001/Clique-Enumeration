package algorithm.other_impl;

import algorithm.CliqueAlgorithm;
import graph.GraphAL;
import graph.Vertex;
import utility.SetOperation;

import java.util.Arrays;
import java.util.ArrayList;

/**
 * Simplest implementation of the Bron-Kerbosch algorithm for testing and verification purposes. Not efficient.
 */
public class TomitaAdjacencyList extends CliqueAlgorithm {
	private final int size;
	private GraphAL graph;

	public TomitaAdjacencyList(GraphAL graph) {
		this(graph, false);
	}

	public TomitaAdjacencyList(GraphAL graph, boolean verbose) {
		super(verbose);
		this.size = graph.size();
		this.graph = graph;
	}

	public void run() {
		ArrayList<Vertex> P = new ArrayList<Vertex>(Arrays.asList(graph.getVertices()));
		extend(new ArrayList<Vertex>(), P, new ArrayList<Vertex>());
	}

	
	private void extend(ArrayList<Vertex> R, ArrayList<Vertex> P, ArrayList<Vertex> X) {
		
		if (P.isEmpty() && X.isEmpty()) reportClique(R);
		
		else {
			// Find the pivot
			ArrayList<Vertex> union = new ArrayList<Vertex>(P);
			union.addAll(X);
			Vertex pivot = null;
			int max = -1;
			for (Vertex v : union) {
				ArrayList<Vertex> set = SetOperation.intersect(P, v.getAdjacencyList());
				if (set.size() > max) {
					max = set.size();
					pivot = v;
					if (max == P.size()) break;
				}
			}

			ArrayList<Vertex> disconnected = new ArrayList<Vertex>(P);
			disconnected.removeAll(pivot.getAdjacencyList());

			for (Vertex v : disconnected){
				extend(SetOperation.addToSet(R, v), SetOperation.intersect(P, v.getAdjacencyList()), SetOperation.intersect(X, v.getAdjacencyList()));
			
				P.remove(v);
				X.add(v);
			}
		}
	}

}
