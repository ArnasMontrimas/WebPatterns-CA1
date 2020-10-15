package DataAccessObjects;

import DataTransferObjects.Book;
import DataTransferObjects.Loan;
import DataTransferObjects.User;
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
    void allLoansByUserIdTest1() {
        System.out.println("Test AllLoansByUserId() WHEN: 'user has no loans active' EXPECTED: 'empty ArrayList'");

        //If the tests fail first refer to database to see if the hard coded object match what is in the database otherwise fix your method
        User u = new User(2,"Member","Arnas","1","arnas@gmail.com","0869542586","2020-10-12 17:13:03",true);

        //Expected
        ArrayList<Loan> expected = new ArrayList<>();

        //Actual
        ArrayList<Loan> actual = dao.allLoansByUserId(u);

        //Result
        assertEquals(expected, actual);
    }

    @Test
    void allLoansByUserIdTest2() {
        System.out.println("Test allLoansByUserId() WHEN: 'user has active loans' EXPECTED 'ArrayList of active loans for that user'");

        //If the tests fail first refer to database to see if the hard coded object match what is in the database otherwise fix your method
        User u = new User(1, "Member", "Sam", "sam1", "sam@gmail.com", "0838568457", "2020-10-12 17:13:03", true);

        Book book1 = new Book(3,"Biology Intro","678-1-60309-025-4","3.1","Biology Intro","Emily Dickinson","HarperCollins", 57);
        Book book2 = new Book(1,"Computing Intro","978-1-60309-025-4","1.1","Introduction to Computing Book","William Shakespeare","Penguin Random House", 60);

        Loan loan1 = new Loan(2,u,book1,"2020-10-12 14:25:19","2020-10-16 17:00:00",1);
        Loan loan2 = new Loan(3,u,book2,"2020-10-12 13:23:00","2020-10-14 09:19:00",1);

        //Expected
        ArrayList<Loan> expected = new ArrayList<>();
        expected.add(loan1);
        expected.add(loan2);

        //Actual
        ArrayList<Loan> actual = dao.allLoansByUserId(u);

        //Result
        assertEquals(expected, actual);

    }

    @Test
    void allLoansSinceJoiningTest1() {
        System.out.println("Test allLoansSinceJoining() WHEN: 'user has previous loans active or not' EXPECTED: 'ArrayList of his loans'");

        //If the tests fail first refer to database to see if the hard coded object match what is in the database otherwise fix your method
        User u = new User(1, "Member", "Sam", "sam1", "sam@gmail.com", "0838568457", "2020-10-12 17:13:03", true);

        Book book1 = new Book(3,"Biology Intro","678-1-60309-025-4","3.1","Biology Intro","Emily Dickinson","HarperCollins", 57);
        Book book2 = new Book(1,"Computing Intro","978-1-60309-025-4","1.1","Introduction to Computing Book","William Shakespeare","Penguin Random House", 60);
        Book book3 = new Book(5,"Harry Potter","978-1-60309-029-4","7.1","Fantasy Wizard Book","H. P. Lovecraft","Macmillan Publishers",12);

        Loan loan1 = new Loan(2,u,book1,"2020-10-12 14:25:19","2020-10-16 17:00:00",1);
        Loan loan2 = new Loan(3,u,book2,"2020-10-12 13:23:00","2020-10-14 09:19:00",1);
        Loan loan3 = new Loan(4,u,book3,"2020-10-14 11:00:00","2020-10-19 00:00:00",0);

        //Expected
        ArrayList<Loan> expected = new ArrayList<>();
        expected.add(loan1);
        expected.add(loan2);
        expected.add(loan3);

        //Actual
        ArrayList<Loan> actual = dao.allLoansSinceJoining(u);

        //Result
        assertEquals(expected, actual);
    }

    @Test
    void allLoansSinceJoiningTest2() {
        System.out.println("Test allLoansSinceJoining() WHEN: 'user had no previous loans taken' EXPECTED: 'empty ArrayList'");

        //If the tests fail first refer to database to see if the hard coded object match what is in the database otherwise fix your method
        User u = new User(6,"Member","Sam1","1","sam1@gmail.com","0589653258","2020-10-12 17:20:54", true);

        //Expected
        ArrayList<Loan> expected = new ArrayList<>();

        //Actual
        ArrayList<Loan> actual = dao.allLoansSinceJoining(u);

        //Result
        assertEquals(expected, actual);
    }

    //Will do later this takes ageeessss.......
    @Test
    void loanBook() {
        System.out.println("");
    }

    @Test
    void returnBook() {
        System.out.println("");
    }
}