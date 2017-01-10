package model.heroes;

public class AssassinBuilder extends HeroBuilder {
    @Override
    void buildHP() {
        hero.setHp(450);
    }

    @Override
    void buildDMG() {
        hero.setDamage(80);
    }

    @Override
    void buildHeroType() {
        hero.setHero_type(3);
    }
}
