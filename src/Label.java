import graph.GraphBitSet;
import labelled.LabelledGraph;
import labelled.LabelledTools;
import utility.LoadFile;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;


/**
 *
 */
public class Label {

	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);		// number of labelled graphs to be generated per graph
		int l = Integer.parseInt(args[1]);		// number of different labels

		File inputFolder = new File(args[2]);
		String outputPath = args[3];

		File[] graphs = inputFolder.listFiles();

		// iterate through all files in the input folder
		for (File graphFile : graphs) {
			GraphBitSet graph = LoadFile.loadGraphBS(graphFile.getPath());

			// Generate n random labelled graphs
			for (int i = 0; i < n; i++) {

				PrintWriter writer = null;
				try {
					try {
						writer = new PrintWriter(outputPath + graphFile.getName() + "_" + i);
						// Generate random labels for the graph
						LabelledGraph labelled = LabelledTools.assignLabels(graph, l);
						// Write the labelled graph to a file
						writer.print(labelled);
					} finally {
						if (writer != null) writer.close();
					}
				} catch (IOException e) { e.printStackTrace(); }
			}

		}
	}

}
