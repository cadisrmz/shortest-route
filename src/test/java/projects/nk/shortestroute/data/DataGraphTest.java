package projects.nk.shortestroute.data;

import static org.junit.Assert.*;

import org.junit.Test;

import junit.framework.Assert;

public class DataGraphTest {

	@Test
	public void testMSPtoAMSPath() {
		DataGraph graph = new DataGraph();
		try {
			DataPath<String> path = graph.findPath("MSP", "AMS");
			// we are expecting the unsupported exception at this time
			fail("Found success for an unimplemented method");
			//TODO: assertEquals("Validate path from MSP to AMS", path.getPath().size(), 1);
		} catch (UnsupportedOperationException e) {
			// success
		}
		
	}

}
