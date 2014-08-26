package algorithm.eppstein_impl;

/**
 * Data structure that stores the vertices that come before and after a vertex in the degeneracy ordering.
 */
class NeighbourListArray {
	int vertex; // index of the vertex this represents

	int[] earlier; // vertices that come before this in the ordering
	int earlierDegree;

	int[] later; // vertices that come after this in the ordering
	int laterDegree;

	int orderNumber;

	// this was used for testing purposes
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("Vertex" + vertex + " degeneracy order: " + orderNumber + "\n");
		s.append("\tEarlier vertices:");
		for (int v : earlier) s.append(" ").append(v);
		s.append('\n');
		s.append("\tLater vertices:");
		for (int v : later) s.append(" ").append(v);
		s.append('\n');
		return s.toString();
	}
}
