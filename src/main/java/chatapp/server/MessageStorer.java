package chatapp.server;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageStorer implements Runnable{
    File file;
    private FileOutputStream fos;
    private ObjectOutputStream oos;
    private FileInputStream fis;
    private ObjectInputStream ois;
    private final BlockingQueue<Message> receivedMessages;
    private List<Message> messages;

    MessageStorer(BlockingQueue<Message> receivedMessages) throws FileNotFoundException, IOException, ClassNotFoundException {
        file = new File("/Users/admin/Desktop/mosig-ids-java-rmi/src/main/java/chatapp/common/History.txt");
        // if file doesnt exists, then we create it
        if (!file.exists()) {
        file.createNewFile();
			}
        fis = new FileInputStream(file);
        ois = new ObjectInputStream(fis);
        messages = (LinkedList) ois.readObject();
        ois.close();
        this.receivedMessages = receivedMessages;
        fos = new FileOutputStream(file);
        oos = new ObjectOutputStream(fos);
    }

    @Override
    public void run() {
        try {
            Message msg;
            while (!Thread.currentThread().isInterrupted()) {
                msg = receivedMessages.take();
                messages.add(msg);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            final List<Message> remainingMsgs = new LinkedList<>();
            receivedMessages.drainTo(remainingMsgs);
            messages.addAll(remainingMsgs);
            try {
                oos.writeObject(messages);
                oos.close();
            } catch (IOException ex) {
                Logger.getLogger(MessageStorer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}
