package chatapp.server;

import chatapp.common.ClientInfo_itf;
import chatapp.common.MessageService_itf;

public class MessageService_impl implements MessageService_itf {
    public void sendMessage(ClientInfo_itf src, ClientInfo_itf dest, String message) {
        return;
    }

    public void sendBroadcastMessage(ClientInfo_itf src, String message) {
        System.out.println(src.getUsername() + ": " +  message);
        return;
    }
}
