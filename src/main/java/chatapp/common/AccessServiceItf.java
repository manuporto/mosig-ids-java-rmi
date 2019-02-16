package chatapp.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface AccessServiceItf extends Remote {
    Boolean join(ClientInfo_itf client) throws RemoteException;
    void leave(ClientInfo_itf client) throws RemoteException;

    ArrayList<String> getConnectedClients() throws RemoteException;
}
