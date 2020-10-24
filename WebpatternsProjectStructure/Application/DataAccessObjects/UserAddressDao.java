package DataAccessObjects;

import DataAccessObjects.Interfaces.UserAddressDaoInterface;
import DataTransferObjects.Address;
import Program.Program;
import DataTransferObjects.User;
import java.sql.*;

/**
 * @author Sam Ponik
 */
public class UserAddressDao extends Dao implements UserAddressDaoInterface {

    /**
     * Initialises a UserDao to access the specified database name
     * @param databaseName The name of the MySQL database to be accessed (this database should
     * be running on localhost and listening on port 3306).
     */
    public UserAddressDao(String databaseName) {
        super(databaseName);
    }

    /**
     * Returns true if the username is valid (if the username does not exist in the database)
     * @return boolean true/false
     * @param username The username to validate
     */
    @Override
    public boolean validateUsername(String username) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean validUsername = true;

        try{
            con = getConnection();
            String query = "SELECT username FROM users WHERE username = ?";
            ps = con.prepareStatement(query);
            ps.setString(1,username);
            rs = ps.executeQuery();

            if (rs.next()){
                // There is a user with that same username but returns true for Sam,sam so need to check again
                String name = rs.getString("username");
                if (name.equals(username)){
                    validUsername = false;
                }
            }
        }
        catch(SQLException ex){
            System.out.println(Program.bookMessages.getString("UserDao_Sql_Users"));
            ex.printStackTrace();
        }
        finally{
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.out.println(Program.bookMessages.getString("UserDao_ResultSet"));
                    ex.printStackTrace();
                }
            }
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println(Program.bookMessages.getString("UserDao_PreparedSt"));
                    ex.printStackTrace();
                }
            }
            if(con != null){
                freeConnection(con);
            }
        }
        return validUsername;
    }

    /**
     * Returns true if the email is valid false otherwise if a email like that exists in the database already
     * @return boolean true/false
     * @param email The email to validate
     */
    @Override
    public boolean validateEmail(String email) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean validEmail = true;

        try{
            con = getConnection();
            String query = "SELECT email FROM users WHERE email = ?";
            ps = con.prepareStatement(query);
            ps.setString(1,email);
            rs = ps.executeQuery();

            if (rs.next()){
                String e = rs.getString("email");
                if (e.equals(email)){
                    validEmail = false;
                }
            }
        }
        catch(SQLException ex){
            System.out.println(Program.bookMessages.getString("UserDao_Sql_Users"));
            ex.printStackTrace();
        }
        finally{
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.out.println(Program.bookMessages.getString("UserDao_ResultSet"));
                    ex.printStackTrace();
                }
            }
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println(Program.bookMessages.getString("UserDao_PreparedSt"));
                    ex.printStackTrace();
                }
            }
            if(con != null){
                freeConnection(con);
            }
        }
        return validEmail;
    }

    /**
     * Returns true if the phone number is valid false otherwise if a phone number like that exists in the database already
     * @return boolean true/false
     * @param phonenumber The phone number to validate
     */
    @Override
    public boolean validatePhonenumber(String phonenumber) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean validPhone = true;

        try{
            con = getConnection();
            String query = "SELECT phoneNumber FROM users WHERE phoneNumber = ?";
            ps = con.prepareStatement(query);
            ps.setString(1,phonenumber);
            rs = ps.executeQuery();

            if (rs.next()){
                String phoneNum = rs.getString("phoneNumber");
                if (phoneNum.equals(phonenumber)){
                    validPhone = false;
                }
            }
        }
        catch(SQLException ex){
            System.out.println(Program.bookMessages.getString("UserDao_Sql_Users"));
            ex.printStackTrace();
        }
        finally{
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.out.println(Program.bookMessages.getString("UserDao_ResultSet"));
                    ex.printStackTrace();
                }
            }
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println(Program.bookMessages.getString("UserDao_PreparedSt"));
                    ex.printStackTrace();
                }
            }
            if(con != null){
                freeConnection(con);
            }
        }
        return validPhone;
    }

    /**
     * Returns true if the member was inserted into the database.(Registered)
     * @return boolean true/false
     * @param username The username for the account
     * @param email The email for the account
     * @param password The password for the account
     * @param phonenumber The phone number for the account
     * @param addressID The foreign key to link the user to his address
     */
    @Override
    public boolean registerUser(String username, String password, String email, String phonenumber,int addressID) {
        Connection con = null;
        PreparedStatement ps = null;
        int rowsAffected = 0;

        try{
            con = getConnection();
            ps = con.prepareStatement("INSERT into users VALUES(NULL,'Member',?,?,?,?,current_timestamp(),true,?)");
            ps.setString(1,username);
            ps.setString(2,password);
            ps.setString(3,email);
            ps.setString(4,phonenumber);
            ps.setInt(5,addressID);
            rowsAffected = ps.executeUpdate();

        }catch (SQLException e) {
            System.out.println(Program.bookMessages.getString("UserDao_Sql_Users_Insert"));
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println(Program.bookMessages.getString("UserDao_Sql_Finally"));
                e.printStackTrace();
            }
        }
        return rowsAffected != 0;
      }

    /**
     * Method to insert the users address into the address table and return the users address primary key for the users table
     * @return The primary key for the address to be used to link the user with the users address
     * @param firstname The users firstname
     * @param lastname The users lastname
     * @param address  The users address
     * @param city The users city
     * @param state The state if the user is american null otherwise
     * @param country The users country
     * @param postalcode The users postal code
     */
    @Override
    public int insertAddress(String firstname,String lastname,String address,String address2,String city,String state,String country,String postalcode) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs;

        String query;
        int addressID = 0;

        try{
           query = "INSERT INTO address VALUES(NULL,?,?,?,?,?,?,?,?)";
           con = getConnection();
           ps = con.prepareStatement(query);

           ps.setString(1,firstname);
           ps.setString(2,lastname);
           ps.setString(3,address);
           ps.setString(4,address2);
           ps.setString(5,city);
           ps.setString(6,state);
           ps.setString(7,country);
           ps.setString(8,postalcode);

            ps.executeUpdate();
            // Get the last key which is associated with the insert to be used to link address/users
            query = "SELECT max(address_id) from address";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            if (rs.next()){
                addressID = rs.getInt("max(address_id)");
            }

        }catch (SQLException e) {
            System.out.println(Program.bookMessages.getString("UserDao_AddressInsert"));
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println(Program.bookMessages.getString("UserDao_Sql_Finally"));
                e.printStackTrace();
            }
        }
        return addressID;
    }

    /**
     * @return Returns the unique ID of the user if password/username match else return 0 if the account is disabled by admin return -1
     * @param username The username for login.
     * @param password The password for login.
     */
    @Override
    public int validateLogin(String username, String password) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int ID = 0;

        try{
            con = getConnection();
            String query = "SELECT id,activeAccount FROM users WHERE username = ? AND password = ?";
            ps = con.prepareStatement(query);
            ps.setString(1,username);
            ps.setString(2,password);
            rs = ps.executeQuery();

            if (rs.next()){
                boolean validAccount = rs.getBoolean("activeAccount");
                if (validAccount){
                    ID = rs.getInt("id");
                } else {
                    ID = -1;
                }
            }
        }
        catch(SQLException ex){
            System.out.println(Program.bookMessages.getString("UserDao_Sql_Users"));
            ex.printStackTrace();
        }
        finally{
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.out.println(Program.bookMessages.getString("UserDao_ResultSet"));
                    ex.printStackTrace();
                }
            }
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println(Program.bookMessages.getString("UserDao_PreparedSt"));
                    ex.printStackTrace();
                }
            }
            if(con != null){
                freeConnection(con);
            }
        }
        return ID;
    }

    /**
     * Gets the user object by the primary key which is the ID
     * @return the User object
     * @param userID The id of the user
     */
    @Override
    public User getUserByID(int userID) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;
        Address address = null;
        String query;
        int addressID = 0;
        try{
            con = getConnection();
            query = "SELECT address FROM users WHERE id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1,userID);
            rs = ps.executeQuery();
            if (rs.next()){
                addressID = rs.getInt("address");
            }

            query = "SELECT * FROM address WHERE address_id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1,addressID);
            rs = ps.executeQuery();

            if (rs.next()){
                address = new Address(
                rs.getInt("address_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("address1"),
                rs.getString("address2"),
                rs.getString("city"),
                rs.getString("state"),
                rs.getString("country"),
                rs.getString("postalcode")
               );
            }
            query = "SELECT * FROM users WHERE id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1,userID);
            rs = ps.executeQuery();

            if (rs.next()){
                user = new User(userID,
                        rs.getString("type"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("phoneNumber"),
                        rs.getString("dateRegistered"),
                        rs.getBoolean("activeAccount"),
                        address
                );
            }
        }
        catch(SQLException ex){
            System.out.println(Program.bookMessages.getString("UserDao_Sql_Users"));
            ex.printStackTrace();
        }
        finally{
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.out.println(Program.bookMessages.getString("UserDao_ResultSet"));
                    ex.printStackTrace();
                }
            }
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println(Program.bookMessages.getString("UserDao_PreparedSt"));
                    ex.printStackTrace();
                }
            }
            if(con != null){
                freeConnection(con);
            }
        }
        //System.out.println(user.toString());
        return user;
    }

    /**
     * Admin method to disable a members account
     * @return Returns 1 if successful 0 if username does not exist -1 if tried to disable a admins account
     *  and -2 if the account was already disabled
     * @param name The members username
     */
    @Override
    public int disableMembersAccount(String name) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int result = 0;

        try{
            con = getConnection();
            ps = con.prepareStatement("Select * from users where username = ?");
            ps.setString(1,name);
            rs = ps.executeQuery();
            // The username Exists on the system
            if (rs.next()){
                // The user is a admin so cant disable
                if (rs.getString("type").equals("Admin")){
                    result = -1;
                } else {
                    // The account is a member account and its not disabled
                    if (rs.getBoolean("activeAccount")){
                        ps = con.prepareStatement("UPDATE users SET activeAccount = 0 WHERE username = ?");
                        ps.setString(1,name);
                        ps.executeUpdate();
                        result = 1;
                    } else {
                        // Account is already disabled
                        result = -2;
                    }
                }
            }
        }
        catch(SQLException ex){
            System.out.println(Program.bookMessages.getString("UserDao_Sql_Users"));
            ex.printStackTrace();
        }
        finally{
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.out.println(Program.bookMessages.getString("UserDao_ResultSet"));
                    ex.printStackTrace();
                }
            }
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println(Program.bookMessages.getString("UserDao_PreparedSt"));
                    ex.printStackTrace();
                }
            }
            if(con != null){
                freeConnection(con);
            }
        }
        return result;
    }
}


