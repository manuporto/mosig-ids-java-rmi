/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

public interface MessageService_itf {
    
   public String sendMessage(ClientInfo_itf name, String message);
           
           
   public String sendBroadcastMessage(String message);
           
    
}
