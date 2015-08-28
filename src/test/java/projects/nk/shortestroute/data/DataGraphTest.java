package projects.nk.shortestroute.data;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import projects.nk.shortestroute.ShortestPath;

public class DataGraphTest {

	private static final String DEL = "DEL";
	private static final String BOM = "BOM";
	private static final String PNQ = "PNQ";
	private static final String TEST_SAMPLE_DATA_TXT = "src/test/resources/test-sample-data.txt";
	
	@Test
	public void testPopulateGraphOneNode() throws ObjectNotFoundException {
		DataGraph dataGraph = new DataGraph();
		dataGraph.populateGraph(BOM, PNQ);
		
		assertTrue(dataGraph.hasGraphNode(BOM));
		assertTrue(dataGraph.hasGraphNode(PNQ));
		
		// PNQ should not be connected to anything else
		assertEquals(dataGraph.getConnectionCount(PNQ), 0);
		
		// BOM should not be connected to PNQ and nothing else
		assertEquals(dataGraph.getConnectionCount(BOM), 1);
		assertTrue(dataGraph.isConnected(BOM,PNQ));
	}
	
	@Test
	public void testPopulateGraphTwoNode() throws ObjectNotFoundException {
		DataGraph dataGraph = new DataGraph();
		dataGraph.populateGraph(BOM, PNQ);
		dataGraph.populateGraph(BOM, DEL);
		
		// PNQ should not be connected to anything else
		assertTrue(dataGraph.hasGraphNode(PNQ));
		assertEquals(dataGraph.getConnectionCount(PNQ), 0);
		
		// DEL should not be connected to anything else
		assertNotNull(dataGraph.hasGraphNode(DEL));
		assertEquals(dataGraph.getConnectionCount(DEL), 0l);
		
		// BOM should not be connected to PNQ and DEL and nothing else
		assertTrue(dataGraph.hasGraphNode(BOM));
		assertEquals(dataGraph.getConnectionCount(BOM), 2);
		assertTrue(dataGraph.isConnected(BOM, PNQ));
		assertTrue(dataGraph.isConnected(BOM, DEL));
	}

	@Test
	public void testfindPathsMSPtoAMS() {
		DataGraph dataGraph = new DataGraph();
		
		ShortestPath app = new ShortestPath();
		app.readDataFile(TEST_SAMPLE_DATA_TXT, dataGraph);

		List<String[]> path = dataGraph.findPaths("MSP", "AMS");
		
		assertEquals("Validate path from MSP to AMS", path.size(), 2);
		assertArrayEquals(path.get(0), new String[] {"MSP", "AMS"});
		assertArrayEquals(path.get(1), new String[] {"MSP", "FFK", "AMS"});

	}

}
