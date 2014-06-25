package graph;

public interface Graph {

	public void addEdge(int v, int w);

	public int size();

	public boolean isAdjacent(int v, int w);
}
