package DataAccessObjects.Interfaces;

import DataTransferObjects.Book;

public interface BookDaoInterface {
    /**
     * Get a Book object from it's ID
     *
     * @param book_id Book ID to get in DB
     * @return The Book object if found, or null if no book with this ID
     */
    public Book findById(int book_id);
}
