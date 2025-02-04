package DataAccessObjects;

import DataAccessObjects.Interfaces.LoanUserBookDaoInterface;
import DataTransferObjects.Book;
import DataTransferObjects.Loan;
import DataTransferObjects.User;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import Program.Program;

/**
 * This class will interact with the Loan Table in db
 * @author Arnas M.
 */
public class LoanUserBookDao extends Dao implements LoanUserBookDaoInterface {

    private BookDao bookDao = new BookDao("dundalk_library");

    /**
     * Constructor opens a connection to the database
     * @param databaseName name of the database to be used
     */
    public LoanUserBookDao(String databaseName) {
        super(databaseName);
    }

    /**
     * This method gets all currently active loans for a particular user
     * @param user1 the user object for which we want to get loans
     * @return ArrayList of loan objects if none found empty ArrayList is returned
     */
    @Override
    public ArrayList<Loan> allLoansByUserId(User user1) {
        int id = user1.getId();
        Connection con = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList<Loan> loans = new ArrayList<>();
        Book book;

        try {
            con = getConnection();

            ps = con.prepareStatement("SELECT * FROM loan WHERE loan_user_id = ? AND loan_returned IS NULL");
            ps.setInt(1, id);

            rs = ps.executeQuery();

            while (rs.next()) {
                book = getBookById(rs, con);

                loans.add(new Loan(
                        rs.getInt("loan_id"),
                        user1,
                        book,
                        rs.getString("loan_started"),
                        rs.getString("loan_ends"),
                        rs.getString("loan_returned")
                ));
            }

        } catch (SQLException e) {
            System.err.println(Program.globalMessages.getString("GenericMessage") + e.getMessage());
        } finally {
            closeConnections(con, ps, rs);
        }
        return loans;
    }

    /**
     * Gets all the loans since user joined library
     * @param user1 the user object for which we want to get loans
     * @return ArrayList of loan objects if none found empty ArrayList is returned
     */
    @Override
    public ArrayList<Loan> allLoansSinceJoining(User user1) {
        int id = user1.getId();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList<Loan> loans = new ArrayList<>();
        Book book;

        try {
            con = getConnection();

            ps = con.prepareStatement("SELECT * FROM loan WHERE loan_user_id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                book = getBookById(rs, con);

                loans.add(new Loan(
                        rs.getInt("loan_id"),
                        user1,
                        book,
                        rs.getString("loan_started"),
                        rs.getString("loan_ends"),
                        rs.getString("loan_returned")
                ));
            }

        } catch (SQLException e) {
            System.err.println(Program.globalMessages.getString("GenericMessage") + e.getMessage());
        } finally {
            closeConnections(con, ps, rs);
        }
        return loans;
    }

    /**
     * This method loans a book to a particular user
     * @param name The title of the book to be loaned
     * @param loanDays How many days the user wants the loan to last
     * @param user The user who is loaning a book
     * @return This method returns a particular number for each error encountered "-2 = Book was attempted to be loaned for longer than 7 days", "-1 = The book was already loaned by the user", "0 = The book in question is out of stock", "2 = The quantity of the book could not be reduced after the loan succeeded", "1 = The loan was successful", "-3 = The user has maximum loans available for him at any given time", "-4 = The user can not loan a book that does not exist"
     */
    @Override
    public int loanBook(String name, int loanDays, User user) {
        //Check if the user dose not have more than or 5 loans
        if((allLoansByUserId(user).size()) >= 5) return -3;

        //All the date stuff...
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        java.util.Date currentDate = new java.util.Date();
        String startDate = dateFormat.format(currentDate);

        // convert date to calendar
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);

        // manipulate date
        c.add(Calendar.DATE, loanDays); //same with c.add(Calendar.DAY_OF_MONTH, 1);

        // convert calendar to date
        Date currentDatePlusOne = c.getTime();

        String endDate = dateFormat.format(currentDatePlusOne);
        //********************************************************************//

        Connection con = null;
        PreparedStatement ps = null;
        int rs;

        Book book;

        int result = -3;

        try {
            con = getConnection();
            ps = con.prepareStatement("INSERT INTO loan (`loan_id`, `loan_user_id`, `loan_book_id`, `loan_started`, `loan_ends`, `loan_returned`) VALUES (null,?,?,?,?,null)");
            if((book = getBookByName(name, con)) == null) return -4;

            ps.setInt(1, user.getId());
            ps.setInt(2, book.getBook_id());
            ps.setString(3, startDate);
            ps.setString(4, endDate);

            if (loanDays > 0 && loanDays <= 7) {
                if (!checkIfBookAlreadyLoaned(book.getBook_id(), user.getId())) {
                    if (book.getQuantityInStock() > 0) {
                        rs = ps.executeUpdate();
                        if (rs > 0) {
                            if (bookDao.removeCopies(book.getBook_id(), 1)) result = 1; //Book has been loaned
                            else result = 2; //Book quantity could not be reduced
                        } else System.err.println(Program.globalMessages.getString("LoanUserBookDao_SQL_ResultSet"));
                    } else result = 0; //The book is out of stock
                } else result = -1; //The book is already loaned by that user
            } else result = -2; //Books cant be loaned for longer than 7 days


        } catch (SQLException ex) {
            System.err.println(Program.globalMessages.getString("GenericMessage") + ex.getMessage());
            ex.printStackTrace();
        } finally {
            closeConnections(con, ps, null);
        }
        return result;
    }

    /**
     * This method returns a book loaned by a particular user
     * @param name The name of the book to be returned
     * @param user The user who is returning the book
     * @return True is returned if successful False if not
     */
    @Override
    public boolean returnBook(String name, User user) {
        Connection con;
        PreparedStatement ps;

        Book book;

        int rs;
        boolean result = false;

        try {
            con = getConnection();
            if((book = getBookByName(name, con)) == null) return false;
            ps = con.prepareStatement("UPDATE loan SET loan_returned = current_timestamp() WHERE loan_book_id = ? and loan_user_id = ?");
            ps.setInt(1, book.getBook_id());
            ps.setInt(2, user.getId());

            //Make sure that the book is actually loaned before returning it
            if (checkIfBookAlreadyLoaned(book.getBook_id(), user.getId())) {
                rs = ps.executeUpdate();
                if (rs > 0) if (bookDao.addCopies(book.getBook_id(), 1)) result = true;
            }


        } catch (SQLException ex) {
            System.err.println(Program.globalMessages.getString("GenericMessage") + ex.getMessage());
            ex.printStackTrace();
        }

        return result;
    }

    /**
     * This method queries the database and gets a book by its id
     * @param rsLoan this is the ResultSet object from which we extract book_id from the loan table to be matched in book table
     * @param con Connection object to connect to the database and run queries
     * @return A book object if one is found if not found the object will be assigned a null value
     * @throws SQLException if an an error occurs with query
     */
    private Book getBookById(ResultSet rsLoan, Connection con) throws SQLException {
        PreparedStatement ps;
        ResultSet rs;

        Book book;

        ps = con.prepareStatement("SELECT * FROM book WHERE book_id = ?");
        ps.setInt(1, rsLoan.getInt("loan_book_id"));
        rs = ps.executeQuery();

        if (rs.isBeforeFirst()) {
            rs.next();
            book = new Book(
                    rs.getInt("book_id"),
                    rs.getString("book_name"),
                    rs.getString("book_isbn"),
                    rs.getString("book_edition"),
                    rs.getString("book_description"),
                    rs.getString("author"),
                    rs.getString("publisher"),
                    rs.getInt("quantityInStock")
            );
        } else book = null;
        closeRsPs(ps, rs);
        return book;
    }

    /**
     * This method queries the database and gets a book by its name
     * @param name the name with which we want to find a book from table
     * @param con Connection object to connect to the database and run queries
     * @return a book object if one is found if not found the object will be assigned a null value
     * @throws SQLException if an error occurs with query
     */
    private Book getBookByName(String name, Connection con) throws SQLException {
        PreparedStatement ps;
        ResultSet rs;

        Book book;

        ps = con.prepareStatement("SELECT * FROM book WHERE book_name = ?");
        ps.setString(1, name);
        rs = ps.executeQuery();

        if (rs.isBeforeFirst()) {
            rs.next();
            book = new Book(
                    rs.getInt("book_id"),
                    rs.getString("book_name"),
                    rs.getString("book_isbn"),
                    rs.getString("book_edition"),
                    rs.getString("book_description"),
                    rs.getString("author"),
                    rs.getString("publisher"),
                    rs.getInt("quantityInStock")
            );
        } else book = null;
        closeRsPs(ps, rs);
        return book;
    }

    /**
     * This method closes an open connection to database
     * @param con Connection object
     * @param ps PreparedStatement object
     * @param rs ResultSet object
     */
    private void closeConnections(Connection con, PreparedStatement ps, ResultSet rs) {
        closeRsPs(ps, rs);
        if (con != null) {
            freeConnection(con);
        }
    }

    /**
     * This method is required for use cases when the connection variable is passed as a parameter and we dont want to close it inside the method
     * @param ps PreparedStatement object
     * @param rs ResultSet object
     */
    private void closeRsPs(PreparedStatement ps, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                System.err.println(Program.globalMessages.getString("LoanUserBookDao_Closing_ResultSet"));
                ex.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException ex) {
                System.err.println(Program.globalMessages.getString("LoanUserBookDao_Closing_PreparedStatement"));
                ex.printStackTrace();
            }
        }
    }

    /**
     * This method checks if a book being loaned by a user is not already on loan by that user
     * @param book_id the id of the book to be loaned
     * @param user_id the id of the user who wants to loan the book
     * @return True if the book is already on loan by that user False if the book is not on loan by that user
     */
    private boolean checkIfBookAlreadyLoaned(int book_id, int user_id) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        boolean loaned = true;

        try {
            con = getConnection();
            ps = con.prepareStatement("SELECT * FROM loan WHERE loan_book_id = ? AND loan_user_id = ? AND loan_returned IS NULL");
            ps.setInt(1, book_id);
            ps.setInt(2, user_id);
            rs = ps.executeQuery();

            loaned = rs.next();

        } catch (SQLException ex) {
            System.err.println(Program.globalMessages.getString("GenericMessage") + ex.getMessage());
            ex.printStackTrace();
        } finally {
            closeConnections(con, ps, rs);
        }

        return loaned;
    }
}
