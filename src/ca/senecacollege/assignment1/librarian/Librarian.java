package ca.senecacollege.assignment1.librarian;

import ca.senecacollege.assignment1.main.User;

import java.io.Serializable;
import java.io.Serial;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;


/**
 * @authors Michal Kot-Kawula, Jawwad Ahmed Abbasi, Arryell Parris
 */


public class Librarian extends User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Store the qualification
     */
    private String qualification;

    /**
     * The date when hired librarian
     */
    private String joiningDate;

    /**
     * username for login
     */
    private String username;

    /**
     * password for login
     */
    private String password;

    /**
     * Constructor to initialize the librarin object
     * @param fName full name of the librarin
     * @param address where the librarinn lives
     * @param contact_number how to contact the librarin
     * @param email how to contact the librarian
     * @param qualification her/his qualifications
     * @param username credentials for login
     * @param password credentitials for login
     * @param user_id the user id
     */
    public Librarian(String fName, String address, String contact_number, String email, String qualification, String username, String password, int user_id) {
        super(fName, address, contact_number, email, user_id);
        setQualification(qualification);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        this.joiningDate = dtf.format(now);
        setUsername(username);
        setPassword(password);
    }

    /**
     * Get the username
     * @return username string
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username
     * @param username username string
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the password
     * @return password password string
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password
     * @param password password string
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the qualification
     * @return qualification string
     */
    public String getQualification() {
        return qualification;
    }

    /**
     * Set the qualification
     * @param qualification string
     */
    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    /**
     * Get the date the librian was hired
     * @return string
     */
    public String getJoiningDate() {
        return joiningDate;
    }

    /**
     * Set the date the librarin was hired
     * @param joiningDate string
     */
    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }
}