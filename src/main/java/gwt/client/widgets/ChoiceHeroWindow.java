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

public class ChoiceHeroWindow extends Composite{
    Label info_lb = new Label("Choose a hero");
    Button warrBtn = new Button("Warrior");
    Button barbBtn = new Button("Barbarian");
    Button asBtn = new Button("Assassin");
    VerticalPanel mainPanel = new VerticalPanel();
    HorizontalPanel choicePanel = new HorizontalPanel();

    public ChoiceHeroWindow(UsersGWT usersGWT){

        mainPanel.add(info_lb);

        choicePanel.add(warrBtn);
        choicePanel.add(barbBtn);
        choicePanel.add(asBtn);

        mainPanel.add(choicePanel);

        RootPanel.get("NewAccPage").add(mainPanel);

        initWidget(mainPanel);

        warrBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                callGetHero(usersGWT, 1);
            }
        });

        barbBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                callGetHero(usersGWT, 2);
            }
        });

        asBtn.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                callGetHero(usersGWT, 3);
            }
        });
    }

    private static void callGetHero(UsersGWT correctUser,int hero_type){

        GameService.App.getInstance().getHero(correctUser, hero_type, new AsyncCallback<EntityForBattle>() {
            @Override
            public void onFailure(Throwable throwable) {

                if (throwable instanceof GameNCException) {
                    Window.alert(((GameNCException) throwable).getMsg());
                }
            }

            @Override
            public void onSuccess(EntityForBattle result) {
                RootPanel.get("NewAccPage").clear();
                RootPanel.get("NewAccPage").add(new BattleWindow(result));
            }
        });

    }

}
