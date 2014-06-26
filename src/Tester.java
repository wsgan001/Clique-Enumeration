import algorithm.TomitaBS;
import graph.GraphBS;
import algorithm.TomitaBKStyle;
import utility.FileLoader;


public class Tester {

	public static void main(String[] args) {

		/*GraphAL graph = FileLoader.loadGraphAL("data/biogrid/biogrid-human");
		PowerGraphSpeedTest.test(graph, 7);
		System.gc();

		GraphBS gbs = new GraphBS(graph);*/

		GraphBS gbs = FileLoader.loadGraphBS("data/marknewman/marknewman-condmat");
		/*EppsteinBS alg = new EppsteinBS(gbs);
		alg.run();*/

		TomitaBS tom1 = new TomitaBS(gbs);
		tom1.measureTime();
		TomitaBKStyle tom2 = new TomitaBKStyle(gbs);
		tom2.measureTime();

		System.out.println("Results: " + tom1.getNumberOfCliques() + " and " + tom2.getNumberOfCliques());
	}


}
