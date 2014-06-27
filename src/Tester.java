import algorithm.BS_WithPivot;
import algorithm.BS_Tomita;
import graph.GraphBS;
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

		BS_Tomita tom1 = new BS_Tomita(gbs);
		tom1.measureTime();
		BS_WithPivot tom2 = new BS_WithPivot(gbs);
		tom2.measureTime();

		System.out.println("Results: " + tom1.getNumberOfCliques() + " and " + tom2.getNumberOfCliques());
	}


}
