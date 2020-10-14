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

/**
 * This class will interact with the Loan Table in db
 * @author Arnas
 */
public class LoanUserBookDao extends Dao implements LoanUserBookDaoInterface {
    /**
     * Constructor opens a connection to the database
     * @param databaseName name of the database to be used
     */
    public LoanUserBookDao(String databaseName) {
        super(databaseName);
    }

    /**
     *
     * @param user1
     * @return
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

            ps = con.prepareStatement("SELECT * FROM loan WHERE loan_user_id = ? AND loan_is_active = 1");
            ps.setInt(1, id);

            rs = ps.executeQuery();

            while(rs.next()) {
                book = getBookById(rs, con);

                loans.add(new Loan(
                   rs.getInt("loan_id"),
                   user1,
                   book,
                   rs.getString("loan_started"),
                   rs.getString("loan_ends"),
                   rs.getInt("loan_is_active")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Exception Occurred: " + e.getMessage());
        } finally {
            closeConnections(con, ps, rs);
        }
        return loans;
    }

    /**
     *
     * @param user1
     * @return
     */
    @Override
    public ArrayList<Loan> allLoansSinceJoining(User user1) {
        int id = user1.getId();
        String date = user1.getDateRegistered();

        Connection con = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        ArrayList<Loan> loans = new ArrayList<>();
        Book book;

        try {
            con = getConnection();

            ps = con.prepareStatement("SELECT * FROM loan WHERE loan_user_id = ? AND loan_started >= ?");
            ps.setInt(1, id);
            ps.setString(2, date);

            rs = ps.executeQuery();

            while(rs.next()) {
                book = getBookById(rs, con);

                loans.add(new Loan(
                        rs.getInt("loan_id"),
                        user1,
                        book,
                        rs.getString("loan_started"),
                        rs.getString("loan_ends"),
                        rs.getInt("loan_is_active")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Exception Occurred: " + e.getMessage());
        } finally {
            closeConnections(con, ps, rs);
        }
        return loans;
    }


    /**
     *
     * @param name
     * @return
     */
    @Override
    public int loanBook(String name, int loanDays, User user) {
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
            ps = con.prepareStatement("INSERT INTO loan (`loan_id`, `loan_user_id`, `loan_book_id`, `loan_started`, `loan_ends`, `loan_is_active`) VALUES (null,?,?,?,?,?)");
            book = getBookByName(name, con);

            ps.setInt(1, user.getId());
            ps.setInt(2, book.getBook_id());
            ps.setString(3, startDate);
            ps.setString(4, endDate);
            ps.setInt(5, 1);

            if(loanDays > 0 && loanDays <= 7) {
                if (!checkIfBookAlreadyLoaned(book.getBook_id(), user.getId())) {
                    if (book.getQuantityInStock() > 0) {
                        rs = ps.executeUpdate();
                        if (rs > 0) {
                            if (reduceOrAddBookQuantity(book.getBook_id(), -1)) result = 1; //Book has been loaned
                            else result = 2; //Book quantity could not be reduced
                        } else System.err.println("Something went wrong book could not be loaned");
                    } else result = 0; //The book is out of stock
                } else result = -1; //The book is already loaned by that user
            } else result = -2; //Books cant be loaned for longer than 7 days


        } catch(SQLException ex) {
            System.err.println("Exception Occurred: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            closeConnections(con, ps, null);
        }
        return result;
    }

    /**
     *
     * @param name
     * @return
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
            ps = con.prepareStatement("UPDATE loan SET loan_is_active = 0");
            book = getBookByName(name, con);

            //Make sure that the book is actually loaned before returning it
            if(checkIfBookAlreadyLoaned(book.getBook_id(), user.getId())) {
                rs = ps.executeUpdate();
                if(rs > 0) if (reduceOrAddBookQuantity(book.getBook_id(), +1)) result = true;
            }


        } catch(SQLException ex) {
            System.err.println("Exception Occurred: " + ex.getMessage());
            ex.printStackTrace();
        }

        return result;
    }

    //Some private methods to keep be more organized
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
    private User getUserById(ResultSet rsLoan, Connection con) throws SQLException {
        PreparedStatement ps;
        ResultSet rs;

        User user;

        ps = con.prepareStatement("SELECT * FROM users WHERE id = ?");
        ps.setInt(1, rsLoan.getInt("loan_user_id"));
        rs = ps.executeQuery();

        if (rs.isBeforeFirst()) {
            rs.next();
            user = new User(
                    rs.getInt("id"),
                    rs.getString("type"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("phoneNumber"),
                    rs.getString("dateRegistered"),
                    rs.getBoolean("activeAccount")
            );
        } else user = null;
        closeRsPs(ps, rs);
        return user;
    }
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
    private void closeConnections(Connection con, PreparedStatement ps, ResultSet rs) {
        closeRsPs(ps, rs);
        if (con != null) {
            freeConnection(con);
        }
    }
    private void closeRsPs(PreparedStatement ps, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                System.err.println("Exception when closing result set");
                ex.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException ex) {
                System.err.println("Exception when closing prepared statement");
                ex.printStackTrace();
            }
        }
    }
    private boolean checkIfBookAlreadyLoaned(int book_id, int user_id) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        boolean loaned = true;

        try {
            con = getConnection();
            ps = con.prepareStatement("SELECT * FROM loan WHERE loan_book_id = ? AND loan_user_id = ? AND loan_is_active = 1");
            ps.setInt(1, book_id);
            ps.setInt(2, user_id);
            rs = ps.executeQuery();

            loaned = rs.next();

        } catch(SQLException ex) {
            System.err.println("Exception Occurred: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            closeConnections(con, ps, rs);
        }

        return loaned;
    }
    private boolean reduceOrAddBookQuantity(int book_id, int quantity) {
        Connection con = null;
        PreparedStatement ps = null;
        int rs;

        boolean reduced = true;

        try {
            con = getConnection();
            ps = con.prepareStatement("UPDATE book SET quantityInStock = (quantityInStock+?) WHERE book_id = ?");
            ps.setInt(1, quantity);
            ps.setInt(2, book_id);
            rs = ps.executeUpdate();

            reduced = rs > 0;

        } catch(SQLException ex) {
            System.err.println("Exception Occurred: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            closeConnections(con, ps, null);
        }
        return reduced;
    }
}
