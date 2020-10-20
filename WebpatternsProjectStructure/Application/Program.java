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
import DataTransferObjects.Loan;
import DataTransferObjects.User;

public class Program {

    public static final String DATABASE = "dundalk_library";

    private static String getCommand(Scanner userInput,User user) {

        // Default View the user should only be able to see these 3 actions while not logged in
        if (user == null){
            System.out.println("-------------------------------------\n" +
                    "|Type EN for English or FR for french !\n" +
                    "|Type 1: To Register !\n" +
                    "|Type 2: To Login !\n"+
                    "|Type 3: To view all the books in the library  !\n" +
                    "-------------------------------------"
            );
        }
        // If the user has logged in as a Member
        if (user != null && user.getType().equals("Member")){
            System.out.println("-------------------------------------\n" +
                    "|Type EN for English or FR for french !\n" +
                    "|Logged In as: " +user.getUsername() +"\n"+
                    "|Type 3: To view all the books in the library  !\n" +
                    "|Type 4: To view all active loans !\n" +// only when logged in
                    "|Type 5: To view all loans since joining the library !\n" +// only when logged in
                    "|Type 6: To loan a book !\n" + // only when logged in
                    "|Type 7: To return a book currently on loan!\n" + // only when logged in
                    "|Type 8: To Logout\n" + // only when logged in
                    "-------------------------------------"
            );
        }

        // If the user has logged in as a Admin
        if (user != null && user.getType().equals("Admin")){
            System.out.println("-------------------------------------\n" +
                    "|Type EN for English or FR for french !\n" +
                    "|Logged In as Admin: " +user.getUsername() +"\n"+
                    "|Type 3: To view all the books in the library  !\n" +
                    "|Type 4: To view all active loans !\n" +
                    "|Type 5: To view all loans since joining the library !\n" +// only when logged in
                    "|Type 6: To loan a book !\n" + // only when logged in
                    "|Type 7: To return a book currently on loan!\n" + // only when logged in
                    "|Type 8: To Logout\n" + // only when logged in
                    "|Type 9: To add a book to the library !\n" + // only when logged in
                    "|Type 10: To increase the quantity of a book ! \n" + // only when logged in as Admin
                    "|Type 11: To disable a members account  ! \n" + // only when logged in as Admin
                    "-------------------------------------"
            );
        }

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

    private static void displayLoan(Loan l) {
        String activeLoan;
        if (l.getLoan_is_active() == 1){
            activeLoan = "True";
        } else {
            activeLoan = "False";
        }
        System.out.println("--------------------------------------------------\n" +
                "|Book Loaned: " + l.getBook_id().getBook_name() + "\n" +
                "|Loan Started: "  + l.getLoan_started() + "\n" +
                "|Return by Date: "  + l.getLoan_ends() + "\n"  +
                "|Active Loan: " + activeLoan + "\n" +
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
        LoanUserBookDao loanUserBookDao = new LoanUserBookDao(DATABASE);
        Scanner userInput = new Scanner(System.in);
        boolean librarySystemOnline = true;
        //boolean validCommand = true;
        // Has to be internalized to french based on locale ?
        System.out.println("***** Welcome to the Dundalk Library service ! *****");

        User user = null;
        int userID = 0;
        String userName;
        String passWord;
        String email;
        String phoneNumber;

        while (librarySystemOnline){
            String command = getCommand(userInput,user);

                switch (command){

                    case "EN":

                        break;

                    case "FR":

                        break;

                    case "1":
                        if (user == null){
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
                        } else {
                            System.out.println("Please pick an appropriate number  !");
                        }

                        break;

                    case "2":
                        if (user == null){
                            System.out.println("Please type in your username.");
                            userName = validateString(userInput);
                            System.out.println("Please type in your password.");
                            passWord = validateString(userInput);
                            userID = userDao.validateLogin(userName,passWord);
                            if (userID == 0){
                                System.out.println("The username or password is incorrect please try again");
                            } else if (userID == -1){
                                System.out.println("The account has been disabled please create a new account");
                                userID = 0;
                            } else {
                                user = userDao.getUserByID(userID);
                                System.out.println("Welcome " + user.getUsername() + " to the dundalk library !");
                            }
                        } else {
                            System.out.println("Please pick an appropriate number  !");
                        }
                        break;

                    case "3":
                        for(Book b: bookDao.getAllBooks()) {
                            displayBook(b);
                        }
                        break;

                    case "4":
                        if (user != null){
                            for(Loan l: loanUserBookDao.allLoansByUserId(user) ) {
                                displayLoan(l);
                            }
                        } else {
                            System.out.println("Please pick an appropriate number  !");
                        }
                        break;

                    case "5":
                        if (user != null){
                            if (loanUserBookDao.allLoansSinceJoining(user).isEmpty()){
                                System.out.println("You have made no loans yet ");
                            } else {
                                for(Loan l: loanUserBookDao.allLoansSinceJoining(user) ) {
                                    displayLoan(l);
                                }
                            }
                        } else {
                            System.out.println("Please pick an appropriate number  !");
                        }
                        break;

                    case "6":
                        if (user != null){
                            System.out.println("Please type in the name of the book to loan");
                            String bookName = validateString(userInput);
                            Book book = bookDao.findByName(bookName);
                            if (book == null){
                                System.out.println("That book does not exist");
                            } else {
                                System.out.println("Please type in the number of days to loan the book (Maximum 7 days)");
                                int loanDays = userInput.nextInt();

                                if(loanUserBookDao.loanBook(bookName,loanDays,user) == 1){
                                    System.out.println("You have loaned the book !");
                                } else if (loanUserBookDao.loanBook(bookName,loanDays,user) == -2){
                                    System.out.println("Books cant be loaned for longer than 7 days !");
                                } else if (loanUserBookDao.loanBook(bookName,loanDays,user) == -1){
                                    System.out.println("You already loan that book !");
                                } else if (loanUserBookDao.loanBook(bookName,loanDays,user) == 0){
                                    System.out.println("That book is out of stock !");
                                }
                            }
                        } else {
                            System.out.println("Please pick an appropriate number  !");
                        }

                        break;

                    case "7":
                        if (user != null){
                            System.out.println("Please type in the name of the book to return");
                            String bookName = validateString(userInput);
                            Book book = bookDao.findByName(bookName);
                            if (book == null){
                                System.out.println("That book does not exist");
                            } else {
                                if (loanUserBookDao.returnBook(bookName,user)){
                                    System.out.println("The book has been returned thanks !");
                                } else {
                                    System.out.println("You do not have that book loaned");
                                }
                            }
                        } else {
                            System.out.println("Please pick an appropriate number  !");
                        }
                        break;


                    case "8":
                        if (user != null){
                            System.out.println(user.getUsername() + " has been logged out !");
                            user = null;
                        }
                        break;

                    case "9":
                        if (user != null && user.getType().equals("Admin")){
                            System.out.println("Please type the title of the Book to add");
                            String title = validateString(userInput);
                            Book book = bookDao.findByName(title);
                            if (book != null){
                                System.out.println("A book with that name already exists");
                            } else {
                                System.out.println("Please type the ISBN of the  Book");
                                String ISBN = validateString(userInput);
                                System.out.println("Please type the Book edition");
                                String edition = validateString(userInput);
                                System.out.println("Please type the Book description");
                                String description = validateString(userInput);
                                System.out.println("Please type the Book author");
                                String author = validateString(userInput);
                                System.out.println("Please type the Book publisher");
                                String publisher = validateString(userInput);
                                System.out.println("Please type the quantity of the book to be added");
                                // TODO: 15/10/2020 // VALIDATION HERE NEEDED not only for string but for 0 quantity
                                String quantity = validateString(userInput);
                                bookDao.addBook(title,ISBN,edition,description,author,publisher,Integer.parseInt(quantity));
                                System.out.println("Your book as been added");
                            }

                        } else {
                            System.out.println("Please pick an appropriate number  !");
                        }
                        break;

                    case "10":
                        if (user != null && user.getType().equals("Admin")){
                            System.out.println("Please type the title of the Book to increase its quantity");
                            String title = validateString(userInput);
                            Book book = bookDao.findByName(title);
                            if (book == null){
                                System.out.println("No book by that name exists in the system");
                            } else {
                                // Book exists so ask for quantity and add it
                                System.out.println("Please type the quantity");
                                String quantity = validateString(userInput);
                                // TODO: 19/10/2020 just string validation needed here for int 
                                if (bookDao.addCopies(book.getBook_id(),Integer.parseInt(quantity))){
                                    System.out.println("Quantity increased");
                                } else {
                                    System.out.println("Quantity cant be 0");
                                }
                            }
                        } else {
                            System.out.println("Please pick an appropriate number  !");
                        }
                        break;

                    case "11":
                        if (user != null && user.getType().equals("Admin")){
                            System.out.println("Please type the members username to disable the account");
                            String username = validateString(userInput);
                            if (userDao.disableMembersAccount(username) == 0){
                                System.out.println("That username does not exist");
                            } else if (userDao.disableMembersAccount(username) == 1){
                                System.out.println("The account has been disabled");
                            } else if (userDao.disableMembersAccount(username) == -1){
                                System.out.println("Cant disable a admin account");
                            } else if (userDao.disableMembersAccount(username) == -2){
                                System.out.println("The member account was already disabled");
                            }
                        } else {
                            System.out.println("Please pick an appropriate number  !");
                        }
                        break;

                    default:
                        System.out.println("Please pick an appropriate number  !"); // internaliozed
                }
        }
    }
}