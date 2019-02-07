package chatapp.common;

import java.rmi.Remote;

public interface AccessServiceItf extends Remote {
    void join(ClientInfo_itf client);
    void leave(ClientInfo_itf client);
}
