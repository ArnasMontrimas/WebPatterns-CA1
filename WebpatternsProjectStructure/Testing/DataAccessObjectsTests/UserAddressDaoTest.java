package DataAccessObjectsTests;

import DataTransferObjects.*;
import DataAccessObjects.*;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

/**
 * @author Sam Ponik
 */
class UserAddressDaoTest {

    UserAddressDao userAddressDaoTest = new UserAddressDao("dundalk_library_test");

    /**
     * Junit Tests for all methods associated with UserDao
     */

    /**
     * Testing for a username which is created and in the database
     * This should return false because that username is already created and in use.
     */
    @Test
    void validateTakenUsernameTest() {
        boolean nameExists = userAddressDaoTest.validateUsername("Sam");
        assertFalse(nameExists);
    }

    /**
     * Testing for a username which is available
     * This should return true because that username is available
     */
    @Test
    void validateAvailableUsernameTest() {
        boolean nameExists = userAddressDaoTest.validateUsername("TEST_USER_NAME");
        assertTrue(nameExists);
    }

    /**
     * Testing for a email which is created and in the database
     * This should return false because the email is already created and in use.
     */
    @Test
    void validateTakenEmailTest() {
        boolean emailExists = userAddressDaoTest.validateEmail("sam@gmail.com");
        assertFalse(emailExists);
    }

    /**
     * Testing for a email which is available
     * This should return true because the email is available
     */
    @Test
    void validateAvailableEmailTest() {
        boolean emailExists = userAddressDaoTest.validateUsername("TEST_EMAIL@gmail.com");
        assertTrue(emailExists);
    }

    /**
     * Testing for phone number which is created and in the database
     * This should return false because the phone number is already created and in use.
     */
    @Test
    void validateTakenPhonenumberTest() {
        boolean phoneExists = userAddressDaoTest.validatePhonenumber("0838568457");
        assertFalse(phoneExists);
    }

    /**
     * Testing for a phone number which is available
     * This should return true because the phone number is available
     */
    @Test
    void validateAvailablePhonenumberTest() {
        boolean phoneExists = userAddressDaoTest.validatePhonenumber("9999999999");
        assertTrue(phoneExists);
    }

    /**
     * Test to insert a new member into the testing environment database.
     * Validation of username email and phone would be done before those values would be used in this method
     * Testing if the account CAN be INSERTED
     * Unique ID will be added automatically with AUTO INCREMENT
     */
    @Test
    void insertMemberTest() {
        int addressKey = userAddressDaoTest.insertAddress("INSERT_TEST_FNAME","INSERT_TEST_LNAME","INSERT_TEST_ADDRESS","INSERT_TEST_ADDRESS2","INSERT_TEST_CITY","INSERT_TEST_STATE","INSERT_TEST_COUNTRY","01010101");
        assertTrue(userAddressDaoTest.registerUser("TEST_USER","test","test@gmail.com","0000000000",addressKey));
    }

    /**
     * Test to validate correct login.
     * Should return 1 which is Sam's unique ID
     */
    @Test
    void validateCorrectCredentialsTest() {
        int correctID = 1;
        assertEquals(correctID, userAddressDaoTest.validateLogin("Sam","sam1"));
    }

    /**
     * Test to validate incorrect login.
     * Should return 0 since that username and password dont match
     */
    @Test
    void validateIncorrectCredentialsTest() {
        int response = 0;
        assertEquals(response, userAddressDaoTest.validateLogin("Sam","TEST"));
    }

    /**
     * Test to validate the user object
     * Should return true since the User by that ID 1 is the same as the user object
     */
    @Test
    void validateUserObjectTest() {
        Address address = new Address(1,"Sam","Smith","Cavan Town","Cavan Green Hills","Cavan",null,"Ireland","141478");
        User user = new User(1,"Member","Sam","sam1","sam@gmail.com","0838568457","2020-10-12 17:13:03",true,address);
        assertEquals(userAddressDaoTest.getUserByID(1),user);
    }

    /**
     * Test to try to disable admins account
     * Should return -1 because u cant disable a admins account
     */
    @Test
    void disableAdminAccountTest() {
        assertEquals(-1, userAddressDaoTest.disableMembersAccount("AdminBob"));
    }

    /**
     * Test to try to disable a user account that doesnt exist
     * Should return 0 because the user name does not exist
     */
    @Test
    void disableUserAccountTest() {
        assertEquals(0, userAddressDaoTest.disableMembersAccount("TEST_USER_NAME"));
    }

    /**
     * Test to try to disable a user account that is already disabled
     * Should return -2 becasue the account is already disabled
     */
    @Test
   void disableUserAccountTest2() {
        assertEquals(-2,userAddressDaoTest.disableMembersAccount("Sam1"));
   }
}