package DataAccessObjectsTests;

import DataAccessObjects.BookDao;
import DataTransferObjects.Book;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.ArrayList;

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
     * Testing fetching a non-existing Book from its ID
     */
    @Test
    void testFindByIdNonExistingBook() {
        // Fetching from ID
        Book bookFound = bookTestDao.findById(100);
        assertNull(bookFound);
    }

    /**
     * Testing fetching an existing Book from its name and check if objects are equal
     */
    @Test
    void testFindByNameExistingBook() {
        // Fetching from Name
        Book bookFound = bookTestDao.findByName("Computing Intro");
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
     * Testing fetching a non-existing Book from its name
     */
    @Test
    void testFindByNameNonExistingBook() {
        // Fetching from Name
        Book bookFound = bookTestDao.findByName("This nice Book doesn't exist at all, and that's normal because that is what we are searching and testing");
        assertNull(bookFound);
    }

    /**
     * Testing adding a Book in the DB
     */
    @Test
    @Order(1)
    void testAddBookNew() {
        boolean added = bookTestDao.addBook(
                "Java: The Complete Reference",
                "978-1260440232",
                "11",
                "The Definitive Java Programming Guide",
                "Herbert Schildt",
                "McGraw-Hill Education",
                60
        );

        ArrayList<Book> fetchedBooks = bookTestDao.getAllBooks();
        Book bookAdded = null;
        for (Book book : fetchedBooks) {
            if (book.getBook_name().equals("Java: The Complete Reference"))
                bookAdded = book;
        }

        bookTestDao.removeBook(bookAdded);

        assertTrue(added && bookAdded != null);
    }

    /**
     * Testing adding a Book in the DB that already exists
     */
    @Test
    void testAddBookAlreadyExisting() {
        boolean added = bookTestDao.addBook(
                "Computing Intro",
                "978-1260440232",
                "11",
                "The Definitive Java Programming Guide",
                "Herbert Schildt",
                "McGraw-Hill Education",
                60
        );

        assertFalse(added);
    }

    /**
     * Testing removing an existing Book from the DB
     */
    @Test
    @Order(2)
    void testRemoveBookExisting() {
        boolean added = bookTestDao.addBook(
                "Java: The Complete Reference",
                "978-1260440232",
                "11",
                "The Definitive Java Programming Guide",
                "Herbert Schildt",
                "McGraw-Hill Education",
                60
        );

        ArrayList<Book> fetchedBooks = bookTestDao.getAllBooks();
        Book bookAdded = null;
        for (Book book : fetchedBooks) {
            if (book.getBook_name().equals("Java: The Complete Reference"))
                bookAdded = book;
        }

        boolean removed = bookTestDao.removeBook(bookAdded);

        assertTrue(added && removed && bookAdded != null);
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
        assertEquals(bookTestDao.getAllBooks().toString(), "[Book{book_id=1, book_name='Computing Intro', book_isbn='978-1-60309-025-4', book_edition='1.1', book_description='Introduction to Computing Book ', author='William Shakespeare', publisher='Penguin Random House', quantityInStock=60}, Book{book_id=2, book_name='Computing Intro 2', book_isbn='978-1-60309-025-5', book_edition='2.1', book_description='Introduction to Computing Book 2', author='William Shakespeare', publisher='Penguin Random House', quantityInStock=49}, Book{book_id=3, book_name='Biology Intro', book_isbn='678-1-60309-025-4', book_edition='3.1', book_description='Biology Intro', author='Emily Dickinson', publisher='HarperCollins', quantityInStock=57}, Book{book_id=4, book_name='Biology Intro 2', book_isbn='778-1-60309-025-4', book_edition='4.1', book_description='Biology Intro 2', author='Emily Dickinson', publisher='HarperCollins', quantityInStock=46}, Book{book_id=5, book_name='Harry Potter', book_isbn='978-1-60309-029-4', book_edition='7.1', book_description='Fantasy Wizard Book', author='H. P. Lovecraft', publisher='Macmillan Publishers', quantityInStock=15}, Book{book_id=6, book_name='Harry Potter 2', book_isbn='978-1-60309-089-4', book_edition='5.8', book_description='Fantasy Wizard Book Epic', author='H. P. Lovecraft', publisher='Macmillan Publishers', quantityInStock=0}, Book{book_id=7, book_name='Computing Intro 4', book_isbn='678-1-60209-025-4', book_edition='4.5', book_description='Computing Book 4', author='William Shakespeare', publisher='HarperCollins', quantityInStock=48}, Book{book_id=8, book_name='Computing Java', book_isbn='678-1-60309-925-4', book_edition='9.4', book_description='Java Intro', author='William Shakespeare', publisher='Macmillan Publishers', quantityInStock=47}, Book{book_id=9, book_name='Maths Intro', book_isbn='678-1-60709-025-4', book_edition='4.7', book_description='Book for beginners for learning maths', author='Emily Dickinson', publisher='Penguin Random House', quantityInStock=9}, Book{book_id=10, book_name='Introduction to Music', book_isbn='778-1-60307-025-4', book_edition='1.0', book_description='Book for beginners for learning music', author='Emily Dickinson', publisher='Macmillan Publishers', quantityInStock=47}, Book{book_id=11, book_name='Intro To French', book_isbn='678-1-60389-025-4', book_edition='2.4', book_description='Beginners book for learning french', author='H. P. Lovecraft', publisher='Penguin Random House', quantityInStock=8}, Book{book_id=12, book_name='Intro To German', book_isbn='678-1-67389-025-4', book_edition='2.3', book_description='Beginners book for learning german', author='H. P. Lovecraft', publisher='HarperCollins', quantityInStock=14}, Book{book_id=17, book_name='Intro To Science', book_isbn='748-9856-9999', book_edition='1.0', book_description='Book About Science', author='David Smith', publisher='Macmillian', quantityInStock=74}]");
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