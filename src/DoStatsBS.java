import algorithm.bitset_impl.AlgorithmBS;
import algorithm.bitset_impl.Factory;
import algorithm.bitset_impl.Order;
import algorithm.bitset_impl.Pivot;
import graph.GraphBitSet;
import utility.LoadFile;

public class DoStatsBS {

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Arguments: graph(path) order pivot reps");
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

		int n = Integer.parseInt(args[3]);
		long time = 0;

		GraphBitSet graph = LoadFile.loadGraphBS(args[0]);
		AlgorithmBS algorithm = new AlgorithmBS(graph, order, pivot);

		for (int i = 0; i < n; i++) {
			algorithm.execute();
			time += algorithm.getTime();
		}
		time /= n;

		System.out.printf("%d %d %d\n", algorithm.getNumberOfCliques(), time, algorithm.getCalls());
	}
}
