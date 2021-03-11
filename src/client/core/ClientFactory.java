package client.core;


import client.network.Client;
import client.network.SocketClient;

public class ClientFactory {

    private static Client client;

    public static Client getClient() {
        if (client == null) {
            client = new SocketClient();
        }
        return client;
    }
}
