package gwt.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import dao.Entity.Users;
import gwt.client.GameNCException;
import gwt.client.GameService;
import model.LogIn;
import model.NewAccount;
import org.apache.log4j.Logger;

import java.sql.SQLException;


public class GameServiceImpl extends RemoteServiceServlet implements GameService {
    private static Logger loger = Logger.getLogger(GameServiceImpl.class.getName());
    @Override
    public void addAccount(String name, String pass) throws GameNCException{
        try {
            NewAccount newAccount = new NewAccount();
            if(newAccount.add(name, pass) != 0){
                throw new GameNCException("User " + name + " already exist!");
            }

        }catch (SQLException ex){
            loger.error("Problem with Data Base: " + ex);
            throw new GameNCException("Problem with Data Base!");
        }

    }

    @Override
    public Users logIn(String name, String pass) throws GameNCException{
        try {
            LogIn logInUser = new LogIn();
            Users user = logInUser.makeLogIn(name, pass);
            if(user == null){
                throw new GameNCException("Wrong name or password!");
            }
            return user;

        }catch (SQLException ex){
            loger.error("Problem with Data Base: " + ex);
            throw new GameNCException("Problem with Data Base!");
        }
    }
}