import labelled.LabelledTools;

public class RandomLGraph {

	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		int p = Integer.parseInt(args[1]);
		int l = Integer.parseInt(args[2]);

		System.out.println(LabelledTools.generate(n, l, p));
	}
}
