package gwt.client.widgets;

import com.google.gwt.user.client.ui.*;
import gwt.client.GWTEntity.EntityForBattle;

public class BattleWindow extends Composite {
    Label userNameLB = new Label();
    Label heroTypeLB = new Label();
    Label heroLvlLB = new Label();
    Label heroHpLB = new Label();
    Label heroDmgLB = new Label();
    Grid battleGround = new Grid(7,9);

    HorizontalPanel horizontalPanel = new HorizontalPanel();
    VerticalPanel groundPanel = new VerticalPanel();
    VerticalPanel panelForUser = new VerticalPanel();
    VerticalPanel panelForHero = new VerticalPanel();

    public BattleWindow(EntityForBattle efb){



        userNameLB.setText("Username: " + efb.getUserName());

        switch (efb.getHeroType()){
            case 1:
                heroTypeLB.setText("Hero type: Warrior");
                break;
            case 2:
                heroTypeLB.setText("Hero type: Barbarian");
                break;
            case 3:
                heroTypeLB.setText("Hero type: Assassin");
                break;
        }

        heroLvlLB.setText("Hero level: " + efb.getHeroLvl());
        heroHpLB.setText("   HP: " + efb.getHeroHP());
        heroDmgLB.setText("  Dmg: " + efb.getHeroDamage());

        panelForUser.add(userNameLB);
        panelForUser.add(heroTypeLB);
        panelForUser.add(heroLvlLB);

        panelForHero.add(heroHpLB);
        panelForHero.add(heroDmgLB);

        horizontalPanel.add(panelForUser);
        horizontalPanel.add(panelForHero);

        groundPanel.add(battleGround);
        groundPanel.add(horizontalPanel);
        RootPanel.get("NewAccPage").add(groundPanel);

        initWidget(groundPanel);

    }

}
