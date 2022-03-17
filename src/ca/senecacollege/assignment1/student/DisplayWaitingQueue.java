package ca.senecacollege.assignment1.student;

import ca.senecacollege.assignment1.main.DatabaseManipulator;


import java.util.ArrayList;
import java.util.Objects;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 * @authors Michal Kot-Kawula, Jawwad Ahmed Abbasi, Arryell Parris
 */


/**
 * Class to display student's waiting requests
 */
public class DisplayWaitingQueue {
    @FXML
    private TableView<IssueRequest> books_collection_table;

    
    /**
     * Display student's waiting requests upon initialization
     */
    public void initialize() {
        books_collection_table.setEditable(true);
        ArrayList<IssueRequest> borrowedBooks = new ArrayList<>();

        for (IssueRequest request : DatabaseManipulator.issueRequests) {
            if (request.getStudentid() == DatabaseManipulator.alreadyLoggedIn.getUserid() && !request.isIssued()) {
                borrowedBooks.add(request);
            }
        }

        ObservableList<IssueRequest> booksToLoad = FXCollections.observableArrayList(borrowedBooks);

        TableColumn bookID = new TableColumn("bookID");
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
        books_collection_table.setItems(booksToLoad);
        books_collection_table.getColumns().addAll(bookID, book_title, book_author, book_year, reqID);
    }

    
    /**
     * Handle exit button to redirect back to student menu
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
