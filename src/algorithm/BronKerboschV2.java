package algorithm;

import graph.GraphAL;
import graph.VertexAL;
import utility.FileLoader;
import utility.SetOperation;

import java.util.ArrayList;

public class BronKerboschV2 {
	private static int cliqueNumber = 0;

	public static void main(String[] args) {
		GraphAL g = FileLoader.loadGraphAL("marknewman/marknewman-polblogs");
		
		ArrayList<VertexAL> P = new ArrayList<>();
		for (VertexAL v : g.getVertices()) P.add(v);
		extend(new ArrayList<VertexAL>(), P, new ArrayList<VertexAL>());
	}

	
	private static void extend(ArrayList<VertexAL> R, ArrayList<VertexAL> P, ArrayList<VertexAL> X) {
		
		if (P.isEmpty() && X.isEmpty()) System.out.println(++cliqueNumber);
		
		else {
			int minnod = X.size() + P.size() + 1;
			
			ArrayList<VertexAL> disconnected = new ArrayList<>();
			
			// search for disconnections
			ArrayList<VertexAL> union = new ArrayList<>(X);
			union.addAll(P);
			
			for(VertexAL v : union) {
				ArrayList<VertexAL> d = new ArrayList<>(P);
				d.removeAll(v.getAdjacencyList());
				
				int count = d.size();
				
				if (count < minnod) {
					minnod = count;
					disconnected = d;
				}
			
				if (minnod == 0) break;
			}

			for (VertexAL v : disconnected){
				extend(SetOperation.addToSet(R,v), SetOperation.intersect(P, new ArrayList<VertexAL>(v.getAdjacencyList())),
						SetOperation.intersect(X, new ArrayList<VertexAL>(v.getAdjacencyList())));
			
				P.remove(v);
				X.add(v);
			}
		}
	}
}
