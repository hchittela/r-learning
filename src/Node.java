
public class Node {
	public int n;
	private double weight;
	
	public Node(int n, double weight) {
		if (n < 0) {
			throw new IllegalArgumentException("Node number was less than 0");
		}
		
		this.n = n;
		this.weight = weight;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}
}
