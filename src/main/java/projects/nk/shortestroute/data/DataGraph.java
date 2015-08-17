package projects.nk.shortestroute.data;

/** The Directed graph is stored by this data structure */
public class DataGraph<DataType> {
	/** Starting point of the directed graph. This is only for
	 * computation purposes. The actual graph may actually be circular
	 * and hence needs to be handled appropriately.
	 */
	DataNode root;
	
	public DataPath<DataType> findPath(DataType src, DataType dest) {
		throw new UnsupportedOperationException("Not Implemented");
	}
}
