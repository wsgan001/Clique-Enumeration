package graph;

/**
 * Common interface for different graph representations.
 */
public interface Graph {

	/**
	 * Adds an edge joining v and w.
	 * @param v an endpoint.
	 * @param w another endpoint.
	 */
	public void addEdge(int v, int w);

	/**
	 * Returns the size of this graph.
	 * @return the size.
	 */
	public int size();

	/**
	 * Returns whether v and w are connected.
	 * @param v first vertex.
	 * @param w second vertex.
	 * @return true if v and w are connected, false if not.
	 */
	public boolean isAdjacent(int v, int w);
}
