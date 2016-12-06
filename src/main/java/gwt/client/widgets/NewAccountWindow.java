package gwt.client.widgets;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import gwt.client.GameNCException;
import gwt.client.GameService;



public class NewAccountWindow extends Composite {

    Label unL = new Label("Username:");
    TextBox nameTxtBox = new TextBox();
    Label passL = new Label("Password:");
    PasswordTextBox passTxtBox = new PasswordTextBox();
    Label repPassL = new Label("Repeat password:");
    PasswordTextBox repPassTxtBox = new PasswordTextBox();
    Button createAcc = new Button("Create account");


    VerticalPanel panelToCreate = new VerticalPanel();

    public NewAccountWindow() {

        panelToCreate.add(unL);
        panelToCreate.add(nameTxtBox);
        panelToCreate.add(passL);
        panelToCreate.add(passTxtBox);
        panelToCreate.add(repPassL);
        panelToCreate.add(repPassTxtBox);

        panelToCreate.add(createAcc);

        RootPanel.get("NewAccPage").add(panelToCreate);



        createAcc.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent clickEvent) {
                String userName = nameTxtBox.getText().toUpperCase().trim();
                String userPass = passTxtBox.getText().toUpperCase().trim();
                String repUserPass = repPassTxtBox.getText().toUpperCase().trim();

                if(userName.matches("^[0-9A-Z_-]{3,16}$")) {
                    if (userPass.matches("^.{6,}$") && userPass.compareTo(repUserPass)==0) {
                        GameService.App.getInstance().addAccount(userName, userPass, new AsyncCallback<Void>() {
                            @Override
                            public void onFailure(Throwable throwable) {
                                if (throwable instanceof GameNCException) {
                                    Window.alert(((GameNCException) throwable).getMsg());
                                }
                            }

                            @Override
                            public void onSuccess(Void result) {

                            }
                        });
                    }
                    else{
                        Window.alert("Passwords do not match or is not long enough");
                        passTxtBox.setText("");
                        repPassTxtBox.setText("");
                    }
                }
                else{
                    Window.alert(userName + " is not valid symbol or Username is not long enough ");
                    nameTxtBox.setText("");
                }
            }
        });
    }

}
