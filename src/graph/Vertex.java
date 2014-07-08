package graph;

import java.util.ArrayList;

public class Vertex {

	private final int index;
	private ArrayList<Vertex> adjList;
	
	public Vertex(int index) {
		adjList = new ArrayList<>();
		this.index = index;
	}

	public Vertex(ArrayList<Vertex> adjacencyList, int index) {
		this.adjList = adjacencyList;
		this.index = index;
	}
	
	public void addAdjacent(Vertex v) {
		if(!adjList.contains(v))
			adjList.add(v);
	}

	public void setAdjacency(ArrayList<Vertex> adjList) { this.adjList = adjList; }

	public boolean isAdjacent(int v) {
		for(Vertex w : adjList) {
			if(w.getIndex() == v) return true;
		}

		return false;
	}
	
	public ArrayList<Vertex> getAdjacencyList() {
		return adjList;
	}
	
	public int getIndex() {
		return index;
	}
	
	public String toString() {
		String s = index + ":";
		for(Vertex v : adjList) s = s + " " + v.getIndex();
		return s;
	}
}
