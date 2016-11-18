package dao;
import java.sql.*;



import dao.daoImpl.*;

import org.apache.log4j.Logger;

public class DBFactory {


    private static Connection connection = null;
    private static Logger loger = Logger.getLogger(DBFactory.class.getName());



    public Connection getConnection() throws SQLException, ClassNotFoundException
    {


            Class.forName("org.postgresql.Driver");
            loger.info("Driver loaded successfully");

            connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/JavaGameDB", "postgres", "root");
            loger.info("The connection has been successfully installed!");
           return connection;
    }

    public void closeConnection() throws SQLException
    {
           if (connection != null) {
               connection.close();
               connection = null;
               loger.info("Connection has been closed");
           }
           else throw new RuntimeException("It can not be closed. No connection");

    }

    public UserDAO createUserDAO(){
        return new UserDaoImp(connection);
    }

    public HeroDAO createHeroDAO(){
        return new HeroDaoImp(connection);
    }

    public StatisticDAO createStatisticDAO(){
        return new StatisticDaoImp(connection);
    }

    public SecurityDAO createSecurityDAO(){
        return new SecurityDaoImp(connection);
    }
}
