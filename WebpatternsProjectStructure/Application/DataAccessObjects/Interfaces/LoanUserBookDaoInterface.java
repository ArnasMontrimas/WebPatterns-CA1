package DataAccessObjects.Interfaces;

import DataTransferObjects.Loan;
import DataTransferObjects.User;

import java.util.ArrayList;

/**
 * Interface For accessing loan table from Db
 * @author Arnas
 */
public interface LoanUserBookDaoInterface {
    //TODO---------------Add JavaDocs----------------------
    ArrayList<Loan> allLoansByUserId(User user1);
    ArrayList<Loan> allLoansSinceJoining(User user1);
    int loanBook(String name, int loanDays, User user1);
    boolean returnBook(String name, User user1);
}
