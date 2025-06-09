package ui; // Ensure this matches the directory structure if QuizApplication.java is in the 'ui' folder.

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.util.Objects;

public class QuizApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Path to FXML assumes 'ui' is a top-level directory in resources/classpath
            // or that QuizApplication.class is in a location where this relative path works.
            // If QuizApplication is in package ui, and QuizView.fxml is also in ui:
            // Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("QuizView.fxml")));
            // If 'ui' is a resource folder:
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ui/QuizView.fxml")));


            Scene scene = new Scene(root);
            primaryStage.setTitle("Quiz Application");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace(); 

            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Application Error");
            alert.setHeaderText("Failed to load the application interface.");
            alert.setContentText("Could not load QuizView.fxml. Please ensure the file is in the correct location (e.g., /ui/QuizView.fxml relative to your resources/classpath) and not corrupted.\\n\\nTechnical Details: " + e.getMessage());
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
