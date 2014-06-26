package test;

import graph.GraphAL;
import graph.GraphBS;
import utility.PowerGraph;
import utility.Utility;

public abstract class PowerGraphSpeedTest {

	public static void testRandom(int n, int m, int exponent) {

		GraphAL graphAL = Utility.generateSparseAL(n, m);
		GraphBS graphBS = new GraphBS(graphAL);

		long start = System.nanoTime();
		System.out.println("Starting raiseBFS algorithm:");
		PowerGraph.raiseBFS(graphBS, exponent);
		printTime(start);

		start = System.nanoTime();
		System.out.println("\nStarting raiseBFSv2 algorithm:");
		PowerGraph.raiseBFSv2(graphBS, exponent);
		printTime(start);

		start = System.nanoTime();
		System.out.println("\nStarting raiseAL algorithm:");
		PowerGraph.raiseAL(graphAL, exponent);
		printTime(start);

		start = System.nanoTime();
		System.out.println("\nStarting raiseMMult algorithm:");
		PowerGraph.raiseMMult(graphBS, exponent);
		printTime(start);
	}


	private static void printTime(long start) {
		System.out.println("\tTook " + (System.nanoTime() - start) / 1000000 + " ms.");
	}
}
