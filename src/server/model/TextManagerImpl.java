package server.model;

import shared.transferobjects.InputChat;
import shared.transferobjects.InputUser;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class TextManagerImpl implements TextManager{

    private PropertyChangeSupport support;
    private List<InputChat> chatListe;
    private List<InputUser> userNameList;

    public TextManagerImpl() {
        support = new PropertyChangeSupport(this);
        chatListe = new ArrayList<>();
        userNameList = new ArrayList<>();
    }

    @Override
    public String sendMsg(String str) {
        InputChat inputChat = new InputChat(str);
        chatListe.add(inputChat);
        support.firePropertyChange("NewInputChat", null, inputChat);
        System.out.println("support.getPropertyChangeListeners().length:" + support.getPropertyChangeListeners().length);
        return str;
    }

    @Override
    public List<InputChat> getChat() {
        return new ArrayList<>(chatListe);
    }

    @Override
    public String username(String txt) {
        InputUser inputUser = new InputUser(txt);
        userNameList.add(inputUser);
        support.firePropertyChange("NewInputUser", null, inputUser);
        System.out.println("support.getPropertyChangeListeners().length:" + support.getPropertyChangeListeners().length);
        return txt;

    }

    @Override
    public List<InputUser> getUser() {
        return new ArrayList<>(userNameList);
    }

    @Override
    public void addListener(String eventName, PropertyChangeListener listener) {
        support.addPropertyChangeListener(eventName, listener);
    }

    @Override
    public void removeListener(String eventName, PropertyChangeListener listener) {
        support.removePropertyChangeListener(eventName, listener);
    }
}
