package dao.daoImpl;


import dao.Entity.Users;
import dao.StatisticDAO;
import dao.Entity.Statistics;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class StatisticDaoImp implements StatisticDAO{
    private Connection connection;


    private static Logger loger = Logger.getLogger(HeroDaoImp.class.getName());


    public StatisticDaoImp(Connection connect) {
        this.connection = connect;
    }

    // DBFactory.connection будет использоваться здесь для создания запросов
    @Override
    public void createStatistic(Statistics statistics, Users user) throws SQLException {
        if (connection != null) {

            if (!userExist(user)) {
                Statement statement = connection.createStatement();

                statement.executeUpdate("INSERT INTO statistics(user_id," +
                                                               "user_level," +
                                                               "win," +
                                                               "defeat) values(" +
                                                               user.getId() + "," +
                                                               statistics.getUser_level() + "," +
                                                               statistics.getWin() + "," +
                                                               statistics.getDefeat() + ")");

                statement.close();
                loger.info("Statistic for " + user.getName() + " created successfully");
            }
            else
                throw new RuntimeException("User does not exist");



        } else {
            throw new RuntimeException("First, create a connection!");
        }

    }

    @Override
    public void deleteStatistic(Statistics statistic) throws SQLException{
        if (connection != null) {

            if (statisticExist(statistic)){
                Statement statement = connection.createStatement();
                statement.executeUpdate("DELETE FROM statistics WHERE id = " + statistic.getId());
                statement.close();
                loger.info("Statistic for" + statistic.getUser_id() + " deleted successfully");
            }

            else{
                throw new RuntimeException("Statistic does not exist");

            }
        } else {
            throw new RuntimeException("First, create a connection!");
        }


    }

    @Override
    public Statistics findStatistic(String user_name) throws SQLException{
        if (connection != null) {
            Statistics statistics = new Statistics();

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM statistics WHERE user_id = (SELECT id FROM users WHERE name = ?)");
            statement.setString(1,user_name);
            ResultSet result = statement.executeQuery();

            result.next();
            if (!  result.wasNull()) {

                statistics.setId(result.getInt("id"));
                statistics.setUser_id(result.getInt("user_id"));
                statistics.setWin(result.getInt("win"));
                statistics.setDefeat(result.getInt("defeat"));
                statistics.setUser_level(result.getInt("user_level"));

                loger.info("Hero for " +user_name + "successfully found");
            } else {
                result.close();
                statement.close();
                throw new RuntimeException("Statistic for that user does not exist!");
            }

            return statistics;
        } else {
            throw new RuntimeException("First, create a connection!");
        }
    }

    @Override
    public void updateStatistic(Statistics statistics) throws SQLException{
        if (connection != null) {

            if (!  statisticExist(statistics)) {
                PreparedStatement upStatement = connection.prepareStatement("UPDATE statistics SET user_id = ?, win = ?, defeat = ?, user_level = ?, WHERE id = ?");
                upStatement.setInt(1, statistics.getUser_id());
                upStatement.setInt(2, statistics.getWin());
                upStatement.setInt(3, statistics.getDefeat());
                upStatement.setInt(4, statistics.getUser_level());

                upStatement.executeUpdate();

                upStatement.close();
                loger.info("Statistic " + statistics.getUser_id() + " updated successfully");
            }
            else{
                throw new RuntimeException("Statistic does not exist!");
            }
        } else {
            throw new RuntimeException("First, create a connection!");
        }
    }

    @Override
    public Collection<Statistics> selectAllStatistics() throws SQLException {
        if (connection != null) {

            ArrayList<Statistics> allStatistics = new ArrayList<>();
            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery("SELECT * FROM statistics ORDER BY id");


            while (result.next()) {
                if (!result.wasNull()) {
                    Statistics _stat = new Statistics();
                    _stat.setId(result.getInt("id"));
                    _stat.setUser_id(result.getInt("user_id"));
                    _stat.setWin(result.getInt("win"));
                    _stat.setDefeat(result.getInt("defeat"));
                    _stat.setUser_level(result.getInt("user_level"));

                    allStatistics.add(_stat);
                    result.close();
                    statement.close();
                    loger.info("Statistic gotten successfully");
                }
                else {
                    result.close();
                    statement.close();
                    throw new RuntimeException("Statistics does not exist!");
                }
            }
            return allStatistics;
        } else {
            throw new RuntimeException("First, create a connection!");
        }
    }

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
    @Override
    public boolean statisticExist(Statistics statistic) throws SQLException {

        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT id FROM statistics WHERE id = " + statistic.getId());
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
