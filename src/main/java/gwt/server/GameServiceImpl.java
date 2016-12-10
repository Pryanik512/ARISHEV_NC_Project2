package gwt.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import dao.Entity.Heroes;
import dao.Entity.Users;
import gwt.client.GWTEntity.EntityForBattle;
import gwt.client.GWTEntity.UsersGWT;
import gwt.client.GameNCException;
import gwt.client.GameService;
import model.ChoiceHero;
import model.LogIn;
import model.NewAccount;
import org.apache.log4j.Logger;

import java.sql.SQLException;


public class GameServiceImpl extends RemoteServiceServlet implements GameService {
    private static Logger loger = Logger.getLogger(GameServiceImpl.class.getName());
    @Override
    public UsersGWT addAccount(String name, String pass) throws GameNCException{
        try {
            NewAccount newAccount = new NewAccount();
            Users newUser = newAccount.add(name, pass);
            if(newUser == null){
                throw new GameNCException("User " + name + " already exist!");
            }
            UsersGWT newUserGWT = new UsersGWT();
            newUserGWT.setId(newUser.getId());
            newUserGWT.setName(newUser.getName());

            return newUserGWT;

        }catch (SQLException ex){
            loger.error("Problem with Data Base: " + ex);
            throw new GameNCException("Problem with Data Base!");
        }

    }

    @Override
    public UsersGWT logIn(String name, String pass) throws GameNCException{
        try {
            LogIn logInUser = new LogIn();
            Users user = logInUser.makeLogIn(name, pass);
            if(user == null){
                throw new GameNCException("Wrong name or password!");
            }
            UsersGWT usersGWT = new UsersGWT();
            usersGWT.setId(user.getId());
            usersGWT.setName(user.getName());

            return usersGWT;

        }catch (SQLException ex){
            loger.error("Problem with Data Base: " + ex);
            throw new GameNCException("Problem with Data Base!");
        }
    }

    public EntityForBattle getHero(UsersGWT usersGWT, int hero_type) throws GameNCException{

        try{
            Users userDao = new Users();
            ChoiceHero choiceHero = new ChoiceHero();
            userDao.setId(usersGWT.getId());
            userDao.setName(usersGWT.getName());

            Heroes createdHero = choiceHero.createHeroForCurrentUser(userDao,hero_type);

            EntityForBattle entityForBattle = new EntityForBattle();

            entityForBattle.setUserName(usersGWT.getName());
            entityForBattle.setHeroHP(createdHero.getHp());
            entityForBattle.setHeroDamage(createdHero.getDamage());
            entityForBattle.setHeroLvl(createdHero.getLevel());
            entityForBattle.setHeroType(createdHero.getHero_type());

            return entityForBattle;

        }catch(SQLException ex){
            loger.error("Problem with Data Base: " + ex);
            throw new GameNCException("Problem with Data Base!");
        }
    }
}