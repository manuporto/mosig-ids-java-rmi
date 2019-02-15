package chatapp.client;

import chatapp.common.AccessServiceItf;
import chatapp.common.ClientInfo_itf;
import chatapp.common.MessageService_itf;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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
        MessageService_itf msgSrvc = (MessageService_itf) registry.lookup("MessageService");

        System.out.println("Please select your nickname: ");
        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();
        while (!accessService.userNameAvailable(username)) {
            System.out.println("Username already taken, please choose another one.");
            username = scanner.nextLine();
        }
        ClientInfo_itf clientInfo = new ClientInfo(username);
        accessService.join(clientInfo);

        // Remote method invocation
        System.out.println("Type the message below. For exit type /exit");
        String message = scanner.nextLine();
        while (!message.equals("/exit")) {
            List<String> command = Arrays.asList(message.split(" "));
            switch (command.get(0)) {
                case "/msg":
                    msgSrvc.sendMessage(clientInfo.getUsername(), command.get(1), command.get(2));
                    break;
                case "/bmsg":
                    msgSrvc.sendBroadcastMessage(clientInfo.getUsername(), command.get(1));
                    break;
                case "/clients":
                    Iterable<String> clients = msgSrvc.getClients();
                    System.out.println("Connected clients: ");
                    for (String client : clients) System.out.println(client);
                    break;
                case "/history":
                    System.out.println("Not implemented!");
                    break;
                default:
                    System.out.println("Command not recognized.");
                    System.out.println("List of comannds....");
            }
            message = scanner.nextLine();
        }

        accessService.leave(clientInfo);
        registry.unbind("AccessService");
        registry.unbind("MessageService");
        scanner.close();
    }
}