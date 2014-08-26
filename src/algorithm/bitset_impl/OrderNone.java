package algorithm.bitset_impl;

import graph.GraphBitSet;

/**
 * Ordering class whose {@link algorithm.bitset_impl.OrderNone#order(graph.GraphBitSet)} method returns null. It is
 * used when no ordering is required.
 */
public class OrderNone implements Order {

	/**
	 * Returns null to show that no ordering is needed.
	 * @param graph the graph.
	 * @return a null order.
	 */
	@Override
	public int[] order(GraphBitSet graph) {
		return null;
	}
}
