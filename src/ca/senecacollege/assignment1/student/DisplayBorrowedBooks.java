package ca.senecacollege.assignment1.student;

import ca.senecacollege.assignment1.main.DatabaseManipulator;

import java.io.File;
import java.io.FileWriter;
import java.util.Objects;
import java.util.ArrayList;


import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;


/**
 * @authors Michal Kot-Kawula, Jawwad Ahmed Abbasi, Arryell Parris
 */


/**
 * Class to display student's borrowed books
 */
public class DisplayBorrowedBooks {

    @FXML
    private TableView<IssueRequest> books_collection_table;

    /**
     * Display student's borrowed books 
     */
    public void initialize() {

        books_collection_table.setEditable(true);

        ArrayList<IssueRequest> borrowed_books = new ArrayList<>();
        for (IssueRequest request : DatabaseManipulator.issueRequests) {
            if (request.getStudentid() == DatabaseManipulator.alreadyLoggedIn.getUserid() && request.isIssued()) {
                borrowed_books.add(request);
            }
        }

        generateBorrowedBooksFile(borrowed_books);

        ObservableList<IssueRequest> books_load = FXCollections.observableArrayList(borrowed_books);

        TableColumn bookID = new TableColumn("BookID");
        TableColumn book_title = new TableColumn("Title");
        TableColumn book_author = new TableColumn("Author");
        TableColumn book_year = new TableColumn("Year");
        TableColumn<IssueRequest, Integer> reqID = new TableColumn<>("requestID");

        book_author.setCellValueFactory((Callback<TableColumn.CellDataFeatures<IssueRequest, String>, ObservableValue<String>>)
                param -> new SimpleStringProperty(param.getValue().getBook().getAuthor()
                ));

        book_year.setCellValueFactory((Callback<TableColumn.CellDataFeatures<IssueRequest, String>, ObservableValue<String>>)
                param -> new SimpleStringProperty(Integer.toString(param.getValue().getBook().getYear())
                ));

        bookID.setCellValueFactory((Callback<TableColumn.CellDataFeatures<IssueRequest, String>, ObservableValue<String>>)
                param -> new SimpleStringProperty(Integer.toString(param.getValue().getBook().getBookid())
        ));

        book_title.setCellValueFactory((Callback<TableColumn.CellDataFeatures<IssueRequest, String>, ObservableValue<String>>)
                param -> new SimpleStringProperty(param.getValue().getBook().getTitle()
        ));

        reqID.setCellValueFactory(
                new PropertyValueFactory<>("requestID")
        );

        books_collection_table.setItems(books_load);
        books_collection_table.getColumns().addAll(bookID, book_title, book_author, book_year, reqID);

    }

    /**
     * Generate text file with list of books that student has borrowed
     */
    private void generateBorrowedBooksFile(ArrayList<IssueRequest> borrowed_books) {

        try {
            File borrowedBooks = new File("studBorrowedBooks.txt");
            borrowedBooks.createNewFile();

            FileWriter fw = new FileWriter(borrowedBooks);
            fw.write("Student: " + DatabaseManipulator.alreadyLoggedIn.getUNmae() + "\n\n");
            fw.write("Books Borrowed: \n\n");

            for (IssueRequest request : borrowed_books) {
                fw.write("Title: " + request.getBook().getTitle() + "\n");
                fw.write("Author: " + request.getBook().getAuthor() + "\n");
                fw.write("BookID: " + request.getBook().getBookid() + "\n\n");
            }

            fw.write("Total books: " + borrowed_books.size());
            fw.close();

        } catch (Exception exc) { System.out.println(exc); }
    }

    /**
     * Handle exit button to redirect to student menu
     */
    public void exitToStudMenu(ActionEvent actionEvent) {

        try {
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("./StudentMenu.fxml")));
            Scene sc = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Student Menu");
            stage.setScene(sc);
            stage.show();

        } catch (Exception exc) {System.out.println(exc);}
    }
}
