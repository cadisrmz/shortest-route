package projects.nk.shortestroute.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import projects.nk.shortestroute.ShortestPath;
import projects.nk.shortestroute.data.DataGraph.DataNode;

public class DataGraphTest {

	private static final String TEST_SAMPLE_DATA_TXT = "src/test/resources/test-sample-data.txt";
	private static final Logger LOG = LoggerFactory.getLogger(DataGraphTest.class);

	/** We assume that the readDataFile method has been tested and
	 * only the findPaths functionality needs to be tested
	 * 
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testPopulateGraphOneNode() {
		DataGraph dataGraph = new DataGraph();
		dataGraph.populateGraph("BOM", "PNQ");
		
		DataNode nodeBom = dataGraph.getGraphNode("BOM");
		DataNode nodePnq = dataGraph.getGraphNode("PNQ");
		
		// PNQ should not be connected to anything else
		assertNotNull(nodePnq);
		assertEquals(nodePnq.connectedTo.size(), 0);
		
		// BOM should not be connected to PNQ and nothing else
		assertNotNull(nodeBom);
		assertEquals(nodeBom.connectedTo.size(), 1);
		assertTrue(nodeBom.connectedTo.remove(nodePnq));
	}
	
	@Test
	public void testPopulateGraphTwoNode() {
		DataGraph dataGraph = new DataGraph();
		dataGraph.populateGraph("BOM", "PNQ");
		dataGraph.populateGraph("BOM", "DEL");
		
		DataNode nodeBom = dataGraph.getGraphNode("BOM");
		DataNode nodePnq = dataGraph.getGraphNode("PNQ");
		DataNode nodeDel = dataGraph.getGraphNode("DEL");
		
		// PNQ should not be connected to anything else
		assertNotNull(nodePnq);
		assertEquals(nodePnq.connectedTo.size(), 0);
		
		// DEL should not be connected to anything else
		assertNotNull(nodeDel);
		assertEquals(nodeDel.connectedTo.size(), 0);
		
		// BOM should not be connected to PNQ and DEL and nothing else
		assertNotNull(nodeBom);
		assertEquals(nodeBom.connectedTo.size(), 2);
		assertTrue(nodeBom.connectedTo.remove(nodePnq));
		assertTrue(nodeBom.connectedTo.remove(nodeDel));
	}

	@Test
	public void testfindPathsMSPtoAMS() {
		DataGraph dataGraph = new DataGraph();
		
		ShortestPath app = new ShortestPath();
		app.readDataFile(TEST_SAMPLE_DATA_TXT, dataGraph);

		try {
			List<String[]> path = dataGraph.findPaths("MSP", "AMS");
			// we are expecting the unsupported exception at this time
			fail("Found success for an unimplemented method");
			LOG.info(path.toString());
			// TODO: assertEquals("Validate path from MSP to AMS",
			// path.getPath().size(), 1);
		} catch (UnsupportedOperationException e) {
			// success
		}

	}

}
