package chatapp.server;

import chatapp.common.ClientInfo_itf;
import chatapp.common.MessageService_itf;

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
        ClientInfo_itf senderClient = cRegister.getClient(senderUsername);
        ClientInfo_itf receiverClient = cRegister.getClient(receiverUserName);
        if (receiverClient == null) return; // TODO raise exception
        senderClient.receiveMessage(msg.toString());
        receiverClient.receiveMessage(msg.toString());
        try {
            receivedMessages.put(msg);
        } catch (InterruptedException ex) {
            Logger.getLogger(MessageService_impl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public synchronized void sendBroadcastMessage(String senderUsername, String message) throws RemoteException {
        Message msg = new Message(new Date(), senderUsername, message);
        try {
            receivedMessages.put(msg);
        } catch (InterruptedException ex) {
            Logger.getLogger(MessageService_impl.class.getName()).log(Level.SEVERE, null, ex);
        }
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
