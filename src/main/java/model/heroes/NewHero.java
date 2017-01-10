package model.heroes;

import dao.Entity.Heroes;
import dao.Entity.Users;

public class NewHero {


    public Heroes getHero(Users user, int hero_type) {

        Heroes new_hero;
        HeroDirector heroDirector = new HeroDirector(user.getId());

        switch(hero_type){
            case 1:
                heroDirector.setHeroBuilder(new WarriorBuilder());
                break;
            case 2:
                heroDirector.setHeroBuilder(new BarbarianBuilder());
                break;
            case 3:
                heroDirector.setHeroBuilder(new AssassinBuilder());
                break;
            default:

        }

        new_hero = heroDirector.buildHero();

        return new_hero;
    }
}
