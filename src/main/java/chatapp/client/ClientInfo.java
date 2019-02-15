package chatapp.client;

import chatapp.common.ClientInfo_itf;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientInfo extends UnicastRemoteObject implements ClientInfo_itf {
    String userName;

    public ClientInfo(String userName) throws RemoteException {
        super();
        this.userName = userName;
    }

    @Override
    public String getUsername() throws RemoteException {
        return userName;
    }

    @Override
    public void receiveMessage(String message) throws RemoteException {
        System.out.println(message);
    }
}
