package ca.senecacollege.assignment1.student;

import ca.senecacollege.assignment1.librarian.Book;
import ca.senecacollege.assignment1.main.DatabaseManipulator;


import java.util.ArrayList;
import java.util.Objects;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.control.Alert;


/**
 * @authors Michal Kot-Kawula, Jawwad Ahmed Abbasi, Arryell Parris
 */

/**
 * Class handling searching titles of books
 */
public class FindBook {

    @FXML
    private TextField name_of_book;
    
    public static List<Book> foundBooks = new ArrayList<Book>();

    /**
     * Search for title provided by student, and provide according results
     */
    public void searchBook(ActionEvent event) {
        String name = name_of_book.getText();
        if (name.length() < 4) {
        	Alert alrt = new Alert(Alert.AlertType.ERROR);
            alrt.setTitle("Invalid Search!");
            alrt.setContentText("Please enter at least 4 characters.");
            alrt.showAndWait();
        }
        else {
        	foundBooks.clear();

            for (Book bk : DatabaseManipulator.books) {
                if (bk.getTitle().toLowerCase().contains(name.toLowerCase())) {foundBooks.add(bk);}
            }
            
            if (foundBooks.size() > 0) {
                try {
                    Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("./LoadBook.fxml")));
                    Scene sc = new Scene(parent);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(sc);
                    stage.show();
                } catch (Exception exc) {System.out.println(exc);}

            }
            else {
                Alert alrt = new Alert(Alert.AlertType.ERROR);
                alrt.setTitle("Book not here!");
                alrt.setContentText("Book not in inventory!");
                alrt.showAndWait();
            }
        }        
    }

    /**
     * Handle exit button by returning student to their menu
     */
    public void exitToMenu(ActionEvent event) {

        try {
            Parent parent = FXMLLoader.load(getClass().getResource("./StudentMenu.fxml"));
            Scene sc = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Student Menu");
            stage.setScene(sc);
            stage.show();

        } catch (Exception exc) {System.out.println(exc);}
    }
}
