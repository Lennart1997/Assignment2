package client.views.chat;

import client.model.TextConverter;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shared.transferobjects.InputChat;
import shared.transferobjects.InputUser;

import java.beans.PropertyChangeEvent;
import java.util.List;

public class chatViewModel {

    private StringProperty request;

    private client.views.Loginbox.loginViewModel vm;

    private ObservableList<InputChat> chats;
    private ObservableList<InputUser> users;

    private TextConverter textConverter;

    private String navn;

    public String getNavn() {
        return vm.navnProperty().getName();
    }

    public StringProperty getRequest(){
        return request;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public chatViewModel(TextConverter textConverter) {
        this.textConverter = textConverter;
        textConverter.addListener("NewInputChat", this::onNewInputChat);
        request = new SimpleStringProperty();
        textConverter.addListener("NewInputUser", this::OnNewInputUser);

    }

    // lav request om til chat
    void chatPrint() {
        if (request.getValue() != null && !"".equals(request.getValue())) {
            textConverter.sendMsg(request.getValue());
            textConverter.sendMsg("Besked fra: " + navn);


        } else {
            System.out.println(request.getValue());

        }
        request.set("");
    }

    public void onNewInputChat(PropertyChangeEvent evt) {
      chats.add((InputChat) evt.getNewValue());


    }

    public void OnNewInputUser(PropertyChangeEvent evt){
        users.add((InputUser) evt.getNewValue());

    }


    void loadLogs() {
        List<InputChat> chatList = textConverter.getChat();
        chats = FXCollections.observableArrayList(chatList);
    }

    ObservableList<InputChat> getChats() {
        return chats;
    }

    ObservableList<InputUser> getUsers() {
        return users;
    }


    public void loadLogs2() {
        List<InputUser> userLists = textConverter.getUser();
        users = FXCollections.observableArrayList(userLists);
    }

}
