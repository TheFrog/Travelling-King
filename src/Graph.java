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
	// Post: returns the shortest Route from source to target, and throws a NodesNotConnectedException if no Route is found (so the Nodes are not connected).Throws an IllegalArgumentException if the pre-conditions are not met. 
	// + shortestPath(source : Node, target : Node) : Route
	public Route shortestPath(Node source, Node target) throws IllegalArgumentException, NodesNotConnectedException {
		if (source == null || target == null) {
			throw new IllegalArgumentException();
		}
		
		// Store Nodes that have to be visited.
		Set<Node> items = new HashSet<Node>();
		
		// Store distance from the source for every Node.
		Map<Node, Float> distances = new HashMap<Node, Float>();
		
		// Store the previous Node in the shortest routes for every Node.
		Map<Node, Node> previous = new HashMap<Node, Node>();
		
		// Add the source Node.
		distances.put(source, new Float(0));
		items.add(source);
		
		while (!items.isEmpty()) {
			// Get the Node with the smallest distance.
			Node n = null;
			float x = Float.POSITIVE_INFINITY;
			for (Node y : items) {
				if (distances.get(y) < x) {
					x = distances.get(y);
					n = y;
				}
			}
			
			items.remove(n);
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
				
				// Update the distance and previous Node.
				if (distanceThroughN < d) {
					items.remove(t);
					distances.put(t, distanceThroughN);
					previous.put(t, n);
					items.add(t);
				}
			}
		}
		
		// Throw a NodesNotConnectedException if Node target was not reached.
		if (distances.get(target) == null) {
			throw new NodesNotConnectedException();
		}
		
		// Create a list of Nodes that corresponds with the shortest Route.
		List<Node> nodes = new ArrayList<Node>();
		Node n = target;
		nodes.add(n);
		while (previous.containsKey(n)) {
			n = previous.get(n);
			nodes.add(n);
		}
		
		// Return the Route.
		return new Route(distances.get(target), nodes);
	}
}