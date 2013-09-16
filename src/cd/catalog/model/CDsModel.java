package cd.catalog.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cd.catalog.entities.CD;
import cd.catalog.enums.EFieldType;

public class CDsModel implements ICDSubject {
	private static final String EMPTY_STRING = "";
	private Map<CD, CD> cdsMap = new HashMap<CD, CD>();
	private List<ICDListener> listeners;
	
	public CDsModel(List<CD> allCDs) {
		listeners = new ArrayList<ICDListener>();
		
		for (CD currCD : allCDs) {
			cdsMap.put(currCD, currCD);
		}
	}
	
	@Override
	public void addCDListener(ICDListener listener) {
		if (listener != null) {
			listeners.add(listener);
		}
	}

	@Override
	public void removeCDListener(ICDListener listener) {
		if (listener != null) {
			listeners.remove(listener);
		}
	}
	
	public List<CD> getAllCDs() {
		List<CD> allCDs = new ArrayList<CD>();
		
		for (CD cd : cdsMap.keySet()) {
			allCDs.add(cd.cloneCD());
		}
		
		return allCDs;
	}
	
	public List<CD> findPatternBy(String pattern, List<EFieldType> typesToSearchIn) {
		Map<CD, CD> matchedCDs = new HashMap<CD, CD>();
		Set<CD> allCDKeys = cdsMap.keySet();

		boolean searchInLabel = typesToSearchIn.contains(EFieldType.CD_LABEL);
		boolean serachInPerformer = typesToSearchIn.contains(EFieldType.PERFORMER);
		boolean searchInComposer = typesToSearchIn.contains(EFieldType.COMPOSER);
		boolean searchInFreeText = typesToSearchIn.contains(EFieldType.FREE_TEXT);
		
		for (CD currCD : allCDKeys) {
			if (pattern.equals(EMPTY_STRING)) {
				CD clonedCD = currCD.cloneCD();
				matchedCDs.put(clonedCD, clonedCD);
			}
			else {
				if (searchInLabel) {
					if (currCD.getCdLabel().contains(pattern)) {
						if (!matchedCDs.containsKey(currCD)) {
							CD clonedCD = currCD.cloneCD();
							matchedCDs.put(clonedCD, clonedCD);
						}
					}
				}
				
				if (serachInPerformer) {
					if (currCD.getPerformer().contains(pattern)) {
						if (!matchedCDs.containsKey(currCD)) {
							CD clonedCD = currCD.cloneCD();
							matchedCDs.put(clonedCD, clonedCD);
						}
					}
				}
				
				if (searchInComposer) {
					if (currCD.getComposer().contains(pattern)) {
						if (!matchedCDs.containsKey(currCD)) {
							CD clonedCD = currCD.cloneCD();
							matchedCDs.put(clonedCD, clonedCD);
						}
					}
				}
				
				
				if (searchInFreeText) {
					if (currCD.getFreeText().contains(pattern)) {
						if (!matchedCDs.containsKey(currCD)) {
							CD clonedCD = currCD.cloneCD();
							matchedCDs.put(clonedCD, clonedCD);
						}
					}
					
				}
			}
		}
		 
		List<CD> allCDs = new ArrayList<CD>(matchedCDs.values());
		
		return allCDs;
	}

	public void replaceCD(CD currentCD, CD modifiedCD) {
		CD remove = cdsMap.remove(currentCD);
		
		if (remove == null) {
			System.out.println("Error has occured, the removed CD was not found");
			System.out.println(currentCD);
		} else {
			cdsMap.put(modifiedCD, modifiedCD);
			
			for (ICDListener listener : listeners) {
				listener.handleUpdateCD(currentCD.cloneCD(), modifiedCD.cloneCD());
			}
		}
	}
	
	public boolean addNewCD(CD newCD) {
		boolean isSuccessful = false;
		
		if (!cdsMap.containsKey(newCD)) {
			CD cloneCD = newCD.cloneCD();
			cdsMap.put(cloneCD, cloneCD);
			isSuccessful = true;
			
			for (ICDListener listener : listeners) {
				listener.handleNewCD(newCD.cloneCD());
			}
		}
		
		return isSuccessful;
	}
	
	public boolean deleteCD(CD cdToDelete) {
		boolean isSuccessful = false;
		
		CD removedCD = cdsMap.remove(cdToDelete);
		
		if (removedCD != null) {
			isSuccessful = true;
			for (ICDListener listener : listeners) {
				listener.handleRemoveCD(cdToDelete.cloneCD());
			}
		}
		
		return isSuccessful;
	}
}
