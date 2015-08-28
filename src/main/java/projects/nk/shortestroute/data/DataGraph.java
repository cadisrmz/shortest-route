package projects.nk.shortestroute.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;

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
	private TreeMap<String, DataNode> graph = new TreeMap<>();

	/** Returns the path computed for the source and destination */
	public List<String[]> findPaths(String src, String dest) {
		
		List<String[]> allPaths = new ArrayList<>();
		Stack<String> currPath = new Stack<>();
		
		findAndAddPaths(allPaths, src, dest, currPath);
		
		return allPaths;
	}

	private void findAndAddPaths(List<String[]> allPaths, String currNodeData, 
			String dest, Stack<String> currPath) {	
		
		DataNode currNode = getGraphNode(currNodeData);
		// Node not found. End of traversal path. Return.
		if (currNode == null) {
			return;
		}
		
		try {
			// push the data
			// and we use the finally block to clean up, no matter what
			currPath.push(currNodeData);
			LOG.info("Visiting: "+currPath);
			// recursion always has a termination condition
			// if we found the data, then we add the path to
			// the list of solutions and return
			if (dest.equals(currNodeData)) {
				allPaths.add(currPath.toArray(new String[0]));
				return;
			}
			
			// Directed graph. Lets traverse depth first
			// and lets use recursion
			// we know that recursion will result in a limitation on the
			// length of the paths we can support, but it should work
			// for the intended solution at this time
			
			// eliminate circular dependency
			Set<String> toVisit = new HashSet<>();
			currNode.connectedTo.forEach(node -> toVisit.add(node.data));			
			toVisit.removeAll(currPath);

			for( String nodeData : toVisit) {								
				// Potential path. Initiate investigation.
				findAndAddPaths(allPaths, nodeData, dest, currPath);
			}
		} finally {
			// done processing all paths from this node. Clean up.
			currPath.pop();
		}
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
		LOG.debug("Updated node:" + srcNode);
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
		DataNode node = getGraphNode(data);
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
	protected class DataNode {
		private String data;
		private Set<DataNode> connectedTo = new HashSet<DataNode>();

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

		/** Get the number of outgoing connections 
		 * from this nodes. */
		public int getConnectionCount() {
			return this.connectedTo.size();
		}

		/** Check if there is an outgoing connection from this node
		 * to the specified node
		 * @param node DataNode to which an outgoing connection needs to be verified
		 * @return
		 */
		public boolean isConnectedTo(DataNode node) {			
			return connectedTo.contains(node);
		}
	}

}
