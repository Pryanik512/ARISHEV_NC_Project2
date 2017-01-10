package model.heroes;

import dao.Entity.Heroes;

public class HeroDirector {
    private HeroBuilder builder;
    private int userID;

    HeroDirector(int userID){
        this.userID = userID;
    }
    void setHeroBuilder(HeroBuilder builder){
        this.builder = builder;
    }

     Heroes buildHero(){

        builder.createHero();
        builder.buildUserID(userID);
        builder.buildHeroType();
        builder.buildHP();
        builder.buildDMG();
        builder.buildLevel();

        return builder.getHero();
    }
}
