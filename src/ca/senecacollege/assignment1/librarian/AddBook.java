package ca.senecacollege.assignment1.librarian;

import ca.senecacollege.assignment1.main.DatabaseManipulator;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.Objects;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 * @authors Michal Kot-Kawula, Jawwad Ahmed Abbasi, Arryell Parris
 */


public class AddBook implements Initializable {
    @FXML
    private TextField title;

    @FXML
    private TextField author;

    @FXML
    private TextField year;

    @FXML
    private TextField copies;

    @FXML
    private int bookID = 0;

    /**
     * Constructor initialize addbook object.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (DatabaseManipulator.books.size() > 0) {
            this.bookID = DatabaseManipulator.books.get(DatabaseManipulator.books.size() - 1).getBookid();
        }
    }

    /**
     * This function add book in the object and checks and validates the data
     */
    public void addBook(ActionEvent event) throws SQLException, IOException {

        if (title.getText().toString().length() == 0 || author.getText().toString().length() == 0 || year.getText().toString().length() == 0 || copies.getText().toString().length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Oh no! Something went wrong!");
            alert.setHeaderText("Looks like values are missing!");
            alert.setContentText("Fields are mandatory!");
            alert.showAndWait();
            return;
        }
        else {
            boolean found = false;
            for (Book book : DatabaseManipulator.books) {
                if (title.getText().toString().equals(book.getTitle()) && author.getText().toString().equals(book.getAuthor()) && book.getYear() == Integer.parseInt(year.getText().toString())) {
                    found = true;
                    for (int i = 0; i < Integer.parseInt(copies.getText()); i++) {
                        book.addCopy();
                    }
                    DatabaseManipulator.updateNumberOfCopies(book);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Book Info");
                    alert.setHeaderText("Yay its a success");
                    alert.setContentText(copies.getText() + " ; the copies are added");
                    alert.showAndWait();
                    break;
                }

            }
            if (!found) {
            	List<Integer> tempBorrowed = new ArrayList<Integer>();
                Book newBook = new Book(this.bookID,title.getText().toString(), author.getText().toString(), Integer.parseInt(year.getText().toString()), Integer.parseInt(copies.getText()), tempBorrowed);
                DatabaseManipulator.books.add(newBook);

                if (!DatabaseManipulator.saveBook(newBook)) {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setTitle("Oh no! Something went wrong!");
                    error.setHeaderText("Oops. We couldn't save the book info");
                    error.showAndWait();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Book Info");
                    alert.setHeaderText("Yay its a success");
                    alert.setContentText("We added the book information");
                    alert.showAndWait();

                    this.back(event);
                }
            }
        }
    }

    /**
     * The function takes you back page
     */
    public void back(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LibrarianMenu.fxml")));

        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Administrator Window");
        stage.setScene(scene);
        stage.show();
    }
}
