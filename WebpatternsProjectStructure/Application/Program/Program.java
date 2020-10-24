package Program;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import DataAccessObjects.*;
import DataTransferObjects.Book;
import DataTransferObjects.Loan;
import DataTransferObjects.User;

public class Program {

    // Default English
    public static final String DATABASE = "dundalk_library";
    public static Locale chosenLocale = new Locale("en","gb");
    public static ResourceBundle bookMessages = ResourceBundle.getBundle("Languages.Book",chosenLocale);
    public static ResourceBundle globalMessages= ResourceBundle.getBundle("Languages.Global",chosenLocale);

    private static String getCommand(Scanner userInput,User user,ResourceBundle language) {

        // Default View the user should only be able to see these 3 actions while not logged in
        if (user == null){
            System.out.println(language.getString("main_defaultView"));
        }
        // If the user has logged in as a Member
        if (user != null && user.getType().equals("Member")){
            System.out.printf(language.getString("main_memberView"),user.getUsername());
        }
        // If the user has logged in as a Admin
        if (user != null && user.getType().equals("Admin")){
            System.out.printf(language.getString("main_adminView"),user.getUsername());
        }
        return userInput.nextLine();
    }

    private static void displayBook(Book b,ResourceBundle language) {
        // Display Book Info
        System.out.printf(language.getString("main_displayBook"),b.getBook_name(),b.getBook_isbn(),b.getBook_edition(),b.getBook_description(),b.getAuthor(),b.getPublisher(),(b.getQuantityInStock() > 0 ? "Yes" : "No"));
    }

    private static void displayLoan(Loan l,Locale locale,ResourceBundle language) {

        String pattern = "E, MMM dd yyyy HH:mm:ss";
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat(pattern,locale);
        Date loanStartedDate = null;
        Date loanEndDate = null;
        try {
            loanStartedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(l.getLoan_started());
            loanEndDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(l.getLoan_ends());
        } catch (ParseException e) {
            System.out.println(language.getString("main_parse"));
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
        System.out.printf(language.getString("main_displayLoan"),l.getBook_id().getBook_name(),started,end,activeLoan,fine);
    }

    private static String validateString(Scanner userInput,ResourceBundle language) {
        String var;
        var = userInput.nextLine();
        while (var.isBlank()){
            System.out.println(language.getString("main_Blank"));
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
        String email;
        String phoneNumber;

        while (librarySystemOnline){
            String command = getCommand(userInput,user,globalMessages);

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
                        if (user == null){
                            System.out.println(globalMessages.getString("main_TypeName"));
                            userName = validateString(userInput,globalMessages);
                            while (!userAddressDao.validateUsername(userName)){
                                System.out.println(globalMessages.getString("main_NameExists"));
                                userName = validateString(userInput,globalMessages);
                            }

                            System.out.println(globalMessages.getString("main_TypePass"));
                            passWord = validateString(userInput,globalMessages);

                            System.out.println(globalMessages.getString("main_TypeEmail"));
                            email = validateString(userInput,globalMessages);
                            while (!userAddressDao.validateEmail(email)){
                                System.out.println(globalMessages.getString("main_EmailExists"));
                                email = validateString(userInput,globalMessages);
                            }

                            System.out.println(globalMessages.getString("main_TypePhone"));
                            phoneNumber = validateString(userInput,globalMessages);
                            while (!userAddressDao.validatePhonenumber(phoneNumber)){
                                System.out.println(globalMessages.getString("main_PhoneExists"));
                                phoneNumber = validateString(userInput,globalMessages);
                            }
                            System.out.println(globalMessages.getString("main_address")+"\n");
                            System.out.println(globalMessages.getString("main_FirstName"));
                            String firstName = validateString(userInput,globalMessages);
                            System.out.println(globalMessages.getString("main_LastName"));
                            String lastName = validateString(userInput,globalMessages);
                            System.out.println(globalMessages.getString("main_addressDetails"));
                            String address = validateString(userInput,globalMessages);
                            String address2 = null;
                            System.out.println(globalMessages.getString("main_extraAddress"));
                            String moreInfo = validateString(userInput,globalMessages);
                            if (moreInfo.equalsIgnoreCase("Y")){
                                System.out.println(globalMessages.getString("main_extraAddressInfo"));
                                address2 = validateString(userInput,globalMessages);
                            }
                            System.out.println(globalMessages.getString("main_city"));
                            String city = validateString(userInput,globalMessages);
                            System.out.println(globalMessages.getString("main_country"));
                            String country = validateString(userInput,globalMessages);
                            String state = null;

                            if (country.equalsIgnoreCase("America")){
                                System.out.println(globalMessages.getString("main_state"));
                                state = validateString(userInput,globalMessages);
                            }
                            System.out.println(globalMessages.getString("main_postal"));
                            String postalCode = validateString(userInput,globalMessages);

                            // Insert the Address Details First
                            int addressID = userAddressDao.insertAddress(firstName,lastName,address,address2,city,state,country,postalCode);

                            // Finally after all validation insert a member into the table.
                            userAddressDao.registerUser(userName,passWord,email,phoneNumber,addressID);
                            System.out.println(globalMessages.getString("main_Registered"));
                        } else {
                            System.out.println(globalMessages.getString("main_Default"));
                        }
                        break;

                    case "2":
                        if (user == null){
                            System.out.println(globalMessages.getString("main_TypeName"));
                            userName = validateString(userInput,globalMessages);
                            System.out.println(globalMessages.getString("main_TypePass"));
                            passWord = validateString(userInput,globalMessages);
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
                            displayBook(b,bookMessages);
                        }
                        break;

                    case "4":
                        if (user != null){
                            if (loanUserBookDao.allLoansByUserId(user).isEmpty()){
                                System.out.println(globalMessages.getString("main_noLoans"));
                            } else {
                                for(Loan l: loanUserBookDao.allLoansByUserId(user) ) {
                                    displayLoan(l,chosenLocale,globalMessages);
                                }
                            }
                        } else {
                            System.out.println(globalMessages.getString("main_Default"));
                        }
                        break;

                    case "5":
                        if (user != null){
                            if (loanUserBookDao.allLoansSinceJoining(user).isEmpty()){
                                System.out.println(globalMessages.getString("main_noLoans"));
                            } else {
                                for(Loan l: loanUserBookDao.allLoansSinceJoining(user) ) {
                                    displayLoan(l,chosenLocale,globalMessages);
                                }
                            }
                        } else {
                            System.out.println(globalMessages.getString("main_Default"));
                        }
                        break;

                    case "6":
                        if (user != null){
                            System.out.println(globalMessages.getString("main_loanBook"));
                            String bookName = validateString(userInput,globalMessages);
                            Book book = bookDao.findByName(bookName);
                            if (book == null){
                                System.out.println(globalMessages.getString("main_NoBook"));
                            } else {
                                System.out.println(globalMessages.getString("main_LoanDays"));
                                // TODO: 21/10/2020 validation needed for string here like this or the scanner buffer will jump to next line
                                int loanDays = Integer.parseInt(validateString(userInput,globalMessages));

                                if(loanUserBookDao.loanBook(bookName,loanDays,user) == 1){
                                    System.out.println(globalMessages.getString("main_Loaned"));
                                } else if (loanUserBookDao.loanBook(bookName,loanDays,user) == -2){
                                    System.out.println(globalMessages.getString("main_LoanVal"));
                                } else if (loanUserBookDao.loanBook(bookName,loanDays,user) == -1){
                                    System.out.println(globalMessages.getString("main_LoanAlready"));
                                } else if (loanUserBookDao.loanBook(bookName,loanDays,user) == 0){
                                    System.out.println(globalMessages.getString("main_Stock"));
                                }else if (loanUserBookDao.loanBook(bookName,loanDays,user) == -3){
                                    System.out.println(globalMessages.getString("main_maxLoans"));
                                }
                            }
                        } else {
                            System.out.println(globalMessages.getString("main_Default"));
                        }

                        break;

                    case "7":
                        if (user != null){
                            System.out.println(globalMessages.getString("main_ReturnBook"));
                            String bookName = validateString(userInput,globalMessages);
                            Book book = bookDao.findByName(bookName);
                            if (book == null){
                                System.out.println(globalMessages.getString("main_NoBook"));
                            } else {
                                if (loanUserBookDao.returnBook(bookName,user)){
                                    System.out.println(globalMessages.getString("main_Returned"));
                                } else {
                                    System.out.println(globalMessages.getString("main_NoLoan"));
                                }
                            }
                        } else {
                            System.out.println(globalMessages.getString("main_Default"));
                        }
                        break;

                    case "8":
                        if (user != null){
                            System.out.printf(globalMessages.getString("main_Logout"),user.getUsername());
                            user = null;
                        }
                        break;

                    case "9":
                        if (user != null && user.getType().equals("Admin")){
                            System.out.println(globalMessages.getString("main_AddBook"));
                            String title = validateString(userInput,globalMessages);
                            Book book = bookDao.findByName(title);
                            if (book != null){
                                System.out.println(globalMessages.getString("main_BookExists"));
                            } else {
                                System.out.println(globalMessages.getString("main_BookISBN"));
                                String ISBN = validateString(userInput,globalMessages);
                                System.out.println(globalMessages.getString("main_Edition"));
                                String edition = validateString(userInput,globalMessages);
                                System.out.println(globalMessages.getString("main_Desc"));
                                String description = validateString(userInput,globalMessages);
                                System.out.println(globalMessages.getString("main_Author"));
                                String author = validateString(userInput,globalMessages);
                                System.out.println(globalMessages.getString("main_Publisher"));
                                String publisher = validateString(userInput,globalMessages);
                                System.out.println(globalMessages.getString("main_BookQuantity"));
                                // TODO: 15/10/2020 // VALIDATION HERE NEEDED not only for string but for 0 quantity
                                String quantity = validateString(userInput,globalMessages);
                                bookDao.addBook(title,ISBN,edition,description,author,publisher,Integer.parseInt(quantity));
                                System.out.println(globalMessages.getString("main_BookAdded"));
                            }
                        } else {
                            System.out.println(globalMessages.getString("main_Default"));
                        }
                        break;

                    case "10":
                        if (user != null && user.getType().equals("Admin")){
                            System.out.println(globalMessages.getString("main_AddBookQty"));
                            String title = validateString(userInput,globalMessages);
                            Book book = bookDao.findByName(title);
                            if (book == null){
                                System.out.println(globalMessages.getString("main_NoBookByName"));
                            } else {
                                // Book exists so ask for quantity and add it
                                System.out.println(globalMessages.getString("main_Quantity"));
                                String quantity = validateString(userInput,globalMessages);
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
                        break;

                    case "11":
                        if (user != null && user.getType().equals("Admin")){
                            System.out.println(globalMessages.getString("main_TypeMember"));
                            String username = validateString(userInput,globalMessages);
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
                        break;

                    case "12":
                        if (user != null && user.getType().equals("Admin")){
                            System.out.println(globalMessages.getString("main_DecBookQty"));
                            String title = validateString(userInput,globalMessages);
                            Book book = bookDao.findByName(title);
                            if (book == null){
                                System.out.println(globalMessages.getString("main_NoBookByName"));
                            } else {
                                System.out.println(globalMessages.getString("main_Quantity"));
                                String quantity = validateString(userInput,globalMessages);
                                if (bookDao.removeCopies(book.getBook_id(),Integer.parseInt(quantity))){
                                    System.out.println(globalMessages.getString("main_QtyDec"));
                                } else {
                                    System.out.println(globalMessages.getString("main_QtyDenied"));
                                }
                            }
                        } else {
                            System.out.println(globalMessages.getString("main_Default"));
                        }
                        break;

                    default:
                        System.out.println(globalMessages.getString("main_Default")); // internaliozed
                }
        }
    }
}