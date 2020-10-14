package DataTransferObjects;

import java.util.Objects;

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
    private int loan_is_active;

    //Constructor
    public Loan(int loan_id, User user_id, Book book_id, String loan_started, String loan_ends, int loan_is_active) {
        this.loan_id = loan_id;
        this.user_id = user_id;
        this.book_id = book_id;
        this.loan_started = loan_started;
        this.loan_ends = loan_ends;
        this.loan_is_active = loan_is_active;
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

    public int getLoan_is_active() {
        return loan_is_active;
    }

    public void setLoan_is_active(int loan_is_active) {
        this.loan_is_active = loan_is_active;
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
        return "\nLoan{" +
                "loan_id=" + loan_id +
                ", user_id=" + user_id +
                ", book_id=" + book_id +
                ", loan_started='" + loan_started + '\'' +
                ", loan_ends='" + loan_ends + '\'' +
                ", loan_is_active=" + loan_is_active +
                '}';
    }
}
