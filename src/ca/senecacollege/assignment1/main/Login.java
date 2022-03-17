package ca.senecacollege.assignment1.main;

import ca.senecacollege.assignment1.student.Student;
import ca.senecacollege.assignment1.librarian.Librarian;
import ca.senecacollege.assignment1.admin.Administrator;

import java.io.IOException;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * @authors Michal Kot-Kawula, Jawwad Ahmed Abbasi, Arryell Parris
 */


public class Login implements Initializable {

    @FXML
    private TextField idField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ComboBox<String> type;

    public void initialize(java.net.URL location, ResourceBundle resources) {
        type.getItems().addAll(
                "Administration",
                "Librarian",
                "Student"
        );
        type.getSelectionModel().select("Administration");

    }

    /**
     * Exit program
     */
    public void ExitPrg() {
        System.exit(0);
    }

    /**
     * Handle login event, check for username, password, and appropriate access
     */
    public void login(ActionEvent event) throws IOException {
        String username = idField.getText();
        String password = passwordField.getText();


        boolean issueWithLog = true;
        if (type.getValue().equalsIgnoreCase("Administration")) {

            for (Administrator admin : DatabaseManipulator.adminList) {

                if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                    DatabaseManipulator.alreadyLoggedIn = admin;
                    Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../admin/AdministratorMenu.fxml")));
                    Scene sc = new Scene(parent);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setTitle("Administrator Menu");
                    stage.setScene(sc);
                    stage.show();
                    return;
                }
            }
        } else if (type.getValue().equalsIgnoreCase("Librarian")) {
            if (DatabaseManipulator.librarianList.size() > 0) {
                for (Librarian lib : DatabaseManipulator.librarianList) {
                    if (lib.getUsername().equals(username) && lib.getPassword().equals(password)) {
                        DatabaseManipulator.alreadyLoggedIn = lib;
                        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../librarian/LibrarianMenu.fxml")));
                        Scene sc = new Scene(parent);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setTitle("Librarian Menu");
                        stage.setScene(sc);
                        stage.show();
                        return;
                    }
                }
            } else {
                issueWithLog = false;
                Alert alrt = new Alert(AlertType.ERROR);
                alrt.setTitle("SignIn Error!");
                alrt.setHeaderText("No librarians found!");
                alrt.setContentText("Contact admin to get issue resolved");
                alrt.showAndWait();
            }
        } else if (type.getValue().equalsIgnoreCase("Student")) {
            if (DatabaseManipulator.studentList.size() > 0) {
                for (Student student : DatabaseManipulator.studentList) {
                    if (student.getUsername().equals(username) && student.getPassword().equals(password)) {
                        DatabaseManipulator.alreadyLoggedIn = student;
                        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../student/StudentMenu.fxml")));
                        Scene sc = new Scene(parent);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setTitle("Administrator Menu");
                        stage.setScene(sc);
                        stage.show();
                        return;
                    }
                }
            } else {
                issueWithLog = false;
                Alert alrt = new Alert(AlertType.ERROR);
                alrt.setTitle("SignIn Error!");
                alrt.setHeaderText("No students found!");
                alrt.setContentText("Contact admin to get issue resolved");
                alrt.showAndWait();
            }
        } else {System.out.println("Invalid Details!");}

        if (issueWithLog) {
            Alert alrt = new Alert(AlertType.ERROR);
            alrt.setTitle("SignIn Error!");
            alrt.setHeaderText("Issue with Logging In!");
            alrt.setContentText("Username or password wrong!");
            alrt.showAndWait();
        }
        return;
    }
}
