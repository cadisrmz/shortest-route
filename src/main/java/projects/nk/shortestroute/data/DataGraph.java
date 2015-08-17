package projects.nk.shortestroute.data;

import java.util.ArrayList;
import java.util.List;

/** The Directed graph is stored by this data structure */
public class DataGraph<DataType> {
	/** Starting point of the directed graph. This is only for
	 * computation purposes. The actual graph may actually be circular
	 * and hence needs to be handled appropriately.
	 */
	DataNode root;
	
	/** Returns the path computed for the source and destinaion */
	public DataPath<DataType> findPath(DataType src, DataType dest) {
		throw new UnsupportedOperationException("Not Implemented");
	}

	/** Each node in the DataGraph */
	class DataNode {
		DataType data;
		List<DataNode> connectedTo = new ArrayList<DataNode>();	
	}

}
