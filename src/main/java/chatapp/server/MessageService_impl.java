package chatapp.server;

import chatapp.common.ClientInfo_itf;
import chatapp.common.MessageService_itf;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageService_impl implements MessageService_itf {

    private MessageStorer msgStorer;
    private ClientRegister cRegister;

    public MessageService_impl(MessageStorer msgStorer, ClientRegister cRegister) {
        this.msgStorer = msgStorer;
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
        msgStorer.addMessage(msg);
    }

    @Override
    public synchronized void sendBroadcastMessage(String senderUsername, String message) throws RemoteException {
        Message msg = new Message(new Date(), senderUsername, message);
        Iterable<ClientInfo_itf> clients = cRegister.getClients();
        for (ClientInfo_itf client : clients) {
            client.receiveMessage(msg.toString());
        }
        msgStorer.addMessage(msg);
    }

    @Override
    public ArrayList<String> getMessageHistory() throws RemoteException {
        ArrayList<String> messages = new ArrayList<>();
        List<Message> objMessages = msgStorer.getMessages();
        for (Message msg : objMessages) messages.add(msg.toString());
        return messages;
    }
}
