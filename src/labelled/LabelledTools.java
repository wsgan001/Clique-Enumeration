package labelled;

import graph.GraphBitSet;

import java.io.FileReader;
import java.io.IOException;
import java.util.BitSet;
import java.util.Random;
import java.util.Scanner;

/**
 * Helper functions for the maximal labelled clique problem.
 */
public class LabelledTools {

	/**
	 * Generates a random labelled graph.
	 * @param n Size of the graph.
	 * @param labels The total number of possible labels.
	 * @param p Edge probability.
	 * @return A random labelled graph.
	 */
	public static LabelledGraph generate(int n, int labels, int p) {
		Random random = new Random();
		LabelledGraph graph = new LabelledGraph(n, labels);

		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				if (random.nextInt(1000) < p) {
					graph.addEdge(i, j, random.nextInt(labels) + 1);
				}
			}
		}

		return graph;
	}

	/**
	 * Loads a labelled graph from a file.
	 * @param fileName The input file.
	 * @return The labelled graph object.
	 */
	public static LabelledGraph loadFromFile(String fileName) {

		LabelledGraph graph = null;
		Scanner in = null;
		FileReader reader = null;

		try {
			try {
				reader = new FileReader(fileName);
				in = new Scanner(reader);

				in.useDelimiter("\\D+");
				int size = in.nextInt();
				int l = in.nextInt();
				graph = new LabelledGraph(size, l);

				while(in.hasNextInt()) {
					graph.addEdge(in.nextInt(), in.nextInt(), in.nextInt());
				}
			}
			finally {
				if (reader != null) reader.close();
				if (in != null) in.close();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		return graph;
	}

	/**
	 * Randomly assigns labels to an unlabelled graph.
	 * @param graph The input graph.
	 * @param labels The total number of possible labels.
	 * @return A randomly labelled graph.
	 */
	public static LabelledGraph assignLabels(GraphBitSet graph, int labels) {
		Random random = new Random();
		LabelledGraph lGraph = new LabelledGraph(graph.size(), labels);

		for (int i = 0; i < graph.size(); i++) {
			for (int j = i + 1; j < graph.size(); j++) {
				if (graph.isAdjacent(i, j))
					lGraph.addEdge(i, j, random.nextInt(labels) + 1);
			}
		}
		return lGraph;
	}

	/**
	 * Constructs an unlabelled graph from a labelled one, including only the edges with the specified labels.
	 * @param labelled The labelled graph from which the unlabelled graph is constructed.
	 * @param labels The labels to be included. 1's in the BitSet represent labels that are to be included.
	 * @return An unlabelled graph.
	 */
	public static GraphBitSet getGraphFromGivenLabels(LabelledGraph labelled, BitSet labels) {
		GraphBitSet graph = new GraphBitSet(labelled.n);

		for (int i = 0; i < labelled.n; i++) {
			for (int j = i + 1; j < labelled.n; j++) {
				int label = labelled.label(i, j);
				if (label != 0 && labels.get(label - 1)) {
					graph.addEdge(i, j);
					graph.addEdge(j, i);
				}
			}
		}
		return graph;
	}
}
