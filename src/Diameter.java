import graph.GraphBitSet;
import utility.LoadFile;
import utility.PowerGraph;

import java.io.File;

public class Diameter {

	public static void main(String[] args) {
		File file = new File(args[0]);
		GraphBitSet graph = LoadFile.loadGraphBS(file.getAbsolutePath());
		GraphBitSet power;

		int maxEdges = ((graph.size() - 1) * graph.size()) / 2;
		double density = graph.countEdges() / (double)maxEdges;

		String diameter = "\\textgreater 9";

		for (int k = 2; k < 10; k++) {
			power = PowerGraph.raiseGMB(graph, k);
			if (power.countEdges() == maxEdges) {
				diameter = String.valueOf(k);
				break;
			}
		}

		System.out.printf("%s %.3f %s\n", file.getName(), density, diameter);
	}
}
