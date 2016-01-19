package net.ddns.nimna.chat_away;

/**
 * Created by Nimna Ekanayaka on 19/01/2016.
 */
public class DatabaseConnection {
    private String host = "nimna.ddns.net";
    private String dbName = "chat_away";
    private String username = "chat_away_admin";
    private String password = "opensesame";

    public DatabaseConnection(String host, String dbName, String username, String password) {
        this.host = host;
        this.dbName = dbName;
        this.username = username;
        this.password = password;
    }

    public DatabaseConnection() {
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
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
}
