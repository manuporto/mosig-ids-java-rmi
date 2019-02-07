package chatapp.server;

import chatapp.common.AccessServiceItf;
import chatapp.common.ClientInfo_itf;

import java.util.HashMap;
import java.util.Map;

public class ClientRegister implements AccessServiceItf {

    private Map<ClientInfo_itf, String> clients;

    ClientRegister() {
        clients = new HashMap<>();
    }

    public void join(ClientInfo_itf client) {
        clients.put(client, client.getUsername());
    }

    public void leave(ClientInfo_itf client) {
        clients.remove(client);
    }
}
