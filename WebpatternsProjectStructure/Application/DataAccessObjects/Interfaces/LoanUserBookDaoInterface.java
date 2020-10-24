package DataAccessObjects.Interfaces;

import DataTransferObjects.Loan;
import DataTransferObjects.User;

import java.util.ArrayList;

/**
 * Interface For accessing loan table from Db
 * @author Arnas
 */
public interface LoanUserBookDaoInterface {

    /**
     * This method gets all loans of a user using his id
     * @param user1 User object
     * @return ArrayList of loans or empty arrayList if nothing found
     */
    ArrayList<Loan> allLoansByUserId(User user1);

    /**
     * This method gets all loans of a user since joining the library
     * @param user1 User object
     * @return ArrayList of loans or empty ArrayList if nothing found
     */
    ArrayList<Loan> allLoansSinceJoining(User user1);

    /**
     * This method allows a user to loan a book
     * @param name Book name
     * @param loanDays How long the loan should last
     * @param user1 User object
     * @return This method returns a particular number for each error encountered "-2 = Book was attempted to be loaned for longer than 7 days", "-1 = The book was already loaned by the user", "0 = The book in question is out of stock", "2 = The quantity of the book could not be reduced after the loan succeeded", "1 = The loan was successful", "-3 = The user has maximum loans available for him at any given time"
     */
    int loanBook(String name, int loanDays, User user1);

    /**
     * This method allows a user to return a loaned book
     * @param name Name of the book returned
     * @param user1 User object
     * @return true if successful or false otherwise
     */
    boolean returnBook(String name, User user1);
}
