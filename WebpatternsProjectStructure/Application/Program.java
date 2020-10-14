import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import DataAccessObjects.*;
import DataTransferObjects.Book;
import DataTransferObjects.User;

public class Program {

    public static final String DATABASE = "dundalk_library";

    private static String getCommand(Scanner userInput) {
        System.out.println("-------------------------------------\n" +
                "|Type 1: To Register !\n" +
                "|Type 2: To Login !\n" +
                "|Type 3: To view all the books in the library  !\n" +
                "|Type 4: To view all active loans !\n" +// only when logged in
                "|Type 5: To view all loans since joining the library !\n" +// only when logged in
                "|Type 6: To loan a book !\n" + // only when logged in
                "|Type 7: To return a book currently on loan!\n" + // only when logged in
                "|Type 8: To Logout\n" + // only when logged in
                "|Type 9: To terminate the system\n" + // only when logged in as Admin
                "-------------------------------------"
        );
        return userInput.nextLine();
    }

    private static void displayBook(Book b) {

        System.out.println("--------------------------------------------------\n" +
                "|Book Title: " + b.getBook_name() + "\n" +
                "|ISBN: "  + b.getBook_isbn() + "\n" +
                "|Edition: "  + b.getBook_edition() + "\n"  +
                "|Description " + b.getBook_description() + "\n" +
                "|Author " + b.getAuthor() + "\n" +
                "|Publisher " + b.getPublisher() + "\n" +
                "|Available: " + (b.getQuantityInStock() > 0 ? "Yes" : "No") + "\n" +
                "--------------------------------------------------"
        );
    }

    private static String validateString(Scanner userInput) {
        String var;
        var = userInput.nextLine();
        while (var.isBlank()){
            System.out.println("No blank spaces ! Try again.");
            var = userInput.nextLine();
        }
        return var;
    }

    public static void main(String[] args) {

        UserDao userDao = new UserDao(DATABASE);
        BookDao bookDao = new BookDao(DATABASE);
        Scanner userInput = new Scanner(System.in);
        boolean librarySystemOnline = true;
        //boolean validCommand = true;
        // Has to be internalized to french based on locale ?
        System.out.println("***** Welcome to the Dundalk Library service ! *****");

        int userID = 0;
        String userName;
        String passWord;
        String email;
        String phoneNumber;

        while (librarySystemOnline){
            String command = getCommand(userInput);

            switch (command){
                case "1":
                    System.out.println("Please type in your username.");
                    userName = validateString(userInput);
                    while (!userDao.validateUsername(userName)){
                        System.out.println("That username exists try again.");
                        userName = validateString(userInput);
                    }

                    System.out.println("Please type in your password.");
                    passWord = validateString(userInput);

                    System.out.println("Please type in your email address.");
                    email = validateString(userInput);
                    while (!userDao.validateEmail(email)){
                        System.out.println("That email exists try again.");
                        email = validateString(userInput);
                    }

                    System.out.println("Please type in your phone number.");
                    phoneNumber = validateString(userInput);
                    while (!userDao.validatePhonenumber(phoneNumber)){
                        System.out.println("That phone number already exists try again.");
                        phoneNumber = validateString(userInput);
                    }
                    // Finally after all validation insert a member into the table.
                    userDao.registerUser(userName,passWord,email,phoneNumber);
                    System.out.println("Your account has been registered !");
                    break;

                case "2":
                    System.out.println("Please type in your username.");
                    userName = validateString(userInput);
                    System.out.println("Please type in your password.");
                    passWord = validateString(userInput);
                    userID = userDao.validateLogin(userName,passWord);
                    if (userID == 0){
                        System.out.println("The username or password is incorrect please try again");
                    } else if (userID == -1){
                        System.out.println("The account has been disabled please create a new account");
                    } else {
                        System.out.println("Welcome" + " name of user here " + userID);

                    }
                    break;

                case "3":
                    for(Book b: bookDao.getAllBooks()) {
                        displayBook(b);
                    }
                    break;

                default:
                    System.out.println("Please pick a number between 1-10."); // internaliozed

            }

        }
    }
}