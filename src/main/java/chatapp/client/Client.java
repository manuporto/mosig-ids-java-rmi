package chatapp.client;

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
            System.out.println("Please select your nickname: ");
            Scanner scanner = new Scanner(System.in);
            String username = scanner.nextLine();
            ClientInfo_itf clientInfo = new ClientInfo(username);

            // Get remote object reference
            Registry registry = LocateRegistry.getRegistry(host);
            MessageService_itf msgSrvc = (MessageService_itf) registry.lookup("MessageService");

            // Remote method invocation
            System.out.println("Type the message below. For exit type /exit");
            String message = scanner.nextLine();
            while (!message.equals("/exit")) {
                msgSrvc.sendBroadcastMessage(clientInfo, message);
                message = scanner.nextLine();
            }

        } catch (Exception e)  {
            System.err.println("Error on client: " + e);
        }

    }
}