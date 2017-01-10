package model.heroes;

public class BarbarianBuilder extends HeroBuilder {
    @Override
    void buildHP() {
        hero.setHp(650);
    }

    @Override
    void buildDMG() {
        hero.setDamage(45);
    }

    @Override
    void buildHeroType() {
        hero.setHero_type(2);
    }
}
