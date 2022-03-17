package ca.senecacollege.assignment1.admin;

import ca.senecacollege.assignment1.main.User;

/**
 * Administrator class to set administrator info
 */

/**
 * @authors Michal Kot-Kawula, Jawwad Ahmed Abbasi, Arryell Parris
 */


public class Administrator extends User {
   
    private String uName;
    private String password;

    /**
     * Set administrator information such as name, address, contact_number, email, username, password, user_id
     */
    public Administrator(String name, String address, String contact_number, String email, String username, String password, int user_id) {
        super(name, address, contact_number, email, user_id);
        setUsername(username);
        setPassword(password);
    }

    /**
     * Set username of admin
     */
    public void setUsername(String username) {
        this.uName = username;
    }

    /**
     * Set password of admin
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get username of admin
     */
    public String getUsername() {
        return uName;
    }

    /**
     * Get password of admin
     */
    public String getPassword() {
        return password;
    }
}
