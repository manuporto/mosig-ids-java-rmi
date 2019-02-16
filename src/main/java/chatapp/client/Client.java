package chatapp.client;

import chatapp.common.AccessServiceItf;
import chatapp.common.ClientInfoItf;
import chatapp.common.MessageServiceItf;

import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        if (args.length < 1) {
            System.out.println("Usage: java Client <rmiregistry host>");
            return;
        }

        String host = args[0];

        // Get remote object reference
        Registry registry = LocateRegistry.getRegistry(host);
        AccessServiceItf accessService = (AccessServiceItf) registry.lookup("AccessService");
        MessageServiceItf msgSrvc = (MessageServiceItf) registry.lookup("MessageService");

        System.out.println("Please select your nickname: ");
        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();
        ClientInfoItf clientInfo = new ClientInfo(username);
        while (!accessService.join(clientInfo)) {
            System.out.println("Username already taken, please choose another one.");
            username = scanner.nextLine();
            ((ClientInfo) clientInfo).setUserName(username);
        }

        // Remote method invocation
        ChatInterface chat = new ChatInterface(clientInfo.getUsername(), accessService, msgSrvc);
        chat.run();

        scanner.close();
        try {
            accessService.leave(clientInfo);
            UnicastRemoteObject.unexportObject(clientInfo, true);
        } catch (ConnectException e) {
            Logger.getLogger(Client.class.getName()).log(Level.INFO, "Server already closed");
            System.exit(0);
        }
    }
}