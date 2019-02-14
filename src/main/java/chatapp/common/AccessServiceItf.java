package chatapp.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AccessServiceItf extends Remote {
    void join(ClientInfo_itf client) throws RemoteException;
    void leave(ClientInfo_itf client) throws RemoteException;

    Boolean userNameAvailable(String userName) throws RemoteException;
}
