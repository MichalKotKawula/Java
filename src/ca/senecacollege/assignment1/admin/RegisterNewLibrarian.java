package ca.senecacollege.assignment1.admin;

import ca.senecacollege.assignment1.librarian.Librarian;
import ca.senecacollege.assignment1.main.DatabaseManipulator;
import java.util.Objects;
import java.net.URL;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;

/**
 * @authors Michal Kot-Kawula, Jawwad Ahmed Abbasi, Arryell Parris
 */

/**
 * Fields for Fmxl to register a new Librarian
 */
public class RegisterNewLibrarian implements Initializable {

	 @FXML
	private TextField username;
	
    @FXML
    private TextField name;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private TextField qualification;

    @FXML
    private TextField phone;

    @FXML
    private TextArea address;
     

    private int user_id = 0;

    /**
     * Initialize librarian ID
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	if (DatabaseManipulator.librarianList.size() > 0) {
    		this.user_id = DatabaseManipulator.librarianList.get(DatabaseManipulator.librarianList.size() - 1).getUserid() + 1;
    	}
    	else {this.user_id = 0;}
    }

    /**
     * Sets values of librarian from input fields
     */
    public void regLibrarian(ActionEvent event) throws SQLException, IOException {
        Alert alrt = new Alert(AlertType.INFORMATION);
        if (name.getText().length() == 0) {
            alrt.setHeaderText("Error encountered with name!");
            alrt.showAndWait();
            return;
        }

        if (email.getText().length() == 0) {
            alrt.setHeaderText("Error encountered with email!");
            alrt.showAndWait();
            return;
        }

        if (phone.getText().length() == 0) {
            alrt.setHeaderText("Error encountered with phone number!");
            alrt.showAndWait();
            return;
        }

        if (address.getText().length() == 0) {
            alrt.setHeaderText("Error encountered with address!");
            alrt.showAndWait();
            return;
        }
        if (username.getText().length() == 0) {
            alrt.setHeaderText("Error encountered with username!");
            alrt.showAndWait();
            return;
        }
        if (qualification.getText().length() == 0) {
            alrt.setHeaderText("Error encountered with qualification!");
            alrt.showAndWait();
            return;
        }
        if (password.getText().length() == 0) {
            alrt.setHeaderText("Error encountered with Password!");
            alrt.showAndWait();
            return;
        }

        for (Librarian librariann : DatabaseManipulator.librarianList) {
            if (librariann.getUsername().equalsIgnoreCase(username.getText())) {
                alrt.setContentText("Error encountered! UserName already taken!");
                alrt.showAndWait();
                return;
            }
        }

        // create new Librarian 
        Librarian newLibrarian = new Librarian(name.getText(), address.getText(), phone.getText(), email.getText(),
                qualification.getText(), username.getText(), password.getText(), user_id++);

        //add to Librarian records
        DatabaseManipulator.librarianList.add(newLibrarian);

        //save to database
        if (!DatabaseManipulator.saveLibrarianUser(newLibrarian)) {
            Alert error = new Alert(AlertType.ERROR);
            alrt.setHeaderText("Librarian was not saved!");
            alrt.showAndWait();
        }
        else {
            alrt.setHeaderText("Success! Librarian record added!");
            alrt.showAndWait();
            Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdministratorMenu.fxml")));
            Scene sc = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Library Management");
            stage.setScene(sc);
            stage.show();
        }
    }

    /**
     * handle Exit back to Administrator menu
     */
    public void exitToAdminMenu(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdministratorMenu.fxml")));
        Scene sc = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Administrator Menu");
        stage.setScene(sc);
        stage.show();
    }


}