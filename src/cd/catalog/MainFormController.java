package cd.catalog;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import cd.catalog.entities.CD;
import cd.catalog.enums.EFieldType;
import cd.catalog.model.CDsModel;
import cd.catalog.model.ICDListener;
import cd.catalog.utils.impl.CsvCDHandler;

public class MainFormController implements Initializable, ICDListener {
	
	private static final String EMPTY_STRING = "";
	private CDsModel model = null;
	private ObservableList<CD> cdsList = null;
		
	private Stage mainStage;

	private String inputFileName = "";
	
	// Component Members
	@FXML Pane mainPanel;
	
	@FXML TextField searchPatternTxt;
	
	// Menu Components
	@FXML MenuItem saveBackToFileMenuItem;
	@FXML MenuItem closeMenuItem;
	@FXML MenuItem aboutMenuItem;
	
	// Action Buttons
	@FXML Button searchBtn;
	@FXML Button clearFileterBtn;
	@FXML Button showAllCDsBtn;
	@FXML Button enterNewCDBtn;
	@FXML Button deleteCDs;
	
	// Check Boxes
	@FXML CheckBox labelChk;
	@FXML CheckBox performerChk;
	@FXML CheckBox composerChk;
	@FXML CheckBox freeTextChk;
	
	@FXML TableView<CD> cdsTable;
	
	// Columns
	@FXML TableColumn<CD, String> labelCol;
	@FXML TableColumn<CD, String> seriesCol;
	@FXML TableColumn<CD, String> serialCol;
	@FXML TableColumn<CD, String> performerCol;
	@FXML TableColumn<CD, String> composerCol;
	@FXML TableColumn<CD, String> freeTextCol;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}
	
	public void loadData(String inputFilePath) {
		File inputFile = new File(inputFilePath);
		this.inputFileName = inputFilePath;
		
		CsvCDHandler handler = new CsvCDHandler();
		
		List<CD> allCDs = handler.loadAllCDs(inputFile);
		
		labelCol.setCellValueFactory(new PropertyValueFactory<CD, String>("cdLabel"));
		seriesCol.setCellValueFactory(new PropertyValueFactory<CD, String>("series"));
		serialCol.setCellValueFactory(new PropertyValueFactory<CD, String>("serial"));
		performerCol.setCellValueFactory(new PropertyValueFactory<CD, String>("performer"));
		composerCol.setCellValueFactory(new PropertyValueFactory<CD, String>("composer"));
		freeTextCol.setCellValueFactory(new PropertyValueFactory<CD, String>("freeText"));
		
		model = new CDsModel(allCDs);
		
		cdsList = FXCollections.observableArrayList();
		cdsTable.setItems(cdsList);
		cdsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		clearTable();
		fillTable(allCDs);
		
		model.addCDListener(this);
	}
	
	@FXML
    public void processSearch(ActionEvent event) {
    	List<EFieldType> searchParameters = collectCheckParameters();
    	
    	String searchPattern = getSearchPattern();
    	
    	List<CD> filteredCDs = model.findPatternBy(searchPattern, searchParameters);
    	
    	clearTable();
    	fillTable(filteredCDs);
    }
    
	@FXML
	public void showAllCDsBtnClicked(ActionEvent event) {
		clearTable();
		List<CD> allCDs = model.getAllCDs();
		fillTable(allCDs);
	}
    	
	@FXML
	public void clearFilterClicked(ActionEvent event) {
    	searchPatternTxt.setText(EMPTY_STRING);
    	labelChk.setSelected(false);
    	performerChk.setSelected(false);
    	composerChk.setSelected(false);
    	freeTextChk.setSelected(false);
    }
    
    
	@FXML 
	public void enterNewCDClicked(ActionEvent event) {
    	openNewCDForm();
    }
    
    @FXML
    void tableClickHandler(MouseEvent event) {
    	if (event.getClickCount() > 1) {
    		
    		CD selectedItem = cdsTable.getSelectionModel().getSelectedItem();
    		
    		if (selectedItem != null) {
    			openEditCDForm(selectedItem);	
    		}
    	}
    }

    @FXML
    public void deleteSelectedClicked(ActionEvent event) {
    	
    	ObservableList<CD> selectedItems = cdsTable.getSelectionModel().getSelectedItems();
    	
    	if (selectedItems.size() > 0) {
    		for (int index = 0 ; index < selectedItems.size() ; index++ ) {
    			model.deleteCD(selectedItems.get(index));
    		}
    	} else {
    		new AlertDialog(mainStage, "You didn't select any items to delete", AlertDialog.ICON_ERROR).showAndWait();	
    	}
    }
    
    
    @FXML 
    public void closeClicked(ActionEvent event) {
		saveToFileAndCloseApplication();
    }

	public void saveToFileAndCloseApplication() {
		saveBackToFile();
		new AlertDialog(mainStage, "Goodbye, You're changes were saved back to: " + inputFileName, AlertDialog.ICON_INFO).showAndWait();
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				Platform.exit();				
			}
		});

	}
    
	@FXML
	public void saveBackToFileClicked(ActionEvent event) {
		saveBackToFile();
		
		new AlertDialog(mainStage, "You're changes were saved successfuly back to: " + inputFileName, AlertDialog.ICON_INFO).showAndWait();
    }

	@FXML
	public void aboutClicked(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(
				Main.class.getResource("AboutForm.fxml"));
		Stage stage = new Stage(StageStyle.DECORATED);
		stage.setResizable(false);
		stage.setTitle("About CD Catalog");

		AnchorPane page;
		try {
			page = (AnchorPane) loader.load();
			Scene scene = new Scene(page);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void saveBackToFile() {
		CsvCDHandler handler = new CsvCDHandler();
		System.out.println("Saving to File:");
		
		File destPath = new File(inputFileName);
		
		System.out.println(destPath);
		
		boolean isSuccessful = handler.saveCDs(destPath, model.getAllCDs());
		
		System.out.println("Is successful: " + isSuccessful);
	}
    
    // Private Methods
    private List<EFieldType> collectCheckParameters() {
    	List<EFieldType> fieldsToFilterBy = new ArrayList<EFieldType>();
    	
    	if (labelChk.isSelected()) {
    		fieldsToFilterBy.add(EFieldType.CD_LABEL);
    	}
    	
    	if (performerChk.isSelected()) {
    		fieldsToFilterBy.add(EFieldType.PERFORMER);
    	}
    	
    	if (composerChk.isSelected()) {
    		fieldsToFilterBy.add(EFieldType.COMPOSER);
    	}
    	
    	if (freeTextChk.isSelected()) {
    		fieldsToFilterBy.add(EFieldType.FREE_TEXT);
    	}
    	
    	return fieldsToFilterBy;
    }
    
    private String getSearchPattern() {
    	String searchPattern = searchPatternTxt.getText();
		return searchPattern;
	}
    
    private void clearTable() {
    	cdsList.clear();
    }
    
    private void fillTable(List<CD> cds) {
    	cdsList.addAll(cds);
    }
    
    private void openEditCDForm(CD editedCD) {
		try {
			  FXMLLoader loader = new FXMLLoader(Main.class.getResource("EditCDForm.fxml"));
	          Stage stage = new Stage(StageStyle.DECORATED);
	  		  stage.setResizable(false);
	          stage.setTitle("Edit CD Data");
	          
	          AnchorPane page = (AnchorPane) loader.load();
	          EditCDFormController controller = loader.<EditCDFormController>getController();
			  controller.enterEditingCDData(editedCD, model);

	          Scene scene = new Scene(page);
			  stage.initModality(Modality.APPLICATION_MODAL);
	          stage.setScene(scene);

	          stage.show();
	          
		} catch (IOException e) {
			e.printStackTrace();
		}

    }
    
    private void openNewCDForm() {
		try {
			  FXMLLoader loader = new FXMLLoader(Main.class.getResource("EditCDForm.fxml"));
	          Stage stage = new Stage(StageStyle.DECORATED);
	  		  stage.setResizable(false);
	          stage.setTitle("Enter New CD");
	          
	          AnchorPane page = (AnchorPane) loader.load();
	          EditCDFormController controller = loader.<EditCDFormController>getController();
	          controller.enterNewCDData(model);

	          Scene scene = new Scene(page);
			  stage.initModality(Modality.APPLICATION_MODAL);
	          stage.setScene(scene);

	          stage.show();
	          
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	public void setStage(Stage primaryStage) {
		mainStage = primaryStage;
	}

	@Override
	public void handleNewCD(CD newCD) {
		cdsList.add(newCD);
	}

	@Override
	public void handleUpdateCD(CD oldCD, CD newCD) {
		cdsList.remove(oldCD);
		cdsList.add(newCD);
		
		
	}

	@Override
	public void handleRemoveCD(CD newCD) {
		cdsList.remove(newCD);
	}
}
