package chatapp.common;

import java.rmi.RemoteException;

public class AccessService implements AccessServiceItf {
    public void join(ClientInfo_itf client) throws RemoteException {
        return;
    }
    public void leave(ClientInfo_itf clientName) throws RemoteException { return; }
}
