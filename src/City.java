import java.io.*;

// Imports Vehicle routes in territory
public class City extends Territory {
	
	public City(){
		super();
	}
	
	//Loads the vehicle with its corresponding file
	public void load(Vehicle vehicle, String file){
		
		// Load vehicle in territory
		addVehicle(vehicle);
		
		BufferedReader br = null;
		String line = "";
		String splitBy = ";";
		
		try{
			br = new BufferedReader(new FileReader(file));
			while((line = br.readLine()) != null) {
				String[] data = line.split(splitBy);
				
				float x1 = Float.valueOf(data[0]);
				float y1 = Float.valueOf(data[1]);
				
				float x2 = Float.valueOf(data[2]);
				float y2 = Float.valueOf(data[3]);
				
				Position a = new Position(x1, y1);
				Position b = new Position(x2, y2);
				
				float length = (float) Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
				
				addPath(a, b, length, vehicle);
			}
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e){
			e.printStackTrace();
		}
		finally {
			if (br != null){
				try {
					br.close();
				}
				catch (IOException e){
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public static void main(String[] args) {
		City Delft = new City();
		
		Vehicle car = new Vehicle(5, "Car", 30);
		Delft.load(car, "C:\\Users\\Friso Kingma\\Documents\\GitHub\\Travelling-King\\src\\routes\\car.csv");
		
		Position a = new Position(new Float(5.0), new Float(5.0));
		Position b = new Position(new Float(1.0), new Float(1.0));
		
		try {
			float path = Delft.shortestPath(a , b);
			System.out.println(path);
		} catch (PositionsNotConnectedException exception) {
			System.out.println("Nodes not connected!");
		}
	}
}