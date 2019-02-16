package chatapp.client;

import chatapp.common.AccessServiceItf;
import chatapp.common.MessageServiceItf;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ChatInterface {

    private String clientName;
    private AccessServiceItf accessService;
    private MessageServiceItf messageService;

    public ChatInterface(String clientName, AccessServiceItf accessService, MessageServiceItf messageService) {
        this.clientName = clientName;
        this.accessService = accessService;
        this.messageService = messageService;
    }

    public void run() throws RemoteException {
        printHelpText();
        Scanner scanner = new Scanner(System.in);
        String message = scanner.nextLine();
        while (!message.equals("/exit")) {
            List<String> command = Arrays.asList(message.split(" "));
            switch (command.get(0)) {
                case "/msg":
                    handleMessage(command);
                    break;
                case "/bmsg":
                    handleBroadcastMessage(command);
                    break;
                case "/clients":
                    handleClientsRequest();
                    break;
                case "/history":
                    handleHistoryRequest();
                    break;
                default:
                    System.out.println("Command not recognized.");
                    printHelpText();
            }
            message = scanner.nextLine();
        }
        scanner.close();
    }

    private void printHelpText() {
        System.out.print("List of commands:\n/msg <destName> <message>: sends direct message to user." +
                "\n/bmsg <message>: sends broadcast message.\n/clients: prints list of connected users." +
                "\n/history: get full message history.\n/exit: leave the chat room.\n");
    }

    private void handleMessage(List<String> command) throws RemoteException {
        if (command.size() < 3) {
            printHelpText();
            return;
        }
        List<String> message = command.subList(2, command.size());
        String strMessage = String.join(" ", message);
        messageService.sendMessage(clientName, command.get(1), strMessage);
    }

    private void handleBroadcastMessage(List<String> command) throws RemoteException {
        if (command.size() < 2) {
            printHelpText();
            return;
        }
        List<String> message = command.subList(1, command.size());
        String strMessage = String.join(" ", message);
        messageService.sendBroadcastMessage(clientName, strMessage);
    }

    private void handleClientsRequest() throws RemoteException {
        Iterable<String> clients = accessService.getConnectedClients();
        System.out.println("Connected clients: ");
        for (String client : clients) System.out.println(client);
    }

    private void handleHistoryRequest() throws RemoteException {
        ArrayList<String> history = messageService.getMessageHistory(clientName);
        for (String msg : history) System.out.println(msg);
    }
}
