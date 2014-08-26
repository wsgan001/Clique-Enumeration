package test;

import java.util.Arrays;
import java.util.BitSet;

public class BoolBSTest {

	public static void main(String[] args) {
		int n = 30;
		int reps = 10000000;

		boolean[] original = new boolean[n];
		BitSet origBS = new BitSet(n);


		long start = System.nanoTime();
		for (int i = 0; i < reps; i++) {
			boolean[] copied = new boolean[n];
			for (int j = 0; j < n; j++) {
				copied[j] = original[j];
			}
		}
		printTime(start);

		start = System.nanoTime();
		for (int i = 0; i < reps; i++) {
			boolean[] copied = Arrays.copyOf(original, n);
		}
		printTime(start);

		start = System.nanoTime();
		for (int i = 0; i < reps; i++) {
			BitSet bs = (BitSet)origBS.clone();
		}
		printTime(start);

		start = System.nanoTime();
		for (int i = 0; i < reps; i++) {
			BitSet bs = new BitSet(n);
			bs.or(origBS);
		}
		printTime(start);
	}

	private static void printTime(long start) {
		System.out.println((System.nanoTime() - start) / 1000000 + " ms");
	}

}
