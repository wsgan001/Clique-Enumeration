package algorithm.bitset_impl;

/**
 * Class containing methods to return a pivot selector and ordering based in an input string.
 */
public abstract class Factory {

	/**
	 * Returns an ordering based on the input string.
	 * @param input should be none, core or degree for no ordering, degeneracy and degree ordering in this order.
	 * @return	an ordering.
	 * @throws java.lang.IllegalArgumentException
	 */
	public static Order createOrder(String input) throws IllegalArgumentException {
		Order order;
		if (input.equals("none"))
			order = new OrderNone();
		else if (input.equals("core"))
			order = new OrderCores();
		else if (input.equals("degree"))
			order = new OrderDegree();
		else throw new IllegalArgumentException();

		return order;
	}

	/**
	 * Returns a pivot selector based on the input string
	 * @param input should be none or tomita for arbitrary and Tomita pivot selection.
	 * @return	a pivot selector.
	 * @throws java.lang.IllegalArgumentException
	 */
	public static Pivot createPivot(String input) throws IllegalArgumentException {
		Pivot pivot;
		if (input.equals("none"))
			pivot = new PivotNone();
		else if (input.equals("tomita"))
			pivot = new PivotTomita();
		else throw new IllegalArgumentException();

		return pivot;
	}
}
