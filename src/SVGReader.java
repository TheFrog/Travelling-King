import javax.xml.parsers.*;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

import java.io.*;
import java.util.*;

public class SVGReader extends DefaultHandler {
	public static final String delimiter = ",";
	public static final String extension = "csv";
	public static final String newline = "\n";
	
	private Map<String, Set<List<Float>>> data;
	private Set<List<Float>> lines;
	
	public SVGReader() {
		data = new HashMap<String, Set<List<Float>>>();
	}
	
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
				Float.parseFloat(attributes.getValue("x1")),
				Float.parseFloat(attributes.getValue("y1")),
				Float.parseFloat(attributes.getValue("x2")),
				Float.parseFloat(attributes.getValue("y2"))
			);
			lines.add(list);
			System.out.println("    Added line: " + list.toString() + " (line)");
			
		} else if (element.equals("polyline")) {
			String[] points = attributes.getValue("points").trim().split("\\s+"); // Get the points String, strip leading and trailing whitespace, and split on whitespace. Trim is needed, because otherwise leading whitespace results in empty points.
			
			for (int i = 0; i < points.length - 1; i++) {
				String[] v = points[i].split(",");
				String[] w = points[i + 1].split(",");
				
				List<Float> list = Arrays.asList(
					Float.parseFloat(v[0]),
					Float.parseFloat(v[1]),
					Float.parseFloat(w[0]),
					Float.parseFloat(w[1])
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
}