package projects.nk.shortestroute.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/** Stores the Path to the data as a directed graph. 
 * Once element added, cannot be removed. This is a "add-only" datastructure.
 */ 
public class DataPath<DataType> implements Iterable<DataType> {
	private List<DataType> path = new ArrayList<DataType>();
	private List<DataType> returnablePath;

	synchronized public List<DataType> getPath() {
		if ( returnablePath == null ) 
			this.returnablePath = Collections.unmodifiableList(path);
		
		return this.returnablePath;
	}

	public void addPath(DataType pathElement) {
		this.path.add(pathElement);
		synchronized (this) {
			this.returnablePath = null;
		}
	}

	public Iterator<DataType> iterator() {		
		return getPath().iterator();
	}
}
