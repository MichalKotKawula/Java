package ca.senecacollege.assignment1.librarian;

import ca.senecacollege.assignment1.main.DatabaseManipulator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.Objects;


/**
 * @authors Michal Kot-Kawula, Jawwad Ahmed Abbasi, Arryell Parris
 */

public class LibrarianMenu {
    @FXML
    private Label message;

    @FXML
    public void initialize() {
        String name = "Hey there beautiful " + DatabaseManipulator.alreadyLoggedIn.getUNmae();
        message.setText(name);
    }

    /**
     * Add book here!
     * @param event
     * @throws IOException
     */
    public void addBook(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddBook.fxml")));
        Scene scene = new Scene(parent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Add the book!");
        stage.setScene(scene);
        stage.show();
    }

    public void issueBook(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("IssueBook.fxml")));

        Scene scene = new Scene(parent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Issue the book!");
        stage.setScene(scene);
        stage.show();
    }

    public void viewBooks(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("DisplayBooks.fxml")));

        Scene scene = new Scene(parent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Please view all books!");
        stage.setScene(scene);
        stage.show();
    }

    public void returnBook(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ReturnBook.fxml")));

        Scene scene = new Scene(parent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("You gotta return the book!");
        stage.setScene(scene);
        stage.show();
    }

    public void logOut(ActionEvent event) throws IOException {
        DatabaseManipulator.alreadyLoggedIn = null;
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../main/Login.fxml")));

        Scene scene = new Scene(parent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("SignOut");
        stage.setScene(scene);
        stage.show();
    }
}