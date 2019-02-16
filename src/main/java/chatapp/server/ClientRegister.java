package chatapp.server;

import chatapp.common.AccessServiceItf;
import chatapp.common.ClientInfo_itf;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClientRegister implements AccessServiceItf {

    private Map<String, ClientInfo_itf> clients;

    ClientRegister() {
        clients = new HashMap<>();
    }

    @Override
    public synchronized Boolean join(ClientInfo_itf client) throws RemoteException {
        if (clients.containsKey(client.getUsername())) return false;
        clients.put(client.getUsername(), client);
        return true;
    }

    @Override
    public synchronized void leave(ClientInfo_itf client) throws RemoteException {
        clients.remove(client.getUsername());
    }

    @Override
    public ArrayList<String> getConnectedClients() throws RemoteException {
        ArrayList<String> clientsArray = new ArrayList<>();
        clientsArray.addAll(clients.keySet());
        return clientsArray;
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
