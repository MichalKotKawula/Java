package ca.senecacollege.assignment1.librarian;

import ca.senecacollege.assignment1.main.DatabaseManipulator;
import ca.senecacollege.assignment1.student.IssueRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.collections.FXCollections;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

/**
 * @authors Michal Kot-Kawula, Jawwad Ahmed Abbasi, Arryell Parris
 */

public class IssueBook {
    private List<IssueRequest> waiting_request = new ArrayList<>();

    @FXML
    private TableView<IssueRequest> borrowed_books_table;

    @FXML
    private ComboBox<Integer> requests;

    public void initialize() {

        for (IssueRequest request : DatabaseManipulator.issueRequests) {
            if (!request.isIssued()) {
                waiting_request.add(request);
                requests.getItems().add(request.getIssueRequestID());
            }
        }
        borrowed_books_table.setEditable(true);
        ObservableList<IssueRequest> booksToLoad = FXCollections.observableArrayList(waiting_request);

        TableColumn bookID = new TableColumn("bookID");
        TableColumn book_title = new TableColumn("Title");
        TableColumn book_author = new TableColumn("Author");
        TableColumn book_year = new TableColumn("Year");
        TableColumn<IssueRequest, Integer> reqID = new TableColumn<IssueRequest, Integer>("requestID");
        TableColumn<IssueRequest, Integer> student_id = new TableColumn<IssueRequest, Integer>("studentID");

        bookID.setCellValueFactory((
                Callback<TableColumn.CellDataFeatures<IssueRequest, String>, ObservableValue<String>>)
                param -> new SimpleStringProperty(Integer.toString(param.getValue().getBook().getBookid())
        ));

        book_title.setCellValueFactory((
                Callback<TableColumn.CellDataFeatures<IssueRequest, String>, ObservableValue<String>>)
                param -> new SimpleStringProperty(param.getValue().getBook().getTitle()
        ));

        book_author.setCellValueFactory((
                Callback<TableColumn.CellDataFeatures<IssueRequest, String>, ObservableValue<String>>)
                param -> new SimpleStringProperty(param.getValue().getBook().getAuthor()
        ));

        book_year.setCellValueFactory((
                Callback<TableColumn.CellDataFeatures<IssueRequest, String>, ObservableValue<String>>)
                param -> new SimpleStringProperty(Integer.toString(param.getValue().getBook().getYear())
        ));

        student_id.setCellValueFactory((
                new PropertyValueFactory<IssueRequest, Integer>("studentID")
        ));

        reqID.setCellValueFactory(
                new PropertyValueFactory<IssueRequest, Integer>("requestID")
        );

        borrowed_books_table.setItems(booksToLoad);

        borrowed_books_table.getColumns().addAll(bookID, book_title, book_author, book_year, student_id, reqID);
    }

    public void issueBook(ActionEvent e) {
        boolean issued = false;
        for (IssueRequest request : waiting_request) {
            if (request.getIssueRequestID() == Integer.parseInt(requests.getValue().toString()) && request.getBook().getNumberOfCopies() > 0 && !request.isIssued()) {
                if (request.approveIssue()) {
                    issued = true;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("The Info");
                    alert.setHeaderText("Yay its a success");
                    alert.setContentText("Book has been issued");
                    alert.showAndWait();
                }
            }
        }
        if (!issued) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Oh no! Something went wrong");
            alert.setHeaderText("We weren't able to approve the issue");
            alert.setContentText("we are sorrow. Looks like there arent any copies available or maybe you have a request before. ");
            alert.showAndWait();
        } else {
            requests.getItems().clear();
            waiting_request.clear();
            borrowed_books_table.getColumns().clear();
            this.initialize();
        }
    }

    public void back(ActionEvent e) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LibrarianMenu.fxml")));

        /* scene graph */
        Scene scene = new Scene(parent);

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        /* window using scene graph */
        stage.setTitle("Administrator Window");
        stage.setScene(scene);
        stage.show();
    }
}
