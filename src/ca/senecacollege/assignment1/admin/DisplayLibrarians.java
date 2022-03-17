package ca.senecacollege.assignment1.admin;

import ca.senecacollege.assignment1.librarian.Librarian;
import ca.senecacollege.assignment1.main.DatabaseManipulator;

import java.util.Objects;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.net.URL;

import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

/**
 * @authors Michal Kot-Kawula, Jawwad Ahmed Abbasi, Arryell Parris
 */

/**
 * Allows to select a Librarian to view and possibility to delete them
 */
public class DisplayLibrarians implements Initializable {
    @FXML
    private TextArea details;

    @FXML
    private ComboBox<String> users;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        for (Librarian librariann : DatabaseManipulator.librarianList) {
            users.getItems().add(librariann.getUsername());
        }
    }

    /**
     * Get values and fetch database for librarian information.
     */
    public void findLibrarian() {
    	if (users.getValue() != null) {
    		String username = users.getValue();
            for (Librarian librariann : DatabaseManipulator.librarianList) {
                if (librariann.getUsername().equals(username)) {
         
                    details.setText("Name: " + librariann.getUNmae() + "\n" +
                            "Phone: " + librariann.getContactnumber() + "\n" +
                            "Email: " + librariann.getEmail() + "\n" +
                            "Address: " + librariann.getAddress() + "\n" +
                            "Joining Date: " + librariann.getJoiningDate() + "\n" +
                            "Qualification: " + librariann.getQualification() + "\n"
                    );
                }
            }	
    	}
    }

    /**
     * Exit admin to their menu
     */
    public void exitToAdminMenu(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdministratorMenu.fxml")));
        Scene sc = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(sc);
        stage.setResizable(false);
        stage.show();
    }
    
    /**
     * Remove selected librarian from dropdown menu
     */
    public void deleteLibrarian() throws SQLException {
        String username = users.getValue().toString();
        for (Librarian librariann : DatabaseManipulator.librarianList) {
            if (librariann.getUsername().equals(username)) {
                DatabaseManipulator.deleteLibrarianUser(librariann);
                Alert alrt = new Alert(Alert.AlertType.INFORMATION);
                alrt.setHeaderText("Deleted!");
                alrt.setContentText("Librarian user deleted");
                alrt.showAndWait();
                break;
            }
        }
        users.getItems().clear();
        for (Librarian librariann : DatabaseManipulator.librarianList) {
            users.getItems().add(librariann.getUsername());
        }
        details.setText("");
    }
}
