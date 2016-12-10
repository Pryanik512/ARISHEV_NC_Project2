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

    Button f_hero = new Button("Hero 1");
    Button s_hero = new Button("Hero 2");
    Button t_hero = new Button("Hero 3");
    HorizontalPanel choicePanel = new HorizontalPanel();

    public ChoiceHeroWindow(UsersGWT usersGWT){

        choicePanel.add(f_hero);
        choicePanel.add(s_hero);
        choicePanel.add(t_hero);

        RootPanel.get("NewAccPage").add(choicePanel);

        initWidget(choicePanel);

        f_hero.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                callGetHero(usersGWT, 1);
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
