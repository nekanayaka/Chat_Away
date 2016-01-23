package net.ddns.nimna.chat_away;

/**
 * Created by Chris on 2016-01-22.
 */
public class User {

    private String username;
    private int userID;
    private String email;
    private String password;

    public User(String username, int userID, String email, String password){
        this.username = username;
        this.userID = userID;
        this.email = email;
        this.password = password;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
