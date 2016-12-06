package model;

import dao.DBFactory;
import dao.Entity.Users;
import dao.SecurityDAO;
import dao.UserDAO;
import org.apache.log4j.Logger;

import java.sql.SQLException;

public class LogIn {
    private static Logger loger = Logger.getLogger(LogIn.class.getName());

    private DBFactory db_access = new DBFactory();
    private UserDAO userDAO;
    private SecurityDAO securityDAO;

    public LogIn(){
        try {
            db_access.getConnection();
            userDAO = db_access.createUserDAO();
            securityDAO = db_access.createSecurityDAO();

        }catch(ClassNotFoundException | SQLException ex){loger.error("Error during authorization:" + ex);}

    }

    public Users makeLogIn(String user_name, String password) throws SQLException{
        Users exist_user = userDAO.findUser(user_name);
        if(exist_user != null) {
           if(securityDAO.checkSecurity(exist_user, password)) {
               return exist_user;
           }
        }

            return null;

    }


}
