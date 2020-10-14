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
     * Get all users from database
     * @return ArrayList of users
     */
    ArrayList<User> getAllUsers();

}
