package DataTransferObjects;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * This class models the loan table from database('dundalk_library')
 */
public class Loan {
    //Variables which model loan table
    private int loan_id;
    private User user_id;
    private Book book_id;
    private String loan_started;
    private String loan_ends;
    private String loan_returned;

    //Constructor
    public Loan(int loan_id, User user_id, Book book_id, String loan_started, String loan_ends, String loan_returned) {
        this.loan_id = loan_id;
        this.user_id = user_id;
        this.book_id = book_id;
        this.loan_started = loan_started;
        this.loan_ends = loan_ends;
        this.loan_returned = loan_returned;
    }

    //Rest is boilerplate
    public int getLoan_id() {
        return loan_id;
    }

    public void setLoan_id(int loan_id) {
        this.loan_id = loan_id;
    }

    public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }

    public Book getBook_id() {
        return book_id;
    }

    public void setBook_id(Book book_id) {
        this.book_id = book_id;
    }

    public String getLoan_started() {
        return loan_started;
    }

    public void setLoan_started(String loan_started) {
        this.loan_started = loan_started;
    }

    public String getLoan_ends() {
        return loan_ends;
    }

    public void setLoan_ends(String loan_ends) {
        this.loan_ends = loan_ends;
    }

    public String getLoan_returned() {
        return loan_returned;
    }

    public void setLoan_returned(String loan_returned) {
        this.loan_returned = loan_returned;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return loan_id == loan.loan_id &&
                Objects.equals(user_id, loan.user_id) &&
                Objects.equals(book_id, loan.book_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loan_id, user_id, book_id);
    }

    @Override
    public String toString() {
        return "Loan{" +
                "loan_id=" + loan_id +
                ", user_id=" + user_id +
                ", book_id=" + book_id +
                ", loan_started='" + loan_started + '\'' +
                ", loan_ends='" + loan_ends + '\'' +
                ", loan_is_active=" + loan_returned +
                '}';
    }

    public double calculateLoanFine() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date firstDate;
        Date secondDate;
        Date currentDate = new Date();

        //Have this here in long instead of int because if number gets to large there will be errors and to avoid that im using long here (The numbers might not get that big but just in-case will prevent future errors)
        long diffInMillies;
        long diff;

        double finePerDay = 5.00;
        double totalFine = 0.00;

        try {
            if(getLoan_returned() == null) {
                if(currentDate.getTime() > sdf.parse(loan_ends).getTime()) {
                    firstDate = sdf.parse(loan_ends);
                    secondDate = currentDate;
                } else return totalFine;
            } else {
                firstDate = sdf.parse(loan_ends);
                secondDate = sdf.parse(loan_returned);
                if(secondDate.getTime() <= firstDate.getTime()) return 0.00;
            }

            //Calculate difference in milliseconds then convert milliseconds into days
            diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
            diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

            //Calculate Fine
            totalFine = finePerDay * diff;

        } catch (ParseException ex) {
            System.err.println("Exception Occurred: " + ex.getMessage());
            ex.printStackTrace();
        }

        return totalFine;
    }
}
