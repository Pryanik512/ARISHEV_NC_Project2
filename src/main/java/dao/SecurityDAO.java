package dao;


import dao.Entity.Users;
import java.sql.SQLException;

public interface SecurityDAO {

    public void createSecurity(Users user, String password) throws SQLException;
    public void deleteSecurity(Users user) throws SQLException;
    public boolean checkSecurity(Users user, String password) throws SQLException;
    public boolean userExist(Users user) throws SQLException;

}
