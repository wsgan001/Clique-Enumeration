package test;

import graph.GraphAL;
import graph.GraphBitSet;
import utility.LoadFile;
import utility.PowerGraph;
import utility.Generator;

public abstract class PowerGraphSpeedTest {

	public static void testDataSet(String file, int exponent) {
		GraphBitSet graphBS = LoadFile.loadGraphBS(file);

		test(graphBS, exponent);
	}

	public static void testRandom(int n, double p, int exponent) {
		GraphBitSet graphBS = Generator.generateBS(n, p);

		test(graphBS, exponent);
	}

	public static void testRandom(int n, int m, int exponent) {
		GraphBitSet graphBS = Generator.generateBS(n, m);

		test(graphBS, exponent);
	}

	public static void test(GraphBitSet graphBS, int exponent) {
		GraphAL graphAL = new GraphAL(graphBS);

		long start = System.nanoTime();/*
		PowerGraph.raiseAL(graphAL, exponent);
		printTime(start);

		start = System.nanoTime();
		PowerGraph.raiseBBS(graphBS, exponent);
		printTime(start);

		start = System.nanoTime();
		PowerGraph.raiseMM(graphBS, exponent);
		printTime(start);

		start = System.nanoTime();
		PowerGraph.raiseGMA(graphBS, exponent);
		printTime(start);*/

		start = System.nanoTime();
		PowerGraph.raiseGMB(graphBS, exponent);
		printTime(start);

		/*
		System.out.println();
		for (int i = 1; i < results.length; i++) {
			if (!results[i-1].equals(results[i])) {
				System.err.println("Algorithm no. " + (i - 1) + " and algorithm no. " + i + " don't match.");
			}
		}*/
	}

	private static void printTime(long start) {
		System.out.print("\t" + (System.nanoTime() - start) / 1000000 + "ms");
	}
}
