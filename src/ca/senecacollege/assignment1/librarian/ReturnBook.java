package ca.senecacollege.assignment1.librarian;

import ca.senecacollege.assignment1.main.DatabaseManipulator;
import ca.senecacollege.assignment1.student.IssueRequest;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @authors Michal Kot-Kawula, Jawwad Ahmed Abbasi, Arryell Parris
 */

public class ReturnBook {
    private List<IssueRequest> waiting_request = new ArrayList<>();

    @FXML
    private TableView<IssueRequest> borrowed_books_table;

    @FXML
    private ComboBox<Integer> requests;

    public void initialize() {
        for (IssueRequest request : DatabaseManipulator.issueRequests) {
            if (request.isIssued()) {
                waiting_request.add(request);
                requests.getItems().add(request.getIssueRequestID());
            }
        }
        borrowed_books_table.setEditable(true);
        ObservableList<IssueRequest> booksToLoad = FXCollections.observableArrayList(waiting_request);

        TableColumn bookID = new TableColumn("bookID");
        TableColumn bookTitle = new TableColumn("Title");
        TableColumn bookAuthor = new TableColumn("Author");
        TableColumn bookYear = new TableColumn("Year");
        TableColumn<IssueRequest, Integer> reqID = new TableColumn<IssueRequest, Integer>("requestID");
        TableColumn<IssueRequest, Integer> student_id = new TableColumn<IssueRequest, Integer>("studentID");


        bookID.setCellValueFactory((Callback<TableColumn.CellDataFeatures<IssueRequest, String>, ObservableValue<String>>) param -> new SimpleStringProperty(Integer.toString(param.getValue().getBook().getBookid())));

        bookTitle.setCellValueFactory((Callback<TableColumn.CellDataFeatures<IssueRequest, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().getBook().getTitle()));

        bookAuthor.setCellValueFactory((Callback<TableColumn.CellDataFeatures<IssueRequest, String>, ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().getBook().getAuthor()));

        bookYear.setCellValueFactory((Callback<TableColumn.CellDataFeatures<IssueRequest, String>, ObservableValue<String>>) param -> new SimpleStringProperty(Integer.toString(param.getValue().getBook().getYear())));

        student_id.setCellValueFactory((
                new PropertyValueFactory<>("studentID")
        ));

        reqID.setCellValueFactory(
                new PropertyValueFactory<>("requestID")
        );

        borrowed_books_table.setItems(booksToLoad);
        borrowed_books_table.getColumns().addAll(bookID, bookTitle , bookAuthor, bookYear, student_id, reqID);
    }

    public void returnBook() throws SQLException {
        boolean returned = false;
        for (IssueRequest request : waiting_request) {
            if (request.getIssueRequestID() == Integer.parseInt(requests.getValue().toString()) && request.isIssued()) {
                returned = true;
                if (request.approveBookReturn()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("The Info!");
                    alert.setHeaderText("Yay its a success");
                    alert.setContentText("You have successfully returned the book!");
                    alert.showAndWait();
                }
            }
        }
        if (!returned) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Oh no! Something went wrong!");
            alert.setHeaderText("We weren't able to return the book!");
            alert.setContentText("Look like there was an error returning the book!");
            alert.showAndWait();
        }
        else {
            requests.getItems().clear();
            waiting_request.clear();
            borrowed_books_table.getColumns().clear();
            this.initialize();
        }
    }

    public void back(ActionEvent e) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LibrarianMenu.fxml")));

        Scene scene = new Scene(parent);

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setTitle("Admin Window");
        stage.setScene(scene);
        stage.show();
    }
}
