package cd.catalog;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
	private static final String APPLICATION_TITLE = "CD Catalog";

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(
					"MainForm.fxml"));
			Parent root = (Parent) loader.load();
			MainFormController controller = loader.getController();
			controller.setStage(primaryStage); // or what you want to do

			AnchorPane page = (AnchorPane) FXMLLoader.load(Main.class
					.getResource("MainForm.fxml"));
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

	private void setStageMaximized(Stage primaryStage) {
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();

		primaryStage.setX(bounds.getMinX());
		primaryStage.setY(bounds.getMinY());
		primaryStage.setWidth(bounds.getWidth());
		primaryStage.setHeight(bounds.getHeight());
	}

	public static void main(String[] args) {
		launch(args);
	}
}
