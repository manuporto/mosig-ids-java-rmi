package chatapp.client;

import chatapp.common.AccessServiceItf;
import chatapp.common.ClientInfo;
import chatapp.common.ClientInfo_itf;
import chatapp.common.MessageService_itf;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            if (args.length < 1) {
                System.out.println("Usage: java Client <rmiregistry host>");
                return;}

            String host = args[0];

            // Get remote object reference
            Registry registry = LocateRegistry.getRegistry(host);
            AccessServiceItf accessService = (AccessServiceItf) registry.lookup("AccessService");
            MessageService_itf msgSrvc = (MessageService_itf) registry.lookup("MessageService");

            System.out.println("Please select your nickname: ");
            Scanner scanner = new Scanner(System.in);
            String username = scanner.nextLine();
            while (!accessService.userNameAvailable(username)) {
                System.out.println("Username already taken, pleas choose another one.");
                username = scanner.nextLine();
            }
            ClientInfo_itf clientInfo = new ClientInfo(username);
            accessService.join(clientInfo);

            // Remote method invocation
            System.out.println("Type the message below. For exit type /exit");
            String message = scanner.nextLine();
            while (!message.equals("/exit")) {
                // TODO add switch case to differentiate between different user actions
                msgSrvc.sendBroadcastMessage(clientInfo, message);
                message = scanner.nextLine();
            }

            accessService.leave(clientInfo);
        } catch (Exception e)  {
            System.err.println("Error on client: " + e);
        }

    }
}