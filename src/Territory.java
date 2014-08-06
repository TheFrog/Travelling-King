import java.io.*;
import java.util.*;

// Represents a certain Territory with a Graph.
public class Territory {
	// Stores the Graph.
	// - graph : Graph
	private Graph graph;
	
	// Stores the last found Route.
	// - lastRoute : Route
	private List<Position> lastRoute;
	
	// Map that stores another Map for every Position. This second Map contains Nodes for some Vehicles.
	// - nodes : Map<Position, Map<Vehicle, Node>>
	private Map<Position, Map<Vehicle, Node>> nodes;
	
	// HashMap that stores keys and the corresponding Positions.
	// - positions : Map<String, Position>
	private Map<String, Position> positions;
	
	// Stores the Vehicles.
	// - vehicles : List<Vehicle>
	private Set<Vehicle> vehicles;
	
	// Post: has created an empty Territory.
	public Territory() {
		graph = new Graph();
		nodes = new HashMap<Position, Map<Vehicle, Node>>();
		positions = new HashMap<String, Position>();
		vehicles = new HashSet<Vehicle>();
	}
	
	// Pre: start != null, end != null, length >= 0.
	// Post: has added a path from Position start to Position finish with the specified length for the Vehicles in the list. If vehicles == null, then all Vehicles are used. Nodes have been created if non-existent. Throws an IllegalArgumentException if the pre-conditions are not met.
	// + addPath(start : Position, end : Position, length : float, vehicles : List<Vehicle>)
	public void addPath(Position start, Position end, float length, Set<Vehicle> set) throws IllegalArgumentException {
		if (start == null || end == null || length < 0) {
			throw new IllegalArgumentException();
		}
		
		// Get the nodes.
		Map<Vehicle, Node> startNodes = getNodes(start, set);
		Map<Vehicle, Node> endNodes = getNodes(end, set);
		
		// Add the edge for each vehicle.
		for (Vehicle vehicle : set) {
			float l = length / vehicle.getSpeed();
			graph.addEdge(startNodes.get(vehicle), endNodes.get(vehicle), l);
		}
	}
	
	// Pre: start != null, end != null, length >= 0.
	// Post: has added a path from Position start to Position finish with the specified length for the passed Vehicle. Nodes have been created if non-existent. Throws an IllegalArgumentException if the pre-conditions are not met.
	// + addPath(start: Position, end : Position, length : float, vehicle : Vehicle)
	public void addPath(Position start, Position end, float length, Vehicle vehicle) throws IllegalArgumentException {
		Set<Vehicle> v = new HashSet<Vehicle>();
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
	// Post: returns the shortest path from source to target, and throws a PositionsNotConnectedException if no path is found (so the Positions are not connected). Throws an IllegalStateException if a found Route contains a Node with data that is not a Position, and throws an IllegalArgumentException if one of the Positions is not found.
	// + shortestPath(source : Position, target : Position) : float
	public float shortestPath(Position source, Position target) throws IllegalArgumentException, IllegalStateException, PositionsNotConnectedException {
		// Check whether the Positions exist.
		if (!nodes.containsKey(source) || !nodes.containsKey(target)) {
			throw new IllegalArgumentException();
		}
		
		// Add a temporary starting Node.
		Node s = graph.addNode();
		for (Map.Entry<Vehicle, Node> entry : nodes.get(source).entrySet()) {
			Vehicle v = entry.getKey();
			Node n = entry.getValue();
			graph.addEdge(s, n, v.getCost());
		}
		
		// Add the temporary end Node.
		Map<Node, Edge> edges = new HashMap<Node, Edge>();
		Node t = graph.addNode();
		for (Node x : nodes.get(target).values()) {
			Edge e = graph.addEdge(x, t, 0);
			edges.put(x, e);
		}
		
		// Find the shortest path.
		Route route;
		try {
			route = graph.shortestPath(s, t);
		} catch (NodesNotConnectedException e) {
			throw new PositionsNotConnectedException();
		}
		
		// Remove the temporary end Nodes.
		for (Map.Entry<Node, Edge> entry : edges.entrySet()) {
		    entry.getKey().removeEdge(entry.getValue());
		}
		
		// Remove the temporary starting and end Nodes.
		List<Node> nodes = route.getNodes();
		nodes.remove(0);
		nodes.remove(nodes.size() - 1);
		
		// Save the route for future use.
		lastRoute = new ArrayList<Position>();
		for (Node n : nodes) {
			if (!(n.getData() instanceof Position)) {
				throw new IllegalStateException();
			}
			
			// Add the Position to the list. Invert, because the Route starts with the target.
			Position p = (Position)n.getData();
			lastRoute.add(0, p);
		}
		
		return route.getDistance();
	}
	
	// Pre: lastRoute != null.
	// Post: prints the last found route, and displays a message if the pre-condition is not met. Throws an IllegalStateException if a Node's data attribute is not a Position object.
	// + printLastRoute()
	public void printLastRoute() {
		if (lastRoute == null) {
			System.out.println("No last route found.");
		}
		
		for (int i = 0; i < lastRoute.size(); i++) {
			Position p = lastRoute.get(i);
			System.out.println("(" + p.getX() + ", " + p.getY() + ")");
			
			// Display a message when a switch between vehicles has been made.
			if (i + 1 < lastRoute.size() && p.equals(lastRoute.get(i + 1))) {
				System.out.println("Switched vehicles!");
			}
		}
		
		System.out.println("Target reached!");
	}
	
	// Pre: lastRoute != null.
	// Post: has saved the last found route in SVG format in file. Throws an IllegalStateException if the pre-condition is not met.
	// + saveLastRoute(file : File)
	public void saveLastRoute(File file) {
		StringBuilder builder = new StringBuilder("<svg xmlns=\"http://www.w3.org/2000/svg\" style=\"stroke:rgb(0,0,0); stroke-width:1\" version=\"1.1\">");
		
		// Add the remaining lines.
		for (int i = 0; i < lastRoute.size() - 1; i++) {
			Position p1 = lastRoute.get(i);
			Position p2 = lastRoute.get(i + 1);
			builder.append("<line x1=\"" + p1.getX() + "\" y1=\"" + p1.getY() + "\" x2=\"" + p2.getX() + "\" y2=\"" + p2.getY() + "\" />");
		}
			
		builder.append("</svg>");
		
		// Write.
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(builder.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Pre: position != null, list != null.
	// Post: returns all Nodes at the specified Position for the specified Vehicles. Nodes have been created if needed.
	// - getNodes(position : Position, list : List<Vehicle>) : Map<Vehicle, Node>
	private Map<Vehicle, Node> getNodes(Position position, Set<Vehicle> set) throws IllegalArgumentException {
		if (position == null || set == null) {
			throw new IllegalArgumentException();
		}
		
		// Create a HashMap if it doesn't exist.
		if (nodes.get(position) == null) {
			nodes.put(position, new HashMap<Vehicle, Node>());
		}
		
		// Get the Map and add Nodes if necessary (Map is passed by reference, so we can edit it).
		Map<Vehicle, Node> map = nodes.get(position);
		for (Vehicle v : set) {
			if (map.get(v) == null) {
				// Create the Node and store the associated Position.
				Node n = new Node();
				n.setData(position);
				map.put(v, n);
				
				// Connect this Node to all other Nodes at this Position.
				for (Map.Entry<Vehicle, Node> entry : map.entrySet()) {
					// Don't connect a Node to itself.
					if (entry.getKey() == v) {
						continue;
					}
					
					// Connect the Node both ways.
					graph.addEdge(n, entry.getValue(), entry.getKey().getCost());
					graph.addEdge(entry.getValue(), n, v.getCost());
				}
			}
			
		}
		
		return map;
	}
}