package graph;

/**
 * Adjacency list representation of a graph. It contains an array of its vertices. Every vertex is represented by a
 * {@link graph.Vertex} object, which contains an adjacency list for the vertex.
 */
public class GraphAL implements Graph {
	private Vertex[] vertices;
	private int size;

	/**
	 * Default constructor that initialises the vertex set but does not add any edges.
	 * @param size number of vertices.
	 */
	public GraphAL(int size) {
		this.size = size;

		initVertices(size);
	}

	/**
	 * Constructor that builds the graph from a BitSet graph.
	 * @param graph a BitSet graph to be copied.
	 */
	public GraphAL(GraphBitSet graph) {
		this(graph.size());

		for(int i = 0; i < size; i++) {
			for(int j = graph.neighbours(i).nextSetBit(i); j > -1; j = graph.neighbours(i).nextSetBit(j + 1)) {
				vertices[i].addAdjacent(vertices[j]);
				vertices[j].addAdjacent(vertices[i]);
			}
		}
	}

	// Helper method for the constructor.
	private void initVertices(int n) {
		vertices = new Vertex[n];
		
		for(int i = 0; i < n; i++) {
			vertices[i] = new Vertex(i);
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

	/**
	 * Returns the vertex object of a given index.
	 * @param v the index of the vertex.
	 * @return the {@link graph.Vertex} object.
	 */
	public Vertex getVertex(int v) {
		return vertices[v];
	}

	/**
	 * Returns the vertex set of the graph.
	 * @return the vertex set.
	 */
	public Vertex[] getVertices() { return vertices; }

	@Override
	public String toString() {
		String s = "";
		for (Vertex v : vertices) s = s + v + '\n';
		return s;
	}

}
