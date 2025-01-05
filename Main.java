import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {

			// Load the FXML file for the calendar interface.
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/calendar.fxml"));
			AnchorPane root = loader.load();

			// Initialize the CalendarController.
			CalendarController controller = loader.getController();
			controller.initialize();

			// Set up the scene and stage.
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Calendar Application");
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}