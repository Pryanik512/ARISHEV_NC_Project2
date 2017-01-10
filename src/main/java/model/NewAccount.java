package model;

import dao.*;
import dao.Entity.Heroes;
import dao.Entity.Statistics;
import dao.Entity.Users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class NewAccount {
    private static Logger loger = LoggerFactory.getLogger(NewAccount.class.getName());
    private DBFactory db_access = new DBFactory();
    private UserDAO userDAO;
    private SecurityDAO securityDAO;
    private StatisticDAO statisticDAO;
    private HeroDAO heroDAO;
    private Users new_user;
    private Statistics statistics;
    private Heroes hero;

    public NewAccount(){
        try {
            db_access.getConnection();
            userDAO = db_access.createUserDAO();
            securityDAO = db_access.createSecurityDAO();
            statisticDAO = db_access.createStatisticDAO();
            heroDAO = db_access.createHeroDAO();


        }catch(ClassNotFoundException | SQLException ex){loger.error("Error during authentication:" + ex);}

    }

    public Users add(String user_name, String password) throws SQLException{
        try {
            if (this.checkUniqName(user_name)) {

                new_user = new Users();
                statistics = new Statistics();
                hero = new Heroes();

                new_user.setName(user_name);
                userDAO.createUser(new_user);
                new_user = userDAO.findUser(user_name);
                securityDAO.createSecurity(new_user, password);

                this.createNewStatisticRecord();

                statisticDAO.createStatistic(statistics, new_user);

                for (int i = 1; i <= 3; i++) {
                    this.createNewHeroRecord(i);
                    heroDAO.createHero(hero, new_user);
                }

                return new_user;
            } else {
                return null;
            }
        }finally {
            if(db_access != null)
                db_access.closeConnection();
        }
    }

    private boolean checkUniqName(String name) throws SQLException{

        if(userDAO.findUser(name) == null)
            return true;
        else
            return false;

    }

    private void createNewStatisticRecord(){
        statistics.setUser_level(0);
        statistics.setWin(0);
        statistics.setDefeat(0);

    }

    private void createNewHeroRecord(int hero_type){
        hero.setHero_type(hero_type);
        hero.setDamage(0);
        hero.setHp(0);
        hero.setLevel(0);

    }


}


