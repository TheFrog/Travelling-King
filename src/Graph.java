public class Graph {
	// Post: has created an empty graph.
	// + Graph()
	
	// Pre: source != null and target != null.
	// Post: has added an edge from Node source to Node target with the specified length, and returns this Edge.
	// + addEdge(source : Node, target : Node, length : float) : Edge
	
	// Post: return a newly created (unconnected) Node.
	// + addNode() : Node
	
	// Pre: source != null and target != null.
	// Post: returns the shortest path from source to target, and throws a NodesNotConnectedException if no path is found (so the Nodes are not connected).
	// + shortestPath(source : Node, target : Node) : float
}