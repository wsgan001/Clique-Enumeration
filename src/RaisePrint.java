import graph.GraphBitSet;
import utility.LoadFile;
import utility.PowerGraph;

/**
 * This class is to be used when {@link Raise} runs out of memory, as that uses a StringBuffer which needs lots of extra
 * memory. This one is a lot slower though.
 */
public class RaisePrint {

	public static void main(String[] args) {
		GraphBitSet graph = LoadFile.loadGraphBS(args[0]);

		int k = Integer.parseInt(args[1]);
		GraphBitSet power = PowerGraph.raiseGMB(graph, k);
		power.print();
	}
}
