package DataAccessObjects.Interfaces;

public interface UserDaoInterface {

    /**
     * Methods to do with Registering a member.
     */
    boolean validateUsername(String username);
    boolean validateEmail(String email);
    boolean validatePhonenumber(String phonenumber);
    boolean registerUser(String username,String password,String email,String phonenumber);


}
