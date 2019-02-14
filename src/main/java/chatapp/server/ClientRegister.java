package chatapp.server;

import chatapp.common.AccessServiceItf;
import chatapp.common.ClientInfo_itf;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class ClientRegister implements AccessServiceItf {

    private Map<String, ClientInfo_itf> clients;

    ClientRegister() {
        clients = new HashMap<>();
    }

    public void join(ClientInfo_itf client) throws RemoteException {
        // TODO throw exception if it already exists an user with that name
        clients.put(client.getUsername(), client);
    }

    public void leave(ClientInfo_itf client) throws RemoteException {
        clients.remove(client.getUsername());
    }

    @Override
    public Boolean userNameAvailable(String userName) throws RemoteException {
        return !clients.containsKey(userName);
    }

    public Iterable<ClientInfo_itf> getClients() {
        return clients.values();
    }

    public Iterable<String> getUsernames() {
        return clients.keySet();
    }

    public ClientInfo_itf getClient(String name) {
        return clients.get(name);
    }
}
