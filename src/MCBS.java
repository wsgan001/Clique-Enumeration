import algorithm.bitset_impl.*;
import graph.GraphBitSet;
import utility.LoadFile;
import utility.Generator;

public class MCBS {

	public static void main(String[] args) throws Exception {

		if (args.length == 0) {
			System.out.println("Parameters:");
			System.out.println("for random: vertices edges order pivot");
			System.out.println("for file input: filename order pivot");
			return;
		}

		int n, m;
		int offset = 0;
		GraphBitSet graph;

		try {
			n = Integer.parseInt(args[0]);
			m = Integer.parseInt(args[1]);
			graph = Generator.generateBS(n, m);
		} catch (Exception e) {
			graph = LoadFile.loadGraphBS("data/" + args[0]);
			offset = -1;
		}

		Order order;
		Pivot pivot;

		try {
			order = Factory.createOrder(args[2 + offset]);
			pivot = Factory.createPivot(args[3 + offset]);
		} catch (Exception e) {
			System.err.println("Invalid pivot or order choice.");
			return;
		}

		AlgorithmBS algorithm = new AlgorithmBS(graph, order, pivot);

		algorithm.execute();
		System.out.printf("%12s %12s\n", "Cliques", "Exec. time");
		System.out.printf("%12d %12d\n", algorithm.getNumberOfCliques(), algorithm.getTime());
	}
}
