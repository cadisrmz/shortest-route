package projects.nk.shortestroute;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** The Class where it all begins. This is responsible for
 * initiating and orchestrating the flow of the application.
 *  
 * @author Navneet
 */
public class ShortestPath {
	private static final Logger LOG = LoggerFactory.getLogger(ShortestPath.class);
	
	Properties settings = new Properties();
	
	public static void main(String[] args) {
		// read default properties
		new ShortestPath().run();
	}

	private void run() {
		System.out.println("Hello World !!");
		// read default settings
		
		readDefaultProperties();
		readDataFile();
	}

	private void readDataFile() {
		String dataFileName = settings.getProperty("data.file");

		// file not found in path
		try (FileReader dataFile = new FileReader(dataFileName);
				BufferedReader reader = new BufferedReader(dataFile)) {
			String line = reader.readLine();
			LOG.info("Found line:" + line);
		} catch (FileNotFoundException e) {
			LOG.error("File Not Found: " + dataFileName);
		} catch (IOException e) {
			LOG.error("Error accessing file: " + dataFileName, e);
		}
	}

	private void readDefaultProperties() {
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
