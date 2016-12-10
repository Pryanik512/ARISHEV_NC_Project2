package gwt.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import gwt.client.GWTEntity.EntityForBattle;
import gwt.client.GWTEntity.UsersGWT;
import gwt.client.GameNCException;
import gwt.client.GameService;

public class MainMenuWindow extends Composite {

    Label user_name = new Label();

    Button startBattle_btn = new Button("Start Battle");
    Button statistics_btn = new Button("Statistics");

    VerticalPanel menuPanel = new VerticalPanel();

    public MainMenuWindow(UsersGWT correctUser) {

        user_name.setText("Username: " + correctUser.getName() + " " + correctUser.getId());
        menuPanel.add(user_name);
        menuPanel.add(startBattle_btn);
        menuPanel.add(statistics_btn);


        RootPanel.get("NewAccPage").add(menuPanel);

        initWidget(menuPanel);
        startBattle_btn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {

                RootPanel.get("NewAccPage").clear();
                RootPanel.get("NewAccPage").add(new ChoiceHeroWindow(correctUser));


            }
        });

        statistics_btn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Window.alert("To stat window");
            }
        });
    }


}
