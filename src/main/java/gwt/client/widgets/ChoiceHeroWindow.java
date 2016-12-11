package gwt.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import gwt.client.GWTEntity.EntityForBattle;
import gwt.client.GWTEntity.UsersGWT;
import gwt.client.GameNCException;
import gwt.client.GameService;

public class ChoiceHeroWindow extends Composite{

    Button warrBtn = new Button("Warrior");
    Button barbBtn = new Button("Barbarian");
    Button asBtn = new Button("Assassin");
    HorizontalPanel choicePanel = new HorizontalPanel();

    public ChoiceHeroWindow(UsersGWT usersGWT){

        choicePanel.add(warrBtn);
        choicePanel.add(barbBtn);
        choicePanel.add(asBtn);

        RootPanel.get("NewAccPage").add(choicePanel);

        initWidget(choicePanel);

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
