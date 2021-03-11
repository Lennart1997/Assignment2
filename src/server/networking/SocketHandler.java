package server.networking;

import server.model.TextManager;
import shared.transferobjects.InputChat;
import shared.transferobjects.InputUser;
import shared.transferobjects.message;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class SocketHandler implements Runnable {

    private Socket socket;
    private TextManager textManager;

    private ObjectOutputStream outToClient;
    private ObjectInputStream inFromClient;

    public SocketHandler(Socket socket, TextManager textManager ) {
        this.socket = socket;
        this.textManager = textManager;

        try {
            outToClient = new ObjectOutputStream(socket.getOutputStream());
            inFromClient = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                message request = (message) inFromClient.readObject();
                if ("Listener".equals(request.getType())) {
                    textManager.addListener("NewInputChat", this::onNewInputChat);
                    textManager.addListener("NewInputUser", this::onNewInputUser);
                } else if ("sendMessage".equals(request.getType())) {
                    String result = textManager.sendMsg((String) request.getArg());
                    outToClient.writeObject(new message("sendMessage", result));
                } else if ("FetchLog".equals(request.getType())) {
                    List<InputChat> log = textManager.getChat();
                    outToClient.writeObject(new message("FetchLog", log));

                } else if ("Username".equals(request.getType())) {
                    String txt = textManager.username((String) request.getArg());
                    outToClient.writeObject(new message("Username", txt));

                } else if ("FetchLogOut".equals(request.getType())) {
                    List<InputUser> logOut = textManager.getUser();
                    outToClient.writeObject(new message("FetchLogOut", logOut));

                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void onNewInputChat(PropertyChangeEvent evt) {
        try {
            outToClient.writeObject(new message(evt.getPropertyName(), evt.getNewValue()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onNewInputUser(PropertyChangeEvent evt) {
        try {
            outToClient.writeObject(new message(evt.getPropertyName(), evt.getNewValue()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
