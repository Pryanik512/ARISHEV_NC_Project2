import dao.DBFactory;
import dao.Entity.Security;
import dao.SecurityDAO;
import dao.UserDAO;
import dao.Entity.Users;
import dao.daoImpl.SecurityDaoImp;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
public class main {

    private static Logger loger = Logger.getLogger(main.class.getName());
    public static void main(String[] args) {
        DBFactory psqlDB = new DBFactory();

        try {
            psqlDB.getConnection();
            UserDAO users = psqlDB.createUserDAO();
            if(users.findUser("King") == null){
                System.out.println("All ok!1");
            }
            else System.out.println("All baaaaaaaaaaaaaaadddd!!!");
          //  Users user1 = new Users();
            //Security userSecur = new Security();


            psqlDB.closeConnection();

        }catch (SQLException | ClassNotFoundException ex){
           loger.error("Some exception", ex);
        }

    }
}
