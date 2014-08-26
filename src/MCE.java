import graph.GraphAL;
import utility.LoadFile;
import utility.Generator;

public class MCE {

	public static void main(String[] args) {
		int n, m;
		GraphAL graph;

		if (args.length == 0) {
			System.out.println("Arguments:");
			System.out.println("for random: vertices edges");
			System.out.println("for file input: filename");
		}

		try {
			n = Integer.parseInt(args[0]);
			m = Integer.parseInt(args[1]);
			graph = Generator.generateAL(n, m);
		} catch(Exception e) {
			graph = LoadFile.loadGraphAL("data/" + args[0]);
		}

		algorithm.eppstein_impl.Eppstein algorithm = new algorithm.eppstein_impl.Eppstein(graph);
		algorithm.execute();

		System.out.printf("%12s %12s\n", "Cliques", "Exec. time");
		System.out.printf("%12d %12d\n", algorithm.getNumberOfCliques(), algorithm.getTime());
	}
}
