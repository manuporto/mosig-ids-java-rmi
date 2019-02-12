package chatapp.common;

import java.io.Serializable;
import java.rmi.RemoteException;

public class ClientInfo implements ClientInfo_itf, Serializable {
    private static final long serialversionUID = 129348938L;
    String userName;

    public ClientInfo(String userName) {
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
