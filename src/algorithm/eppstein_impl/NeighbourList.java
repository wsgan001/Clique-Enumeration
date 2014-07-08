package algorithm.eppstein_impl;

import java.util.LinkedList;

public class NeighbourList {

	int vertex;
	LinkedList<Integer> earlier;
	LinkedList<Integer> later;
	int orderNumber;

	public NeighbourList() {
		earlier = new LinkedList<>();
		later = new LinkedList<>();
	}

}
