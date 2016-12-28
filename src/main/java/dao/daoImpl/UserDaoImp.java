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
    {   Statement statement = null;
        try {
            if (connection != null) {
                statement = connection.createStatement();
                statement.executeUpdate("INSERT INTO users(name) values('" + user.getName() + "')");

                loger.info("User " + user.getName() + " created successfully");
            } else {

                throw new RuntimeException("First, create a connection!");
            }
        }finally {
            statement.close();
        }

    }

    @Override
    public void deleteUser(Users user) throws SQLException
    {
        Statement statement = null;
        try {

            if (connection != null) {

                if (userExist(user)) {
                    statement = connection.createStatement();
                    statement.executeUpdate("DELETE FROM users WHERE id = " + user.getId());

                    loger.info("User " + user.getName() + " deleted successfully");
                } else {
                    throw new RuntimeException("User does not exist");

                }
            } else {
                throw new RuntimeException("First, create a connection!");
            }
        }finally {
            statement.close();
        }

    }

    @Override
    public Users findUser(String name) throws SQLException
    {
        PreparedStatement statement = null;
        ResultSet result = null;

        try {

            if (connection != null) {
                Users user = new Users();

                statement = connection.prepareStatement("SELECT * FROM users WHERE name = ?");
                statement.setString(1, name);
                result = statement.executeQuery();

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
        }finally {
            result.close();
            statement.close();
        }
    }

    @Override
    public void updateUser(Users user) throws SQLException
    {
        PreparedStatement upStatement = null;
        try {
            if (connection != null) {

                if (userExist(user)) {
                    upStatement = connection.prepareStatement("UPDATE users SET name = ? WHERE id = ?");
                    upStatement.setString(1, user.getName());
                    upStatement.setInt(2, user.getId());
                    upStatement.executeUpdate();


                    loger.info("User " + user.getName() + " updated successfully");
                } else {
                    throw new RuntimeException("User does not exist!");
                }
            } else {
                throw new RuntimeException("First, create a connection!");
            }
        }finally {
            upStatement.close();
        }
    }

    @Override
    public Collection<Users> selectAllUser() throws SQLException
    {
        Statement statement = null;
        ResultSet result = null;
        try {
            if (connection != null) {
                ArrayList<Users> allUsers = new ArrayList<>();
                statement = connection.createStatement();
                result = statement.executeQuery("SELECT * FROM users ORDER BY id");

                while (result.next()) {
                    if (!result.wasNull()) {
                        Users _user = new Users();
                        _user.setId(result.getInt("id"));
                        _user.setName(result.getString("name"));

                        allUsers.add(_user);
                    } else {
                        throw new RuntimeException("Users does not exist!");
                    }
                }
                return allUsers;
            } else {
                throw new RuntimeException("First, create a connection!");
            }
        }finally {
            result.close();
            statement.close();
        }
    }

    @Override
    public boolean userExist(Users user) throws SQLException {
        Statement statement = null;
        ResultSet result = null;
        try {
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT id FROM users WHERE id = " + user.getId());

            return result.next();
        }finally {
            statement.close();
            result.close();
        }
    }
}
