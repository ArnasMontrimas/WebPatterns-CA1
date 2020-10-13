package DataAccessObjects.Interfaces;

import DataAccessObjects.Dao;

public class UserDao extends Dao implements UserDaoInterface {

    public UserDao(String databaseName) {
        super(databaseName);
    }

    @Override
    public boolean validateUsername(String username) {
        return false;
    }

    @Override
    public boolean validateEmail(String email) {
        return false;
    }

    @Override
    public boolean validatePhonenumber(String phonenumber) {
        return false;
    }

    @Override
    public boolean registerUser(String username, String password, String email, String phonenumber) {
        return false;
    }

}
