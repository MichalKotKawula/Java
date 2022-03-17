package ca.senecacollege.assignment1.student;

import ca.senecacollege.assignment1.librarian.Book;
import ca.senecacollege.assignment1.main.DatabaseManipulator;

import java.sql.SQLException;
import javafx.scene.control.Alert;


/**
 * @authors Michal Kot-Kawula, Jawwad Ahmed Abbasi, Arryell Parris
 */


/**
 * Class which handles logic being student requesting a book to be issued to them
 */
public class IssueRequest {

    private int reqID;
    private Book book;
    private int student_id;
    private boolean hasBeenIssued;

    /**
     * Custom constructor for class set: student and request id's for book
     */
    public IssueRequest(Book book, int student_id, int reqID) {
        if (DatabaseManipulator.issueRequests.size() > 0) {
            this.reqID = DatabaseManipulator.issueRequests.get(DatabaseManipulator.issueRequests.size() - 1).getIssueRequestID() + 1;
        } else {
            this.reqID = reqID;
        }
        this.book = book;
        this.student_id = student_id;
        this.hasBeenIssued = false;

    }

    /**
     * Function to approve return of a book by student
     */
    public boolean approveBookReturn() throws SQLException {
        if (hasBeenIssued) {
            book.deleteBorrowerid(student_id);
            book.addCopy();
            DatabaseManipulator.returnBook(book, student_id);
            DatabaseManipulator.issueRequests.remove(this);
            return true;
        }
        return false;
    }

    /**
     * Function to approve book by student
     */
    public boolean approveIssue() {
        if (!hasBeenIssued) {
            book.insertBorrowerid(student_id);
            book.issueCopy();
            if (DatabaseManipulator.issueBook(book, student_id)) {
                hasBeenIssued = true;
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error occurred");
                alert.setHeaderText("Unable to approve issued");
                alert.setContentText("Database error, cannot issue book.");
                alert.showAndWait();
            }
        }
        return hasBeenIssued;
    }


    /**
     * Get request id
     */
    public int getIssueRequestID() {
        return  this.reqID;
    }

    /**
     * Get student id
     */
    public int getStudentid() {
        return  this.student_id;
    }

    /**
     * Get requested book
     */
    public Book getBook() {
        return  this.book;
    }
   
    /**
     * Set request to true or false
     */
    public void setIssued() {
    	 this.hasBeenIssued = ! this.hasBeenIssued;
    }

    /**
     * Check if request has been approved
     */
    public boolean isIssued() {
        return  this.hasBeenIssued;
    }
   
    /**
     * Set request id
     */
    public void setIssueRequestID(int id) {
        this.reqID = id;
    }
    
    /**
     * Check if book id and student id correspond 
     */
    public boolean isEqual(int bookID, int student_id) {
        return book.getBookid() == bookID && student_id == this.student_id;
    }
}
