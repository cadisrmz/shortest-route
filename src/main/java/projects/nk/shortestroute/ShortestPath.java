package projects.nk.shortestroute;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
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
	public static final String PROPERTY_DATAFILE_PATH = "data.file";

	private static final Logger LOG = LoggerFactory.getLogger(ShortestPath.class);
	
	Properties settings = new Properties();
	DataGraph dataGraph = new DataGraph();
	
	public static void main(String[] args) {
		// read default properties
		new ShortestPath().run(args);
	}

	public void run(String[] args) {
		LOG.debug("Starting ...");
		// read default settings
		
		readDefaultProperties();
		processArguments(args);
		readDataFile();
		LOG.debug("Done.");
	}

	private void processArguments(String[] args) {
		if (args.length == 1) {
			setDataFilePath(args[0]);
		}		
	}

	public void readDataFile() {
		String dataFileName = getDataFilePath();
		Path dataFilePath = Paths.get(dataFileName);
		
		if (dataFilePath.toFile().exists()) {
			// file not found in path
			try ( Stream<String> lines = Files.lines(dataFilePath) ) {
				dataGraph.populateGraph(lines);
			} catch (IOException e) {
				LOG.error("Error accessing file: " + dataFileName, e);
			}
		} else {
			LOG.error("File Not Found: " + dataFileName);
		}
	}

	public String getDataFilePath() {
		return settings.getProperty(PROPERTY_DATAFILE_PATH);
	}

	public void setDataFilePath(String path) {
		assert(path != null);
		settings.setProperty(PROPERTY_DATAFILE_PATH, path);
		LOG.info("Using data from file: " + getDataFilePath());
	}

	public void readDefaultProperties() {
		try (InputStream defaultPropertiesFile = this.getClass().getResourceAsStream("/application.properties")) {
		    settings.load(defaultPropertiesFile);		    
		} catch (IOException e) {
			// Default properties file not found ??!!
			// this cannot happen since we ship it in the jar
			// check the error
			LOG.error("application.properties file not found", e);
		}
	}
}
