package chatapp.server;
import chatapp.client.Client_interface;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface Server_interface extends Remote {
    void registerClient(Client_interface client) throws RemoteException ;
    void broadcastMessage(String message) throws RemoteException ;
}
