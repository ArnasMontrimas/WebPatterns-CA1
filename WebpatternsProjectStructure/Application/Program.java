

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Program {

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

    private static void filmInfo(String[] components) {

        System.out.println("--------------------------------------------------\n" +
                "|Film Name: " +components[0] + "\n" +
                "|Genre: "  + components[1] + "\n" +
                "|Total Ratings: "  + components[2] + "\n"  +
                "|Number of people that rated this film: " + components[3] + "\n" +
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

        Scanner userInput = new Scanner(System.in);
        boolean librarySystemOnline = true;
        //boolean validCommand = true;
        // Has to be internalized to french based on locale ?
        System.out.println("***** Welcome to the Dundalk Library service ! *****");

        int userID = 0;
        String userName = null;
        String passWord = null;
        String email = null;
        String phoneNumber = null;

        while (librarySystemOnline){
            String command = getCommand(userInput);

            switch (command){
                case "1":
                    System.out.println("Please type in your username.");
                    userName = validateString(userInput);
                    System.out.println("Please type in your password.");
                    passWord = validateString(userInput);
                    System.out.println("Please type in your email address.");
                    email = validateString(userInput);
                    System.out.println("Please type in your phone number.");
                    phoneNumber = validateString(userInput);
                    break;

                default:
                    System.out.println("Please pick a number between 1-10."); // internaliozed
                   // validCommand = false;
            }

        }




    }
}
