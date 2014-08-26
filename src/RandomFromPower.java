import graph.GraphBitSet;
import utility.Generator;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class RandomFromPower {

	public static void main(String[] args) {
		Scanner in = null;

		try {
			try {
				in = new Scanner(new File(args[0]));
				int n = in.nextInt();
				int m = in.nextInt();

				GraphBitSet graph = Generator.generateBS(n, m);
				System.out.println(graph);
			}
			finally {
				if (in != null)
					in.close();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
