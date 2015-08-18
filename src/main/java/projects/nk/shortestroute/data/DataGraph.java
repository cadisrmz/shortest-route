package projects.nk.shortestroute.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** The Directed graph is stored by this data structure */
public class DataGraph {

	private static final Logger LOG = LoggerFactory.getLogger(DataGraph.class);

	/**
	 * Starting point of the directed graph. This is only for computation
	 * purposes. The actual graph may actually be circular and hence needs to be
	 * handled appropriately.
	 */
	private HashMap<String, DataNode> graph = new HashMap<>();

	/** Returns the path computed for the source and destination */
	public List<String[]> findPaths(String src, String dest) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	/**
	 * Populate the graph using the lines from the input. Assuming file input
	 * for now.
	 * @param parts 
	 */
	public void populateGraph(String src, String dest) {
		DataNode srcNode = findOrCreateGraphNode(src);
		DataNode destNode = findOrCreateGraphNode(dest);
		srcNode.connectedTo.add(destNode);
		LOG.info("Updated node:" + srcNode);
	}

	/** Find a node in the Map or create, insert and return if none 
	 * @param data the data in the node that needs to be looked up
	 */
	public DataNode getGraphNode(String data) {
		return graph.get(data);
	}
	
	/** Find a node in the Map or create, insert and return if none.
	 * Internal use only 
	 * @param data the data in the node that needs to be looked up
	 */
	synchronized private DataNode findOrCreateGraphNode(String data) {
		DataNode node = graph.get(data);
		if (node == null) {
			node = new DataNode(data);
			graph.put(data, node);
		}

		return node;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((graph == null) ? 0 : graph.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataGraph other = (DataGraph) obj;
		if (graph == null) {
			if (other.graph != null)
				return false;
		} else if (!graph.equals(other.graph))
			return false;
		return true;
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
			StringBuilder connectedToStr = new StringBuilder();
			connectedTo.forEach(node -> connectedToStr.append(","+node.data));
			return "[" + data + ", dest=" + connectedToStr + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((data == null) ? 0 : data.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			DataNode other = (DataNode) obj;
			if (data == null) {
				if (other.data != null)
					return false;
			} else if (!data.equals(other.data))
				return false;
			return true;
		}
	}

}
