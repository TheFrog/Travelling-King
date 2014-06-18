// Represents a Graph with Nodes connected by Edges.
public class Graph {
	// Post: has created an empty graph.
	// + Graph()
	public Graph() {
		
	}
	
	// Pre: source != null and target != null.
	// Post: has added an edge from Node source to Node target with the specified length, and returns this Edge.
	// + addEdge(source : Node, target : Node, length : float) : Edge
	public Edge addEdge(Node source, Node target, float length) {
		if(source == null && target == null){
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
	// Post: returns the shortest path from source to target, and throws a NodesNotConnectedException if no path is found (so the Nodes are not connected).
	// + shortestPath(source : Node, target : Node) : float
	public float shortestPath(Node source, Node target) {
		if(source == null || target == null){
			return null;
		}
		//DIJKSTRA
	}
}