package cd.catalog.utils.intefaces;

import java.io.File;
import java.util.List;

import cd.catalog.entities.CD;

public interface ICDWriter {
	boolean saveCDs(File destPath, List<CD> cdsToSave);
}
