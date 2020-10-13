package DataAccessObjectsTests;

import DataAccessObjects.BookDao;
import DataTransferObjects.Book;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

class ProgramTest {

    @Test
    void main() {
    }

    /**
     * Method to test all methods associated to Books
     */
    @Test
    void BookTesting() {
        BookDao bookDao = new BookDao("dundalk_library");

        // Fetching from ID
        Book bookFound = bookDao.findById(1);
        Book bookModel = new Book(
                1,
                "Computing Intro",
                "978-1-60309-025-4",
                "1.1",
                "Introduction to Computing Book ",
                "William Shakespeare",
                "Penguin Random House",
                60
        );
        assertEquals("Books are equal", bookFound, bookModel);
    }
}