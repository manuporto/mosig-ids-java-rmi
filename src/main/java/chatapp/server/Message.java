package chatapp.server;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private Date timeStamp;
    private String author;
    private String msg;

    public Message(Date timeStamp, String author, String msg) {
        this.timeStamp = timeStamp;
        this.author = author;
        this.msg = msg;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public String getAuthor() {
        return author;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "[" + timeStamp + "] " + author + ": " + msg;
    }
}
