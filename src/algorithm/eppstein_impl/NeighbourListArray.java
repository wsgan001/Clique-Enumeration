package algorithm.eppstein_impl;

class NeighbourListArray {
	int vertex;

	int[] earlier;
	int earlierDegree;

	int[] later;
	int laterDegree;

	int orderNumber;

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
