package ca.senecacollege.assignment1.main;

import java.util.Objects;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

/**
 * @authors Michal Kot-Kawula, Jawwad Ahmed Abbasi, Arryell Parris
 */

/**
 * Main function where program starts add java fxml and sqlite libraries to project
 */
public class Main extends Application {

    static BorderPane rootPane;

    @Override
    public void start(Stage primaryStage) {
        try {
            rootPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Login.fxml")));
            Scene sc = new Scene(rootPane);
            sc.getStylesheets().add(Objects.requireNonNull(getClass().getResource("application.css")).toExternalForm());
            primaryStage.setScene(sc);
            primaryStage.setResizable(false);
            primaryStage.setTitle("Sign in!");
            primaryStage.show();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        DatabaseManipulator data = new DatabaseManipulator();
        data.fetchData();
        launch(args);
    }
}