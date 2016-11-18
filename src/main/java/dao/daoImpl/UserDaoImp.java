package dao.daoImpl;


import dao.Entity.Users;
import dao.UserDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.log4j.Logger;

public class UserDaoImp implements UserDAO {
    private static Logger loger = Logger.getLogger(UserDaoImp.class.getName());
    private Connection connection;


    public UserDaoImp(Connection connect){
        this.connection = connect;
    }


    @Override
    public void createUser(Users user) throws  SQLException
    {
            if (connection != null) {
                Statement statement = connection.createStatement();
                statement.executeUpdate("INSERT INTO users(name) values('" + user.getName()+ "')");
                statement.close();
                loger.info("User " + user.getName() + " created successfully");
            } else {

                throw new RuntimeException("First, create a connection!");
            }

    }

    @Override
    public void deleteUser(Users user) throws SQLException
    {
            if (connection != null) {

                if (userExist(user)){
                    Statement statement = connection.createStatement();
                    statement.executeUpdate("DELETE FROM users WHERE id = " + user.getId());
                    statement.close();
                    loger.info("User " + user.getName() + " deleted successfully");
                }

                else{
                    throw new RuntimeException("User does not exist");

                }
            } else {
                throw new RuntimeException("First, create a connection!");
            }

    }

    @Override
    public Users findUser(String name) throws SQLException
    {
            if (connection != null) {
                Users user = new Users();

                PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE name = ?");
                statement.setString(1,name);
                ResultSet result = statement.executeQuery();

                    result.next();
                    if (!  result.wasNull()) {

                        user.setId(result.getInt("id"));
                        user.setName(result.getString("name"));

                        result.close();
                        statement.close();
                        loger.info("User " +user.getName() + "successfully found");
                    } else {
                        result.close();
                        statement.close();
                        user = null;
                    }

                return user;
            } else {
                throw new RuntimeException("First, create a connection!");
            }
    }

    @Override
    public void updateUser(Users user) throws SQLException
    {
            if (connection != null) {

                if (!  userExist(user)) {
                    PreparedStatement upStatement = connection.prepareStatement("UPDATE users SET name = ? WHERE id = ?");
                    upStatement.setString(1, user.getName());
                    upStatement.setInt(2, user.getId());
                    upStatement.executeUpdate();

                    upStatement.close();
                    loger.info("User " +user.getName() + " updated successfully");
                }
                else{
                    throw new RuntimeException("User does not exist!");
                }
            } else {
                throw new RuntimeException("First, create a connection!");
            }
    }

    @Override
    public Collection<Users> selectAllUser() throws SQLException
    {
            if (connection != null) {

                ArrayList<Users> allUsers = new ArrayList<>();
                Statement statement = connection.createStatement();

                ResultSet result = statement.executeQuery("SELECT * FROM users ORDER BY id");


                    while (result.next()) {
                        if (!result.wasNull()) {
                            Users _user = new Users();
                            _user.setId(result.getInt("id"));
                            _user.setName(result.getString("name"));

                            allUsers.add(_user);
                            result.close();
                            statement.close();
                        }
                        else {
                            result.close();
                            statement.close();
                            throw new RuntimeException("Users does not exist!");
                        }
                    }
                    return allUsers;
            } else {
                throw new RuntimeException("First, create a connection!");
            }
    }

    @Override
    public boolean userExist(Users user) throws SQLException {

        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT id FROM users WHERE id = " + user.getId());
        result.next();
        if(! result.wasNull()){
            result.close();;
            statement.close();
            return true;
        }
        else{
            result.close();
            statement.close();
            return false;
        }
    }
}
