package client.views.Loginbox;

import client.core.ViewHandler;
import client.model.TextConverter;
import client.views.chat.chatViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import shared.transferobjects.InputChat;
import shared.transferobjects.InputUser;

import java.beans.PropertyChangeEvent;

public class loginViewModel {
    private TextConverter textConverter;
    private ViewHandler viewHandler;
    private chatViewModel ch;
    private StringProperty navn;
    private StringProperty kode;
    private StringProperty error;


    public loginViewModel(TextConverter textConverter) {
        this.textConverter = textConverter;
        navn = new SimpleStringProperty();
        kode = new SimpleStringProperty();
        error = new SimpleStringProperty();
    }

    public void printNavn() {
        String input = navn.getValue() ;
        String trys = kode.getValue();


         if (input != null && !"".equals(input) && trys != null && !"".equals(trys))
            {
                textConverter.username(input);
                error.set("korrekt oprettet");

            } else {
                    error.set("Feltet kan ikke være tomt");

                }
         kode.set("");

            }




    public StringProperty errorProperty() {
        return error;
    }

    public StringProperty navnProperty() {
        return navn;
    }

    public StringProperty kodeProperty() {
        return kode;
    }

}
