package chatapp.server;

import chatapp.common.ClientInfo_itf;
import chatapp.common.MessageService_itf;
import java.io.IOException;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageService_impl implements MessageService_itf {

    private final BlockingQueue<Message> receivedMessages;
    private ClientRegister cRegister;

    public MessageService_impl(BlockingQueue<Message> receivedMessages, ClientRegister cRegister) {
        this.receivedMessages = receivedMessages;
        this.cRegister = cRegister;
    }

    @Override
    public void sendMessage(String senderUsername, String receiverUserName, String message) throws RemoteException {
        Message msg = new Message(new Date(), senderUsername, message);
        ClientInfo_itf receiverClient = cRegister.getClient(receiverUserName);
        if (receiverClient == null) return; // TODO raise exception
        receiverClient.receiveMessage(msg.toString());
        try {
            receivedMessages.put(msg);
        } catch (InterruptedException ex) {
            Logger.getLogger(MessageService_impl.class.getName()).log(Level.SEVERE, null, ex);
        }
        Thread t1 = null;
        try {
            t1 = new Thread(new MessageStorer(receivedMessages));
        } catch (IOException ex) {
            Logger.getLogger(MessageService_impl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MessageService_impl.class.getName()).log(Level.SEVERE, null, ex);
        }
        t1.start();
    }

    @Override
    public synchronized void sendBroadcastMessage(String senderUsername, String message) throws RemoteException {
        Message msg = new Message(new Date(), senderUsername, message);
        try {
            receivedMessages.put(msg);
        } catch (InterruptedException ex) {
            Logger.getLogger(MessageService_impl.class.getName()).log(Level.SEVERE, null, ex);
        }
        Thread t1 = null;
        try {
            t1 = new Thread(new MessageStorer(receivedMessages));
        } catch (IOException ex) {
            Logger.getLogger(MessageService_impl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MessageService_impl.class.getName()).log(Level.SEVERE, null, ex);
        }
        t1.start();
        Iterable<ClientInfo_itf> clients = cRegister.getClients();
        for (ClientInfo_itf client : clients) {
            client.receiveMessage(msg.toString());
        }
    }

    @Override
    public ArrayList<String> getClients() throws RemoteException {
        ArrayList<String> clients = new ArrayList<>();
        cRegister.getUsernames().forEach(clients::add);
        return clients;
    }

    @Override
    public ArrayList<String> receiveMessages() throws RemoteException {
        return null;
    }
}
