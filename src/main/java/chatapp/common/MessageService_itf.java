package chatapp.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MessageService_itf  extends Remote {
    
   void sendMessage(ClientInfo_itf src, ClientInfo_itf dest, String message) throws RemoteException;
           
   void sendBroadcastMessage(ClientInfo_itf src, String message) throws RemoteException;
}
