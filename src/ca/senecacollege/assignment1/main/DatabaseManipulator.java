package ca.senecacollege.assignment1.main;

import ca.senecacollege.assignment1.admin.Administrator;
import ca.senecacollege.assignment1.librarian.Book;
import ca.senecacollege.assignment1.student.IssueRequest;
import ca.senecacollege.assignment1.librarian.Librarian;
import ca.senecacollege.assignment1.student.Student;

import java.util.ArrayList;
import java.io.FileWriter;
import java.util.List;

import java.sql.*;


/**
 * @authors Michal Kot-Kawula, Jawwad Ahmed Abbasi, Arryell Parris
 */


public class DatabaseManipulator {



    /**
     * Database connection
     */
    public static Connection connectionn;

    /**
     * Status of the user
     */
    public static User alreadyLoggedIn = null;

    /**
     * Statement
     */
    public static Statement statement;

    /**
     * Arraylist of admins
     */
    public static List<Administrator> adminList = new ArrayList<>();

    /**
     * Arraylist of request issues
     */
    public static List<IssueRequest> issueRequests = new ArrayList<>();

    /**
     * Arraylist of students
     */
    public static List<Student> studentList = new ArrayList<>();

    /**
     * Arraylist of librarians
     */
    public static List<Librarian> librarianList = new ArrayList<>();

    /**
     * Arraylist of Books
     */
    public static List<Book> books = new ArrayList<>();

    /**
     * Custom constructor initializes the connection with the database
     */
    public DatabaseManipulator() throws SQLException {
        connectionn = DriverManager.getConnection("jdbc:sqlite:C:/sqlite/db/ass1.db");
    }

    /**
     * Get admin, librarian, student data
     */
    public static void fetchData() throws SQLException {
        DatabaseManipulator.adminList.clear();
        DatabaseManipulator.librarianList.clear();
        DatabaseManipulator.studentList.clear();
        statement = DatabaseManipulator.connectionn.createStatement();

        // Fetch administrator table and import all the data
        ResultSet results = statement.executeQuery("SELECT * FROM administrators");

        while (results.next()) {
            int userID = results.getInt("user_id");
            String username = results.getString("username");
            String password = results.getString("password");
            String name = results.getString("full_name");
            String address = results.getString("address");
            String phoneNum = results.getString("contact_number");
            String email = results.getString("email");

            DatabaseManipulator.adminList.add(new Administrator(name, address, phoneNum,
                    email, username, password, userID));
        }

        // Fetch library table and import all the data
        results = statement.executeQuery("SELECT * FROM librarians");
        while (results.next()) {
            int userID = results.getInt("user_id");
            String username = results.getString("username");
            String password = results.getString("password");
            String name = results.getString("full_name");
            String email = results.getString("email");
            String phoneNum = results.getString("contact_number");
            String address = results.getString("address");
            String qualifications = results.getString("qualifications");
            DatabaseManipulator.librarianList.add(new Librarian(name, address, phoneNum,
                    email, qualifications, username, password, userID));
        }

        // Fetch student table and import all the data
        results = statement.executeQuery("SELECT * FROM students");
        while (results.next()) {
            int userID = results.getInt("user_id");
            String username = results.getString("username");
            String password = results.getString("password");
            String name = results.getString("full_name");
            String email = results.getString("email");
            String phoneNum = results.getString("contact_number");
            String address = results.getString("address");
            String degree = results.getString("degree");
            DatabaseManipulator.studentList.add(new Student(name, address, phoneNum, email, username,
                    password, userID, true, degree));
        }

        // Fetch books table and import all data
        results = statement.executeQuery("SELECT * FROM books");
        while (results.next()) {
            int bookID = results.getInt("book_id");
            String bookTitle = results.getString("title");
            String author = results.getString("author");
            int year = results.getInt("year");
            int number_of_copies = results.getInt("number_of_copies");

            List<Integer> borrowed = new ArrayList<>();
            Statement temp_statement = DatabaseManipulator.connectionn.createStatement();
            ResultSet books_borrowed_info = temp_statement.executeQuery("SELECT * FROM books_borrowed WHERE book_id =" + bookID);
            while (books_borrowed_info.next()) {
                int studentID = books_borrowed_info.getInt("student_id");
                borrowed.add(studentID);
            }
            DatabaseManipulator.books.add(new Book(bookID, bookTitle, author, year,
                    number_of_copies, borrowed));
        }

        results = statement.executeQuery("SELECT * FROM tickets");

        while (results.next()) {
            for (Book book : books) {
                int bookID = results.getInt("book_id");
                int student_id = results.getInt("student_id");
                if (book.getBookid() == bookID) {
                    issueRequests.add(new IssueRequest(book, student_id, 0));
                }
            }
        }

        Statement temp_statement = DatabaseManipulator.connectionn.createStatement();
        ResultSet books_borrowed_info = temp_statement.executeQuery("SELECT * FROM books_borrowed");

        while (books_borrowed_info.next()) {
            for (Book book : books) {

                int bookID = books_borrowed_info.getInt("book_id");
                int studentID = books_borrowed_info.getInt("student_id");
                if (book.getBookid() == bookID) {
                    IssueRequest borrowed = new IssueRequest(book, studentID, 0);
                    borrowed.setIssued();
                    issueRequests.add(borrowed);
                }
            }
        }
    }

    /**
     * Insert the librarian record into the database
     */
    public static boolean saveLibrarianUser(Librarian librarian) throws SQLException {
        statement = DatabaseManipulator.connectionn.createStatement();
        try {
            statement.execute(
                    "INSERT INTO librarians(username, password, full_name, email, contact_number,address, qualifications)" + " VALUES(" + "\""
                            + librarian.getUsername() + "\"" + "," + "\""
                            + librarian.getPassword() + "\"" + "," + "\""
                            + librarian.getUNmae() + "\"" + "," + "\""
                            + librarian.getEmail() + "\"" + "," + "\""
                            + librarian.getContactnumber() + "\"" + "," + "\""
                            + librarian.getAddress() + "\"" + "," + "\""
                            + librarian.getQualification() + "\"" + ")");
        } catch (SQLException exc) {
            System.out.println("Insertion failed: " + exc);
            return false;
        }
        return true;
    }

    /**
     * Delete the library record from the database
     */
    public static boolean deleteLibrarianUser(Librarian librarian) throws SQLException {
        DatabaseManipulator.statement = DatabaseManipulator.connectionn.createStatement();

        try {
            DatabaseManipulator.statement.execute("DELETE FROM librarians WHERE username=" + "\"" + librarian.getUsername() + "\"");
            librarianList.remove(librarian);
        } catch (SQLException exc) {
            System.out.println("Deletion failed: " + exc);
            return false;
        }

        return true;
    }


    /**
     * This function helps us to generate a txt file containing the information of books
     */
    public static boolean saveBookReport(String filename) {
        try {
            FileWriter writer = new FileWriter(filename);
            writer.write("--------------------\n" +
                    "Book Report\n--------------------\n" +
                    "total records: " + DatabaseManipulator.books.size() + "\n\n");
            for (Book book : books) {
                writer.write("bookID:" + book.getBookid() + "\n" + "Title:"
                        + book.getTitle() + "\n" + "Author:"
                        + book.getAuthor() + "\n" + "Year:"
                        + book.getYear() + "\n" + "Borrowed:"
                        + book.getBorrowerid().size() + "number_of_copies\n\n"
                );
            }
            writer.close();
        } catch (Exception exc) {
            System.out.println("Failed to generate book file:" + exc);
            return false;
        }
        return true;
    }

    /**
     * This function help us to store and insert the data of a book into the database
     */
    public static boolean saveBook(Book book) throws SQLException {
        statement = DatabaseManipulator.connectionn.createStatement();
        try {
            statement.execute(
                    "INSERT INTO books(title, author, year, number_of_copies)" + " VALUES(" + "\""
                            + book.getTitle() + "\",\""
                            + book.getAuthor() + "\",\""
                            + book.getYear() + "\",\""
                            + book.getNumberOfCopies() + "\")");
        } catch (SQLException exc) {
            System.out.println("Failed to insert book data: " + exc);
            return false;
        }
        return true;
    }

    /**
     * Creates librarian text file
     */
    public static boolean saveLibrarianReport(String filename) {

        try {
            FileWriter writer = new FileWriter(filename);
            writer.write("--------------------\n" +
                    "Librarian Report\n--------------------\n" +
                    "total records: "
                    + DatabaseManipulator.librarianList.size() + "\n\n");
            for (Librarian librariann : librarianList) {
                writer.write("Name:"
                        + librariann.getUNmae() + "\n" + "contact_number:"
                        + librariann.getContactnumber() + "\n" + "Email:"
                        + librariann.getEmail() + "\n" + "Address:"
                        + librariann.getAddress() + "\n" + "Joining Date:"
                        + librariann.getJoiningDate() + "\n" + "Qualification:"
                        + librariann.getQualification() + "\n" + "username:"
                        + librariann.getUsername() + "\n\n"
                );
            }
            writer.close();
        } catch (Exception exc) {
            System.out.println("Failed to generate librarian file:" + exc);
            return false;
        }
        return true;
    }

    /**
     * This function creates a queue of people waiting for a book
     */
    public static void createWaitingTicket(Book book, int studID) {
        try {
            statement.execute(
                    "INSERT INTO tickets(book_id, student_id) " +
                            " VALUES (" + "\"" + book.getBookid() + "\",\""
                            + studID + "\")");
        } catch (Exception exc) {
            System.out.println("Couldn't execute this function: " + exc);
        }
    }

    /**
     * This function removes user from the queue
     */
    public static void removeWaitingTicket(int bookID, int studID) {
        try {
            statement.execute(
                    "DELETE FROM tickets " +
                            "WHERE book_id = " + bookID +
                            " AND student_id = " + studID);
        } catch (Exception exc) {
            System.out.println("Couldn't execute this function: " + exc);
        }
    }

    /**
     * This function help us to store and insert the data of number of copies of a book
     */
    public static boolean updateNumberOfCopies(Book book) throws SQLException {
        statement = DatabaseManipulator.connectionn.createStatement();
        try {

            statement.execute(
                    "UPDATE books SET number_of_copies = "
                            + book.getNumberOfCopies() + " WHERE book_id = "
                            + book.getBookid());
        } catch (SQLException exc) {
            System.out.println("Failed to update number of copies of a book: " + exc);
            return false;
        }
        return true;
    }

    /**
     * This function helps us to issue the book to who is borrowing it
     */
    public static boolean issueBook(Book book, int studID) {

        try {

            statement.execute(
                    "UPDATE books " +
                            "SET number_of_copies = "
                            + book.getNumberOfCopies() +
                            " WHERE book_id = "
                            + book.getBookid());

            statement.execute("INSERT INTO books_borrowed(book_id, student_id) VALUES ("
                    + book.getBookid() + ","
                    + studID + ")");

            removeWaitingTicket(book.getBookid(), studID);
            return true;

        } catch (Exception exc) {
            System.out.println("Couldn't issue the book: " + book.getBookid());
            return false;
        }
    }

    /**
     * This function help us return the book. It deletes the book id from the person who borrowed it
     */
    public static void returnBook(Book book, int studentID) throws SQLException {
        statement.execute("DELETE FROM books_borrowed WHERE book_id = "
                + book.getBookid() + " AND student_id = "
                + studentID);
        updateNumberOfCopies(book);
    }

}
