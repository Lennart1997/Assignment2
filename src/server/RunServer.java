package server;


import server.model.TextManagerImpl;
import server.networking.SocketServer;

public class RunServer {
    public static void main(String[] args) {

        SocketServer ss = new SocketServer(new TextManagerImpl());
        ss.startServer();
    }
}
