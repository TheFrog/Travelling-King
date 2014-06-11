public class Territory {
	// - graph : Graph
	// - nodes : HashMap<Position, HashMap<Vehicle, Node>>
	// - positions : HashMap<String, Position>
	// - vehicle : List<Vehicle>
	
	// Pre: start != null, end != null, length >= 0.
	// Post: has added a path from Position start to Position finish with the specified length for the passed Vehicle. Nodes have been created if non-existent.
	// - addPath(start: Position, end : Position, length : float, vehicle : Vehicle)
	
	// Pre: start != null, end != null, length >= 0.
	// Post: has added a path from Position start to Position finish with the specified length for the Vehicles in the list. If vehicles == null, then all Vehicles are used. Nodes have been created if non-existent.
	// - addPath(start : Position, end : Position, length : float, vehicles : List<Vehicle>)
	
	// Pre: start != null, end != null, length >= 0.
	// Post: has added a path from Position start to Position finish with the specified length for all Vehicles. Nodes have been created if non-existent.
	// - addPath(start : Position, end : Position, length : float)
	
	// Pre: key != null.
	// Post: has returned the Position with the specified key if it exists, otherwise returns null.
	// positionForKey(key : String) : Position
	
	// Pre: key != null, position != null.
	// Post: has set String key as the key for Position position, throws a KeyExistsException if the key already exists.
	// setKey(key : String, position : Position)
	
	// Pre: source != null and target != null.
	// Post: returns the shortest path from source to target, and throws a PositionsNotConnectedException if no path is found (so the Positions are not connected).
	// + shortestPath(source : Position, target : Position) : float
}