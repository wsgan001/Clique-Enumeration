import utility.Generator;

public class RandomGraph {

	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		double p = Double.parseDouble(args[1]);

		System.out.print(Generator.generateBS(n, p));
	}
}
