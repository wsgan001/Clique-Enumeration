import algorithm.TomitaBS;
import graph.GraphAL;
import graph.GraphBS;
import utility.FileLoader;
import utility.Measurable;
import utility.Utility;

import java.util.BitSet;


public class Tester {

	public static void main(String[] args) {
		test2();
	}

	public static void test2() {
		GraphBS graph = FileLoader.loadGraphBS("marknewman/marknewman-internet");
		GraphAL graphAL = FileLoader.loadGraphAL("marknewman/marknewman-internet");

		long start = System.nanoTime();
		GraphBS version1 = Utility.raiseToK(graph, 2);
		printTime(start);
		start = System.nanoTime();
		GraphBS version2 = Utility.raiseToKv2(graph, 2);
		printTime(start);
		start = System.nanoTime();
		GraphBS version3 = Utility.raiseToK(graphAL, 2);
		printTime(start);

		System.out.println(version1.equals(version2));
		System.out.println(version1.equals(version3));
	}

	public static void test1() {

		GraphAL g = Utility.generateSparseAL(400000, 1200000);
		//GraphAL g = Utility.generateSparseAL(50000, 100000);
		GraphBS gbs = new GraphBS(g);

		long start = System.nanoTime();
		GraphBS powerGraph = Utility.raiseToK(g, 7);
		System.out.println("Took " + (System.nanoTime()-start)/1000000 + " ms.");
		System.out.println("FROM AL GRAPH");
		System.out.println(powerGraph);
		System.out.println("FROM BS GRAPH");

		start = System.nanoTime();
		powerGraph = Utility.raiseToK(gbs, 7);
		System.out.println("Took " + (System.nanoTime()-start)/1000000 + " ms.");

		Measurable m = new TomitaBS(powerGraph);
		System.out.println("Took " + Utility.measure(m) + " ms.");

	}

	private static void printTime(long start) {
		System.out.println("Took " + (System.nanoTime() - start) / 1000000 + " ms.");
	}
}
