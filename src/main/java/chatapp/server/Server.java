package chatapp.server;

import chatapp.common.AccessServiceItf;
import chatapp.common.MessageService_itf;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {

    public static void main(String[] args) throws IOException, AlreadyBoundException, InterruptedException {
        // Create remote objects
        BlockingQueue<Message> receivedMessages = new LinkedBlockingQueue<>();
        ClientRegister cRegister = new ClientRegister();
        AccessServiceItf cRegisterStub = (AccessServiceItf) UnicastRemoteObject.exportObject(cRegister, 0);
        MessageService_impl msgSv = new MessageService_impl(receivedMessages, cRegister);
        MessageService_itf msgStub = (MessageService_itf) UnicastRemoteObject.exportObject(msgSv, 0);

        // Register the remote objects in RMI registry with a given identifier
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.bind("MessageService", msgStub);
        registry.bind("AccessService", cRegisterStub);

        // Run MessageStorer
        Thread t1;
        t1 = new Thread(new MessageStorer(receivedMessages));
        t1.start();
        System.out.println("Server ready!!");
        System.out.println("To exit press Q");
        Scanner scanner = new Scanner(System.in);
        String letter = scanner.nextLine();
        while (!letter.equalsIgnoreCase("q")) {
            System.out.println("Incorrect letter, press Q to exit.");
            letter = scanner.nextLine();
        }

        // Close server
        t1.interrupt();
        t1.join();
        scanner.close();
        UnicastRemoteObject.unexportObject(cRegister, true);
        UnicastRemoteObject.unexportObject(msgSv, true);
        UnicastRemoteObject.unexportObject(registry, true);
        System.out.println("Server closed.");
    }
}
