package net.ddns.nimna.chat_away_v2.Model;



public class Message {

    private int id;
    private int sender;
    private int receiver;
    private String message;
    private String dateTime;


    public Message(int id, int sender, int receiver, String message, String dateTime) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public int getReceiver() {
        return receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
