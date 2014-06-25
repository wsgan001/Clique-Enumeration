package graph;

import java.util.BitSet;

public class GraphBS implements Graph {

	private VertexBS[] vertices;
	private final int size;

	public GraphBS(int size) {
		this.size = size;

		vertices = new VertexBS[size];
		for(int i = 0; i < size; i++) {
			vertices[i] = new VertexBS(i, size);
		}
	}

	public GraphBS(GraphBS other) {
		size = other.size();

		vertices = new VertexBS[size];

		for (int i = 0; i < size; i++) {
			vertices[i] = new VertexBS(i, (BitSet)other.getNeighbours(i).clone());
		}
	}

	public GraphBS(GraphAL graph) {
		size = graph.size();

		vertices = new VertexBS[size];

		for(int i = 0; i < size; i++) {
			vertices[i] = new VertexBS(i, size);

			for (VertexAL v : graph.getVertex(i).getAdjacencyList()) {
				addEdge(i, v.getIndex());
			}
		}
	}

	@Override
	public void addEdge(int v, int w) {
		vertices[v].addEdge(w);
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isAdjacent(int v, int w) {
		return vertices[v].getAdjacency().get(w);
	}

	public BitSet getNeighbours(int v) {
		return vertices[v].getAdjacency();
	}

	public VertexBS[] getVertexSet() {
		return vertices;
	}

	public VertexBS getVertex(int v) {
		return vertices[v];
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("Graph size: " + size + '\n');
		for(int i = 0; i < size; i++) {
			s.append("Vertex ").append(i).append(" neighbours: ").append(vertices[i].getAdjacency()).append('\n');
		}

		return s.toString();
	}

	@Override
	public boolean equals(Object o) {
		GraphBS other = (GraphBS) o;

		if (this.size != other.size())
			return false;

		for (int i = 0; i < size; i++) {
			if (!vertices[i].equals(other.getVertex(i)))
				return false;
		}

		return true;
	}

}
