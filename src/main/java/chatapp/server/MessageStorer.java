package chatapp.server;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageStorer {
    private List<Message> broadcastMessages;
    private Map<String, List<DirectMessage>> directMessages;
    private String broadcastMessagesBackupFile;
    private String directMessagesBackupFile;

    MessageStorer(String broadcastMessageBackupFile, String directMessagesBackupFile) throws IOException {
        broadcastMessages = new LinkedList<>();
        directMessages = new HashMap<>();
        this.broadcastMessagesBackupFile = broadcastMessageBackupFile;
        this.directMessagesBackupFile = directMessagesBackupFile;
        loadMessages();
    }

    private void loadMessages() throws IOException {
        File file = new File(broadcastMessagesBackupFile);
        if (!file.createNewFile()) {
            FileInputStream fis = new FileInputStream(file);
            try (ObjectInputStream ois = new ObjectInputStream(fis)) {
                Message msg;
                while (fis.available() != 0) {
                    msg = (Message) ois.readObject();
                    broadcastMessages.add(msg);
                }
                for (Message oldMessage : broadcastMessages) {
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
            File file = new File(broadcastMessagesBackupFile);
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (Message msg : broadcastMessages) oos.writeObject(msg);
            oos.close();
            fos.close();
        } catch (IOException ex) {
            Logger.getLogger(MessageStorer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addMessage(Message msg) {
        broadcastMessages.add(msg);
    }

    public void addDirectMessage(DirectMessage msg) {
        if (!directMessages.containsKey(msg.getAuthor())) {
            List<DirectMessage> userMessages = new ArrayList<>();
            directMessages.put(msg.getAuthor(), userMessages);
        }

        if (!directMessages.containsKey(msg.getDestination())) {
            List<DirectMessage> userMessages = new ArrayList<>();
            directMessages.put(msg.getDestination(), userMessages);
        }
        directMessages.get(msg.getAuthor()).add(msg);
        directMessages.get(msg.getDestination()).add(msg);
    }

    public List<String> getMessages(String userName) {
        List<String> messages = new ArrayList<>();
        for (Message msg : broadcastMessages) messages.add(msg.toString());
        if (directMessages.containsKey(userName)) {
            List<DirectMessage> userMessages = directMessages.get(userName);
            for (DirectMessage dmsg : userMessages) messages.add(dmsg.toString());

        }
        return messages;
    }

    public void close() {
        saveMessages();
    }
}
