package view.web.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;


public class GameNC implements EntryPoint {
    public void onModuleLoad() {

        Button button = new Button("LogIn");
        Label label_user = new Label("Username: ");
        TextBox name = new TextBox();

        Label label_pass = new Label("Password: ");
        PasswordTextBox pass = new PasswordTextBox();

        Label label_pass_reenter = new Label("Confirm Password: ");
        PasswordTextBox pass_reenter = new PasswordTextBox();


        RootPanel.get().add(label_user);
        RootPanel.get().add(name);

        RootPanel.get().add(label_pass);
        RootPanel.get().add(pass);

        RootPanel.get().add(label_pass_reenter);
        RootPanel.get().add(pass_reenter);

        RootPanel.get().add(button);

        button.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {

                Window.alert("You are not login, yet!");
            }
        });
    }
}
