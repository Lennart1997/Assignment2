package client.core;

import client.views.ViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

// ret viewhandler

public class ViewHandler {

    private Scene chatScene;
    private Stage stage;
    private ViewModelFactory vmf;

    public ViewHandler(ViewModelFactory vmf) {
        this.vmf = vmf;
    }

    public void start() {
        stage = new Stage();
        openloginview();
    }

    public void openToChatView() {
        if (chatScene == null) {
            try {
                Parent root = loadFXML("../views/chat/chat.fxml");

                stage.setTitle("Chat");
                chatScene = new Scene(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        stage.setScene(chatScene);
        stage.show();
    }

    public void openloginview() {

        try {
            Parent root = loadFXML("../views/Loginbox/login.fxml");

            Scene logScene = new Scene(root);
            stage.setTitle("Log");
            stage.setScene(logScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //kig på det her igen
    private Parent loadFXML(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(path));
        Parent root = loader.load();

        ViewController ctrl = loader.getController();
        ctrl.init(this, vmf);
        return root;
    }
}
