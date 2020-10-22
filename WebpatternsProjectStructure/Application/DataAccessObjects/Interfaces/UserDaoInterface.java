package DataAccessObjects.Interfaces;

import DataTransferObjects.Address;
import DataTransferObjects.User;

import java.util.ArrayList;

public interface UserDaoInterface {

    /**
     * Methods to do with Registering a member.
     */
    boolean validateUsername(String username);
    boolean validateEmail(String email);
    boolean validatePhonenumber(String phonenumber);
    boolean registerUser(String username, String password, String email, String phonenumber,int addressID);
    int insertAddress(String firstname, String lastname,String address,String city,String state,String country,String postalcode);

    /**
     * Checks if the username and password match and if so return the unique user ID the primary key.
     */
    int validateLogin(String username,String password);

    /**
     * Returns the User object by the unique id
     */
    User getUserByID(int userID);

    /**
     * Admin method to disable members accounts
     */
    int disableMembersAccount(String name);
}
