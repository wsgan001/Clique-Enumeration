package labelled;


public class EqualityTest {

	public static void main(String[] args) {

		LabelledGraph original = LabelledTools.generate(30, 5, 800);

		BKL_Combinations alg = new BKL_Combinations(original, 3);
		alg.run();
		System.out.println(alg.getCliques().length);

		BKL_LabelsToR alg2 = new BKL_LabelsToR(original, 3);
		alg2.execute();
		System.out.println(alg2.getNumberOfCliques());


	}

}
