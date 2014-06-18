// This class represents an Edge in the Graph.
public class Edge {
	// The length of the Edge.
	// - length : float
	private float length;
	
	// The node at the end of the Edge.
	// - target : Node
	private Node target;
	
	// Pre: l >= 0 and t != null.
	// Post: has created an Edge with length l and target t.
	// + Edge(l : float, t : Node)
	public Edge(float l, Node t) {
		length = l;
		target = t;
	}

	// Post: return length.
	// + getLength() : float
	public float getLength() {
		return length;
	}
	
	// Post: returns target.
	// + getTarget() : Node
	public Node getTarget() {
		return target;
	}
}