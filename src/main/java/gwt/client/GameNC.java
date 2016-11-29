package gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;


public class GameNC implements EntryPoint {
    HorizontalPanel nameP = new HorizontalPanel();
    HorizontalPanel passP = new HorizontalPanel();
    VerticalPanel panelToCrate = new VerticalPanel();

    public void onModuleLoad() {
        Label unL = new Label("Username:");
        TextBox nameTxtBox = new TextBox();
        nameP.add(unL);
        nameP.add(nameTxtBox);

        Label passL = new Label("Password:");
        PasswordTextBox passTxtBox = new PasswordTextBox();
        passP.add(passL);
        passP.add(passTxtBox);

        Button createAcc = new Button("Create account");

        panelToCrate.add(nameP);
        panelToCrate.add(passP);
        panelToCrate.add(createAcc);

        RootPanel.get("NewAccPage").add(panelToCrate);

        createAcc.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {
                GameService.App.getInstance().addAccount(nameTxtBox.getText(), passTxtBox.getText(), new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable throwable) {

                        if(throwable instanceof GameNCException){
                            Window.alert(((GameNCException)throwable).getMsg());
                        }
                    }
                    @Override
                    public void onSuccess(Void result) {


                        String url = GWT.getHostPageBaseURL() + "NextPage.html";
                        Window.Location.assign(url);

                        HorizontalPanel np_hp = new HorizontalPanel();
                        Label lb = new Label();

                        np_hp.add(lb);
                        RootPanel.get("NextPage").add(np_hp);
                        //Window.alert("New user created!  " + url);

                    }
                });
            }
        });


    }
}
