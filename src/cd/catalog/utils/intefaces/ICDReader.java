package cd.catalog.utils.intefaces;

import java.io.File;
import java.util.List;

import cd.catalog.entities.CD;

public interface ICDReader {
	List<CD> loadAllCDs(File sourcePath);
}
