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
     * Get a Book object from it's Name
     *
     * @param book_name Book Name to get in DB
     * @return The Book object if found, or null if no book with this Name
     */
    Book findByName(String book_name);

    /**
     * Adding a Book to the DB
     *
     * @param book_name Book to add name
     * @param book_isbn Book to add isbn
     * @param book_edition Book to add edition
     * @param book_description Book to add description
     * @param author Book to add author
     * @param publisher Book to add publisher
     * @param quantityInStock Book to add quantityInStock
     * @return true if Book has been added, false if not
     */
    public boolean addBook(String book_name, String book_isbn, String book_edition, String book_description, String author, String publisher, int quantityInStock);

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
     * Add some stocks to a Book (new copies or copies given back)
     *
     * @param book_id The book to add stocks
     * @param quantity Number of copies to add
     * @return true if edition succeeded, false instead
     */
    boolean addCopies(int book_id, int quantity);

    /**
     * Remove some stocks to a Book (removing copies or loaning copies)
     *
     * @param book_id The book to remove stocks
     * @param quantity Number of copies to remove
     * @return true if edition succeeded, false instead
     */
    boolean removeCopies(int book_id, int quantity);
}
