package dao.daoImpl;


import dao.Entity.Users;
import dao.StatisticDAO;
import dao.Entity.Statistics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class StatisticDaoImp implements StatisticDAO{
    private Connection connection;


    private static Logger loger = LoggerFactory.getLogger(StatisticDaoImp.class.getName());


    public StatisticDaoImp(Connection connect) {
        this.connection = connect;
    }

    // DBFactory.connection будет использоваться здесь для создания запросов
    @Override
    public void createStatistic(Statistics statistics, Users user) throws SQLException {

        if (connection != null) {
            try(Statement statement = connection.createStatement()){
                if (userExist(user)) {
                    statement.executeUpdate("INSERT INTO statistics(user_id," +
                            "user_level," +
                            "win," +
                            "defeat) values(" +
                            user.getId() + "," +
                            statistics.getUser_level() + "," +
                            statistics.getWin() + "," +
                            statistics.getDefeat() + ")");
                    loger.info("Statistic for " + user.getName() + " created successfully");
                } else
                    throw new SQLException("User does not exist");
            }catch (SQLException e){
                throw e;
            }
        } else {
            throw new RuntimeException("First, create a connection!");
        }

    }

    @Override
    public void deleteStatistic(Statistics statistic) throws SQLException{
        if (connection != null) {
            try (Statement statement = connection.createStatement();){
                if (statisticExist(statistic)) {
                    statement.executeUpdate("DELETE FROM statistics WHERE id = " + statistic.getId());
                    loger.info("Statistic for" + statistic.getUser_id() + " deleted successfully");
                } else {
                    throw new SQLException("Statistic does not exist");
                }
            }catch(SQLException e){
                throw e;
            }
        } else {
            throw new RuntimeException("First, create a connection!");
        }


    }

    @Override
    public Statistics findStatistic(String user_name) throws SQLException{
        if (connection != null) {
            Statistics statistics = new Statistics();

            try (PreparedStatement statement = findStatisticsPrepStatement(user_name);
                 ResultSet result = statement.executeQuery()){
                if (result.next()) {
                    statistics.setId(result.getInt("id"));
                    statistics.setUser_id(result.getInt("user_id"));
                    statistics.setWin(result.getInt("win"));
                    statistics.setDefeat(result.getInt("defeat"));
                    statistics.setUser_level(result.getInt("user_level"));

                    loger.info("Hero for " + user_name + "successfully found");
                } else {
                    throw new SQLException("Statistic for that user does not exist!");
                }
            }catch (SQLException e){
                throw e;
            }

            return statistics;
        } else {
            throw new RuntimeException("First, create a connection!");
        }
    }

    private PreparedStatement findStatisticsPrepStatement(String user_name) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM statistics WHERE user_id = (SELECT id FROM users WHERE name = ?)");
        statement.setString(1,user_name);
        return statement;
    }

    @Override
    public void updateStatistic(Statistics statistics) throws SQLException{
        if (connection != null) {
            try (PreparedStatement upStatement = updateStatisticPrepStatement(statistics)){
                if (statisticExist(statistics)) {
                    upStatement.executeUpdate();
                    loger.info("Statistic " + statistics.getUser_id() + " updated successfully");
                } else {
                    throw new SQLException("Statistic does not exist!");
                }
            }catch(SQLException e){
                throw e;
            }
        } else {
            throw new RuntimeException("First, create a connection!");
        }
    }

    private PreparedStatement updateStatisticPrepStatement(Statistics statistics) throws SQLException{
        PreparedStatement upStatement = connection.prepareStatement("UPDATE statistics SET user_id = ?, win = ?, defeat = ?, user_level = ?, WHERE id = ?");
        upStatement.setInt(1, statistics.getUser_id());
        upStatement.setInt(2, statistics.getWin());
        upStatement.setInt(3, statistics.getDefeat());
        upStatement.setInt(4, statistics.getUser_level());
        return upStatement;
    }

    @Override
    public Collection<Statistics> selectAllStatistics() throws SQLException {
        if (connection != null) {

            ArrayList<Statistics> allStatistics = new ArrayList<>();

            try( Statement statement = connection.createStatement();
                 ResultSet result = statement.executeQuery("SELECT * FROM statistics ORDER BY id");){
                while (result.next()) {
                    if (!result.wasNull()) {
                        Statistics _stat = new Statistics();
                        _stat.setId(result.getInt("id"));
                        _stat.setUser_id(result.getInt("user_id"));
                        _stat.setWin(result.getInt("win"));
                        _stat.setDefeat(result.getInt("defeat"));
                        _stat.setUser_level(result.getInt("user_level"));

                        allStatistics.add(_stat);

                        loger.info("Statistic gotten successfully");
                    } else {
                        throw new RuntimeException("Statistics does not exist!");
                    }
                }
            }catch(SQLException e){
                throw e;
            }
            return allStatistics;
        } else {
            throw new RuntimeException("First, create a connection!");
        }
    }

    public boolean userExist(Users user) throws SQLException {

        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery("SELECT id FROM users WHERE id = " + user.getId())){

            return result.next();

        }catch (SQLException e){
               throw e;
        }
    }
    @Override
    public boolean statisticExist(Statistics statistic) throws SQLException {

        try( Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery("SELECT id FROM statistics WHERE id = " + statistic.getId());) {

            return result.next();

        }catch(SQLException e){
            throw e;
        }
    }

}
