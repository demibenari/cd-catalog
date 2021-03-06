package cd.catalog;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import cd.catalog.entities.CD;
import cd.catalog.model.CDsModel;


public class EditCDFormController implements Initializable {
	private static final String EMPTY_STRING = "";
	private boolean isEditMode = false;
	private CD enteredCD = null;
	private CDsModel cdsModel = null;
	
	private Stage stage;
	
	@FXML TextField cdLabelTxt;
	@FXML TextField seriesNameTxt;
	@FXML TextField serialNumberTxt;
	@FXML TextField performerNameTxt;
	@FXML TextField composerNameTxt;
	@FXML TextField freeTextTxt;
	
	@FXML Button cancelBtn;
	@FXML Button saveBtn;
	@FXML Button clearDataBtn;
	@FXML Button saveAndExitBtn;

	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}
	
	public void setStage(Stage primaryStage) {
		stage = primaryStage;
	}
	
	@FXML
	public void cancelBtnClicked(ActionEvent event) {
		closeContainerStage();
	}
	
	@FXML
	public void clearDataBtnClicked(ActionEvent event) {
		clearAllFields();
	}

	private void clearAllFields() {
		cdLabelTxt.setText(EMPTY_STRING);
		seriesNameTxt.setText(EMPTY_STRING);
		serialNumberTxt.setText(EMPTY_STRING);
		performerNameTxt.setText(EMPTY_STRING);
		composerNameTxt.setText(EMPTY_STRING);
		freeTextTxt.setText(EMPTY_STRING);
	}
	
	@FXML
	public void saveBtnClicked(ActionEvent event) {
		saveButtonClickHandler();
	}

	public boolean saveButtonClickHandler() {
		boolean result = true;
		
		if (isEditMode) {
			CD cloneCD = enteredCD.cloneCD();
			
			if (isAllFieldsNotEmpty()) {
				CD modifiedCD = collectFieldsToCD();
				
				cdsModel.replaceCD(cloneCD, modifiedCD);
				
				clearAllFields();
			}
			else {
				new AlertDialog(stage, "Did not enter any values, all values can't be empty", AlertDialog.ICON_ERROR).showAndWait();
				result = false;
			}

		} else {
			if (isAllFieldsNotEmpty()) {
				CD newCD = collectFieldsToCD();
				
				cdsModel.addNewCD(newCD);
				
				clearAllFields();
			}
			else {
				new AlertDialog(stage, "Did not enter any values, all values can't be empty", AlertDialog.ICON_ERROR).showAndWait();
				result = false;
			}
		}
		
		return result;
	}
	
	private boolean isAllFieldsNotEmpty() {
		boolean result = false;
		
		if (!cdLabelTxt.getText().equals(EMPTY_STRING)) {
			result = true;
		}
		if (!seriesNameTxt.getText().equals(EMPTY_STRING)) {
			result = true;
		}
		if (!serialNumberTxt.getText().equals(EMPTY_STRING)) {
			result = true;
		}
		if (!performerNameTxt.getText().equals(EMPTY_STRING)) {
			result = true;
		}
		if (!composerNameTxt.getText().equals(EMPTY_STRING)) {
			result = true;
		}
		if (!freeTextTxt.getText().equals(EMPTY_STRING)) {
			result = true;
		}
		
		return result;
	}
	
	@FXML 
	public void saveAndExitClicked(ActionEvent event) {
		boolean positivResult = saveButtonClickHandler();
		
		if (positivResult) {
			closeContainerStage();	
		}
	}
	
	public void enterEditingCDData(CD editedCD, CDsModel model) {
		isEditMode = true;
		enteredCD = editedCD;
		cdsModel = model;
		
		fillData();
	}
	
	public void enterNewCDData(CDsModel model) {
		isEditMode = false;
		cdsModel = model;
		
		fillData();
	}
	
	private void fillData() {
		if (isEditMode) {
			cdLabelTxt.setText(enteredCD.getCdLabel());
			seriesNameTxt.setText(enteredCD.getSeries());
			serialNumberTxt.setText(enteredCD.getSerial());
			performerNameTxt.setText(enteredCD.getPerformer());
			composerNameTxt.setText(enteredCD.getComposer());
			freeTextTxt.setText(enteredCD.getFreeText());
			
			disableEditModelControls();
		}
	}
	
	private CD collectFieldsToCD() {
		CD result = new CD();
		
		result.setCdLabel(cdLabelTxt.getText());
		result.setSeries(seriesNameTxt.getText());
		result.setSerial(serialNumberTxt.getText());
		result.setPerformer(performerNameTxt.getText());
		result.setComposer(composerNameTxt.getText());
		result.setFreeText(freeTextTxt.getText());
		
		return result;
	}
	
	private void disableEditModelControls() {
		clearDataBtn.setDisable(true);
	}
	
	private void closeContainerStage() {
	    Stage stage = (Stage) cancelBtn.getScene().getWindow();
	    stage.close();
	}
}
