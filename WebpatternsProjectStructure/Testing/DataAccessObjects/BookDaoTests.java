package DataAccessObjects;

import DataTransferObjects.Book;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.Assert.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookDaoTests {
    private final BookDao bookTestDao = new BookDao("dundalk_library_test");

    /**
     * Testing fetching an existing Book from its ID and check if objects are equal
     */
    @Test
    void testFindByIdExistingBook() {
        // Fetching from ID
        Book bookFound = bookTestDao.findById(1);
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
     * Testing fetching a non-existing Book
     */
    @Test
    void testFindByIdNonExistingBook() {
        // Fetching from ID
        Book bookFound = bookTestDao.findById(100);
        assertNull(bookFound);
    }

    /**
     * Testing adding a Book in the DB
     */
    @Test
    @Order(1)
    void testAddBookNew() {
        Book book = new Book(
                10,
                "Java: The Complete Reference",
                "978-1260440232",
                "11",
                "The Definitive Java Programming Guide",
                "Herbert Schildt",
                "McGraw-Hill Education",
                60
        );

        boolean added = bookTestDao.addBook(book);
        Book fetchedbook = bookTestDao.findById(10);

        assertTrue(added && fetchedbook.equals(book));
    }

    /**
     * Testing adding a Book in the DB that already exists
     */
    @Test
    void testAddBookAlreadyExisting() {
        Book book = new Book(
                1,
                "Java: The Complete Reference",
                "978-1260440232",
                "11",
                "The Definitive Java Programming Guide",
                "Herbert Schildt",
                "McGraw-Hill Education",
                60
        );

        boolean added = bookTestDao.addBook(book);

        assertFalse(added);
    }

    /**
     * Testing removing an existing Book from the DB
     */
    @Test
    @Order(2)
    void testRemoveBookExisting() {
        boolean removed = bookTestDao.removeBook(10);
        Book fetchedbook = bookTestDao.findById(10);

        assertTrue(removed && fetchedbook == null);
    }

    /**
     * Testing removing a non-existing Book from the DB
     */
    @Test
    void testRemoveBookNonExisting() {
        boolean removed = bookTestDao.removeBook(100);

        assertFalse(removed);
    }

    /**
     * Testing getting all products from DB
     */
    @Test
    @Order(3)
    void testGetAllBooks() {
        assertEquals(bookTestDao.getAllBooks().toString(), "[Book{book_id=1, book_name='Computing Intro', book_isbn='978-1-60309-025-4', book_edition='1.1', book_description='Introduction to Computing Book ', author='William Shakespeare', publisher='Penguin Random House', quantityInStock=60}, Book{book_id=2, book_name='Computing Intro 2', book_isbn='978-1-60309-025-5', book_edition='2.1', book_description='Introduction to Computing Book 2', author='William Shakespeare', publisher='Penguin Random House', quantityInStock=50}, Book{book_id=3, book_name='Biology Intro', book_isbn='678-1-60309-025-4', book_edition='3.1', book_description='Biology Intro', author='Emily Dickinson', publisher='HarperCollins', quantityInStock=57}, Book{book_id=4, book_name='Biology Intro 2', book_isbn='778-1-60309-025-4', book_edition='4.1', book_description='Biology Intro 2', author='Emily Dickinson', publisher='HarperCollins', quantityInStock=47}, Book{book_id=5, book_name='Harry Potter', book_isbn='978-1-60309-029-4', book_edition='7.1', book_description='Fantasy Wizard Book', author='H. P. Lovecraft', publisher='Macmillan Publishers', quantityInStock=12}, Book{book_id=6, book_name='Harry Potter 2', book_isbn='978-1-60309-089-4', book_edition='5.8', book_description='Fantasy Wizard Book Epic', author='H. P. Lovecraft', publisher='Macmillan Publishers', quantityInStock=0}]");
    }

    /**
     * Testing adding copies of an existing book
     */
    @Test
    @Order(4)
    void testAddCopiesExistingBook() {
        Book bookBefore = bookTestDao.findById(1);
        boolean added = bookTestDao.addCopies(1, 10);
        Book bookAfter = bookTestDao.findById(1);

        assertTrue(added && (bookAfter.getQuantityInStock() == bookBefore.getQuantityInStock() + 10));
    }

    /**
     * Testing adding copies of a non-existing book
     */
    @Test
    void testAddCopiesNonExistingBook() {
        boolean added = bookTestDao.addCopies(100, 10);

        assertFalse(added);
    }

    /**
     * Testing removing copies of an existing book
     */
    @Test
    @Order(5)
    void testRemovingCopiesExistingBook() {
        Book bookBefore = bookTestDao.findById(1);
        boolean removed = bookTestDao.removeCopies(1, 10);
        Book bookAfter = bookTestDao.findById(1);

        assertTrue(removed && (bookAfter.getQuantityInStock() == bookBefore.getQuantityInStock() - 10));
    }

    /**
     * Testing adding copies of a non-existing book
     */
    @Test
    void testRemoveCopiesNonExistingBook() {
        boolean removed = bookTestDao.removeCopies(100, 10);

        assertFalse(removed);
    }
}