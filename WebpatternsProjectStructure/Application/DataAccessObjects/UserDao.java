package DataAccessObjects;

import DataAccessObjects.Interfaces.UserDaoInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDao extends Dao implements UserDaoInterface {

    /**
     * Initialises a UserDao to access the specified database name
     * @param databaseName The name of the MySQL database to be accessed (this database should
     * be running on localhost and listening on port 3306).
     */
    public UserDao(String databaseName) {
        super(databaseName);
    }

    /**
     * Returns true if the username is valid (if the username does not exist in the database)
     * @return boolean true/false
     * @param username The username
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
            System.out.println("An exception occurred while querying "
                    + "the users table in the validateUsername() method\n"
                    + ex.getMessage());
        }
        finally{
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.out.println("Exception when closing result set" );
                    ex.printStackTrace();
                }
            }
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Exception when closing prepared statement" );
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
     * @param email The email
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
            System.out.println("An exception occurred while querying "
                    + "the users table in the validateEmail() method\n"
                    + ex.getMessage());
        }
        finally{
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.out.println("Exception when closing result set" );
                    ex.printStackTrace();
                }
            }
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Exception when closing prepared statement" );
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
     * @param phonenumber The phone number
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
            System.out.println("An exception occurred while querying "
                    + "the users table in the validatePhonenumber() method\n"
                    + ex.getMessage());
        }
        finally{
            if(rs != null){
                try {
                    rs.close();
                } catch (SQLException ex) {
                    System.out.println("Exception when closing result set" );
                    ex.printStackTrace();
                }
            }
            if(ps != null){
                try {
                    ps.close();
                } catch (SQLException ex) {
                    System.out.println("Exception when closing prepared statement" );
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
     * Returns true if the phone number is valid false otherwise if a phone number like that exists in the database already
     * @return boolean true/false
     * @param username The username for the account
     * @param email The email for the account
     * @param password The password for the account
     * @param phonenumber The phone number for the account
     */
    @Override
    public boolean registerUser(String username, String password, String email, String phonenumber) {
        Connection con = null;
        PreparedStatement ps = null;
        int rowsAffected = 0;

        try{
            con = getConnection();
            ps = con.prepareStatement("INSERT into users VALUES(NULL,'Member',?,?,?,?,current_timestamp(),true)");
            ps.setString(1,username);
            ps.setString(2,password);
            ps.setString(3,email);
            ps.setString(4,phonenumber);
            rowsAffected = ps.executeUpdate();

        }catch (SQLException e) {
            System.out.println("Exception occured while inserting into users table: " + e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    freeConnection(con);
                }
            } catch (SQLException e) {
                System.out.println("Exception occured in the finally section of the registerUser() method: " + e.getMessage());
            }
        }
        return rowsAffected != 0;
      }
    }


