package DataAccessObjects;

import DataTransferObjects.*;
import DataAccessObjects.*;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

/**
 * @author Sam Ponik
 */
class UserDaoTest {

    UserDao userDaoTest = new UserDao("dundalk_library_test");

    /**
     * Junit Tests for all methods associated with UserDao
     */
    @Test
    void validateTakenUsernameTest() {

        // Testing for a username which is created and in the database
        // This should return false becasue that username is already created and in use.
        boolean nameExists = userDaoTest.validateUsername("Sam");

        assertFalse(nameExists);
    }

    @Test
    void validateAvailableUsernameTest() {

        // Testing for a username which is available
        // This should return true becasue that username is available
        boolean nameExists = userDaoTest.validateUsername("TEST_USER_NAME");

        assertTrue(nameExists);
    }

    @Test
    void validateTakenEmailTest() {

        // Testing for a email which is created and in the database
        // This should return false becasue the email is already created and in use.
        boolean emailExists = userDaoTest.validateEmail("sam@gmail.com");

        assertFalse(emailExists);
    }

    @Test
    void validateAvailableEmailTest() {

        // Testing for a email which is available
        // This should return true becasue the email is available
        boolean emailExists = userDaoTest.validateUsername("TEST_EMAIL@gmail.com");

        assertTrue(emailExists);
    }

    @Test
    void validateTakenPhonenumberTest() {

        // Testing for phonenumber which is created and in the database
        // This should return false becasue the phonenumber is already created and in use.
        boolean phoneExists = userDaoTest.validatePhonenumber("0838568457");

        assertFalse(phoneExists);
    }

    @Test
    void validateAvailablePhonenumberTest() {

        // Testing for a email which is available
        // This should return true becasue the phonenumber is available
        boolean phoneExists = userDaoTest.validatePhonenumber("9999999999");

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

        assertEquals(correctID,userDaoTest.validateLogin("Sam","sam1"));
    }

    @Test
    void validateIncorrectCredentialsTest() {

        // Should return 0 since that username and password dont match
        int response = 0;

        assertEquals(response,userDaoTest.validateLogin("Sam","TEST"));
    }

    @Test
    void validateUserObject() {

        // Should return 0 since that username and password dont match
        User user = new User(1,"Member","Sam","sam1","sam@gmail.com","0838568457","2020-10-12 17:13:03",true);
        userDaoTest.getUserByID(1);
        assertEquals(userDaoTest.getUserByID(1),user);
    }


}