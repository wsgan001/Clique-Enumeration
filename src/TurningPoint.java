import graph.GraphBitSet;
import utility.Generator;
import utility.PowerGraph;

public class TurningPoint {

	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		double startingP = Double.parseDouble(args[1]);
		double inc = Double.parseDouble(args[2]);
		int reps = Integer.parseInt(args[3]);
		int k = Integer.parseInt(args[4]);

		int maxEdges = (n * (n-1)) / 2;

		System.out.print(n);
		for (int i = 0; i < reps; i++) {
			GraphBitSet power;
			double p = startingP - inc;

			do {
				p += inc;
				GraphBitSet graph = Generator.generateBS(n, p);
				power = PowerGraph.raiseGMB(graph, k);
			}
			while (power.countEdges() != maxEdges);
			System.out.printf(" %.3f", p);
		}
		System.out.println();
	}
}
