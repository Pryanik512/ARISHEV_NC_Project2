package model.heroes;

import dao.Entity.Heroes;
import dao.Entity.Users;

public class HeroOne implements IDefaultHeroes{

    private static final int HP_HERO_ONE = 500;
    private static final int DAMAGE_HERO_ONE = 60;



    public Heroes getHero(Users user, int heroType){

        Heroes new_hero = new Heroes();
        new_hero.setLevel(DEFAULT_LVL_HERO);
        new_hero.setHp(HP_HERO_ONE);
        new_hero.setDamage(DAMAGE_HERO_ONE);
        new_hero.setHero_type(heroType);
        new_hero.setUser_id(user.getId());

        return new_hero;

    }


}
