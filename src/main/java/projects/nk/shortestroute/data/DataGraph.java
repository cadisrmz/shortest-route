package projects.nk.shortestroute.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** The Directed graph is stored by this data structure */
public class DataGraph {
	private static final Logger LOG = LoggerFactory.getLogger(DataGraph.class);

	/** Starting point of the directed graph. This is only for
	 * computation purposes. The actual graph may actually be circular
	 * and hence needs to be handled appropriately.
	 */
	HashMap<String, DataNode> graph = new HashMap<>();
	
	/** Returns the path computed for the source and destination */
	public DataPath<String> findPath(String src, String dest) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	/** Each node in the DataGraph */
	class DataNode {
		String data;
		Set<DataNode> connectedTo = new HashSet<DataNode>();	

		public DataNode(String data) {
			this.data = data;
		}

		@Override
		public String toString() {
			return "["+data + ", dest=" + connectedTo + "]";
		}
	}

	/** Populate the graph using the lines from the input.
	 * Assuming file input for now.
	 */
	public void populateGraph(Stream<String> lines) {
		lines.filter(line-> line.split(" ").length == 2)
			.forEach(line -> {
				String[] airports = line.split(" ");
				DataNode src = getGraphNode(airports[0]);
				DataNode dest = getGraphNode(airports[1]);				
				src.connectedTo.add(dest);
		});
		LOG.info("Graph:", graph);
	}
	
	private DataNode getGraphNode(String data) {
		DataNode node = graph.get(data);
		if (node == null) {
			node = new DataNode(data);
			graph.put(data, node);
		}
		
		return node;
	}

}
