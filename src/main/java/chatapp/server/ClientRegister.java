package chatapp.server;

import chatapp.common.AccessServiceItf;
import chatapp.common.ClientInfo_itf;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class ClientRegister implements AccessServiceItf {

    private Map<ClientInfo_itf, String> clients;

    ClientRegister() {
        clients = new HashMap<>();
    }

    public void join(ClientInfo_itf client) throws RemoteException {
        clients.put(client, client.getUsername());
    }

    public void leave(ClientInfo_itf client) throws RemoteException {
        clients.remove(client);
    }

    public Iterable<ClientInfo_itf> getClients() {
        return clients.keySet();
    }
}
