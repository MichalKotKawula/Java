package ca.senecacollege.assignment1.admin;

import ca.senecacollege.assignment1.main.DatabaseManipulator;
import java.io.IOException;
import java.util.Objects;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * @authors Michal Kot-Kawula, Jawwad Ahmed Abbasi, Arryell Parris
 */

/**
 * Administrator menu which allows adding, showing and gathering report of librarian.
 */
public class AdministratorMenu {
    @FXML
    private Label welcomemessage;

    @FXML
    public void initialize() {
        String welcome = "Howdy " + DatabaseManipulator.alreadyLoggedIn.getUNmae();
        welcomemessage.setText(welcome);
    }

    /**
     * regLibrarian feature to add new librarian
     */
    public void regLibrarian(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("RegisterNewLibrarian.fxml")));
        Scene sc = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Register Librarian");
        stage.setScene(sc);
        stage.show();
    }

    /**
     * GenerateReport feature which showcases all librarians in system
     */
    public void generateReport(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LibrarianReport.fxml")));
        Scene sc = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Librarian Report");
        stage.setScene(sc);
        stage.show();
    }

    
    /**
     * ShowLibrarianController which shows all librarians and allows to delete them
     */
    public void displayLibrarian(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("DisplayLibrarians.fxml")));
        Scene sc = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Display Librarian");
        stage.setScene(sc);
        stage.show();
    }
    
    /**
     * Log out user and bring him back to main menu
     */
    public void logOut(ActionEvent event) throws IOException {
        DatabaseManipulator.alreadyLoggedIn = null;
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../main/Login.fxml")));
        Scene sc = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Login");
        stage.setScene(sc);
        stage.show();
    }
}
