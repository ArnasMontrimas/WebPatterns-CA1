package DataAccessObjects;

import DataTransferObjects.Book;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BookDaoTests {
    private final BookDao bookDao = new BookDao("dundalk_library_test");

    /**
     * Testing fetching a Book from its ID and check if objects are equal
     */
    @Test
    void findById() {
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
        assertEquals(bookFound, bookModel);
    }

    /**
     * Testing adding a Book in the DB
     */
    @Test
    void testAddBook() {
        Book book = new Book(
                10,
                "Computing Intro",
                "978-1-60309-025-4",
                "1.1",
                "Introduction to Computing Book ",
                "William Shakespeare",
                "Penguin Random House",
                60
        );

        assertTrue(bookDao.addBook(book));
    }

    /**
     * Testing getting all products from DB
     */
    @Test
    void testGetAllBooks() {
        assertEquals(bookDao.getAllBooks().toString(), "[Book{book_id=1, book_name='Computing Intro', book_isbn='978-1-60309-025-4', book_edition='1.1', book_description='Introduction to Computing Book ', author='William Shakespeare', publisher='Penguin Random House', quantityInStock=60}, Book{book_id=2, book_name='Computing Intro 2', book_isbn='978-1-60309-025-5', book_edition='2.1', book_description='Introduction to Computing Book 2', author='William Shakespeare', publisher='Penguin Random House', quantityInStock=50}, Book{book_id=3, book_name='Biology Intro', book_isbn='678-1-60309-025-4', book_edition='3.1', book_description='Biology Intro', author='Emily Dickinson', publisher='HarperCollins', quantityInStock=57}, Book{book_id=4, book_name='Biology Intro 2', book_isbn='778-1-60309-025-4', book_edition='4.1', book_description='Biology Intro 2', author='Emily Dickinson', publisher='HarperCollins', quantityInStock=47}, Book{book_id=5, book_name='Harry Potter', book_isbn='978-1-60309-029-4', book_edition='7.1', book_description='Fantasy Wizard Book', author='H. P. Lovecraft', publisher='Macmillan Publishers', quantityInStock=12}, Book{book_id=6, book_name='Harry Potter 2', book_isbn='978-1-60309-089-4', book_edition='5.8', book_description='Fantasy Wizard Book Epic', author='H. P. Lovecraft', publisher='Macmillan Publishers', quantityInStock=0}]");
    }
}
