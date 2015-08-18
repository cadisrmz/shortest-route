package projects.nk.shortestroute;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import projects.nk.shortestroute.data.DataGraph;

/** The Class where it all begins. This is responsible for
 * initiating and orchestrating the flow of the application.
 *  
 * @author Navneet
 */
public class ShortestPath {
	private static final Logger LOG = LoggerFactory.getLogger(ShortestPath.class);	
	
	public static void main(String[] args) {
		if (args.length != 3) {
			LOG.error("Usage: ShortestPath <datafile> <src> <dest>");
			LOG.error("    where datafile is has each flight segment on one line");
			LOG.error("    and the Source and Destination separated by a space.");
			LOG.error("    Any additional data after these two fields will be ignored.");
			return;
		}
		
		List<String[]> paths = new ShortestPath().findPaths(args[0], args[1], args[2]);
		if (paths != null) {
			System.out.println("Path options:");
			for (String[] path:paths) {
				System.out.println(Arrays.toString(path));
			}
		}
	}

	public List<String[]> findPaths(String filename, String source, String dest) {		
		DataGraph dataGraph = new DataGraph();
		readDataFile(filename, dataGraph);
		return dataGraph.findPaths(source, dest);
	}

	/** The datafile format is specific to this application of 
	 * the DataGraph object. So keep the specifics to this class.
	 * This method is public only for use with the test class. 
	 * Ideally, I would move it to a Util class as a static method.
	 * 
	 * @param filename the name of the file to process
	 * @param dataGraph the graph to be populated
	 */
	public void readDataFile(String filename, DataGraph dataGraph) {
		Path dataFilePath = Paths.get(filename);
		
		if (dataFilePath.toFile().exists()) {
			// file not found in path
			try ( Stream<String> lines = Files.lines(dataFilePath) ) {
				lines.filter(line-> line.split(" ").length >= 2)
					.forEach(line -> {
						String[] parts = line.split(" ");
						dataGraph.populateGraph(parts[0], parts[1]);
					});
			} catch (IOException e) {
				LOG.error("Error accessing file: " + filename, e);
			}
		} else {
			LOG.error("File Not Found: " + filename);
		}
	}
}
