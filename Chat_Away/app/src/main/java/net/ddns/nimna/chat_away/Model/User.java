package net.ddns.nimna.chat_away.Model;



public class User {

    private int id;
    private String userName;
    private String email;
    private String password;
    //private double banStatus;
    //private int requestingChat;
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
