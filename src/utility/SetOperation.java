package utility;

import java.util.ArrayList;

public abstract class SetOperation {

	public static<V> ArrayList<V> intersect(ArrayList<V> set1, ArrayList<V> set2) {
		ArrayList<V> intersection;
		if (set1.size() < set2.size()) {
			intersection = new ArrayList<>(set1);
			intersection.retainAll(set2);
		}
		else {
			intersection = new ArrayList<>(set2);
			intersection.retainAll(set1);
		}
		return intersection;
	}


	public static<V> ArrayList<V> addToSet(ArrayList<V> set, V element) {
		ArrayList<V> newSet = new ArrayList<>(set);
		set.add(element);
		return newSet;
	}
}
