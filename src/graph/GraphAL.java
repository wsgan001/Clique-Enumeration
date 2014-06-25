package graph;


public class GraphAL implements Graph {
	private VertexAL[] vertices;
	private int size;

	public GraphAL(int size) {
		this.size = size;

		initVertices(size);
	}

	public GraphAL(GraphBS graph) {
		size = graph.size();

		initVertices(size);

		for(int i = 0; i < size; i++) {
			for(int j = graph.getVertex(i).getAdjacency().nextSetBit(i); j > -1; j = graph.getVertex(i).getAdjacency().nextSetBit(j + 1)) {
				vertices[i].addAdjacent(vertices[j]);
				vertices[j].addAdjacent(vertices[i]);
			}
		}
	}

	private void initVertices(int n) {
		vertices = new VertexAL[n];
		
		for(int i = 0; i < n; i++) {
			vertices[i] = new VertexAL(i);
		}
	}

	@Override
	public void addEdge(int vertex1, int vertex2) {
		vertices[vertex1].addAdjacent(vertices[vertex2]);
	}

	@Override
	public int size() { return size; }

	@Override
	public boolean isAdjacent(int v, int w) {
		return vertices[v].isAdjacent(w);
	}

	public VertexAL getVertex(int v) {
		return vertices[v];
	}

	public VertexAL[] getVertices() { return vertices; }

	@Override
	public String toString() {
		String s = "";
		for (VertexAL v : vertices) s = s + v + '\n';
		return s;
	}

}
