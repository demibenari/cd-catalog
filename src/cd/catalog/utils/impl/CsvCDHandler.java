package cd.catalog.utils.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cd.catalog.entities.CD;
import cd.catalog.utils.intefaces.ICDHandler;

public class CsvCDHandler implements ICDHandler {

	public CsvCDHandler() {
	}
	
	@Override
	public List<CD> loadAllCDs(File sourcePath) {
		List<CD> resultList = null;
		
		try {
			if (sourcePath.exists()) {
				Scanner scanner = new Scanner(sourcePath);
				scanner.useDelimiter("\n");
				
				resultList = new ArrayList<CD>();
				
				while (scanner.hasNext()) {
					String nextCD = scanner.next();
					String[] cdData = nextCD.split(",");
					
					CD currentCD = new CD();
					
					currentCD.setCdLabel(cdData[0]);
					currentCD.setSeries(cdData[1]);
					currentCD.setSerial(cdData[2]);
					currentCD.setPerformer(cdData[3]);
					currentCD.setComposer(cdData[4]);
					currentCD.setFreeText(cdData[5]);
					
					resultList.add(currentCD);
				}
				
				scanner.close();
			}
			else {
				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return resultList;
	}

	@Override
	public boolean saveCDs(File destPath, List<CD> cdsToSave) {
		if (destPath.exists()) {
			destPath.delete();
		}
		
		try {
			Writer writer = new FileWriter(destPath);

			for (CD currentCD : cdsToSave) {
				writer.write(currentCD.formatToCsv());
				writer.append("\n");
				writer.flush();
			}
			
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}

}
