package chatapp.server;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageStorer implements Runnable {
    private final BlockingQueue<Message> receivedMessages;
    private List<Message> oldMessages;

    MessageStorer(BlockingQueue<Message> receivedMessages) throws IOException {
        loadMessages();
        this.receivedMessages = receivedMessages;
        oldMessages = new LinkedList<>();
    }

    private void loadMessages() throws IOException {
        File file = new File("./history");
        if (!file.createNewFile()) {
            FileInputStream fis = new FileInputStream(file);
            try (fis; ObjectInputStream ois = new ObjectInputStream(fis)) {
                Message msg;
                while (fis.available() != 0) {
                    msg = (Message) ois.readObject();
                    oldMessages.add(msg);
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

    @Override
    public void run() {
        try {
            Message msg;
            while (!Thread.currentThread().isInterrupted()) {
                msg = receivedMessages.take();
                oldMessages.add(msg);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            receivedMessages.drainTo(oldMessages);
            saveMessages();
        }
    }
}
