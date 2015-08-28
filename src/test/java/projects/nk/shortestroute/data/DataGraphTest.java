package projects.nk.shortestroute.data;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import projects.nk.shortestroute.ShortestPath;
import projects.nk.shortestroute.data.DataGraph.DataNode;

public class DataGraphTest {

	private static final String TEST_SAMPLE_DATA_TXT = "src/test/resources/test-sample-data.txt";
	
	@Test
	public void testPopulateGraphOneNode() {
		DataGraph dataGraph = new DataGraph();
		dataGraph.populateGraph("BOM", "PNQ");
		
		DataNode nodeBom = dataGraph.getGraphNode("BOM");
		DataNode nodePnq = dataGraph.getGraphNode("PNQ");
		
		// PNQ should not be connected to anything else
		assertNotNull(nodePnq);
		assertEquals(nodePnq.getConnectionCount(), 0);
		
		// BOM should not be connected to PNQ and nothing else
		assertNotNull(nodeBom);
		assertEquals(nodeBom.getConnectionCount(), 1);
		assertTrue(nodeBom.isConnectedTo(nodePnq));
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
		assertEquals(nodePnq.getConnectionCount(), 0l);
		
		// DEL should not be connected to anything else
		assertNotNull(nodeDel);
		assertEquals(nodeDel.getConnectionCount(), 0l);
		
		// BOM should not be connected to PNQ and DEL and nothing else
		assertNotNull(nodeBom);
		assertEquals(nodeBom.getConnectionCount(), 2);
		assertTrue(nodeBom.isConnectedTo(nodePnq));
		assertTrue(nodeBom.isConnectedTo(nodeDel));
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
