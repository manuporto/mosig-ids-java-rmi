package chatapp.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface MessageServiceItf extends Remote {

    void sendMessage(String senderUsername, String receiverUserName, String message) throws RemoteException;

    void sendBroadcastMessage(String senderUsername, String message) throws RemoteException;

    ArrayList<String> getMessageHistory() throws RemoteException;
}
