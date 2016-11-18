package dao;


import dao.Entity.Statistics;
import dao.Entity.Users;

import java.sql.SQLException;
import java.util.Collection;

public interface StatisticDAO {

    public void createStatistic(Statistics statistics, Users user) throws SQLException;
    public void deleteStatistic(Statistics statistics) throws SQLException;
    public Statistics findStatistic(String user_name) throws SQLException;
    public void updateStatistic(Statistics statistics) throws SQLException;
    public Collection<Statistics> selectAllStatistics() throws SQLException;
    public boolean userExist(Users user) throws SQLException;

    public boolean statisticExist(Statistics statistics) throws SQLException;

}
