package graph;

import java.util.ArrayList;

public class VertexAL {

	private final int index;
	private ArrayList<VertexAL> adjList;
	
	public VertexAL(int index) {
		adjList = new ArrayList<>();
		this.index = index;
	}

	public VertexAL(ArrayList<VertexAL> adjacencyList, int index) {
		this.adjList = adjacencyList;
		this.index = index;
	}
	
	public void addAdjacent(VertexAL v) {
		if(!adjList.contains(v))
			adjList.add(v);
	}

	public void setAdjacency(ArrayList<VertexAL> adjList) { this.adjList = adjList; }

	public boolean isAdjacent(int v) {
		for(VertexAL w : adjList) {
			if(w.getIndex() == v) return true;
		}

		return false;
	}
	
	public ArrayList<VertexAL> getAdjacencyList() {
		return adjList;
	}
	
	public int getIndex() {
		return index;
	}
	
	public String toString() {
		String s = index + ":";
		for(VertexAL v : adjList) s = s + " " + v.getIndex();
		return s;
	}
}
