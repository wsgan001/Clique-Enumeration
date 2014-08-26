import graph.GraphBitSet;
import utility.LoadFile;

import java.util.BitSet;

public class Maxima {

	public static void main(String[] args) {
		GraphBitSet graph = LoadFile.loadGraphBS(args[0]);

		StringBuilder script = new StringBuilder("load (graphs)$\n");

		script.append("g : create_graph(").append(graph.size()).append(",[");

		for (int i = 0; i < graph.size(); i++) {
			printEdges(script, i, graph.neighbours(i));
		}

		script.deleteCharAt(script.length() - 1);

		script.append("])$");

		System.out.println(script);
	}

	private static void printEdges(StringBuilder script, int v, BitSet neighbours) {
		for (int i = neighbours.nextSetBit(v); i > -1; i = neighbours.nextSetBit(i + 1)) {
			script.append(String.format("[%d,%d],", v, i));
		}
	}
}
