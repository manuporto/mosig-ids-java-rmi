package chatapp.server;

import chatapp.client.Client_interface;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server extends UnicastRemoteObject implements Server_interface{
    private static final long serialVersionUID = 1L;
    private ArrayList<Client_interface>listClients ;
    protected Server() throws RemoteException {
        listClients = new ArrayList<Client_interface>();
    }
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        Naming.rebind("RMIServer",new Server());
    }

    @Override
    public synchronized void registerClient(Client_interface client) throws RemoteException {
        this.listClients.add(client);
    }

    @Override
    public synchronized void broadcastMessage(String message) throws RemoteException {
        int i=0;
        while (i<listClients.size()){
            listClients.get(i++).retrieveMessage(message);
        }
    }
}
