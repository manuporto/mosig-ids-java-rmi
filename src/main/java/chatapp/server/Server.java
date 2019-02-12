package chatapp.server;

import chatapp.common.MessageService_itf;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {

    public static void main(String[] args) {
        try {
            // Create a Hello remote object
            BlockingQueue<Message> receivedMesages = new LinkedBlockingQueue<>();
            ClientRegister cRegister = new ClientRegister();
            MessageService_impl msgSv = new MessageService_impl(receivedMesages, cRegister);
            MessageService_itf msg_stub = (MessageService_itf) UnicastRemoteObject.exportObject(msgSv, 0);

            // Register the remote object in RMI registry with a given identifier
            Registry registry= LocateRegistry.createRegistry(1099);
            registry.bind("MessageService", msg_stub);

            System.out.println ("Server ready");

        } catch (Exception e) {
            System.err.println("Error on server :" + e) ;
            e.printStackTrace();
        }
    }
}
