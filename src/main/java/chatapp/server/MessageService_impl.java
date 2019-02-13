package chatapp.server;

import chatapp.common.ClientInfo_itf;
import chatapp.common.MessageService_itf;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class MessageService_impl implements MessageService_itf {

    private final BlockingQueue<Message> receivedMessages;
    private ClientRegister cRegister;

    public MessageService_impl(BlockingQueue<Message> receivedMessages, ClientRegister cRegister) {
        this.receivedMessages = receivedMessages;
        this.cRegister = cRegister;
    }

    @Override
    public void sendMessage(ClientInfo_itf src, ClientInfo_itf dest, String message) throws RemoteException {}

    @Override
    public synchronized void sendBroadcastMessage(ClientInfo_itf src, String message) throws RemoteException {
        Message msg = new Message(new Date(), src.getUsername(), message);
        // receivedMessages.put(msg);
        Iterable<ClientInfo_itf> clients = cRegister.getClients();
        for (ClientInfo_itf client : clients) {
            client.receiveMessage(msg.toString());
        }
    }

    @Override
    public List<String> receiveMessages() throws RemoteException {
        return null;
    }
}
