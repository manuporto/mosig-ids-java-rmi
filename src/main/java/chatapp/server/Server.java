package chatapp.server;

import chatapp.common.AccessServiceItf;
import chatapp.common.MessageServiceItf;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) throws IOException, AlreadyBoundException, InterruptedException {
        // Create remote objects
        ClientRegister cRegister = new ClientRegister();
        AccessServiceItf cRegisterStub = (AccessServiceItf) UnicastRemoteObject.exportObject(cRegister, 0);

        MessageStorer msgStorer = new MessageStorer();
        MessageServiceImpl msgSv = new MessageServiceImpl(msgStorer, cRegister);
        MessageServiceItf msgStub = (MessageServiceItf) UnicastRemoteObject.exportObject(msgSv, 0);

        // Register the remote objects in RMI registry with a given identifier
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.bind("MessageService", msgStub);
        registry.bind("AccessService", cRegisterStub);

        System.out.println("Server ready!!");
        System.out.println("To exit press Q.");
        Scanner scanner = new Scanner(System.in);
        String letter = scanner.nextLine();
        while (!letter.equalsIgnoreCase("q")) {
            System.out.println("Incorrect letter, press Q to exit.");
            letter = scanner.nextLine();
        }

        // Close server
        msgStorer.close();
        scanner.close();
        UnicastRemoteObject.unexportObject(cRegister, true);
        UnicastRemoteObject.unexportObject(msgSv, true);
        UnicastRemoteObject.unexportObject(registry, true);
        System.out.println("Server closed.");
    }
}
