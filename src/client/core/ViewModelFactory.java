package client.core;


import client.views.Loginbox.loginViewModel;
import client.views.chat.chatViewModel;

public class ViewModelFactory {


    private final ModelFactory mf;
    private client.views.Loginbox.loginViewModel loginViewModel;
    private chatViewModel chatViewModel;

    public ViewModelFactory(ModelFactory mf) {
        this.mf=mf;
    }

    public client.views.Loginbox.loginViewModel getloginViewModel() {
        if (loginViewModel == null)
            loginViewModel = new loginViewModel(mf.getTextConverter());
        return loginViewModel;
    }

    public chatViewModel getchatViewModel() {
        return (chatViewModel = chatViewModel == null ?
                new chatViewModel(mf.getTextConverter()) :
                chatViewModel);
    }

}