package Program;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import DataAccessObjects.*;
import DataTransferObjects.Book;
import DataTransferObjects.Loan;
import DataTransferObjects.User;

/**
 * @author Sam Ponik
 */
public class Program {

    // Default English and static to reduce huge amount of code duplication
    public static final String DATABASE = "dundalk_library";

    // If these 3 were not static then you would have to pass them into methods as parameters
    public static Locale chosenLocale = new Locale("en","gb");
    public static ResourceBundle bookMessages = ResourceBundle.getBundle("Languages.Book",chosenLocale);
    public static ResourceBundle globalMessages = ResourceBundle.getBundle("Languages.Global",chosenLocale);

    /**
     * This method takes in the input from the user and checks if the user is logged in or member/admin
     * And it will display the menu for that user
     * @param userInput The scanner input
     * @param user The user object null if not logged in
     */
    private static String getCommand(Scanner userInput,User user) {

        // Default View the user should only be able to see these 3 actions while not logged in
        if (user == null){
            System.out.println(globalMessages.getString("main_defaultView"));
        }
        // If the user has logged in as a Member
        if (user != null && user.getType().equals("Member")){
            System.out.printf(globalMessages.getString("main_memberView"),user.getUsername());
        }
        // If the user has logged in as a Admin
        if (user != null && user.getType().equals("Admin")){
            System.out.printf(globalMessages.getString("main_adminView"),user.getUsername());
        }
        return userInput.nextLine();
    }

    /**
     * This method will display the book info in a user friendly way
     * @param b The book object
     */
    private static void displayBook(Book b) {
        // Display Book Info
        System.out.printf(bookMessages.getString("main_displayBook"),b.getBook_name(),b.getBook_isbn(),b.getBook_edition(),b.getBook_description(),b.getAuthor(),b.getPublisher(),(b.getQuantityInStock() > 0 ? "Yes" : "No"));
    }

    /**
     * This method will display the loan in a user friendly way
     * @param l The loan object
     */
    private static void displayLoan(Loan l) {

        String pattern = "E, MMM dd yyyy HH:mm:ss";
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat(pattern,chosenLocale);
        Date loanStartedDate = null;
        Date loanEndDate = null;
        try {
            loanStartedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(l.getLoan_started());
            loanEndDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(l.getLoan_ends());
        } catch (ParseException e) {
            System.out.println(globalMessages.getString("main_parse"));
            e.printStackTrace();
        }
        String started = simpleDateFormat.format(loanStartedDate);
        String end = simpleDateFormat.format(loanEndDate);

        String activeLoan;
        if (l.getLoan_returned() == null){
            activeLoan = globalMessages.getString("main_true");
        } else {
            activeLoan = globalMessages.getString("main_false");
        }
        // Store the fine here if there is any to two decimal places
        String fine = String.format("%.2f",l.calculateLoanFine());
        System.out.printf(globalMessages.getString("main_displayLoan"),l.getBook_id().getBook_name(),started,end,activeLoan,fine);
    }

    /**
     * This method validates string input from the user
     * @param userInput The scanner input
     */
    private static String validateString(Scanner userInput) {
        String var;
        var = userInput.nextLine();
        while (var.isBlank()){
            System.out.println(globalMessages.getString("main_Blank"));
            var = userInput.nextLine();
        }
        return var;
    }

    public static void main(String[] args) {

        UserAddressDao userAddressDao = new UserAddressDao(DATABASE);
        BookDao bookDao = new BookDao(DATABASE);
        LoanUserBookDao loanUserBookDao = new LoanUserBookDao(DATABASE);
        Scanner userInput = new Scanner(System.in);
        boolean librarySystemOnline = true;

        System.out.println(globalMessages.getString("main_dundalk"));

        User user = null;
        int userID = 0;
        String userName;
        String passWord;

        while (librarySystemOnline){
            String command = getCommand(userInput,user);

                switch (command){

                    case "EN":
                        chosenLocale = new Locale("en","gb");
                        bookMessages = ResourceBundle.getBundle("Languages.Book",chosenLocale);
                        globalMessages = ResourceBundle.getBundle("Languages.Global",chosenLocale);
                        break;

                    case "FR":
                        chosenLocale = new Locale("fr","fr");
                        bookMessages = ResourceBundle.getBundle("Languages.Book",chosenLocale);
                        globalMessages = ResourceBundle.getBundle("Languages.Global",chosenLocale);
                        break;

                    case "1":
                        registerUserCase(userAddressDao, userInput, user);
                        break;

                    case "2":
                        if (user == null){
                            System.out.println(globalMessages.getString("main_TypeName"));
                            userName = validateString(userInput);
                            System.out.println(globalMessages.getString("main_TypePass"));
                            passWord = validateString(userInput);
                            userID = userAddressDao.validateLogin(userName,passWord);
                            if (userID == 0){
                                System.out.println(globalMessages.getString("main_BadLogin"));
                            } else if (userID == -1){
                                System.out.println(globalMessages.getString("main_BadAccount"));
                                userID = 0;
                            } else {
                                user = userAddressDao.getUserByID(userID);
                                System.out.printf(globalMessages.getString("main_Welcome"),user.getUsername());
                            }
                        } else {
                            System.out.println(globalMessages.getString("main_Default"));
                        }
                        break;

                    case "3":
                        for(Book b: bookDao.getAllBooks()) {
                            displayBook(b);
                        }
                        break;

                    case "4":
                        activeLoansCase(loanUserBookDao, user);
                        break;

                    case "5":
                        allLoansCase(loanUserBookDao, user);
                        break;

                    case "6":
                        loanBookCase(bookDao, loanUserBookDao, userInput, user);
                        break;

                    case "7":
                        returnBookCase(bookDao, loanUserBookDao, userInput, user);
                        break;

                    case "8":
                        if (user != null){
                            System.out.printf(globalMessages.getString("main_Logout"),user.getUsername());
                            user = null;
                        }
                        break;

                    case "9":
                        addBookCase(bookDao, userInput, user);
                        break;

                    case "10":
                        increaseQuantityBookCase(bookDao, userInput, user);
                        break;

                    case "11":
                        disableAccountCase(userAddressDao, userInput, user);
                        break;

                    case "12":
                        decreaseQuantityBookCase(bookDao, userInput, user);
                        break;

                    default:
                        System.out.println(globalMessages.getString("main_Default"));
                }
        }
    }

    private static void decreaseQuantityBookCase(BookDao bookDao, Scanner userInput, User user) {
        if (user != null && user.getType().equals("Admin")){
            System.out.println(globalMessages.getString("main_DecBookQty"));
            String title = validateString(userInput);
            Book book = bookDao.findByName(title);
            if (book == null){
                System.out.println(globalMessages.getString("main_NoBookByName"));
            } else {
                System.out.println(globalMessages.getString("main_Quantity"));
                String quantity = validateString(userInput);
                if (bookDao.removeCopies(book.getBook_id(),Integer.parseInt(quantity))){
                    System.out.println(globalMessages.getString("main_QtyDec"));
                } else {
                    System.out.println(globalMessages.getString("main_QtyDenied"));
                }
            }
        } else {
            System.out.println(globalMessages.getString("main_Default"));
        }
    }

    private static void disableAccountCase(UserAddressDao userAddressDao, Scanner userInput, User user) {
        if (user != null && user.getType().equals("Admin")){
            System.out.println(globalMessages.getString("main_TypeMember"));
            String username = validateString(userInput);
            if (userAddressDao.disableMembersAccount(username) == 0){
                System.out.println(globalMessages.getString("main_NoUser"));
            } else if (userAddressDao.disableMembersAccount(username) == 1){
                System.out.println(globalMessages.getString("main_AccDisabled"));
            } else if (userAddressDao.disableMembersAccount(username) == -1){
                System.out.println(globalMessages.getString("main_AdminDisable"));
            } else if (userAddressDao.disableMembersAccount(username) == -2){
                System.out.println(globalMessages.getString("main_AlreadyDisabled"));
            }
        } else {
            System.out.println(globalMessages.getString("main_Default"));
        }
    }

    private static void increaseQuantityBookCase(BookDao bookDao, Scanner userInput, User user) {
        if (user != null && user.getType().equals("Admin")){
            System.out.println(globalMessages.getString("main_AddBookQty"));
            String title = validateString(userInput);
            Book book = bookDao.findByName(title);
            if (book == null){
                System.out.println(globalMessages.getString("main_NoBookByName"));
            } else {
                // Book exists so ask for quantity and add it
                System.out.println(globalMessages.getString("main_Quantity"));
                String quantity = validateString(userInput);
                // TODO: 19/10/2020 just string validation needed here for int
                if (bookDao.addCopies(book.getBook_id(),Integer.parseInt(quantity))){
                    System.out.println(globalMessages.getString("main_QtyInc"));
                } else {
                    System.out.println(globalMessages.getString("main_QtyDenied"));
                }
            }
        } else {
            System.out.println(globalMessages.getString("main_Default"));
        }
    }

    private static void addBookCase(BookDao bookDao, Scanner userInput, User user) {
        if (user != null && user.getType().equals("Admin")){
            System.out.println(globalMessages.getString("main_AddBook"));
            String title = validateString(userInput);
            Book book = bookDao.findByName(title);
            if (book != null){
                System.out.println(globalMessages.getString("main_BookExists"));
            } else {
                System.out.println(globalMessages.getString("main_BookISBN"));
                String ISBN = validateString(userInput);
                System.out.println(globalMessages.getString("main_Edition"));
                String edition = validateString(userInput);
                System.out.println(globalMessages.getString("main_Desc"));
                String description = validateString(userInput);
                System.out.println(globalMessages.getString("main_Author"));
                String author = validateString(userInput);
                System.out.println(globalMessages.getString("main_Publisher"));
                String publisher = validateString(userInput);
                System.out.println(globalMessages.getString("main_BookQuantity"));
                // TODO: 15/10/2020 // VALIDATION HERE NEEDED not only for string but for 0 quantity
                String quantity = validateString(userInput);
                bookDao.addBook(title,ISBN,edition,description,author,publisher,Integer.parseInt(quantity));
                System.out.println(globalMessages.getString("main_BookAdded"));
            }
        } else {
            System.out.println(globalMessages.getString("main_Default"));
        }
    }

    private static void returnBookCase(BookDao bookDao, LoanUserBookDao loanUserBookDao, Scanner userInput, User user) {
        if (user != null){
            System.out.println(globalMessages.getString("main_ReturnBook"));
            String bookName = validateString(userInput);
            Book book = bookDao.findByName(bookName);
            if (book == null){
                System.out.println(globalMessages.getString("main_NoBook"));
            } else {
                if (loanUserBookDao.returnBook(bookName, user)){
                    System.out.println(globalMessages.getString("main_Returned"));
                } else {
                    System.out.println(globalMessages.getString("main_NoLoan"));
                }
            }
        } else {
            System.out.println(globalMessages.getString("main_Default"));
        }
    }

    private static void loanBookCase(BookDao bookDao, LoanUserBookDao loanUserBookDao, Scanner userInput, User user) {
        if (user != null){
            System.out.println(globalMessages.getString("main_loanBook"));
            String bookName = validateString(userInput);
            Book book = bookDao.findByName(bookName);
            if (book == null){
                System.out.println(globalMessages.getString("main_NoBook"));
            } else {
                System.out.println(globalMessages.getString("main_LoanDays"));
                // TODO: 21/10/2020 validation needed for string here like this or the scanner buffer will jump to next line
                int loanDays = Integer.parseInt(validateString(userInput));

                if(loanUserBookDao.loanBook(bookName,loanDays, user) == 1){
                    System.out.println(globalMessages.getString("main_Loaned"));
                } else if (loanUserBookDao.loanBook(bookName,loanDays, user) == -2){
                    System.out.println(globalMessages.getString("main_LoanVal"));
                } else if (loanUserBookDao.loanBook(bookName,loanDays, user) == -1){
                    System.out.println(globalMessages.getString("main_LoanAlready"));
                } else if (loanUserBookDao.loanBook(bookName,loanDays, user) == 0){
                    System.out.println(globalMessages.getString("main_Stock"));
                }else if (loanUserBookDao.loanBook(bookName,loanDays, user) == -3){
                    System.out.println(globalMessages.getString("main_maxLoans"));
                }
            }
        } else {
            System.out.println(globalMessages.getString("main_Default"));
        }
    }

    private static void allLoansCase(LoanUserBookDao loanUserBookDao, User user) {
        if (user != null){
            if (loanUserBookDao.allLoansSinceJoining(user).isEmpty()){
                System.out.println(globalMessages.getString("main_noLoans"));
            } else {
                for(Loan l: loanUserBookDao.allLoansSinceJoining(user) ) {
                    displayLoan(l);
                }
            }
        } else {
            System.out.println(globalMessages.getString("main_Default"));
        }
    }

    private static void activeLoansCase(LoanUserBookDao loanUserBookDao, User user) {
        if (user != null){
            if (loanUserBookDao.allLoansByUserId(user).isEmpty()){
                System.out.println(globalMessages.getString("main_noLoans"));
            } else {
                for(Loan l: loanUserBookDao.allLoansByUserId(user) ) {
                    displayLoan(l);
                }
            }
        } else {
            System.out.println(globalMessages.getString("main_Default"));
        }
    }

    private static void registerUserCase(UserAddressDao userAddressDao, Scanner userInput, User user) {
        String userName;
        String email;
        String passWord;
        String phoneNumber;
        if (user == null){
            System.out.println(globalMessages.getString("main_TypeName"));
            userName = validateString(userInput);
            while (!userAddressDao.validateUsername(userName)){
                System.out.println(globalMessages.getString("main_NameExists"));
                userName = validateString(userInput);
            }

            System.out.println(globalMessages.getString("main_TypePass"));
            passWord = validateString(userInput);

            System.out.println(globalMessages.getString("main_TypeEmail"));
            email = validateString(userInput);
            while (!userAddressDao.validateEmail(email)){
                System.out.println(globalMessages.getString("main_EmailExists"));
                email = validateString(userInput);
            }

            System.out.println(globalMessages.getString("main_TypePhone"));
            phoneNumber = validateString(userInput);
            while (!userAddressDao.validatePhonenumber(phoneNumber)){
                System.out.println(globalMessages.getString("main_PhoneExists"));
                phoneNumber = validateString(userInput);
            }
            System.out.println(globalMessages.getString("main_address")+"\n");
            System.out.println(globalMessages.getString("main_FirstName"));
            String firstName = validateString(userInput);
            System.out.println(globalMessages.getString("main_LastName"));
            String lastName = validateString(userInput);
            System.out.println(globalMessages.getString("main_addressDetails"));
            String address = validateString(userInput);
            String address2 = null;
            System.out.println(globalMessages.getString("main_extraAddress"));
            String moreInfo = validateString(userInput);
            if (moreInfo.equalsIgnoreCase("Y")){
                System.out.println(globalMessages.getString("main_extraAddressInfo"));
                address2 = validateString(userInput);
            }
            System.out.println(globalMessages.getString("main_city"));
            String city = validateString(userInput);
            System.out.println(globalMessages.getString("main_country"));
            String country = validateString(userInput);
            String state = null;

            if (country.equalsIgnoreCase("America")){
                System.out.println(globalMessages.getString("main_state"));
                state = validateString(userInput);
            }
            System.out.println(globalMessages.getString("main_postal"));
            String postalCode = validateString(userInput);

            // Insert the Address Details First
            int addressID = userAddressDao.insertAddress(firstName,lastName,address,address2,city,state,country,postalCode);

            // Finally after all validation insert a member into the table.
            userAddressDao.registerUser(userName,passWord,email,phoneNumber,addressID);
            System.out.println(globalMessages.getString("main_Registered"));
        } else {
            System.out.println(globalMessages.getString("main_Default"));
        }
    }
}