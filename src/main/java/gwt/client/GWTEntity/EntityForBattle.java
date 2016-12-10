package gwt.client.GWTEntity;

import dao.Entity.Users;

import java.io.Serializable;

public class EntityForBattle implements Serializable{

    String userName;
    int heroDamage;
    int heroHP;
    int heroLvl;
    int heroType;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getHeroDamage() {
        return heroDamage;
    }

    public void setHeroDamage(int heroDamage) {
        this.heroDamage = heroDamage;
    }

    public int getHeroHP() {
        return heroHP;
    }

    public void setHeroHP(int heroHP) {
        this.heroHP = heroHP;
    }

    public int getHeroLvl() {
        return heroLvl;
    }

    public void setHeroLvl(int heroLvl) {
        this.heroLvl = heroLvl;
    }

    public int getHeroType() {
        return heroType;
    }

    public void setHeroType(int heroType) {
        this.heroType = heroType;
    }
}
