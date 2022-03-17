package ca.senecacollege.assignment1.admin;
import ca.senecacollege.assignment1.librarian.Librarian;
import ca.senecacollege.assignment1.main.DatabaseManipulator;

import java.io.IOException;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert;

import javafx.stage.Stage;

/**
 * @authors Michal Kot-Kawula, Jawwad Ahmed Abbasi, Arryell Parris
 */

/**
 * Generates a report of all the Librarians in the system with ability to export to file.
 */
public class LibrarianReport {
    @FXML
    private TextArea outputArea;

    @FXML
    public void initialize() {
        StringBuilder output = new StringBuilder("--------------------\n" +
                "Librarian Report\n--------------------\n" +
                "total records: " + DatabaseManipulator.librarianList.size() + "\n\n");
        for (Librarian lib : DatabaseManipulator.librarianList) {
            output.append("Name: ").append(lib.getUNmae()).append("\n").append("Phone Number: ").append(lib.getContactnumber()).append("\n").append("Email: ").append(lib.getEmail()).append("\n").append("Address: ").append(lib.getAddress()).append("\n").append("Joining Date: ").append(lib.getJoiningDate()).append("\n").append("Qualification: ").append(lib.getQualification()).append("\n\n\n");
        }
        outputArea.setText(output.toString());
    }
    
    /**
     * Exit admin to their menu.
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
     * Export report data to text file
     */
    public void exportToFile() {
        DatabaseManipulator.saveLibrarianReport("exportedLibrarians.txt");
        Alert alrt = new Alert(Alert.AlertType.INFORMATION);
        alrt.setHeaderText("Report Saved");
        alrt.setContentText("Librarian report saved to text file, total records: " + DatabaseManipulator.librarianList.size());
        alrt.showAndWait();
    }
}

