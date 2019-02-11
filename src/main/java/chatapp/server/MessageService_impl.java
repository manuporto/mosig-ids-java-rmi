package chatapp.server;

import chatapp.common.ClientInfo_itf;
import chatapp.common.MessageService_itf;

import java.rmi.RemoteException;
import java.util.List;

public class MessageService_impl implements MessageService_itf {

    @Override
    public void sendMessage(ClientInfo_itf src, ClientInfo_itf dest, String message) throws RemoteException {}

    @Override
    public void sendBroadcastMessage(ClientInfo_itf src, String message) throws RemoteException {
        System.out.println(src.getUsername() + ": " +  message);
    }

    @Override
    public List<String> receiveMessages() throws RemoteException {
        return null;
    }
}
