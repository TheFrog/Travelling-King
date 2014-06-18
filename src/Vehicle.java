// Represents a Vehicle.
public class Vehicle {
	// Cost of entering the Vehicle.
	// - cost : int
	private int cost;
	
	// Name of the Vehicle.
	// - name : String
	private String name;
	
	// Speed of the Vehicle.
	// - speed : int
	private int speed;
	
	// Pre: c >= 0 and s > 0.
	// Post: has created a Vehicle with cost c and speed s.
	// + Vehicle(c : int, n : String, s : int)
	public Vehicle(int c, String n, int s) {
		if (c < 0) {
			c = 0;
		}
		if (s <= 0) {
			s = 1;
		}
		
		cost = c;
		name = n;
		speed = s;
	}
	
	// Post: returns cost.
	// + getCost() : int
	public int getCost() {
		return cost;
	}
	
	// Post: returns name.
	// + getName() : String
	public String getName() {
		return name;
	}
	
	// Post:  returns speed.
	// + getSpeed() : int
	public int getSpeed() {
		return speed;
	}
	
	// Post: returns the hash code for the Vehicle object.
	// + hashCode() : int
	public int hashCode() {
		// Use a prime, because that limits the chance of collisions.
		final int prime = 31;
		
		int result = 1;
		result = prime * result + cost;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + speed;
		
		return result;
	}
	
	// Post: return whether the other Object is equal to this Vehicle.
	// + equals(other : Object) : boolean
	public boolean equals(Object other) {
		if (other instanceof Vehicle) {
			Vehicle v = (Vehicle)other;
			return (cost == v.getCost()
				 && name == v.getName()
				 && speed == v.getSpeed());
		}
		
		return false;
	}
}