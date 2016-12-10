package model.heroes;

import dao.Entity.Heroes;
import dao.Entity.Users;

public interface IDefaultHeroes  {

    int DEFAULT_LVL_HERO = 1;

    Heroes getHero(Users user, int hero_type);


   }
