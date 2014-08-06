import javax.xml.parsers.*;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

import java.io.*;
import java.util.*;

// Read a simple SVG file and export it as CSV files (one per group).
public class SVGReader extends DefaultHandler {
	// CSV delimiter.
	// + delimiter : String
	public static final String delimiter = ",";
	
	// CSV extension.
	// + extension : String
	public static final String extension = "csv";
	
	// CSV newline.
	// + newline : String"
	public static final String newline = "\n";
	
	// Stores the parsed data.
	// - data : Map<String, Set<List<Float>>>
	private Map<String, Set<List<Float>>> data;
	
	// Temporary variable that stores lines for the currently parsing group.
	// - lines : Set<List<Float>>
	private Set<List<Float>> lines;
	
	// Post: has created an empty SVGReader.
	// + SVGReader()
	public SVGReader() {
		data = new HashMap<String, Set<List<Float>>>();
	}
	
	// Post: has opened the file.
	// + open(file : File)
	public void open(File file) {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setNamespaceAware(true);
			SAXParser parser = factory.newSAXParser();
			
			XMLReader reader = parser.getXMLReader();
			reader.setContentHandler(this);
			reader.parse(file.toString());
		
		} catch (IOException e) {
			e.printStackTrace();
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}
	
	// Post: has rounded input to two decimals. Needed to resolve svg rounding errors.
	// - round(input : float) : float
	private float round(float input) {
		return (float)(Math.round(input * 100.0) / 100.0);
	}
	
	// Post: returns a Map with a filename (String) as key and the content of that file as value.
	// + CSV() : Map<String, String>
	public Map<String, String> CSV() {
		Map<String, String> map = new HashMap<String, String>();
		
		for (Map.Entry<String, Set<List<Float>>> entry : data.entrySet()) {
			StringBuilder builder = new StringBuilder();
			for (List<Float> list : entry.getValue()) {
				builder.append(list.get(0));
				builder.append(delimiter);
				builder.append(list.get(1));
				builder.append(delimiter);
				builder.append(list.get(2));
				builder.append(delimiter);
				builder.append(list.get(3));
				builder.append(newline);
			}
			
			map.put(entry.getKey(), builder.toString());
		}
		
		return map;
	}
	
	// Post: has converted a svg file (first argument) to csv files (one for every group). These csv files are placed in the folder specified in the second argument.
	// + main(args : String[])
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("To convert a SVG file to CSV files, two arguments are needed. Specify the input file as the first argument and the output folder as the second argument.");
			return;
		}
		
		// Read and parse the file.
		SVGReader reader = new SVGReader();
		reader.open(new File(args[0]));
		
		// Write all the needed files.
		Map<String, String> map = reader.CSV();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			// Create the output file.
			String s = entry.getKey() + "." + extension;
			File file = new File(args[1], s);
			
			// Write.
			try {
				FileWriter writer = new FileWriter(file);
				writer.write(entry.getValue());
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	/*
	 * Methods called by a XMLReader object to handle the loaded document.
	 */
	public void startDocument() throws SAXException {
		data = new HashMap<String, Set<List<Float>>>();
	}
	
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		String element = localName;
		System.out.println();

		if (element.equals("g")) {
			String name = attributes.getValue("id");
			System.out.println("\n\nEntered group \"" + name + "\".");

			lines = new HashSet<List<Float>>();
			data.put(name, lines);
			
		} else if (element.equals("line")) {
			List<Float> list = Arrays.asList(
				round(Float.parseFloat(attributes.getValue("x1"))),
				round(Float.parseFloat(attributes.getValue("y1"))),
				round(Float.parseFloat(attributes.getValue("x2"))),
				round(Float.parseFloat(attributes.getValue("y2")))
			);
			lines.add(list);
			System.out.println("    Added line: " + list.toString() + " (line)");
			
		} else if (element.equals("polyline")) {
			String[] points = attributes.getValue("points").trim().split("\\s+"); // Get the points String, strip leading and trailing whitespace, and split on whitespace. Trim is needed, because otherwise leading whitespace results in empty points.
			
			for (int i = 0; i < points.length - 1; i++) {
				String[] v = points[i].split(",");
				String[] w = points[i + 1].split(",");
				
				List<Float> list = Arrays.asList(
					round(Float.parseFloat(v[0])),
					round(Float.parseFloat(v[1])),
					round(Float.parseFloat(w[0])),
					round(Float.parseFloat(w[1]))
				);
				lines.add(list);
				System.out.println("    Added line: " + list.toString() + " (polyline)");
			}
			
		} else {
			System.out.println("E   Encountered incompatible element \"" + element + "\".");
		}
	}

	public void warning(SAXParseException e) {
		System.out.println("warning: " + e.getMessage());
	}
	
	public void error(SAXParseException e) {
		System.out.println("error: " + e.getMessage());
	}
}