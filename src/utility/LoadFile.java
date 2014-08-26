package utility;

import graph.Graph;
import graph.GraphAL;
import graph.GraphBitSet;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public abstract class LoadFile {

	public static GraphBitSet loadGraphBS(String fileName) {
		return (GraphBitSet)loadGraph(fileName, true);
	}

	public static GraphAL loadGraphAL(String fileName) {
		return (GraphAL)loadGraph(fileName, false);
	}

	private static Graph loadGraph(String fileName, boolean bs) {

		Graph graph = null;
		Scanner in = null;
		FileReader reader = null;

		try {
			try {
				reader = new FileReader(fileName);
				in = new Scanner(reader);

				in.useDelimiter("\\D+");
				int size = in.nextInt();
				graph = bs ? new GraphBitSet(size) : new GraphAL(size);
				in.nextInt();

				while(in.hasNextInt()) {
					graph.addEdge(in.nextInt(), in.nextInt());
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
}
