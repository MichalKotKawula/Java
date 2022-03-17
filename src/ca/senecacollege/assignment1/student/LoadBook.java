package ca.senecacollege.assignment1.student;

import ca.senecacollege.assignment1.librarian.Book;
import ca.senecacollege.assignment1.main.DatabaseManipulator;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Objects;


/**
 * @authors Michal Kot-Kawula, Jawwad Ahmed Abbasi, Arryell Parris
 */
 

/**
 * Class which loads searched books 
 */
public class LoadBook {

	
    @FXML
    private ComboBox books;
    
    @FXML
    private TableView<Book> books_collection_table;


    /**
     * Display found books from search
     */
    public void initialize() {
        for (Book bk : FindBook.foundBooks) {
            books.getItems().add(bk.getBookid());
        }
        books_collection_table.setEditable(true);

        ObservableList<Book> books_load = FXCollections.observableArrayList(FindBook.foundBooks);

        TableColumn<Book, Integer> bookID = new TableColumn<>("bookID");
        TableColumn<Book, String> book_title = new TableColumn<>("Title");
        TableColumn<Book, String> book_author = new TableColumn<>("Author");
        TableColumn<Book, Integer> book_year = new TableColumn<>("Year");
        TableColumn<Book, Integer> student_id = new TableColumn<>("borrowerID");

        bookID.setCellValueFactory(new PropertyValueFactory<>("bookID"));

        book_title.setCellValueFactory(new PropertyValueFactory<>("title"));

        book_author.setCellValueFactory(new PropertyValueFactory<>("author") );

        book_year.setCellValueFactory(new PropertyValueFactory<>("year"));

        student_id.setCellValueFactory(new PropertyValueFactory<Book, Integer>("studentID"));

        books_collection_table.setItems(books_load);
        books_collection_table.getColumns().addAll(bookID, book_title, book_author, book_year, student_id);
    }

    
    /**
     * Handle exit button, redirect user beck to search module
     */
    public void handleSearchExit(ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("./FindBook.fxml")));
            Scene sc = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(sc);
            stage.show();

        } catch (Exception exc) {System.out.println(exc);}
    }
    
    /**
     * Request to receive a copy of desired book, if no copies exits in inventory, student gets put on waiting list.
     */
    public void requestIssue(ActionEvent event) {

        for (Book bk : FindBook.foundBooks) {

            if (bk.getBookid() == Integer.parseInt(books.getValue().toString())) {
                boolean found = false;
                for (IssueRequest issue : DatabaseManipulator.issueRequests) {
                    if (issue.isEqual(bk.getBookid(), DatabaseManipulator.alreadyLoggedIn.getUserid())) {found = true;}
                }
                if (bk.getBorrowerid().contains(DatabaseManipulator.alreadyLoggedIn.getUserid())) {
                    Alert alrt = new Alert(Alert.AlertType.ERROR);
                    alrt.setTitle("Already Issued!");
                    alrt.setContentText("You already have an issue of this book!");
                    alrt.showAndWait();
                    handleSearchExit(event);
                }
                else if (found) {
                    Alert alrt = new Alert(Alert.AlertType.ERROR);
                    alrt.setTitle("Already Requested");
                    alrt.setContentText("You already requested an issue of this book!");
                    alrt.showAndWait();
                    handleSearchExit(event);
                }
                else if (bk.getNumberOfCopies() > 0) {
                    IssueRequest request = new IssueRequest(bk, DatabaseManipulator.alreadyLoggedIn.getUserid(), 0);
                    DatabaseManipulator.issueRequests.add(request);
                    DatabaseManipulator.createWaitingTicket(bk, DatabaseManipulator.alreadyLoggedIn.getUserid());
                    Alert alrt = new Alert(Alert.AlertType.CONFIRMATION);
                    alrt.setTitle("Request Successful!");
                    alrt.setHeight(400);
                    alrt.setWidth(400);
                    alrt.setContentText("Request success."
                            + " You will receive book copy when "
                            + "Librarian approves your request!");
                    alrt.showAndWait();
                    handleSearchExit(event);
                    break;

                } else {
                    IssueRequest req = new IssueRequest(bk, DatabaseManipulator.alreadyLoggedIn.getUserid(), 0);
                    DatabaseManipulator.issueRequests.add(req);
                    DatabaseManipulator.createWaitingTicket(bk, DatabaseManipulator.alreadyLoggedIn.getUserid());
                    Alert alrt = new Alert(Alert.AlertType.ERROR);
                    alrt.setTitle("Book Currently Not Available!");
                    alrt.setHeight(400);
                    alrt.setWidth(400);
                    alrt.setContentText("All copies for this book are taken."
                            + " You have been added to waiting queue"
                            + " we'll send you a copy when it's available.");
                    alrt.showAndWait();
                    handleSearchExit(event);
                }
            }
        }
    }


}
