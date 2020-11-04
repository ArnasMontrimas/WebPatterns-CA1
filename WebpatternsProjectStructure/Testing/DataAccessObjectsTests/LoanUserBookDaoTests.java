package DataAccessObjectsTests;

import DataAccessObjects.LoanUserBookDao;
import DataTransferObjects.Address;
import DataTransferObjects.Book;
import DataTransferObjects.Loan;
import DataTransferObjects.User;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class all tests for LoanUserBookDao are housed here
 * @author Arnas M.
 */
class LoanUserBookDaoTests {
    LoanUserBookDao dao = new LoanUserBookDao("dundalk_library_test");


    @Test
    @Order(1)
    void allLoansByUserIdTest1() {
        System.out.println("Test AllLoansByUserId() WHEN: 'user has no loans active' EXPECTED: 'empty ArrayList'");

        //If the tests fail first refer to database to see if the hard coded object match what is in the database otherwise fix your method
        Address a = new Address(20,"Micheal","Smith","48 Old Road",null,"Cavan",null,"Ireland","HK-875");
        User u = new User(15,"Member","Micheal","msmith","micheal@gmail.com","2547896587","2020-11-04 09:48:36",true,a);

        //Expected
        ArrayList<Loan> expected = new ArrayList<>();

        //Actual
        ArrayList<Loan> actual = dao.allLoansByUserId(u);

        //Result
        assertEquals(expected, actual);
    }

    @Test
    @Order(2)
    void allLoansByUserIdTest2() {
        System.out.println("Test allLoansByUserId() WHEN: 'user has active loans' EXPECTED 'ArrayList of active loans for that user'");

        //If the tests fail first refer to database to see if the hard coded object match what is in the database otherwise fix your method
        Address a = new Address(1,"Sam","Smith","Cavan Town","Cavan Green Hills","Cava",null,"Ireland","141478");
        User u = new User(1, "Member", "Sam", "sam1", "sam@gmail.com", "0838568457", "2020-10-12 17:13:03", true,a);

        Book book3 = new Book(3,"Biology Intro","678-1-60309-025-4","3.1","Biology Intro","Emily Dickinson","HarperCollins", 57);
        Book book1 = new Book(1,"Computing Intro","978-1-60309-025-4","1.1","Introduction to Computing Book","William Shakespeare","Penguin Random House", 60);
        Book book2 = new Book(2,"Computing Intro 2","978-1-60309-025-5","2.1","Introduction to Computing Book 2","William Shakespeare","Penguin Random House",49);
        Book book4 = new Book(4,"Biology Intro 2","778-1-60309-025-4","4.1","Biology Intro 2","Emily Dickinson","HarperCollins",46);
        Book book5 = new Book(5,"Harry Potter","978-1-60309-029-4","7.1","Fantasy Wizard Book","H.P.Lovecraft","Macmillan Publisher",15);

        Loan loan1 = new Loan(1,u,book3,"2020-10-06 09:19:10","2020-10-09 07:00:00",null);
        Loan loan3 = new Loan(3,u,book1,"2020-10-12 13:23:00","2020-10-14 09:19:00",null);
        Loan loan5 = new Loan(5,u,book2,"2020-10-24 18:19:22","2020-10-28 18:19:22",null);
        Loan loan6 = new Loan(6,u,book4,"2020-10-24 18:19:47","2020-10-27 18:19:47",null);
        //Loan loan7 = new Loan(7,u,book5,"2020-10-24 18:20:03","2020-10-28 18:20:03","2020-11-04 09:56:49");

        //Expected
        ArrayList<Loan> expected = new ArrayList<>();
        expected.add(loan1);
        expected.add(loan3);
        expected.add(loan5);
        expected.add(loan6);
        //expected.add(loan7);

        //Actual
        ArrayList<Loan> actual = dao.allLoansByUserId(u);

        //Result
        assertEquals(expected, actual);

    }

    @Test
    @Order(3)
    void allLoansSinceJoiningTest1() {
        System.out.println("Test allLoansSinceJoining() WHEN: 'user has previous loans active or not' EXPECTED: 'ArrayList of his loans'");

        //If the tests fail first refer to database to see if the hard coded object match what is in the database otherwise fix your method
        Address a = new Address(1,"Sam","Smith","Cavan Town","Cavan Green Hills","Cava",null,"Ireland","141478");
        User u = new User(1, "Member", "Sam", "sam1", "sam@gmail.com", "0838568457", "2020-10-12 17:13:03", true,a);

        Book book3 = new Book(3,"Biology Intro","678-1-60309-025-4","3.1","Biology Intro","Emily Dickinson","HarperCollins", 57);
        Book book1 = new Book(1,"Computing Intro","978-1-60309-025-4","1.1","Introduction to Computing Book","William Shakespeare","Penguin Random House", 56);
        Book book2 = new Book(2,"Computing Intro 2","978-1-60309-025-5","2.1","Introduction to Computing Book 2","William Shakespeare","Penguin Random House",49);
        Book book4 = new Book(4,"Biology Intro 2","778-1-60309-025-4","4.1","Biology Intro 2","Emily Dickinson","HarperCollins",46);
        Book book5 = new Book(5,"Harry Potter","978-1-60309-029-4","7.1","Fantasy Wizard Book","H.P.Lovecraft","Macmillan Publisher",15);

        Loan loan1 = new Loan(1,u,book3,"2020-10-06 09:19:10","2020-10-09 07:00:00",null);
        Loan loan3 = new Loan(3,u,book1,"2020-10-12 13:23:00","2020-10-14 09:19:00",null);
        Loan loan5 = new Loan(5,u,book2,"2020-10-24 18:19:22","2020-10-28 18:19:22",null);
        Loan loan6 = new Loan(6,u,book4,"2020-10-24 18:19:47","2020-10-27 18:19:47",null);
        Loan loan7 = new Loan(7,u,book5,"2020-10-24 18:20:03","2020-10-28 18:20:03","2020-11-04 09:56:49");

        //Expected
        ArrayList<Loan> expected = new ArrayList<>();
        expected.add(loan1);
        expected.add(loan3);
        expected.add(loan5);
        expected.add(loan6);
        expected.add(loan7);

        //Actual
        ArrayList<Loan> actual = dao.allLoansSinceJoining(u);

        //Result
        assertEquals(expected, actual);
    }

    @Test
    @Order(4)
    void allLoansSinceJoiningTest2() {
        System.out.println("Test allLoansSinceJoining() WHEN: 'user had no previous loans taken' EXPECTED: 'empty ArrayList'");

        //If the tests fail first refer to database to see if the hard coded object match what is in the database otherwise fix your method
        Address a = new Address(20,"Micheal","Smith","48 Old Road",null,"Cavan",null,"Ireland","HK-875");
        User u = new User(15,"Member","Micheal","msmith","micheal@gmail.com","2547896587","2020-11-04 09:48:36",true,a);

        //Expected
        ArrayList<Loan> expected = new ArrayList<>();

        //Actual
        ArrayList<Loan> actual = dao.allLoansSinceJoining(u);

        //Result
        assertEquals(expected, actual);
    }

    @Test
    @Order(5)
    void loanBookTest1() {
        System.out.println("Test loanBook() WHEN: user loans 1 book for 1 day EXPECTED: book is loaned to user");

        Address a = new Address(2,"Arnas","Smith","Lakepark",null,"New York","NY","America","154875");
        User u = new User(2,"Member","Arnas","1","arnas@gmail.com","0869542586","2020-10-12 17:13:03",true,a);

        Book book3 = new Book(3,"Biology Intro","678-1-60309-025-4","3.1","Biology Intro","Emily Dickinson","HarperCollins", 57);

        int expected = 1;
        int actual = dao.loanBook(book3.getBook_name(),1,u);

        assertEquals(expected,actual);
    }

    @Test
    @Order(6)
    void loanBookTest2() {
        System.out.println("Test loanBook() WHEN: user wants to loan a book for longer than 7 days EXPECTED: book can not be loaned");

        Address a = new Address(2,"Arnas","Smith","Lakepark",null,"New York","NY","America","154875");
        User u = new User(2,"Member","Arnas","1","arnas@gmail.com","0869542586","2020-10-12 17:13:03",true,a);

        Book book3 = new Book(3,"Biology Intro","678-1-60309-025-4","3.1","Biology Intro","Emily Dickinson","HarperCollins", 57);

        int expected = -2;
        int actual = dao.loanBook(book3.getBook_name(),8,u);

        assertEquals(expected,actual);

    }

    @Test
    @Order(7)
    void loanBookTest3() {
        System.out.println("Test loanBook() WHEN: user wants to loan a book that does not exists EXPECTED: book can not be loaned");

        Address a = new Address(2,"Arnas","Smith","Lakepark",null,"New York","NY","America","154875");
        User u = new User(2,"Member","Arnas","1","arnas@gmail.com","0869542586","2020-10-12 17:13:03",true,a);

        Book book3 = new Book(3,"Biology Introsadwdsa","678-1-60309-025-4","3.1","Biology Intro","Emily Dickinson","HarperCollins", 57);

        int expected = -4;
        int actual = dao.loanBook(book3.getBook_name(),8,u);

        assertEquals(expected,actual);

    }

    @Test
    @Order(8)
    void loanBookTest4() {
        System.out.println("Test loanBook() WHEN: user wants to loan a book which is out of stock EXPECTED: book can not be loaned");

        Address a = new Address(2,"Arnas","Smith","Lakepark",null,"New York","NY","America","154875");
        User u = new User(2,"Member","Arnas","1","arnas@gmail.com","0869542586","2020-10-12 17:13:03",true,a);

        Book book3 = new Book(11,"Intro To French","678-1-60389-025-4","2.4","Beginners book for learning french","H.P.Lovecraft","Penguin Random House", 0);

        int expected = 0;
        int actual = dao.loanBook(book3.getBook_name(),5,u);

        assertEquals(expected,actual);

    }

    @Test
    @Order(9)
    void returnBookTest1() {
        System.out.println("Test returnBook() WHEN: user returns book he has on loan EXPECTED: book returned ");


        Address a = new Address(2,"Arnas","Smith","Lakepark",null,"New York","NY","America","154875");
        User u = new User(2,"Member","Arnas","1","arnas@gmail.com","0869542586","2020-10-12 17:13:03",true,a);

        Book book3 = new Book(3,"Biology Intro","678-1-60309-025-4","3.1","Biology Intro","Emily Dickinson","HarperCollins", 57);

        boolean actual = dao.returnBook(book3.getBook_name(),u);

        assertTrue(actual);
    }

    @Test
    @Order(10)
    void returnBookTest2() {
        System.out.println("Test returnBook() WHEN: user returns book that does not exists EXPECTED: book is not returned");

        Address a = new Address(2,"Arnas","Smith","Lakepark",null,"New York","NY","America","154875");
        User u = new User(2,"Member","Arnas","1","arnas@gmail.com","0869542586","2020-10-12 17:13:03",true,a);

        Book book3 = new Book(3,"Biology Intro949349","678-1-60309-025-4","3.1","Biology Intro","Emily Dickinson","HarperCollins", 57);

        boolean actual = dao.returnBook(book3.getBook_name(),u);

        assertFalse(actual);
    }

    @Test
    @Order(11)
    void returnBookTest3() {
        System.out.println("Test returnBook() WHEN: book being returned is not on loan EXPECTED: book can not be returned");

        Address a = new Address(2,"Arnas","Smith","Lakepark",null,"New York","NY","America","154875");
        User u = new User(2,"Member","Arnas","1","arnas@gmail.com","0869542586","2020-10-12 17:13:03",true,a);

        Book book3 = new Book(10,"Introduction to Music","778-1-60307-025-4","1.0","Book for beginners for learning music","Emily Dickinson","Macmillan Publishers",47);

        boolean actual = dao.returnBook(book3.getBook_name(),u);

        assertFalse(actual);

    }
}
