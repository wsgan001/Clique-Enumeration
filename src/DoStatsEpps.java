import algorithm.eppstein_impl.Eppstein;
import graph.GraphAL;
import utility.LoadFile;

public class DoStatsEpps {

	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Arguments: graph(path) reps");
			return;
		}

		int n = Integer.parseInt(args[1]);
		long time = 0;
		GraphAL graph = LoadFile.loadGraphAL(args[0]);
		Eppstein algorithm = new Eppstein(graph);

		for (int i = 0; i < n; i++) {
			algorithm.execute();
			time += algorithm.getTime();
		}
		time /= n;

		System.out.printf("%d %d %d\n", algorithm.getNumberOfCliques(), time, algorithm.getCalls());
	}
}
