import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 *An adjacency list-represented graph class.
 */
public class Graph {
	int N = 0;
	ArrayList<HashMap<Integer, Edge>> adjList;
	ArrayList<Node> nodeList;
	
    /**
     *  Initializes a graph of size N.
     *
     * @throws IllegalArgumentException
     *             if N is negative.
     * @param N
     *            the number of vertices in the graph.
     */
    public Graph(int N) {
        if (N < 0)
        	throw new IllegalArgumentException();
        
        this.N = N;
        
        adjList = new ArrayList<HashMap<Integer, Edge>>(N);
        nodeList = new ArrayList<Node>(N);
        
        for (int i = 0; i < N; i++) {
        	adjList.add(i, new HashMap<Integer, Edge>());
        	nodeList.add(new Node(i, 0));
        }
    }

    /**
     * @return the number of vertices in the graph
     */
    public int getSize() {
        return N;
    }
    
    /**
     * Ensures the input vertex numbers are between 0 and n-1
     * @param u
     * 		The first vertex to check			
     * @param v
     * 		The second vertex to check
     * @return
     * 		True if within bounds, else false
     */
    boolean outOfBounds(int u, int v) {
    	if (u < 0 || u >= N || v < 0 || v >= N)
    		return true;
    	
    	return false;
    }
    
    public double getNodeWeight(int n) {
    	if (outOfBounds(n, 0))
    		throw new IllegalArgumentException();
    	
    	return nodeList.get(n).getWeight(); 
    }
    
    /**
     * Sets the weight of a given node
     * @param n
     * 		node number
     * @param increment
     * 		weight to set
     */
    public void incNodeWeight(int n, double increment) {
    	if (outOfBounds(n, 0))
			throw new IllegalArgumentException();
    	
    	nodeList.get(n).setWeight(nodeList.get(n).getWeight() + increment);
    }

    /**
     *Returns an edge object for each edge in the graph (non-directional)
     *
     * @return a set containing this graph's edges
     */
    public Set<Edge> getEdges() {
    	Set<Edge> edges = new HashSet<Edge>();
    	
    	for (HashMap<Integer, Edge> h : adjList) {
    		for (Map.Entry<Integer, Edge> entry : h.entrySet()) {
    			if (!edges.contains(entry.getValue())) {
    				edges.add(entry.getValue());
    			}
    		}
    	}

    	return edges;
    }

    /**
     * Checks for edge between two vertices
     * 
     * @throws IllegalArgumentException
     *             if u or v is out of bounds
     * @return true if the u-v edge is in this graph, false otherwise
     */
    public boolean hasEdge(int u, int v) {
    	if (outOfBounds(u, v))
    		throw new IllegalArgumentException();
    	
    	if(adjList.get(u).containsKey(v))
    		return true;
    	else if (adjList.get(v).containsKey(u))
    		return true;
    	else
    		return false;
    }

    /**
     * Creates an edge between u and v if it does not already exist. This should
     * not modify the edge weight if the u-v edge already exists.
     *
     * @throws IllegalArgumentException
     *             if u or v is not in the graph
     * @param u
     *            one vertex to connect
     * @param v
     *            the other vertex to connect
     * @param weight
     *            the edge weight
     * @return false if the u-v edge is already present; true otherwise
     */
    public boolean addEdge(int u, int v, int weight) {
    	if (outOfBounds(u, v))
    		throw new IllegalArgumentException();
    	
    	Edge newEdge = new Edge(u, v, weight);
    	
    	if (hasEdge(u, v))
    		return false;
    	
    	adjList.get(u).put(v, newEdge);
    	adjList.get(v).put(u, newEdge);
    	return true;
    }
    
    /**
     * Clears the adjList so that only the list of N vertices is present without
     * any edges.
     */
    @SuppressWarnings("unused")
	void clearEdges() {
    	for (HashMap<Integer, Edge> h : adjList) {
    		h = new HashMap<Integer, Edge>();
    	}
    }

    /**
     *Gets the weight of a given edge
     *
     * @throws NoSuchElementException
     *             if the u-v edge is not in the graph
     * @throws IllegalArgumentException
     *             if u or v is out of bounds
     * @return the edge weight of u-v
     */
    public int geEdgetWeight(int u, int v) {
    	if (!hasEdge(u, v))
    		throw new NoSuchElementException();
    	
    	return (adjList.get(u).get(v).weight);
    }

    /**
     * Gets neighbors of a vertex
     *
     * @throws NoSuchElementException
     *             if v is not in the graph
     * @param v
     *            the vertex whose neighbors we want
     * @return all neighbors of v
     */
    public Set<Integer> getNeighbors(int v) {
    	if (v < 0 || v >= N)
    		throw new NoSuchElementException();
    	
    	Set<Integer> neighbors = new HashSet<Integer>();
    	
    	for(Map.Entry<Integer, Edge> entry : adjList.get(v).entrySet()) {
    		neighbors.add(entry.getKey());
    	}

        return neighbors;
    }
}
