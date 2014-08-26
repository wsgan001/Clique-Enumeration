import graph.GraphBitSet;
import utility.LoadFile;
import utility.PowerGraph;

public class Raise {

	public static void main(String[] args) {
		GraphBitSet graph = LoadFile.loadGraphBS(args[0]);

		int k = Integer.parseInt(args[1]);
		GraphBitSet power = PowerGraph.raiseGMB(graph, k);
		System.out.println(power);
	}
}
