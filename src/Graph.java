public class Graph {
	// positions : Map<Node>
	
	// Post: has created an empty graph.
	// + Graph()
	
	// Pre: n != null and p != null.
	// Post: has added Node n at Position p if no Node is set for that Position, otherwise throws a PositionTakenException.
	// + addNode(Node n, Position p)
	
	// Post: returns the Node at Position p if it exists (by looking it up in the position map), otherwise returns null.
	// + nodeAtPosition(p : Position) : Node
	
	// Pre: source != null and target != null.
	// Post: returns the shortest path from source to target, and throws a NodesNotConnectionException when no path is found (so the nodes are not connected)
	// + shortestPathTo(Node source, Node target) : float
}