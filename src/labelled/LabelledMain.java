package labelled;

public class LabelledMain {

	public static void main(String[] args) {
		LabelledGraph graph = LabelledTools.generate(4000, 5, 50);

		BKL_PostX alg = new BKL_PostX(graph, 3);
		BKL algAlt = new BKL(graph, 3);

		alg.execute();
		algAlt.execute();

		System.out.println(alg.getNumberOfCliques());
		System.out.println(algAlt.getNumberOfCliques());

		System.out.println();

		System.out.println(alg.getTime());
		System.out.println(algAlt.getTime());
	}
}
