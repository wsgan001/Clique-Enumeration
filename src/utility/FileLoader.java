package utility;

import graph.Graph;
import graph.GraphAL;
import graph.GraphBS;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public abstract class FileLoader {

	public static GraphBS loadGraphBS(String fileName) {
		return (GraphBS)loadGraph(fileName, true);
	}

	public static GraphAL loadGraphAL(String fileName) {
		return (GraphAL)loadGraph(fileName, false);
	}

	private static Graph loadGraph(String fileName, boolean bs) {

		Graph graph = null;

		try(Scanner in = new Scanner(new FileReader(fileName))) {
			in.useDelimiter("\\D+");
			int size = in.nextInt();
			graph = bs ? new GraphBS(size) : new GraphAL(size);
			in.nextInt();

			while(in.hasNextInt()) {
				graph.addEdge(in.nextInt(), in.nextInt());
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}

		return graph;
	}
}
