import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class Learner {
	Graph g;
	ArrayList<Integer> history;
	
	int good;
	double goodReward;
	int bad;
	double badReward;
	
	double rate;
	
	public Learner(Graph g, double goodReward, double badReward, double rate) {
		history = new ArrayList<Integer>();
		this.g = g;
		
		this.goodReward = goodReward;
		this.badReward = badReward;
		this.rate = rate;
	}
	
	public void buildGraph(int good, int bad) {
		g.addEdge(0, 1, 0);
		g.addEdge(0, 4, 0);
		g.addEdge(1, 2, 0);
		g.addEdge(2, 6, 0);
		g.addEdge(2, 3, 0);
		g.addEdge(3, 7, 0);
		g.addEdge(4, 8, 0);
		g.addEdge(6, 7, 0);
		g.addEdge(6, 10, 0);
		g.addEdge(7, 11, 0);
		g.addEdge(8, 9, 0);
		g.addEdge(9, 10, 0);
		g.addEdge(10, 11, 0);
		
		this.good = good;
		this.bad = bad;
	}
	
	public void setRate(double rate) {
		this.rate = rate;
	}
	
	public void reduceRate(double factor) {
		rate = rate / factor;
	}
	
	public void iterateSequence(ArrayList<Integer> seq) {
		Iterator<Integer> it = seq.iterator();
		
		int start = it.next();
		Integer curr = null;
		Integer old = null;
		
		curr = start;
		
		while (it.hasNext()) {
			double weightChange = 0.0;
			old = curr;
			curr = it.next();
			
			weightChange = calculateDeltaWeight(old, curr); 
			g.incNodeWeight(old, weightChange);
		}
		if (curr == good) {
			double weightChange = calculateDeltaWeight(curr, null); 
			g.incNodeWeight(curr, weightChange);
		}
		else if (curr == bad) {
			double weightChange = calculateDeltaWeight(curr, null); 
			g.incNodeWeight(curr, weightChange);
		}
	}
	
	public int pickRandom(int options) {
		Random rand = new Random();		
		return rand.nextInt(options); 
	}
	
	public void iterateRandom() {
		history.clear();
		
		Integer curr;
		Integer next = null;
		int old;

		int start = pickRandom(g.getSize());
		curr = start;
		
		while (curr != null) {
			history.add(curr);
			double weightChange;
			
			if (curr != good && curr != bad) {
				int optionsLeft = 0;
				Set<Integer> optionsSet = g.getNeighbors(curr);
				Integer[] options = optionsSet.toArray(new Integer[optionsSet.size()]);
				optionsLeft = optionsSet.size();
				
				if (optionsLeft > 0) {
					next = options[pickRandom(options.length)];
					optionsLeft--;
				
					while (history.contains(next)) {
						optionsSet.remove(next);
						options = optionsSet.toArray(new Integer[optionsSet.size()]);
						
						if (optionsLeft > 0) {
							next = options[pickRandom(options.length)];
							optionsLeft--;
						}
						else {
							return;
						}
					}
					
				}
			}
			else {
				next = null;
			}
			
			old = curr;
			curr = next;
			
			weightChange = calculateDeltaWeight(old, curr); 
			g.incNodeWeight(old, weightChange);
//			System.out.println("Updated Node: " + old + " - Weight: " + weightChange);
			
		}
	}
	
	public double calculateDeltaWeight(int old, Integer curr) {
		double oldWeight = g.getNodeWeight(old);
		double currWeight;
		double reward;
		
		if (curr != null)
			currWeight = g.getNodeWeight(curr);
		else
			currWeight = 0;
		
		if (old == good)
			reward = goodReward;
		else if (old == bad)
			reward = badReward;
		else
			reward = 0.0;
		
		return rate * (currWeight - oldWeight + reward);
	}

	public void printWeights() {
		System.out.println();
		
		for (int x = 0; x < 3; x++) {
			for (int y = 0 + 4*x; y < 4 + 4*x; y++) {
				Node node = g.nodeList.get(y);
				NumberFormat formatter = new DecimalFormat("#0.00");     
				String w = formatter.format(node.getWeight());
				System.out.print(node.n + "," + w + "       ");
			}
			
			System.out.println();
			System.out.println();
		}
		
		System.out.println("==============================");
	}
	
	public void printPath() {
		System.out.print("Path followed: ");
		Iterator<Integer> it = history.iterator();
		
		while (it.hasNext()) {
			System.out.print(it.next() + ", ");
		}
		
		System.out.println();
		System.out.println("==============================");
	}
	
	public ArrayList<Integer> findBestPath(int start) {
		ArrayList<Integer> bestPath = new ArrayList<Integer>();
		int curr = start;
		bestPath.add(curr);
		
		while (curr != good) {
			Set<Integer> optionsSet = g.getNeighbors(curr);
			
			int best = Integer.MIN_VALUE;
			double bestVal = Integer.MIN_VALUE;
			
			for(int i : optionsSet) {
				if (!bestPath.contains(i)) {
					if (g.getNodeWeight(i) > bestVal) {
						best = i;
						bestVal = g.getNodeWeight(best);
					}
				}
			}
			
			curr = best;
			bestPath.add(curr);
		}
		
		
		return bestPath;
	}
	
}
