package ca.senecacollege.assignment1.librarian;
import ca.senecacollege.assignment1.main.DatabaseManipulator;

import java.io.IOException;
import java.util.Objects;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;

/**
 * @authors Michal Kot-Kawula, Jawwad Ahmed Abbasi, Arryell Parris
 */

public class DisplayBooks {

    @FXML
    private TableView<Book> books_collection_table;

    public void initialize() {

        books_collection_table.setEditable(true);

        ObservableList<Book> books_list = FXCollections.observableArrayList(DatabaseManipulator.books);

        TableColumn<Book, Integer> bookID = new TableColumn<>("bookID");
        TableColumn<Book, String> book_title = new TableColumn<>("Title");
        TableColumn<Book, String> book_author = new TableColumn<>("Author");
        TableColumn<Book, Integer> book_year = new TableColumn<>("Year");
        TableColumn<Book, Integer> book_copies = new TableColumn<>("Copies");
        TableColumn<Book, Integer> borrower_idd = new TableColumn<>("borrowerID");

        bookID.setCellValueFactory(
                new PropertyValueFactory<>("bookID")
        );

        book_title.setCellValueFactory(
                new PropertyValueFactory<>("title")
        );

        book_author.setCellValueFactory(
                new PropertyValueFactory<>("author")
        );

        book_year.setCellValueFactory(
                new PropertyValueFactory<>("year")
        );

        book_copies.setCellValueFactory(
                new PropertyValueFactory<>("book_copies")
        );

        borrower_idd.setCellValueFactory(
                new PropertyValueFactory<>("borrowerID")
        );

        books_collection_table.setItems(books_list);
        books_collection_table.getColumns().addAll(bookID, book_title, book_author, book_year, borrower_idd, book_copies);

    }

    public void exportToFile(ActionEvent e) {
        if (DatabaseManipulator.saveBookReport("booksFromLibraryExport.txt")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.getDialogPane().setMinWidth(500);
            alert.setTitle("Info!");
            alert.setHeaderText("Yay it was a success.");
            alert.setContentText("Please check the new report file created. The Total Records Are: " + DatabaseManipulator.books.size());
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Oh no something went wrong!");
            alert.setHeaderText("There was an error saving the reports");
            alert.setContentText("Looks like the file couldn't be saved");
            alert.showAndWait();
        }
    }

    public void back(ActionEvent e) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("./LibrarianMenu.fxml")));

        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

        stage.setTitle("Administrator Window");
        stage.setScene(scene);
        stage.show();
    }
}
