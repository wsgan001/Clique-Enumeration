import algorithm.other_impl.BKPointers;
import graph.GraphBitSet;
import utility.LoadFile;

public class DoStatsPointers {
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Arguments: graph(path) reps");
			return;
		}

		int n = Integer.parseInt(args[1]);
		long time = 0;

		GraphBitSet graph = LoadFile.loadGraphBS(args[0]);
		BKPointers algorithm = new BKPointers(graph);

		for (int i = 0; i < n; i++) {
			algorithm.execute();
			time += algorithm.getTime();
		}
		time /= n;

		System.out.printf("%d %d %d\n", algorithm.getNumberOfCliques(), time, algorithm.getCalls());
	}
}
