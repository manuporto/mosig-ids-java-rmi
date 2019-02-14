package chatapp.server;
import java.io.EOFException;
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
    private final BlockingQueue<Message> receivedMessages;
    private List<Message> oldmessages;

    MessageStorer(BlockingQueue<Message> receivedMessages) throws FileNotFoundException, IOException, ClassNotFoundException {
        File file = new File("/Users/admin/Desktop/mosig-ids-java-rmi/src/main/java/chatapp/common/History.txt");
        // if file doesnt exists, then we create it
        if (!file.exists()) {
        file.createNewFile();
			}
        else{
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        try {
        oldmessages = (LinkedList) ois.readObject();
        }
        catch (EOFException e) {
        ois.close();
        fis.close();
        }
        }
        this.receivedMessages = receivedMessages;
    }

    @Override
    public void run() {
        try {
            Message msg;
            while (!Thread.currentThread().isInterrupted()) {
                msg = receivedMessages.take();
                oldmessages.add(msg);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            final List<Message> remainingMsgs = new LinkedList<>();
            receivedMessages.drainTo(remainingMsgs);
            oldmessages.addAll(remainingMsgs);
            try {
                File file = new File("/Users/admin/Desktop/mosig-ids-java-rmi/src/main/java/chatapp/common/History.txt");
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(oldmessages);
                oos.close();
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(MessageStorer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}
