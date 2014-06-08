public class Graph {
	// - positions : HashMap<Position, Node>
	// - vehicles: List<Vehicle>
	
	// Post: has created an empty graph.
	// + Graph()
	
	// Post: has connected the Nodes at coordinates (x1, y1) and (x2, y2) that have the same Vehicles with an Edge of length l. Then returns this Edge.
	// + addEdge(l : float, x1 : int, y1 : int, x2 : int, y2: int) : Edge
	
	// Pre: n != null and v != null.
	// Post: has added a Node for Vehicle v at coordinates (x, y) if no Node exists at that Position. Also makes connections between existing Nodes with the same coordinates. Returns the Node at Position (x, y).
	// + addNode(v : Vehicle, x : int, y : int) : Node
	
	// Pre: n != null.
	// Post: has added Nodes at coordinates (x, y) for all vehicles if they do not exist. Also connects all added Nodes.
	// + addNodes(x : int, y : int)
	
	// Pre: v != null.
	// Post: has added Vehicle v to the list of vehicles.
	// + addVehicle(v : Vehicle)
	
	// Post: returns the Node at Position p if it exists (by looking it up in the position map), otherwise returns null.
	// + nodeAtPosition(p : Position) : Node
	
	// Pre: source != null and target != null.
	// Post: returns the shortest path from source to target, and throws a NodesNotConnectedException when no path is found (so the nodes are not connected).
	// + shortestPath(source : Node, target : Node) : float
}