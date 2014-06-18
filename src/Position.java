// Represents a Position.
public class Position {	
	// X coordinate.
	// - x : int
	private int x;
	
	// Y coordinate.
	// - y : int
	private int y;
	
	// Post: has created a Position with coordinates (a, b).
	// + Position(a : int, b : int)
	public Position(int a, int b) {
		x = a;
		y = b;
	}
	
	// Post: returns x.
	// + getX() : int
	public int getX() {
		return x;
	}
	
	// Post: returns y.
	// + getY() : int
	public int getY() {
		return y;
	}
	
	// Post: returns the hash code for the Position object.
	// + hashCode() : int
	public int hashCode() {
		// Use a prime, because that limits the chance of collisions.
		final int prime = 31;
		
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		
		return result;
	}
	
	// Post: return whether the other Object is equal to this Position.
	// + equals(other : Object) : boolean
	public boolean equals(Object other) {
		if (other instanceof Position) {
			Position p = (Position)other;
			return p.getX() == x && p.getY() == y;
		}
		
		return false;
	}
}