package DataAccessObjectsTests;

import DataTransferObjects.*;
import DataAccessObjects.*;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

/**
 * @author Sam Ponik
 */
class UserDaoTest {

    UserDao userDao = new UserDao("dundalk_library");
    UserDao userDaoTest = new UserDao("dundalk_library_test");

    /**
     * Junit Tests for all methods associated with UserDao
     */
    @Test
    void validateTakenUsernameTest() {

        // Testing for a username which is created and in the database
        // This should return false becasue that username is already created and in use.
        boolean nameExists = userDao.validateUsername("Sam");

        assertFalse(nameExists);
    }

    @Test
    void validateAvailableUsernameTest() {

        // Testing for a username which is available
        // This should return true becasue that username is available
        boolean nameExists = userDao.validateUsername("TEST_USER_NAME");

        assertTrue(nameExists);
    }

    @Test
    void validateTakenEmailTest() {

        // Testing for a email which is created and in the database
        // This should return false becasue the email is already created and in use.
        boolean emailExists = userDao.validateEmail("sam@gmail.com");

        assertFalse(emailExists);
    }

    @Test
    void validateAvailableEmailTest() {

        // Testing for a email which is available
        // This should return true becasue the email is available
        boolean emailExists = userDao.validateUsername("TEST_EMAIL@gmail.com");

        assertTrue(emailExists);
    }

    @Test
    void validateTakenPhonenumberTest() {

        // Testing for phonenumber which is created and in the database
        // This should return false becasue the phonenumber is already created and in use.
        boolean phoneExists = userDao.validatePhonenumber("0838568457");

        assertFalse(phoneExists);
    }

    @Test
    void validateAvailablePhonenumberTest() {

        // Testing for a email which is available
        // This should return true becasue the phonenumber is available
        boolean phoneExists = userDao.validatePhonenumber("9999999999");

        assertTrue(phoneExists);
    }

    @Test
    void insertMemberTest() {

        // Test to insert a new member into the testing environment database.
        // Validation of username email and phone would be done before those values would be used in this method
        // Thats why you can use the same String values becasue you are testing if the user CAN be inserted.
        // Unique ID will be added automatically with AUTO INCREMENT
        assertTrue(userDaoTest.registerUser("TEST_USER","test","test@gmail.com","0000000000"));
    }

    @Test
    void validateCorrectCredentialsTest() {

        // Should return 1 which is Sam's unique ID
        int correctID = 1;

        assertEquals(correctID,userDao.validateLogin("Sam","sam1"));
    }

    @Test
    void validateIncorrectCredentialsTest() {

        // Should return 0 since that username and password dont match
        int response = 0;

        assertEquals(response,userDao.validateLogin("Sam","TEST"));
    }


}