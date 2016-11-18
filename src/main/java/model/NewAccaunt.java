package model;

import dao.DBFactory;
import dao.Entity.Users;
import dao.SecurityDAO;
import dao.UserDAO;

import org.apache.log4j.Logger;

import java.sql.SQLException;

public class NewAccaunt {
    private static Logger loger = Logger.getLogger(NewAccaunt.class.getName());
    private DBFactory db_access = new DBFactory();
    private UserDAO userDAO;
    private SecurityDAO securityDAO;

    NewAccaunt(){
        try {
            db_access.getConnection();
            userDAO = db_access.createUserDAO();
            securityDAO = db_access.createSecurityDAO();

        }catch(ClassNotFoundException | SQLException ex){loger.error("Error during authentication:" + ex);}

    }

    public int add(String user_name, String password) throws SQLException{

        if(this.checkUniqName(user_name)) {
            Users new_user = new Users();
            new_user.setName(user_name);

            userDAO.createUser(new_user);

            new_user = userDAO.findUser(user_name);

            securityDAO.createSecurity(new_user,password);

            return 0;
        }
        else
            return -1;
    }

    private boolean checkUniqName(String name) throws SQLException{

        if(userDAO.findUser(name) == null)
            return true;
        else
            return false;

    }
}
