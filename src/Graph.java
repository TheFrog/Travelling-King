import java.util.*;

// Represents a Graph with Nodes connected by Edges.
public class Graph {
	// Post: has created an empty Graph.
	// + Graph()
	public Graph() {
		
	}
	
	// Pre: source != null and target != null.
	// Post: has added an edge from Node source to Node target with the specified length, and returns this Edge.
	// + addEdge(source : Node, target : Node, length : float) : Edge
	public Edge addEdge(Node source, Node target, float length) {
		if(source == null && target == null) {
			return null;
		}
		
		Edge e = new Edge(length, target);
		source.addEdge(e);
		return e;
	}
	
	// Post: return a newly created (unconnected) Node.
	// + addNode() : Node
	public Node addNode() {
		Node n = new Node();
		return n;
	}
	
	// Pre: source != null and target != null.
	// Post: returns the shortest path from source to target, and throws a NodesNotConnectedException if no path is found (so the Nodes are not connected).Throws an IllegalArgumentException if the pre-conditions are not met. 
	// + shortestPath(source : Node, target : Node) : float
	public float shortestPath(Node source, Node target) throws IllegalArgumentException, NodesNotConnectedException {
		if (source == null || target == null) {
			throw new IllegalArgumentException();
		}
		
		// Store Nodes that have to be visited.
		Queue<Node> queue = new PriorityQueue<Node>();
		
		// Store distance from the source for every Node.
		Map<Node, Float> distances = new HashMap<Node, Float>();
		
		// Add the source Node.
		distances.put(source, new Float(0));
		queue.add(source);
		
		while (!queue.isEmpty()) {
			Node n = queue.poll();
			List<Edge> edges = n.listEdges();
			
			for (Edge e : edges) {
				Node t = e.getTarget();
				
				// Get the current distance to Node t.
				if (distances.get(t) == null) {
					distances.put(t, Float.POSITIVE_INFINITY);
				}
				float d = distances.get(t);
				
				// Get the distance through Node n to Node t.
				float distanceThroughN = distances.get(n) + e.getLength();
				
				// Update the distances.
				if (distanceThroughN < d) {
					queue.remove(t);
					distances.put(t, distanceThroughN);
					queue.add(t);
				}
			}
		}
		
		// Throw a NodesNotConnectedException if Node target was not reached.
		if (distances.get(target) == null) {
			throw new NodesNotConnectedException();
		}
		return distances.get(target);
	}
}