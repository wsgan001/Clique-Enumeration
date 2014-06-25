package graph;

import java.util.BitSet;

public class VertexBS implements Comparable<VertexBS> {
	private int index;
	private final BitSet adjacency;
	private int degree;

	public VertexBS(int index, int size) {
		this.index = index;
		degree = 0;
		adjacency = new BitSet(size);
	}

	public VertexBS(int index, BitSet adjacency) {
		this.index = index;
		degree = adjacency.cardinality();
		this.adjacency = adjacency;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int label) { this.index = label; }

	public void decrementDegree() { degree--; }

	public void addEdge(int other) {
		adjacency.set(other);
		degree++;
	}

	public BitSet getAdjacency() {
		return adjacency;
	}

	public int getDegree() {
		return degree;
	}

	@Override
	public int compareTo(VertexBS o) {
		if (this.degree > o.getDegree())
			return 1;
		else if (this.degree < o.getDegree())
			return -1;
		else
			return 0;
	}

	@Override
	public boolean equals(Object obj) {
		VertexBS o = (VertexBS)obj;
		boolean equals = true;

		if (!this.adjacency.equals(o.getAdjacency()))
			equals = false;

		if (this.index != o.getIndex())
			equals = false;

		return equals;
	}
}
