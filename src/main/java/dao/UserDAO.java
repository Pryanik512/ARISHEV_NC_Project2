package dao;

import dao.Entity.Users;

import java.sql.SQLException;
import java.util.Collection;

public interface UserDAO {

    public void createUser(Users user) throws SQLException;
    public void deleteUser(Users user) throws SQLException;
    public Users findUser(String name) throws SQLException;
    public void updateUser(Users user) throws SQLException;
    public boolean userExist(Users user) throws SQLException;

    public Collection<Users> selectAllUser() throws SQLException;



}
