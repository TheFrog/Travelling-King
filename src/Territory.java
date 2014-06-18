import java.util.*;

// Represents a certain Territory with a Graph.
public class Territory {
	// Stores the Graph.
	// - graph : Graph
	private Graph graph;
	
	// HashMap that stores another HashMap for every Position. This second HashMap contains Nodes for some Vehicles.
	// - nodes : HashMap<Position, HashMap<Vehicle, Node>>
	private HashMap<Position, HashMap<Vehicle, Node>> nodes;
	
	// HashMap that stores keys and the corresponding Positions.
	// - positions : HashMap<String, Position>
	private HashMap<String, Position> positions;
	
	// Stores the Vehicles.
	// - vehicles : List<Vehicle>
	private List<Vehicle> vehicles;
	
	// Post: has created an empty Territory.
	public Territory() {
		graph = new Graph();
		nodes = new HashMap<Position, HashMap<Vehicle, Node>>();
		positions = new HashMap<String, Position>();
		vehicles = new ArrayList<Vehicle>();
	}
	
	// Pre: start != null, end != null, length >= 0.
	// Post: has added a path from Position start to Position finish with the specified length for the Vehicles in the list. If vehicles == null, then all Vehicles are used. Nodes have been created if non-existent. Throws an IllegalArgumentException if the pre-conditions are not met.
	// - addPath(start : Position, end : Position, length : float, vehicles : List<Vehicle>)
	public void addPath(Position start, Position end, float length, List<Vehicle> v) throws IllegalArgumentException {
		if (start == null || end == null || length < 0) {
			throw new IllegalArgumentException();
		}
		
		// Get the nodes.
		Map<Vehicle, Node> startNodes = getNodes(start, v);
		Map<Vehicle, Node> endNodes = getNodes(end, v);
		
		// Add the edge for each vehicle.
		for (Vehicle vehicle : v) {
			float l = length / vehicle.getSpeed();
			graph.addEdge(startNodes.get(vehicle), endNodes.get(vehicle), l);
		}
	}
	
	// Pre: start != null, end != null, length >= 0.
	// Post: has added a path from Position start to Position finish with the specified length for the passed Vehicle. Nodes have been created if non-existent. Throws an IllegalArgumentException if the pre-conditions are not met.
	// - addPath(start: Position, end : Position, length : float, vehicle : Vehicle)
	public void addPath(Position start, Position end, float length, Vehicle vehicle) throws IllegalArgumentException {
		List<Vehicle> v = new ArrayList<Vehicle>();
		v.add(vehicle);
		
		addPath(start, end, length, v);
	}
	
	// Pre: start != null, end != null, length >= 0.
	// Post: has added a path from Position start to Position finish with the specified length for all Vehicles. Nodes have been created if non-existent. Throws an IllegalArgumentException if the pre-conditions are not met.
	// - addPath(start : Position, end : Position, length : float)
	public void addPath(Position start, Position end, float length) throws IllegalArgumentException {
		addPath(start, end, length, vehicles);
	}
	
	// Pre: key != null.
	// Post: has returned the Position with the specified key if it exists, otherwise returns null.
	// positionForKey(key : String) : Position
	public Position positionForKey(String key) {
		if (key == null) {
			return null;
		}
		
		return positions.get(key);
	}
	
	// Pre: key != null, position != null.
	// Post: has set String key as the key for Position position, throws a KeyExistsException if the key already exists and throws an IllegalArgumentException if the pre-conditions are not met.
	// setKey(key : String, position : Position)
	public void setKey(String key, Position position) throws IllegalArgumentException, KeyExistsException {
		if (key == null || position == null) {
			throw new IllegalArgumentException();
		}
		
		if (positions.containsKey(key)) {
			throw new KeyExistsException();
		}
		
		positions.put(key, position);
	}
	
	// Pre: source != null and target != null.
	// Post: returns the shortest path from source to target, and throws a PositionsNotConnectedException if no path is found (so the Positions are not connected).
	// + shortestPath(source : Position, target : Position) : float
	public float shortestPath(Position source, Position target) throws PositionsNotConnectedException {
		return 0;
	}
	
	// Pre: position != null, list != null;
	// Post: returns all Nodes at the specified Position for the specified Vehicles. Nodes have been created if needed.
	// - getNodes(position : Position, list : List<Vehicle>) : Map<Vehicle, Node>
	private Map<Vehicle, Node> getNodes(Position position, List<Vehicle> list) throws IllegalArgumentException {
		if (position == null || list == null) {
			throw new IllegalArgumentException();
		}
		
		// Create a HashMap if it doesn't exist.
		if (nodes.get(position) == null) {
			nodes.put(position, new HashMap<Vehicle, Node>());
		}
		
		// Get the Map and add Nodes if necessary (Map is passed by reference, so we can edit it).
		Map<Vehicle, Node> n = nodes.get(position);
		for (Vehicle v : list) {
			if (n.get(v) == null) {
				n.put(v, new Node());
			}
		}
		
		// Connect all Nodes.
		for (Vehicle v : list) {
			for (Vehicle w : list) {
				if (v == w) { // Don't connection a vehicle to itself.
					continue;
				}
				
				float length = w.getCost();
				graph.addEdge(n.get(v), n.get(w), length);
			}
		}
		
		return n;
	}
}