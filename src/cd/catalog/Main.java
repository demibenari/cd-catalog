package cd.catalog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
	private static final String APPLICATION_TITLE = "CD Catalog";
	private static String workingDir = ".";
	private static String inputFileName = "cds.csv";
	
	private static MainFormController mainController;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("MainForm.fxml"));
			Parent root = (Parent) loader.load();
			MainFormController controller = loader.getController();
			controller.loadData(inputFileName);
			controller.setStage(primaryStage); // or what you want to do

			mainController = controller;
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle(APPLICATION_TITLE);

			setStageMaximized(primaryStage);

			primaryStage.show();
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	@Override
	public void stop() {
		try {
			mainController.saveToFileAndCloseApplication();
			
			super.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setStageMaximized(Stage primaryStage) {
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();

		primaryStage.setX(bounds.getMinX());
		primaryStage.setY(bounds.getMinY());
		primaryStage.setWidth(bounds.getWidth());
		primaryStage.setHeight(bounds.getHeight());
	}

	public static void main(String[] args) {
		prepareInputFile();
		
		launch(args);
	}

	private static void prepareInputFile() {
		URL fileUrl = Main.class.getProtectionDomain().getCodeSource().getLocation();
		File myfile;
		try {
			myfile = new File(fileUrl.toURI());
			File dir = myfile.getParentFile(); // strip off .jar file
			
			workingDir = dir.getPath();
			System.out.println("Working dir:" + workingDir);
			
			File inputFile = new File(workingDir + File.separator + inputFileName) ;
			
			if (!inputFile.exists()) {
				System.out.println("Trying to creat the file: " + inputFile.getPath());
				inputFile.createNewFile();
			    FileWriter fw = new FileWriter(inputFile);
			    fw.close();
			    
			    System.out.println("File was not existant, created new one");
			}
			
			System.out.println("Input File is: " + inputFile.getPath());
			System.out.println("=======================================");
			
			inputFileName = inputFile.getPath();
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
