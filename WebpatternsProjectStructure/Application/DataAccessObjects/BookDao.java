package DataAccessObjects;

import DataAccessObjects.Interfaces.BookDaoInterface;
import DataTransferObjects.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Fetch in DB Book data
 * @author grallm
 */
public class BookDao extends Dao implements BookDaoInterface {
    public BookDao(String databaseName) {
        super(databaseName);
    }

    /**
     * Get a Book object from it's ID
     *
     * @param book_id Book's ID to search
     * @return Book object found or null if not found
     */
    public Book findById(int book_id) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Book book = null;

        try{
            con = getConnection();

            ps = con.prepareStatement("SELECT * FROM book WHERE book_id = ?");
            ps.setInt(1, book_id);
            rs = ps.executeQuery();

            if(rs.next())
            {
                book = new Book(
                    book_id,
                    rs.getString("book_name"),
                    rs.getString("book_isbn"),
                    rs.getString("book_edition"),
                    rs.getString("book_description"),
                    rs.getString("author"),
                    rs.getString("publisher"),
                    rs.getInt("quantityInStock")
                );
            }
        }catch (SQLException e) {
            System.out.println("Exception BookDao.findById(): " + e.getMessage());
        } finally {
            closeDaoConnection(con, ps, rs);
        }
        return book;
    }

    /**
     * Adding a Book to the DB
     *
     * @param book Book to add
     * @return true if Book has been added, false if not
     */
    public boolean addBook(Book book) {
        Connection con = null;
        PreparedStatement ps = null;
        int rowsAffected = 0;

        try {
            con = getConnection();

            ps = con.prepareStatement("INSERT INTO `book`" +
                    "(`book_id`, `book_name`, `book_isbn`, `book_edition`, `book_description`, `author`, `publisher`, `quantityInStock`)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?);");

            ps.setInt(1, book.getBook_id());
            ps.setString(2, book.getBook_name());
            ps.setString(3, book.getBook_isbn());
            ps.setString(4, book.getBook_edition());
            ps.setString(5, book.getBook_description());
            ps.setString(6, book.getAuthor());
            ps.setString(7, book.getPublisher());
            ps.setInt(8, book.getQuantityInStock());

            rowsAffected = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Exception BookDao.addBook(): " + e.getMessage());
        } catch( Exception e ) {
            System.out.println("Exception BookDao.addBook(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception BookDao.addBook(): " + e.getMessage());
            }
        }

        if (rowsAffected == -1 ) {
            System.out.println("Book with this ID already exists");
        } else if (rowsAffected != 1) {
            System.out.println("Book couldn't be added");
        }
        return rowsAffected == 1;
    }

    /**
     * Remove a Book from the DB
     *
     * @param book Book to remove
     * @return true if Book has been added, false if not
     */
    public boolean removeBook(Book book) {
        return removeBook(book.getBook_id());
    }

    /**
     * Remove a Book from the DB from its ID
     *
     * @param book_id Book ID to remove
     * @return true if Book has been added, false if not
     */
    public boolean removeBook(int book_id) {
        Connection con = null;
        PreparedStatement ps = null;
        int rowsAffected = 0;

        try {
            con = getConnection();

            ps = con.prepareStatement("DELETE FROM book WHERE book_id = ?;");
            ps.setInt(1, book_id);

            rowsAffected = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Exception BookDao.removeBook(): " + e.getMessage());
        } catch( Exception e ) {
            System.out.println("Exception BookDao.removeBook(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception BookDao.removeBook(): " + e.getMessage());
            }
        }

        if (rowsAffected != 1) {
            System.out.println("Book couldn't be removed");
        }
        return rowsAffected == 1;
    }

    /**
     * Fetch all books
     *
     * @return All Books in library
     */
    public ArrayList<Book> getAllBooks() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Book> books = new ArrayList<>();

        try{
            con = getConnection();

            ps = con.prepareStatement("SELECT * FROM book");
            rs = ps.executeQuery();

            while(rs.next())
            {
                books.add(new Book(
                        rs.getInt("book_id"),
                        rs.getString("book_name"),
                        rs.getString("book_isbn"),
                        rs.getString("book_edition"),
                        rs.getString("book_description"),
                        rs.getString("author"),
                        rs.getString("publisher"),
                        rs.getInt("quantityInStock")
                ));
            }
        }catch (SQLException e) {
            System.out.println("Exception BookDao.findById(): " + e.getMessage());
        } finally {
            closeDaoConnection(con, ps, rs);
        }
        return books;
    }

    private void closeDaoConnection(Connection con, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                freeConnection(con);
            }
        } catch (SQLException e) {
            System.out.println("Exception BookDao.findById(): " + e.getMessage());
        }
    }

    /**
     * Add some stocks to a Book (new copies or copies given back)
     *
     * @param book_id  The book to add stocks
     * @param quantity Number of copies to add
     * @return true if edition succeeded, false instead
     */
    public boolean addCopies(int book_id, int quantity) {
        Connection con = null;
        PreparedStatement ps = null;
        int rowsAffected = 0;

        if (quantity < 0) {
            System.out.println("BookDao.addCopies(): can't add <0 copies");
            return false;
        }

        try {
            con = getConnection();

            ps = con.prepareStatement("UPDATE `book` SET `quantityInStock` = `quantityInStock` + ? WHERE `book_id` = ?;");
            ps.setInt(1, quantity);
            ps.setInt(2, book_id);

            rowsAffected = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Exception BookDao.addCopies(): " + e.getMessage());
        } catch( Exception e ) {
            System.out.println("Exception BookDao.addCopies(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception BookDao.addCopies(): " + e.getMessage());
            }
        }

        if (rowsAffected != 1) {
            System.out.println("Copies couldn't be added");
        }
        return rowsAffected == 1;
    }

    /**
     * Remove some stocks to a Book (removing copies or loaning copies)
     *
     * @param book_id  The book to remove stocks
     * @param quantity Number of copies to remove
     * @return true if edition succeeded, false instead
     */
    public boolean removeCopies(int book_id, int quantity) {
        Connection con = null;
        PreparedStatement ps = null;
        int rowsAffected = 0;

        if (quantity < 0) {
            System.out.println("BookDao.removeCopies(): can't remove <0 copies");
            return false;
        }

        try {
            con = getConnection();

            ps = con.prepareStatement("UPDATE `book` SET `quantityInStock` = `quantityInStock` - ? WHERE `book_id` = ?;");
            ps.setInt(1, quantity);
            ps.setInt(2, book_id);

            rowsAffected = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Exception BookDao.removeCopies(): " + e.getMessage());
        } catch( Exception e ) {
            System.out.println("Exception BookDao.removeCopies(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception BookDao.removeCopies(): " + e.getMessage());
            }
        }

        if (rowsAffected != 1) {
            System.out.println("Copies couldn't be removed");
        }
        return rowsAffected == 1;
    }
}
