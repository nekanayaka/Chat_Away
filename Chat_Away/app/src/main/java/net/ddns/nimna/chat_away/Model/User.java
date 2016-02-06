package net.ddns.nimna.chat_away.Model;



public class User {

    private int id;
    private String userName;
    private String email;
    private double banStatus;
    private int requestingChat;
    private String accountLevel;
    private String coordinates;
    private String latitude;
    private String longitude;


    public User(int id, String userName, String email, double banStatus, int requestingChat, String accountLevel, String coordinates) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.banStatus = banStatus;
    }

    public User(int id, String userName, String password, String email, String accountLevel, String latitude, String longitude) {
        this.id = id;
        this.userName = userName;
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

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getAccountLevel() {
        return accountLevel;
    }

    public void setAccountLevel(String accountLevel) {
        this.accountLevel = accountLevel;
    }

    public int getRequestingChat() {
        return requestingChat;
    }

    public void setRequestingChat(int requestingChat) {
        this.requestingChat = requestingChat;
    }

    public double getBanStatus() {
        return banStatus;
    }

    public void setBanStatus(double banStatus) {
        this.banStatus = banStatus;
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
