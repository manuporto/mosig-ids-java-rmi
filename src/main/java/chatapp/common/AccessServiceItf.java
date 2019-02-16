package chatapp.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface AccessServiceItf extends Remote {
    Boolean join(ClientInfoItf client) throws RemoteException;

    void leave(ClientInfoItf client) throws RemoteException;

    ArrayList<String> getConnectedClients() throws RemoteException;
}
