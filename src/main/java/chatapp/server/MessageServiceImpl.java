package chatapp.server;

import chatapp.common.ClientInfoItf;
import chatapp.common.MessageServiceItf;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageServiceImpl implements MessageServiceItf {

    private MessageStorer msgStorer;
    private ClientRegister cRegister;

    public MessageServiceImpl(MessageStorer msgStorer, ClientRegister cRegister) {
        this.msgStorer = msgStorer;
        this.cRegister = cRegister;
    }

    @Override
    public void sendMessage(String senderUsername, String receiverUserName, String message) throws RemoteException {
        Message msg = new Message(new Date(), senderUsername, message);
        ClientInfoItf senderClient = cRegister.getClient(senderUsername);
        ClientInfoItf receiverClient = cRegister.getClient(receiverUserName);
        if (receiverClient == null) return; // TODO raise exception
        senderClient.receiveMessage(msg.toString());
        receiverClient.receiveMessage(msg.toString());
        msgStorer.addMessage(msg);
    }

    @Override
    public synchronized void sendBroadcastMessage(String senderUsername, String message) throws RemoteException {
        Message msg = new Message(new Date(), senderUsername, message);
        Iterable<ClientInfoItf> clients = cRegister.getClients();
        for (ClientInfoItf client : clients) {
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
