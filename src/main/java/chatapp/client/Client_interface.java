package chatapp.client;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client_interface extends Remote {
    void retrieveMessage(String message) throws RemoteException ;
    
}
