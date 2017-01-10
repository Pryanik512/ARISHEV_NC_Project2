package model.heroes;

public class WarriorBuilder extends HeroBuilder {
    @Override
    void buildHP() {
        hero.setHp(500);
    }

    @Override
    void buildDMG() {
        hero.setDamage(60);
    }

    @Override
    void buildHeroType() {
        hero.setHero_type(1);
    }
}
