package model;

import dao.DBFactory;
import dao.Entity.Heroes;
import dao.Entity.Users;
import dao.HeroDAO;
import dao.UserDAO;
import model.heroes.HeroOne;
import model.heroes.IDefaultHeroes;
import org.apache.log4j.Logger;

import java.sql.SQLException;

public class ChoiceHero {
       private static Logger loger = Logger.getLogger(ChoiceHero.class.getName());
       private DBFactory db_access = new DBFactory();
       private UserDAO userDAO;
       private HeroDAO heroDAO;
       private Heroes heroForUser;

       public ChoiceHero(){
              try{
                     db_access.getConnection();
                     userDAO = db_access.createUserDAO();
                     heroDAO = db_access.createHeroDAO();

              }catch(ClassNotFoundException | SQLException ex){loger.error("Error during creating hero:" + ex);}
       }

       public Heroes createHeroForCurrentUser(Users currentUser, int hero_type) throws SQLException{

              int idHero;

              if(hero_type == 1){
                     heroForUser = heroDAO.findHero(currentUser.getName(), 1);

                     if(heroForUser.getHp() == 0){
                            idHero = heroForUser.getId();

                            IDefaultHeroes newHeroOne = new HeroOne();
                            heroForUser = newHeroOne.getHero(currentUser, 1);
                            heroForUser.setId(idHero);
                            heroDAO.updateHero(heroForUser);

                     }

              }

              return heroForUser;

       }
}
