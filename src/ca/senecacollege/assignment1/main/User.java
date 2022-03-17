package ca.senecacollege.assignment1.main;

import java.io.Serializable;


/**
 * @authors Michal Kot-Kawula, Jawwad Ahmed Abbasi, Arryell Parris
 */

public class User implements Serializable {

    /**
     * Name of the user
     */
    private String name;

    /**
     * Where the user lives
     */
    private String address;

    /**
     * Communication method with the user
     */
    private String contact_number;

    /**
     * Communication method with the user
     */
    private String email;

    /**
     * Unique Id of the user
     */
    private int user_id;

    /**
     * The constructor to initialize the user object

     */
    public User(String name, String address, String contact_number, String email, int user_id) {
        super();
        setUNmae(name);
        setAddress(address);
        setContactnumber(contact_number);
        setEmail(email);
        setUserid(user_id);
    }

    /**
     * Get the id of user
     */
    public int getUserid() {
        return this.user_id;
    }

    /**
     * Get name of the user
     */
    public String getUNmae() {
        return name;
    }

    /**
     * Set name of the user
     */
    public void setUNmae(String name) {
        this.name = name;
    }

    /**
     * Set the address of the user
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Set the contact info of the user
     */
    public void setContactnumber(String contact_number) {
        this.contact_number = contact_number;
    }

    /**
     * Set the email of user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * set the unique id of user
     */
    public void setUserid(int user_id) {
        this.user_id = user_id;
    }

    /**
     * Get the address of the user
     */
    public String getAddress() {
        return address;
    }

    /**
     * Get the contact number of user
     */
    public String getContactnumber() {
        return contact_number;
    }

    /**
     * Get the email of user
     */
    public String getEmail() {
        return email;
    }

}
