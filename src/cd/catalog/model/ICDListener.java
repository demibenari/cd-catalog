package cd.catalog.model;

import cd.catalog.entities.CD;

public interface ICDListener {
	void handleNewCD(CD newCD);
	void handleUpdateCD(CD oldCD, CD newCD);
	void handleRemoveCD(CD newCD);
}
