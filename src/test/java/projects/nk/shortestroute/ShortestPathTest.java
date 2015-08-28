package projects.nk.shortestroute;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import projects.nk.shortestroute.data.DataGraph;

public class ShortestPathTest {

	private static final String TEST_SAMPLE_DATA_TXT = "src/test/resources/test-sample-data.txt";
	private static final Logger LOG = LoggerFactory.getLogger(ShortestPathTest.class); 
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/** This method assumes that the DataGraph functionality has been tested */
	private DataGraph readFileToGraph(String filename) {
		Path dataFilePath = Paths.get(filename);
		DataGraph dataGraph = new DataGraph();
		
		if (dataFilePath.toFile().exists()) {
			// file not found in path
			try ( Stream<String> lines = Files.lines(dataFilePath) ) {
				lines.map(line->line.trim().split(" "))					
				.filter(lineElements -> lineElements.length >= 2)
				.forEach(parts -> {
					dataGraph.populateGraph(parts[0], parts[1]);
				});
			} catch (IOException e) {
				LOG.error("Error accessing file:", e);
				fail("Error accessing file: " + filename);
			}
		} else {
			fail("File Not Found: " + filename);
		}
		
		return dataGraph;
	}
	
	@Test
	public void testReadDataFile() {
		ShortestPath app = new ShortestPath();
		DataGraph dataGraph = new DataGraph();
		
		app.readDataFile(TEST_SAMPLE_DATA_TXT, dataGraph);
		
		// validate data
		assertEquals(readFileToGraph(TEST_SAMPLE_DATA_TXT),dataGraph);
		
	}
	
	@Test
	public void testfindPathsFromFileData() {
		DataGraph dataGraph = new DataGraph();
		
		ShortestPath app = new ShortestPath();
		app.readDataFile(TEST_SAMPLE_DATA_TXT, dataGraph);

		List<String[]> path = dataGraph.findPaths("MSP", "AMS");
		
		assertEquals("Validate path from MSP to AMS", 2, path.size());
		assertArrayEquals(path.get(0), new String[] {"MSP", "AMS"});
		assertArrayEquals(path.get(1), new String[] {"MSP", "FFK", "AMS"});

	}

}
