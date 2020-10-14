package DataAccessObjects.Interfaces;

import DataTransferObjects.User;

import java.util.ArrayList;

public interface UserDaoInterface {

    /**
     * Methods to do with Registering a member.
     */
    boolean validateUsername(String username);
    boolean validateEmail(String email);
    boolean validatePhonenumber(String phonenumber);
    boolean registerUser(String username,String password,String email,String phonenumber);

    /**
     * Checks if the username and password match and if so return the unique user ID the primary key.
     */
    int validateLogin(String username,String password);

    /**
     * This method queries the db for all users
     * @return all users from database
     * @author Arnas
     */
    ArrayList<User> getAllUsers();

}
