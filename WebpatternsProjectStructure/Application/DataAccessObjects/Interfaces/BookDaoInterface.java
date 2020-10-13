package DataAccessObjects.Interfaces;

import DataTransferObjects.Book;

import java.util.ArrayList;

public interface BookDaoInterface {
    /**
     * Get a Book object from it's ID
     *
     * @param book_id Book ID to get in DB
     * @return The Book object if found, or null if no book with this ID
     */
    Book findById(int book_id);

    /**
     * Adding a Book to the DB
     *
     * @param book Book to add
     * @return true if Book has been added, false if not
     */
    boolean addBook(Book book);

    /**
     * Remove a Book from the DB
     *
     * @param book Book to remove
     * @return true if Book has been added, false if not
     */
    boolean removeBook(Book book);

    /**
     * Remove a Book from the DB from its ID
     *
     * @param book_id Book ID to remove
     * @return true if Book has been added, false if not
     */
    boolean removeBook(int book_id);

    /**
     * Fetch all books
     *
     * @return All Books in library
     */
    ArrayList<Book> getAllBooks();
}
