package chatapp.server;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageStorer {
    private List<Message> oldMessages;

    MessageStorer() throws IOException {
        oldMessages = new LinkedList<>();
        loadMessages();
    }

    private void loadMessages() throws IOException {
        File file = new File("./history");
        if (!file.createNewFile()) {
            FileInputStream fis = new FileInputStream(file);
            try (ObjectInputStream ois = new ObjectInputStream(fis)) {
                Message msg;
                while (fis.available() != 0) {
                    msg = (Message) ois.readObject();
                    oldMessages.add(msg);
                }
                for (Message oldMessage : oldMessages) {
                    Message message;
                    message = oldMessage;
                    System.out.println(message.toString());
                }
            } catch (EOFException e) {
                Logger.getLogger(MessageStorer.class.getName()).log(Level.INFO, "History file empty");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                Logger.getLogger(MessageStorer.class.getName()).log(Level.SEVERE, e.toString(), e);
            }
        }
    }


    private void saveMessages() {
        try {
            File file = new File("./history");
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (Message msg : oldMessages) oos.writeObject(msg);
            oos.close();
            fos.close();
        } catch (IOException ex) {
            Logger.getLogger(MessageStorer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addMessage(Message msg) {
        oldMessages.add(msg);
    }

    public List<Message> getMessages() {
        return oldMessages;
    }

    public void close() {
        saveMessages();
    }
}
