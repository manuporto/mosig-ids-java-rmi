package chatapp.common;

public interface MessageService_itf {
    
   void sendMessage(ClientInfo_itf src, ClientInfo_itf dest, String message);
           
   void sendBroadcastMessage(ClientInfo_itf src, String message);
}
