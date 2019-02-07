package chatapp.common;

public interface MessageService_itf {
    
   public String sendMessage(ClientInfo_itf name, String message);
           
           
   public String sendBroadcastMessage(String message);
           
    
}
