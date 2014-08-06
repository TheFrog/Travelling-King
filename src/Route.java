import java.util.*;

// Represents a Route with a distance and the passed Nodes.
public class Route {
	// Stores the total distance of the Route.
	// - distance : float
	private float distance;
	
	// Stores the passed Nodes in order.
	// - nodes : List<Node>
	private List<Node> nodes;
	
	// Post: has created a Route with distance d and nodes n.
	// + Route(d : float, n : List<Node>)
	public Route(float d, List<Node> n) {
		distance = d;
		nodes = n;
	}
	
	// Post: returns the distance.
	// + getDistance() : float
	public float getDistance() {
		return distance;
	}
	
	// Post: returns the nodes.
	// + getNodes() : List<Node>
	public List<Node> getNodes() {
		return nodes;
	}
}