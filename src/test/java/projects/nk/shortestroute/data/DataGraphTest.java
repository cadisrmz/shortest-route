package projects.nk.shortestroute.data;

import static org.junit.Assert.*;

import org.junit.Test;

import junit.framework.Assert;

public class DataGraphTest {

	@Test
	public void testMSPtoAMSPath() {
		DataGraph<String> graph = new DataGraph<>();
		DataPath<String> path = graph.findPath("MSP", "AMS");
		assertEquals("Validate path from MSP to AMS", path.getPath().size(), 1);
	}

}
