import java.util.ArrayList;
import java.util.Scanner;

public class ReinforcementLearning {
	
	public static void main(String[] args) {
		Graph maze = new Graph(12);
		Learner rl = new Learner(maze, 1.0, -1.0, 0.5);
		
		rl.buildGraph(3, 7);
		
		explore(rl);
		exploit(rl, 9);
	}
	
	public static void explore(Learner rl) {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter the number of iterations to perform: ");
		int numIterations = scanner.nextInt();
		
		ArrayList<Integer> seq = new ArrayList<Integer>();
		seq.add(0); seq.add(1); seq.add(2); seq.add(3);
		
		for (int x = 0; x < numIterations; x++) {
			if (x % 10000 == 0 && x > 01) {
				rl.reduceRate(2);
			}
			
			rl.iterateRandom();
			rl.printPath();
		}
		
		rl.printWeights();
	}
	
	public static void exploit(Learner rl, int start) {
		ArrayList<Integer> path = rl.findBestPath(start);
		System.out.print("Best Path: ");
		for(int i : path) {
			System.out.print(i + ", ");
		}
	}
}
