package cd.catalog.model;

public interface ICDSubject {
	void addCDListener(ICDListener listener);
	void removeCDListener(ICDListener listener);
}
