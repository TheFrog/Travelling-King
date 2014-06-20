import java.util.*;

// Represents a certain Territory with a Graph.
public class Territory {
	// Stores the Graph.
	// - graph : Graph
	private Graph graph;
	
	// Map that stores another Map for every Position. This second Map contains Nodes for some Vehicles.
	// - nodes : Map<Position, Map<Vehicle, Node>>
	private Map<Position, Map<Vehicle, Node>> nodes;
	
	// HashMap that stores keys and the corresponding Positions.
	// - positions : Map<String, Position>
	private Map<String, Position> positions;
	
	// Stores the Vehicles.
	// - vehicles : List<Vehicle>
	private List<Vehicle> vehicles;
	
	// Post: has created an empty Territory.
	public Territory() {
		graph = new Graph();
		nodes = new HashMap<Position, Map<Vehicle, Node>>();
		positions = new HashMap<String, Position>();
		vehicles = new ArrayList<Vehicle>();
	}
	
	// Pre: start != null, end != null, length >= 0.
	// Post: has added a path from Position start to Position finish with the specified length for the Vehicles in the list. If vehicles == null, then all Vehicles are used. Nodes have been created if non-existent. Throws an IllegalArgumentException if the pre-conditions are not met.
	// + addPath(start : Position, end : Position, length : float, vehicles : List<Vehicle>)
	public void addPath(Position start, Position end, float length, List<Vehicle> list) throws IllegalArgumentException {
		if (start == null || end == null || length < 0) {
			throw new IllegalArgumentException();
		}
		
		// Get the nodes.
		Map<Vehicle, Node> startNodes = getNodes(start, list);
		Map<Vehicle, Node> endNodes = getNodes(end, list);
		
		// Add the edge for each vehicle.
		for (Vehicle vehicle : list) {
			float l = length / vehicle.getSpeed();
			graph.addEdge(startNodes.get(vehicle), endNodes.get(vehicle), l);
		}
	}
	
	// Pre: start != null, end != null, length >= 0.
	// Post: has added a path from Position start to Position finish with the specified length for the passed Vehicle. Nodes have been created if non-existent. Throws an IllegalArgumentException if the pre-conditions are not met.
	// + addPath(start: Position, end : Position, length : float, vehicle : Vehicle)
	public void addPath(Position start, Position end, float length, Vehicle vehicle) throws IllegalArgumentException {
		List<Vehicle> v = new ArrayList<Vehicle>();
		v.add(vehicle);
		
		addPath(start, end, length, v);
	}
	
	// Pre: start != null, end != null, length >= 0.
	// Post: has added a path from Position start to Position finish with the specified length for all Vehicles. Nodes have been created if non-existent. Throws an IllegalArgumentException if the pre-conditions are not met.
	// + addPath(start : Position, end : Position, length : float)
	public void addPath(Position start, Position end, float length) throws IllegalArgumentException {
		addPath(start, end, length, vehicles);
	}
	
	// Pre: key != null.
	// Post: has returned the Position with the specified key if it exists, otherwise returns null.
	// + positionForKey(key : String) : Position
	public Position positionForKey(String key) {
		if (key == null) {
			return null;
		}
		
		return positions.get(key);
	}
	
	// Pre: vehicle != null.
	// Post: has added the Vehicle, throws an IllegalArgumentException if the pre-condition is not met.
	// + addVehicle(vehicle : Vehicle)
	public void addVehicle(Vehicle vehicle) {
		vehicles.add(vehicle);
	}
	
	// Pre: key != null, position != null.
	// Post: has set String key as the key for Position position, throws a KeyExistsException if the key already exists and throws an IllegalArgumentException if the pre-conditions are not met.
	// + setKey(key : String, position : Position)
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
		// Add a temporary starting Node.
		Node s = graph.addNode();
		for (Node x : nodes.get(source).values()) {
			graph.addEdge(s, x, 0);
		}
		
		// Add the temporary end Node.
		Map<Node, Edge> edges = new HashMap<Node, Edge>();
		Node t = graph.addNode();
		for (Node x : nodes.get(target).values()) {
			Edge e = graph.addEdge(x, t, 0);
			edges.put(x, e);
		}
		
		// Find the shortest path.
		float result;
		try {
			result = graph.shortestPath(s, t);
		} catch (NodesNotConnectedException e) {
			throw new PositionsNotConnectedException();
		}
		
		// Remove the temporary end Nodes.
		for (Map.Entry<Node, Edge> entry : edges.entrySet()) {
		    entry.getKey().removeEdge(entry.getValue());
		}
		
		return result;
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
				if (v == w) { // Don't connect a vehicle to itself.
					continue;
				}
				
				float length = w.getCost();
				graph.addEdge(n.get(v), n.get(w), length);
			}
		}
		
		return n;
	}
	
	// Tests.
	public static void main(String args[]) {
		Territory territory = new Territory();
		Vehicle bicycle =	new Vehicle(5,	"Bicycle",	10);	territory.addVehicle(bicycle);
		Vehicle car =		new Vehicle(20,	"Car",		40);	territory.addVehicle(car);
		Vehicle walker = 	new Vehicle(0,	"Walker",	5);		territory.addVehicle(walker);
		
		Position a = new Position(1, 1);
		Position b = new Position(5, 2);
		Position c = new Position(3, 3);
		Position d = new Position(3, 4);
		
		territory.addPath(a, b, 10, bicycle);
		territory.addPath(a, c, 20, bicycle);
		territory.addPath(c, a, 20, bicycle);
		territory.addPath(c, b, 30, bicycle);
		territory.addPath(b, c, 30, bicycle);
		
		territory.addPath(a, c, 80, car);
		territory.addPath(c, d, 10, car);
		territory.addPath(d, c, 50, car);
		
		territory.addPath(a, c, 5, walker);
		territory.addPath(c, a, 5, walker);
		territory.addPath(a, b, 20, walker);
		territory.addPath(b, a, 20, walker);
		territory.addPath(b, c, 20, walker);
		territory.addPath(c, b, 25, walker);
		territory.addPath(d, c, 10, walker);
		territory.addPath(c, d, 10, walker);
		territory.addPath(d, b, 10, walker);
		territory.addPath(b, d, 20, walker);
		
		try {
			float path = territory.shortestPath(a, d);
			System.out.println(path);
		} catch (PositionsNotConnectedException exception) {
			System.out.println("Nodes not connected!");
		}
	}
}