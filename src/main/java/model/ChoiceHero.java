package model;

import dao.DBFactory;
import dao.Entity.Heroes;
import dao.Entity.Users;
import dao.HeroDAO;
import dao.UserDAO;
import model.heroes.NewHero;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class ChoiceHero {
       private static Logger loger = LoggerFactory.getLogger(ChoiceHero.class.getName());
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


              heroForUser = heroDAO.findHero(currentUser.getName(), hero_type);

              if(heroForUser != null && (heroForUser.getHp() == 0 & heroForUser.getDamage() == 0)){
                     idHero = heroForUser.getId();

                     NewHero newHeroOne = new NewHero();
                     heroForUser = newHeroOne.getHero(currentUser, hero_type);
                     heroForUser.setId(idHero);
                     heroDAO.updateHero(heroForUser);
              }
              db_access.closeConnection();
              return heroForUser;

       }
}
