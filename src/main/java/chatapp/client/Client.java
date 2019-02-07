package chatapp.client;

import chatapp.common.ClientInfo;
import chatapp.common.ClientInfo_itf;
import chatapp.common.MessageService_itf;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) {
        try {
            if (args.length < 1) {
                System.out.println("Usage: java Client <rmiregistry host>");
                return;}

            String host = args[0];
            ClientInfo_itf clientInfo = new ClientInfo("manu");

            // Get remote object reference
            Registry registry = LocateRegistry.getRegistry(host);
            MessageService_itf msgSrvc = (MessageService_itf) registry.lookup("MessageService");

            // Remote method invocation
            msgSrvc.sendBroadcastMessage(clientInfo, "hello world");

        } catch (Exception e)  {
            System.err.println("Error on client: " + e);
        }

    }
}