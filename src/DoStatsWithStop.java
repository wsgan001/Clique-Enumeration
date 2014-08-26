import algorithm.bitset_impl.AlgorithmBS;
import algorithm.bitset_impl.OrderNone;
import algorithm.bitset_impl.PivotTomita;
import graph.GraphBitSet;
import utility.LoadFile;

public class DoStatsWithStop {

	public static void main(String[] args) throws InterruptedException {
		GraphBitSet graph = LoadFile.loadGraphBS(args[0]);
		long timeLimit = Long.parseLong(args[1]);

		AlgorithmBS algorithm = new AlgorithmBS(graph, new OrderNone(), new PivotTomita());

		Thread alg = new Thread(new AlgThread(algorithm));
		alg.start();


		long start = System.currentTimeMillis();
		while(true) {
			if ((System.currentTimeMillis() - start) > (timeLimit)) {
				System.out.println("* * *");
				System.exit(1);
			}
			else if (!alg.isAlive()) {
				System.out.printf("%d %d %d\n", algorithm.getNumberOfCliques(), algorithm.getTime(), algorithm.getCalls());
				System.exit(0);
			}

			Thread.sleep(1000);
		}
	}

	private static class AlgThread implements Runnable {
		private AlgorithmBS algorithm;

		public AlgThread(AlgorithmBS algorithm) {
			this.algorithm = algorithm;
		}

		@Override
		public void run() {
			algorithm.execute();
		}
	}
}
