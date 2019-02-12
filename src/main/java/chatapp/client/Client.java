package chatapp.client;
import chatapp.server.Server_interface;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Client extends UnicastRemoteObject implements Client_interface, Runnable {
    private static final long serialVersionUID = 1L;
    private Server_interface chatServer;
    private String name = null;
    protected Client (String name , Server_interface chatServer) throws RemoteException{
        this.name=name;
        this.chatServer=chatServer;
        chatServer.registerClient(this);
    }
    public static void main(String[] args) throws NotBoundException, MalformedURLException, RemoteException {
        String serverURL = "rmi://localhost/RMIServer";
        Server_interface chatServer = (Server_interface)Naming.lookup(serverURL);
        new Thread(new Client(args[0],chatServer)).start();
    }

    @Override
    public void retrieveMessage(String message) throws RemoteException {
        System.out.println(message);
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String message;
        while(true){
            message=scanner.nextLine();
            try {
                chatServer.broadcastMessage(name + ":" +message);
        } 
            catch (Exception e)  {
            System.err.println("Error on client: " + e);
        }
        }
    }
}