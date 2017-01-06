package dao.daoImpl;


import dao.Entity.Users;
import dao.UserDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDaoImp implements UserDAO {
    private static Logger loger = LoggerFactory.getLogger(UserDaoImp.class.getName());
    private Connection connection;


    public UserDaoImp(Connection connect){
        this.connection = connect;
    }


    @Override
    public void createUser(Users user) throws  SQLException
    {
        try (Statement statement = connection.createStatement()){
            if (connection != null) {
                statement.executeUpdate("INSERT INTO users(name) values('" + user.getName() + "')");
                loger.info("User " + user.getName() + " created successfully");
            } else {
                throw new RuntimeException("First, create a connection!");
            }
        }catch(SQLException e) {
            throw e;

        }

    }

    @Override
    public void deleteUser(Users user) throws SQLException
    {
        try (Statement statement = connection.createStatement() ) {

            if (connection != null) {

                if (userExist(user)) {
                    statement.executeUpdate("DELETE FROM users WHERE id = " + user.getId());

                    loger.info("User " + user.getName() + " deleted successfully");
                } else {
                    loger.info("User " + user.getName() + " does not exist");

                }
            } else {
                throw new RuntimeException("First, create a connection!");
            }
        }catch(SQLException e) {
            throw e;
        }

    }

    @Override
    public Users findUser(String name) throws SQLException
    {

        try(PreparedStatement statement = findUserPrepStatement(name);
            ResultSet result = statement.executeQuery()) {
            if (connection != null) {
                Users user = new Users();

                if (result.next()) {
                    user.setId(result.getInt("id"));
                    user.setName(result.getString("name"));
                    loger.info("User " + user.getName() + " successfully found");
                } else {
                    user = null;
                }
                return user;
            } else {
                throw new RuntimeException("First, create a connection!");
            }
        }catch (SQLException e){
            throw e;
        }
    }

    private PreparedStatement findUserPrepStatement(String name) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE name = ?");
        statement.setString(1, name);
        return  statement;
    }

    @Override
    public void updateUser(Users user) throws SQLException
    {
            if (connection != null) {
                try (PreparedStatement upStatement = updateUserPrepStatement(user)){
                    if (userExist(user)) {
                        upStatement.executeUpdate();
                        loger.info("User " + user.getName() + " updated successfully");
                    } else {
                        throw new SQLException("User does not exist!");
                    }
                }catch (SQLException e){
                    throw e;
                }
            } else {
                throw new RuntimeException("First, create a connection!");
            }

    }

    private PreparedStatement updateUserPrepStatement(Users user) throws SQLException{
        PreparedStatement upStatement = connection.prepareStatement("UPDATE users SET name = ? WHERE id = ?");
        upStatement.setString(1, user.getName());
        upStatement.setInt(2, user.getId());
        return upStatement;
    }

    @Override
    public Collection<Users> selectAllUser() throws SQLException
    {

        try(Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM users ORDER BY id")) {
            if (connection != null) {
                ArrayList<Users> allUsers = new ArrayList<>();

                while (result.next()) {
                    if (!result.wasNull()) {
                        Users _user = new Users();
                        _user.setId(result.getInt("id"));
                        _user.setName(result.getString("name"));

                        allUsers.add(_user);
                    } else {
                        throw new SQLException("Users does not exist!");
                    }
                }
                return allUsers;
            } else {
                throw new RuntimeException("First, create a connection!");
            }
        }catch (SQLException e){
            throw e;
        }
    }

    @Override
    public boolean userExist(Users user) throws SQLException {

        try(Statement statement = connection.createStatement();
            ResultSet result =  statement.executeQuery("SELECT id FROM users WHERE id = " + user.getId())) {

            return result.next();
        }catch (SQLException e){
            throw e;
        }
    }
}
