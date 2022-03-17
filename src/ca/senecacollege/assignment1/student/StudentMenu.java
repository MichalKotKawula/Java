package ca.senecacollege.assignment1.student;

import ca.senecacollege.assignment1.main.DatabaseManipulator;

import java.util.Objects;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;

/**
 * @authors Michal Kot-Kawula, Jawwad Ahmed Abbasi, Arryell Parris
 */

/**
 *Student menu class
 */
public class StudentMenu {

    @FXML
    private Label message;

    /**
     * Initialize menu screen and provide welcome message
     */
    @FXML
    public void initialize() {
        String name = "Howdy " + DatabaseManipulator.alreadyLoggedIn.getUNmae();
        message.setText(name);
    }

    /**
     * Handle view borrowed books button by redirecting to its component
     */
    public void displayBorrowedBooks(ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("./DisplayBorrowedBooks.fxml")));
            Scene sc = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Borrowed Books");
            stage.setScene(sc);
            stage.show();
        } catch (Exception exc) {System.out.println(exc);}
    }

    /**
     * Handle search book button by redirecting to its component
     */
    public void searchBook(ActionEvent event) {

        try {
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("./FindBook.fxml")));
            Scene sc = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(sc);
            stage.setTitle("Search");
            stage.show();
        } catch (Exception exc) {System.out.println(exc);}
    }

    /**
     * Handle view waiting tickets button by redirecting to its component
     */
    public void viewWaitingQueue(ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("./DisplayWaitingQueue.fxml")));
            Scene sc = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Waiting Queue");
            stage.setScene(sc);
            stage.show();
        } catch (Exception exc) {System.out.println(exc);}
    }
    

    /**
     *  Handle logout button by logging out the student from system, and redirecting to default menu
     */
    public void logOut(ActionEvent event) {

        try {
            DatabaseManipulator.alreadyLoggedIn = null;
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../main/Login.fxml")));
            Scene sc = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Login");
            stage.setScene(sc);
            stage.show();
        } catch (Exception exc) {System.out.println(exc);}
    }
}
