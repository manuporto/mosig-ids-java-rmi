package chatapp.server;

import java.util.Date;

public class DirectMessage {
    private Date timeStamp;
    private String author;
    private String destination;
    private String msg;

    public DirectMessage(Date timeStamp, String author, String destination, String msg) {
        this.timeStamp = timeStamp;
        this.author = author;
        this.destination = destination;
        this.msg = msg;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public String getAuthor() {
        return author;
    }

    public String getDestination() {
        return destination;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "<<PRIVATE>> [" + timeStamp + "] " + author + ": " + msg;
    }
}
