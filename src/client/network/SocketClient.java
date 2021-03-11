package client.network;

import shared.transferobjects.InputChat;
import shared.transferobjects.InputUser;
import shared.transferobjects.message;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class SocketClient implements Client {

private PropertyChangeSupport support;

public SocketClient() {
    support = new PropertyChangeSupport(this);
}

public void startClient() {
    try {
        Socket socket = new Socket("localhost", 5516);
        ObjectOutputStream outToServer = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream inFromServer = new ObjectInputStream(socket.getInputStream());

        Thread thread = new Thread(() -> listenToServer(outToServer, inFromServer));
        thread.start();

    } catch (IOException e) {
        e.printStackTrace();
    }
}

//
private void listenToServer(ObjectOutputStream outToServer, ObjectInputStream inFromServer) {
    try {
        outToServer.writeObject(new message("Listener", null));
        while (true) {
            message messagerequest = (message) inFromServer.readObject();
            support.firePropertyChange(messagerequest.getType(), null, messagerequest.getArg());
        }
    } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
    }
}

@Override
public String sendMsg(String str) {
    try {
        message response = sendRequest(str, "sendMessage");
        return (String)response.getArg();
    } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
    }
    return str;
}

@Override
public List<InputChat> getChat() {
    try {
        message response =sendRequest(null, "FetchLog");
        return (List<InputChat>) response.getArg();
    } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
    }
    return null;
}

    @Override
    public String username(String txt) {

        try {
            message response = sendRequest(txt, "Username");
            return (String)response.getArg();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return txt;

    }

    @Override
    public List<InputUser> getUser() {
        try {
            message response = sendRequest(null, "FetchLogOut");
            return (List<InputUser>) response.getArg();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private message sendRequest(String arg, String type) throws IOException, ClassNotFoundException {
    Socket socket = new Socket("localhost", 5516);
    ObjectOutputStream outToServer = new ObjectOutputStream(socket.getOutputStream());
    ObjectInputStream inFromServer = new ObjectInputStream(socket.getInputStream());
    outToServer.writeObject(new message(type, arg));
    return (message) inFromServer.readObject();
}

@Override
public void addListener(String eventName, PropertyChangeListener listener) {
    support.addPropertyChangeListener(eventName, listener);
    System.out.println("support.getPropertyChangeListeners().length:" + support.getPropertyChangeListeners().length);
}

@Override
public void removeListener(String eventName, PropertyChangeListener listener) {
    support.removePropertyChangeListener(eventName, listener);
}

}
