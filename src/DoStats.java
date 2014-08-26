import algorithm.bitset_impl.AlgorithmBS;
import algorithm.bitset_impl.Factory;
import algorithm.bitset_impl.Order;
import algorithm.bitset_impl.Pivot;
import graph.GraphBitSet;
import utility.LoadFile;

public class DoStats {

	private static final int REPS = 1;

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Arguments: graph(path) order pivot");
			return;
		}

		Order order;
		Pivot pivot;

		try {
			order = Factory.createOrder(args[1]);
			pivot = Factory.createPivot(args[2]);
		} catch (Exception e) {
			System.err.println("Invalid pivot or order choice.");
			return;
		}

		GraphBitSet graph = LoadFile.loadGraphBS(args[0]);
		AlgorithmBS algorithm = new AlgorithmBS(graph, order, pivot);

		long time = 0;
		for (int i = 0; i < REPS; i++) {
			algorithm.execute();
			time += algorithm.getTime();
		}

		time /= REPS;
		int edges = graph.countEdges();
		double density = ((double)(2*edges)) / (graph.size() * (graph.size() - 1));

		System.out.printf("%d %.2f %d %d %d\n", edges, density, algorithm.getNumberOfCliques(), time, algorithm.getCalls());
	}
}
