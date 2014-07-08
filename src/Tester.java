import algorithm.other_impl.TomitaPointers;
import algorithm.bitset_impl.*;
import algorithm.eppstein_impl.AlgorithmEppstein;
import graph.GraphAL;
import utility.Generator;


public class Tester {

	public static void main(String[] args) {
		//raphBitSet bs = FileLoader.loadGraphBS("data/marknewman/marknewman-lesmis");
		GraphBitSet bs = Generator.generateBS(20000, 5000000);
		//bs = PowerGraph.raiseBFSv2(bs, 2);
		AlgorithmBitSet tom = new AlgorithmBitSet(bs, new OrderNone(), new PivotTomita(), false);
		tom.measureTime();
		System.out.println(tom.getNumberOfCliques());

		GraphAL al = new GraphAL(bs);
		AlgorithmEppstein epps = new AlgorithmEppstein(al);
		epps.measureTime();
		System.out.println(epps.getNumberOfCliques());

		TomitaPointers tom2 = new TomitaPointers(bs);
		tom2.measureTime();
		System.out.println(tom2.getNumberOfCliques());
		//BS_Tomita tom = new BS_Tomita(bs);
		//tom.measureTime();
	}

	private static void testInteger(Integer x) {
		x++;
	}
}
