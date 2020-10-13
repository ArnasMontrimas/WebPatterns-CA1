package DataAccessObjects;

import DataAccessObjects.Interfaces.BookDaoInterface;
import DataTransferObjects.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
     * @param book_id
     * @return
     */
    public Book findById(int book_id) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Book p = null;

        try{
            con = getConnection();

            ps = con.prepareStatement("SELECT * FROM book WHERE book_id = ?");
            ps.setInt(1, book_id);
            rs = ps.executeQuery();

            if(rs.next())
            {
                p = new Book(
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
        return p;
    }
}
