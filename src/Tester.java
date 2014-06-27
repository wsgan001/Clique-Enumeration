import algorithm.AL_Tomita;
import algorithm.BS_Eppstein;
import algorithm.BS_Tomita;
import graph.GraphAL;
import graph.GraphBS;
import utility.FileLoader;
import utility.Generator;


public class Tester {

	public static void main(String[] args) {

		GraphAL graph = FileLoader.loadGraphAL("/home/david/Documents/Glasgow/PROJECT/enumeration/data/snap/snap-roadNet-PA");

		AL_Tomita tom = new AL_Tomita(graph);

		long start = System.nanoTime();
		tom.run();
		System.out.println((System.nanoTime()-start)/1000000);
	}
}
