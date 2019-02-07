package chatapp.common;

import java.rmi.Remote;

public interface AccessServiceItf extends Remote {
    void join(String clientName);
    void leave(String client);
}
