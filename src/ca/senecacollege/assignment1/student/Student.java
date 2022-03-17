package ca.senecacollege.assignment1.student;

import ca.senecacollege.assignment1.main.User;


/**
 * @authors Michal Kot-Kawula, Jawwad Ahmed Abbasi, Arryell Parris
 */

/**
 * Student class to set and get Student information
 */
public class Student extends User {

    private boolean isCurrent;
    private String degree;
    private String uName;
    private String password;

    /**
     * Student custom constructor.
     */
    public Student(String name, String address, String phoneNumber, String email, String username, String password,int studID, boolean isCurrent, String degree) {
        super(name, address, phoneNumber, email, studID);
        setCurrent(isCurrent);
        setDegree(degree);
        setuName(username);
        setPassword(password);
    }
    
    /**
     * Get student password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Get student username
     */
    public String getUsername() {
        return this.uName;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}