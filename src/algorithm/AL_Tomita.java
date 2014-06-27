package algorithm;

import graph.GraphAL;
import graph.VertexAL;
import utility.SetOperation;

import java.util.Arrays;
import java.util.ArrayList;

public class AL_Tomita extends CliqueAlgorithm {
	private final int size;
	private GraphAL graph;

	public AL_Tomita(GraphAL graph) {
		this(graph, false);
	}

	public AL_Tomita(GraphAL graph, boolean verbose) {
		super(verbose);
		this.size = graph.size();
		this.graph = graph;
	}

	public void run() {
		ArrayList<VertexAL> P = new ArrayList<>(Arrays.asList(graph.getVertices()));
		extend(new ArrayList<VertexAL>(), P, new ArrayList<VertexAL>());
	}

	
	private void extend(ArrayList<VertexAL> R, ArrayList<VertexAL> P, ArrayList<VertexAL> X) {
		
		if (P.isEmpty() && X.isEmpty()) reportClique(R);
		
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

}