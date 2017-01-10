package model.heroes;

import dao.Entity.Heroes;

public abstract class HeroBuilder {

    Heroes hero;
    void createHero(){
        hero = new Heroes();
    }

    Heroes getHero(){
        return hero;
    }

    void buildLevel(){
        hero.setLevel(1);
    }

    abstract void buildHP();

    abstract void buildDMG();


    abstract void buildHeroType();


    void buildUserID(int userID){
        hero.setUser_id(userID);
    }



}
