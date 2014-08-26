package graph;

import java.util.ArrayList;

/**
 * Adjacency list representation of a vertex.
 */
public class Vertex {

	private final int index;				// index of the vertex
	private ArrayList<Vertex> adjList;		// list of neighbours

	/**
	 * Constructor that only specifies the index of the vertex.
	 * @param index index of the vertex.
	 */
	public Vertex(int index) {
		adjList = new ArrayList<Vertex>();
		this.index = index;
	}

	/**
	 * Constructor that specifies the index and the neighbours of the vertex.
	 * @param adjacencyList neighbourhood.
	 * @param index index of the vertex.
	 */
	public Vertex(ArrayList<Vertex> adjacencyList, int index) {
		this.adjList = adjacencyList;
		this.index = index;
	}

	/**
	 * Adds a vertex to the adjacency neighbourhood.
	 * @param v the {@link graph.Vertex} to be added.
	 */
	public void addAdjacent(Vertex v) {
		if(!adjList.contains(v))
			adjList.add(v);
	}

	/**
	 * Resets the adjacency list of this vertex to a new list specified in the parameter.
	 * @param adjList the new neighbourhood.
	 */
	public void setAdjacency(ArrayList<Vertex> adjList) { this.adjList = adjList; }

	/**
	 * Returns whether vertex v is adjacent to this vertex.
	 * @param v the other vertex.
	 * @return true, if they are adjacent, false otherwise.
	 */
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
