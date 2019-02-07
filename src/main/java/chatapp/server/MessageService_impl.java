package chatapp.server;

import chatapp.common.ClientInfo_itf;
import chatapp.common.MessageService_itf;

import java.rmi.RemoteException;

public class MessageService_impl implements MessageService_itf {

    @Override
    public void sendMessage(ClientInfo_itf src, ClientInfo_itf dest, String message) throws RemoteException {}

    @Override
    public void sendBroadcastMessage(ClientInfo_itf src, String message) throws RemoteException {
        System.out.println(src.getUsername() + ": " +  message);
    }
}
