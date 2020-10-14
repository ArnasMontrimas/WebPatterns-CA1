import DataAccessObjects.LoanUserBookDao;
import DataAccessObjects.UserDao;
import DataTransferObjects.User;

import java.util.ArrayList;

public class TestingLoanDAO {
    public static void main(String[] args) {
        UserDao userDao = new UserDao(Program.DATABASE);
        LoanUserBookDao loanUserBookDao = new LoanUserBookDao(Program.DATABASE);
        ArrayList<User> users = userDao.getAllUsers();
        User u = null;

        for(User us: users) {
            if(us.getId() == 7) {
                u = us;
                break;
            }
        }

        assert u != null; //Intellij wants me to do this -_-
        //System.out.println(loanUserBookDao.allLoansByUserId(u)); //Get All Active Loans For User Works
        //System.out.println(loanUserBookDao.allLoansSinceJoining(u)); //Get All Loans For User Since Joining Works
        //System.out.println(loanUserBookDao.loanBook("Computing Intro", 5, u)); //Loan a book works
        //System.out.println(loanUserBookDao.returnBook("Computing Intro", u)); //Returning a book works
    }

}
