package model.heroes;

import dao.Entity.Heroes;
import dao.Entity.Users;

public class NewHero {

    private static final int HP_WARRIOR = 500;
    private static final int DAMAGE_WARRIOR = 60;

    private static final int HP_BARBARIAN = 650;
    private static final int DAMAGE_BARBARIAN = 45;

    private static final int HP_ASSASSIN = 450;
    private static final int DAMAGE_ASSASSIN = 80;

    private static final int DEFAULT_LVL_HERO = 1;


    public Heroes getHero(Users user, int hero_type) {

        int heroHP;
        int heroDmg;

        switch(hero_type){
            case 1:
                heroHP = HP_WARRIOR;
                heroDmg = DAMAGE_WARRIOR;
                break;
            case 2:
                heroHP = HP_BARBARIAN;
                heroDmg = DAMAGE_BARBARIAN;
                break;
            case 3:
                heroHP = HP_ASSASSIN;
                heroDmg = DAMAGE_ASSASSIN;
                break;
            default:
                heroHP = 0;
                heroDmg = 0;
        }

        Heroes new_hero = new Heroes();
        new_hero.setLevel(DEFAULT_LVL_HERO);
        new_hero.setHp(heroHP);
        new_hero.setDamage(heroDmg);
        new_hero.setHero_type(hero_type);
        new_hero.setUser_id(user.getId());

        return new_hero;
    }
}
