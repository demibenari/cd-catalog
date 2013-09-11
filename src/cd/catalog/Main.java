package cd.catalog;
	
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cd.catalog.entities.CD;
import cd.catalog.enums.EFieldType;
import cd.catalog.model.CDsModel;
import cd.catalog.utils.impl.CsvCDHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
        try {
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("MainForm.fxml"));
        	Parent root = (Parent) loader.load();
        	MainFormController controller = loader.getController();
        	controller.setStage(primaryStage); // or what you want to do
        	
            AnchorPane page = (AnchorPane) FXMLLoader.load(Main.class.getResource("MainForm.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception ex) {
        	System.out.println(ex);
        }
	}
	
	   @Override
	    public void stop() throws Exception {
	      super.stop();
	    }
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
