import java.io.*;
import java.util.*;

// Imports Vehicle routes in Territory.
public class City extends Territory {
	public City(){
		super();
	}
	
	// Loads the vehicle with its corresponding File.
	public void load(Vehicle vehicle, File file){
		// Load the Vehicle in Territory.
		addVehicle(vehicle);
		
		BufferedReader reader = null;
		String line = "";
		String splitBy = ",";
		
		try {
			reader = new BufferedReader(new FileReader(file));
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(splitBy);
				
				float x1 = Float.parseFloat(data[0]);
				float y1 = Float.parseFloat(data[1]);
				
				float x2 = Float.parseFloat(data[2]);
				float y2 = Float.parseFloat(data[3]);
				
				Position a = new Position(x1, y1);
				Position b = new Position(x2, y2);
				
				float length = (float)Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1-y2, 2));
				
				// All roads are bidirectional.
				addPath(a, b, length, vehicle);
				addPath(b, a, length, vehicle);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		if (args.length != 5) {
			System.out.println("Five arguments are needed: the location of the csv files containing the tram lines, canals, roads and sidewalks (in this order), followed by svg file to save the route in.");
			return;
		}
		
		// Set up the city. Costs are in minutes, and vehicle speeds in points per minute. Because with the scale of the map of Delft we use x km/h is equivalent with 5,55x points/minute.
		City delft = new City();
		
		Vehicle tram = new Vehicle(5, "Tram", 83);
		delft.load(tram, new File(args[0]));
		
		Vehicle boat = new Vehicle(3, "Boat", 55);
		delft.load(boat, new File(args[1]));

		Vehicle carriage = new Vehicle(6, "Carriage", 111);
		delft.load(carriage, new File(args[2]));
		
		Vehicle walking = new Vehicle(0, "Walking", 28);
		delft.load(walking, new File(args[2]));
		delft.load(walking, new File(args[3]));
		
		// Get the start and target from the user.
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter the starting coordinates (x, y):");
		float x1 = scanner.nextFloat();
		float y1 = scanner.nextFloat();
		
		System.out.println("Enter the target coordinates (x, y):");
		float x2 = scanner.nextFloat();
		float y2 = scanner.nextFloat();
		
		Position p1 = new Position(x1, y1);
		Position p2 = new Position(x2, y2);
		
		try {
			float distance = delft.shortestPath(p1, p2);
			System.out.println("Route found! Distance = " + distance + ".");
			System.out.println("Route:");
			delft.printLastRoute();
			delft.saveLastRoute(new File(args[4]));
		} catch (IllegalArgumentException e) {
			System.out.println("One of the entered coordinates does not exist.");
		} catch (IllegalStateException e) {	
			e.printStackTrace();
		} catch (PositionsNotConnectedException e) {
			System.out.println("No route exists between these points!");
		}
	}
}