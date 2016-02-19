package net.ddns.nimna.chat_away_v2.Model;

/**
 * Created by nekanayaka on 1/18/2016.
 */

public class User {

    private int id;
    private String userName;
    private String email;
    private String password;
    private String banStatus;
    private String requestingChat;
    private String accountLevel;
    //private String coordinates;
    private String latitude;
    private String longitude;


    public User(int id, String userName, String email, String accountLevel, String latitude, String longitude) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.accountLevel = accountLevel;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public User(int id, String userName, String password, String email, String accountLevel, String latitude, String longitude) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.accountLevel = accountLevel;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public User(int id, String userName, String email, String password, String banStatus, String requestingChat, String accountLevel, String latitude, String longitude) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.banStatus = banStatus;
        this.requestingChat = requestingChat;
        this.accountLevel = accountLevel;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getBanStatus() {
        return banStatus;
    }

    public void setBanStatus(String banStatus) {
        this.banStatus = banStatus;
    }

    public String getRequestingChat() {
        return requestingChat;
    }

    public void setRequestingChat(String requestingChat) {
        this.requestingChat = requestingChat;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountLevel() {
        return accountLevel;
    }

    public void setAccountLevel(String accountLevel) {
        this.accountLevel = accountLevel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
