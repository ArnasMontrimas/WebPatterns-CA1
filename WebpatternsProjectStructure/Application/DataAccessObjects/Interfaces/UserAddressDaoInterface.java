package DataAccessObjects.Interfaces;
import DataTransferObjects.User;

/**
 * @author Sam Ponik
 */
public interface UserAddressDaoInterface {

    /**
     * Methods to do with Registering a member.
     */
    boolean validateUsername(String username);
    boolean validateEmail(String email);
    boolean validatePhonenumber(String phonenumber);
    boolean registerUser(String username, String password, String email, String phonenumber,int addressID);
    int insertAddress(String firstname, String lastname,String address,String address2,String city,String state,String country,String postalcode);

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
