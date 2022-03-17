package ca.senecacollege.assignment1.librarian;

import java.util.ArrayList;
import java.util.List;

/**
 * @authors Michal Kot-Kawula, Jawwad Ahmed Abbasi, Arryell Parris
 */

public class Book {

    /**
     * Unique Book Id
     */
    private int bookID;

    /**
     * The title of the book
     */
    private String title;

    /**
     * The name of the author
     */
    private String author;

    /**
     * The year the book was published
     */
    private int year;

    /**
     * The list of all ids of the user who borrowed the book
     */
    private List<Integer> borrow_user_id = new ArrayList<Integer>();;

    /**
     * The list of all people in the waiting list
     */
    private List<Integer> waiting_list = new ArrayList<Integer>();

    /**
     * The number of numOfCopies of the book
     */
    private int numOfCopies;

    /**
     * This is book contructor that initiailze the object
     * @param bookID
     * @param title
     * @param author
     * @param year
     * @param numOfCopies
     * @param borrow_user_id
     */
    public Book(int bookID, String title, String author, int year, int numOfCopies, List<Integer> borrow_user_id) {
        setBook_id(bookID);
        setTitle(title);
        setAuthor(author);
        setYear(year);
        setBorrow_user_id(borrow_user_id);
        setNumber_of_copies(numOfCopies);
    }

    /**
     * sets bookID
     * @param bookID int
     */
    public void setBook_id(int bookID) {
        this.bookID = bookID;
    }

    /**
     * sets title
     * @param title string
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * sets the author
     * @param author string
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * sets the year
     * @param year int
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * sets the borrow user id
     * @param borrow_user_id list<integer>
     */
    public void setBorrow_user_id(List<Integer> borrow_user_id) {
        this.borrow_user_id = borrow_user_id;
    }

    /**
     * sets the waiting list
     * @param waiting_list list of integer
     */
    public void setWaiting_list(List<Integer> waiting_list) {
        this.waiting_list = waiting_list;
    }

    /**
     * sets the number of copies
     * @param numOfCopies int
     */
    public void setNumber_of_copies(int numOfCopies) {
        this.numOfCopies = numOfCopies;
    }

    /**
     * Get the book id
     * @return id of the book
     */
    public int getBookid() {
        return bookID;
    }

    /**
     * add the user id of the one who borrowed the book to list
     * @param userID id of the user
     */
    public void insertBorrowerid(int userID) {
        this.borrow_user_id.add(userID);
    }

    /**
     * now remove the id of the one who returned the book
     * @param userID id of the user
     */
    public void deleteBorrowerid(int userID) {
        this.borrow_user_id.remove((Integer) userID);
    }

    /**
     * get all the id of the people who borrowed the book
     * @return list of arraylist in interger
     */
    public List<Integer> getBorrowerid() {
        return borrow_user_id;
    }

    /**
     * get the number of numOfCopies of a book
     * @return int of numOfCopies
     */
    public int getNumberOfCopies() {
        return numOfCopies;
    }

    /**
     * add the user to waiting list
     * @param student_id int
     */
    public void appendWaitingList(int student_id) {
        waiting_list.add(student_id);
    }

    /**
     * if book has numOfCopies than increment a copy by one.
     */
    public void addCopy() {
        this.numOfCopies++;
    }

    /**
     * if issued a copy then decrement by one.
     */
    public void issueCopy() {
    	this.numOfCopies--;
    }

    /**
     * check from the list that user is in the waiting list
     * @param student_id id of the student int
     * @return true or false if user is in or not
     */
    public boolean isWaiting(int student_id)
    {
        return waiting_list.contains(student_id);
    }

    /**
     * now remove the user from the waiting list
     * @param student_id id of the student
     */
    public void removeFromWaitingList(int student_id) {
        waiting_list.remove(student_id);
    }

    /**
     * get the title of the book
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * fetch the name of the author of the book
     * @return author name
     */
    public String getAuthor() {
        return author;
    }

    /**
     * fetch the year when the book was published
     * @return year published
     */
    public int getYear() {
        return year;
    }

    /**
     * check when if the book has been borrowed or not
     * @param student_id id of the user
     * @return true or false
     */
    public boolean isBorrowed(int student_id) {
        return borrow_user_id.contains(student_id);
    }
}
