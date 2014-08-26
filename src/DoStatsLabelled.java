import labelled.BKL_PostX;
import labelled.LabelledGraph;
import labelled.LabelledTools;

public class DoStatsLabelled {

	public static void main(String[] args) {
		LabelledGraph graph = LabelledTools.loadFromFile(args[0]);
		int budget = Integer.parseInt(args[1]);

		BKL_PostX algorithm = new BKL_PostX(graph, budget);
		algorithm.execute();

		System.out.printf("%d %d %d\n", algorithm.getNumberOfCliques(), algorithm.getTime(), algorithm.getCalls());
	}

}
