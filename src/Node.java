import java.util.*;

// Represents a Node in the Graph.
public class Node {
	// Stores data related to this Node.
	// - data : Object
	private Object data;
	
	// Stores connecting Edges.
	// - edges : List<Edge>
	private List<Edge> edges;
	
	// Post: has created a Node.
	// + Node()
	public Node() {
		edges = new ArrayList<Edge>();
	}
	
	// Pre: e != null.
	// Post: has added edge e.
	// + addEdge(e : Edge)
	public void addEdge(Edge e) {
		if (e != null){
			edges.add(e);
		}
	}

	// Post: returns the edges.
	// + listEdges() : List<Edge>
	public List<Edge> listEdges() {
		return edges;
	}
	
	// Pre: e != null.
	// Post: has removed edge e.
	// + removeEdge(e : Edge)
	public void removeEdge(Edge e) {
		if (e != null){
			edges.remove(e);
		}
	}

	// Post: returns the data.
	// + getData() : Object
	public Object getData() {
		return data;
	}

	// Post: has set data to d.
	// + setData(d : Object)
	public void setData(Object d) {
		data = d;
	}
}