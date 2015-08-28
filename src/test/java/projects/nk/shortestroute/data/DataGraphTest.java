package projects.nk.shortestroute.data;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import projects.nk.shortestroute.ShortestPath;

public class DataGraphTest {

	private static final String DEL = "DEL";
	private static final String BOM = "BOM";
	private static final String PNQ = "PNQ";
	private static final String TEST_SAMPLE_DATA_TXT = "src/test/resources/test-sample-data.txt";
		
	@Test
	public void testFindInvalidNode() throws ObjectNotFoundException {
		DataGraph dataGraph = new DataGraph();
		dataGraph.populateGraph(BOM, PNQ);
		
		assertFalse("Should not find this node since it was not added", dataGraph.hasGraphNode(DEL));
	}
	
	public void testFindInvalidPath() throws ObjectNotFoundException {
		DataGraph dataGraph = new DataGraph();
		dataGraph.populateGraph(BOM, PNQ);
		dataGraph.populateGraph(DEL, PNQ);
		dataGraph.populateGraph(BOM, DEL);
		
		assertFalse("This path was never added", dataGraph.isConnected(PNQ, DEL));
	}
	
	@Test(expected=ObjectNotFoundException.class)
	public void testFindInvalidNodeInPath() throws ObjectNotFoundException {
		DataGraph dataGraph = new DataGraph();
		dataGraph.populateGraph(BOM, PNQ);
		dataGraph.populateGraph(DEL, PNQ);
		dataGraph.populateGraph(BOM, DEL);
		
		dataGraph.isConnected(PNQ, "MAA");
	}
	
	@Test
	public void testPopulateGraph() throws ObjectNotFoundException {
		DataGraph dataGraph = new DataGraph();
		dataGraph.populateGraph(BOM, PNQ);
		
		assertTrue("Should find this node",dataGraph.hasGraphNode(BOM));
		assertTrue("Should find this node",dataGraph.hasGraphNode(PNQ));
		
		// PNQ should not be connected to anything else
		assertEquals("There should be exactly 0 outgoing paths from here", 0, dataGraph.getConnectionCount(PNQ));
		
		// BOM should not be connected to PNQ and nothing else
		assertEquals("There should be exactly 1 outgoing paths from here", 1, dataGraph.getConnectionCount(BOM));
		assertTrue("Needs to have this path", dataGraph.isConnected(BOM,PNQ));
	}
	
	@Test
	public void testConnectionCount() throws ObjectNotFoundException {
		DataGraph dataGraph = new DataGraph();
		dataGraph.populateGraph(BOM, PNQ);
		dataGraph.populateGraph(BOM, DEL);
		
		// PNQ should not be connected to anything else
		assertEquals(0, dataGraph.getConnectionCount(PNQ));
		
		// DEL should not be connected to anything else
		assertEquals(0, dataGraph.getConnectionCount(DEL));
		
		// BOM should not be connected to PNQ and DEL and nothing else
		assertEquals(2, dataGraph.getConnectionCount(BOM));
		assertTrue(dataGraph.isConnected(BOM, PNQ));
		assertTrue(dataGraph.isConnected(BOM, DEL));	
	}

	@Test
	public void testGraphWithCyclicPath() throws ObjectNotFoundException {
		DataGraph dataGraph = new DataGraph();
		dataGraph.populateGraph(BOM, PNQ);
		dataGraph.populateGraph(BOM, DEL);
		dataGraph.populateGraph(PNQ, DEL);
		dataGraph.populateGraph(DEL, PNQ);
		dataGraph.populateGraph(PNQ, BOM);

		// BOM should not be connected to PNQ and DEL and nothing else
		assertEquals(2, dataGraph.findPaths(BOM, DEL).size());
	}
}
