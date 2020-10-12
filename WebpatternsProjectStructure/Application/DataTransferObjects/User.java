package DataTransferObjects;

import java.util.Objects;

/**
 * This Class Models the User table from database('dundalk_library')
 */
public class User {
    //Variables which model users table
    private int id;
    private String type;
    private String username;
    private String password; //Should The password be Hashed? or nah...
    private String email;
    private String dateOfBirth;
    private int phoneNumber;
    private String dateRegistered;
    private int activeAccount;

    //Constructor
    public User(int id, String type, String username, String password, String email, String dateOfBirth, int phoneNumber, String dateRegistered, int activeAccount) {
        this.id = id;
        this.type = type;
        this.username = username;
        this.password = password;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.dateRegistered = dateRegistered;
        this.activeAccount = activeAccount;
    }

    //Rest is boilerplate
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(String dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public int getActiveAccount() {
        return activeAccount;
    }

    public void setActiveAccount(int activeAccount) {
        this.activeAccount = activeAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                phoneNumber == user.phoneNumber &&
                Objects.equals(username, user.username) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, phoneNumber);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", dateRegistered='" + dateRegistered + '\'' +
                ", activeAccount=" + activeAccount +
                '}';
    }
}
