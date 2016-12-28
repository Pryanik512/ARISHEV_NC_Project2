package gwt.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

import gwt.client.GWTEntity.UsersGWT;
import gwt.client.GameNCException;
import gwt.client.GameService;

public class LogInWindow extends Composite {


    private Label un_label = new Label("Username:");
    private TextBox un_text = new TextBox();
    private Label pass_label = new Label("Password:");
    private PasswordTextBox pass_text = new PasswordTextBox();
    private Button en_button = new Button("Enter");
    private Button reg_button = new Button("Registration");
    private VerticalPanel verticalPanel = new VerticalPanel();

    public LogInWindow(){
        Label header_label = new Label("Log In");

        verticalPanel.add(header_label);
        verticalPanel.add(un_label);
        verticalPanel.add(un_text);

        verticalPanel.add(pass_label);
        verticalPanel.add(pass_text);
        verticalPanel.add(en_button);
        verticalPanel.add(reg_button);

        en_button.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {


                GameService.App.getInstance().logIn(un_text.getText().toUpperCase(), pass_text.getText().toUpperCase(), new AsyncCallback<UsersGWT>() {
                    @Override
                    public void onFailure(Throwable throwable) {

                        if (throwable instanceof GameNCException) {
                            Window.alert(((GameNCException) throwable).getMsg());
                        }
                    }
                    @Override
                    public void onSuccess(UsersGWT result) {

                        RootPanel.get("NewAccPage").clear();
                        RootPanel.get("NewAccPage").add(new MainMenuWindow(result));
                    }
                });
            }
        });



        reg_button.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                RootPanel.get("NewAccPage").clear();
                RootPanel.get("NewAccPage").add(new NewAccountWindow());
            }
        });

        initWidget(verticalPanel);
    }


}
