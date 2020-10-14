package DataTransferObjects;

import java.util.Arrays;
import java.util.Objects;

/**
 * This class models the book table from database('dundalk_library')
 */
public class Book {
    //Variables which model book table
    private int book_id;
    private String book_name;
    private String book_isbn;
    private String book_edition;
    private String book_description;
    private String author;
    private String publisher;
    private int quantityInStock;

    //Constructor
    public Book(int book_id, String book_name, String book_isbn, String book_edition, String book_description, String author, String publisher, int quantityInStock) {
        this.book_id = book_id;
        this.book_name = book_name;
        this.book_isbn = book_isbn;
        this.book_edition = book_edition;
        this.book_description = book_description;
        this.author = author;
        this.publisher = publisher;
        this.quantityInStock = quantityInStock;
    }

    //Rest is boilerplate
    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_isbn() {
        return book_isbn;
    }

    public void setBook_isbn(String book_isbn) {
        this.book_isbn = book_isbn;
    }

    public String getBook_edition() {
        return book_edition;
    }

    public void setBook_edition(String book_edition) {
        this.book_edition = book_edition;
    }

    public String getBook_description() {
        return book_description;
    }

    public void setBook_description(String book_description) {
        this.book_description = book_description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return book_id == book.book_id &&
                Objects.equals(book_isbn, book.book_isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(book_id, book_isbn);
    }

    @Override
    public String toString() {
        return "Book{" +
                "book_id=" + book_id +
                ", book_name='" + book_name + '\'' +
                ", book_isbn='" + book_isbn + '\'' +
                ", book_edition='" + book_edition + '\'' +
                ", book_description='" + book_description + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", quantityInStock=" + quantityInStock +
                '}';
    }
}
