package dao;
import java.io.IOException;
import java.sql.*;

import dao.daoImpl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

public class DBFactory {


    private static Connection connection = null;
    private static Logger loger = LoggerFactory.getLogger(DBFactory.class.getName());



    public Connection getConnection() throws SQLException, ClassNotFoundException
    {
        try {
            JDBCConfiguration config = JDBCConfiguration.getInstance();
            config.applyConfiguration();

            Class.forName(config.getDriverName());
            loger.info("Driver loaded successfully");

            connection = DriverManager.getConnection(config.getURL(), config.getLogin(), config.getPassword());
            loger.info("The connection has been successfully installed!");

        }catch(ParserConfigurationException | IOException | SAXException e){
            loger.error("JDBC configuration file error: " + e.getMessage());
            throw new RuntimeException("JDBC configuration file error: " + e.getMessage());
        }
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
