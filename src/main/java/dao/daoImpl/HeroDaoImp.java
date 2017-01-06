package dao.daoImpl;

import dao.Entity.Users;
import dao.HeroDAO;
import dao.Entity.Heroes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;


public class HeroDaoImp implements HeroDAO{
    private Connection connection;

    private static Logger loger = LoggerFactory.getLogger(HeroDaoImp.class.getName());
    public HeroDaoImp(Connection connect) {
        this.connection = connect;
    }

    // DBFactory.connection будет использоваться здесь для создания запросов
    @Override
    public void createHero(Heroes hero, Users user) throws SQLException {

        if (connection != null) {

            try (Statement statement = connection.createStatement()){
                if (userExist(user)) {

                    statement.executeUpdate("INSERT INTO heroes(" +
                            "user_id," +
                            "level," +
                            "dmg," +
                            "hp," +
                            "hero_type) values(" +
                            user.getId() + "," +
                            hero.getLevel() + "," +
                            hero.getDamage() + "," +
                            hero.getHp() + "," +
                            hero.getHero_type() + ")");


                    loger.info("Hero for " + user.getName() + " created successfully");
                } else
                    throw new SQLException("User does not exist");
            }catch(SQLException e){
                throw e;
            }

        } else {
            throw new RuntimeException("First, create a connection!");
        }
    }

    @Override
    public void deleteHero(Heroes hero)  throws SQLException {

        if (connection != null) {
            try(Statement statement = connection.createStatement()){
                if (heroExist(hero)) {
                    statement.executeUpdate("DELETE FROM heroes WHERE id = " + hero.getId());
                    loger.info("Hero  " + hero.getHero_type() + " deleted successfully");
                } else {
                    throw new SQLException("Hero does not exist");
                }
            }catch(SQLException e){
                throw e;
            }
        } else {
            throw new RuntimeException("First, create a connection!");
        }

    }

    @Override
    public Heroes findHero(String user_name, int hero_type) throws SQLException {
        if (connection != null) {
            Heroes hero = new Heroes();
            try(    PreparedStatement statement = findHeroPrepStatement(user_name,hero_type);
                    ResultSet result = statement.executeQuery()
                    ) {

                if (result.next()) {
                    hero.setId(result.getInt("id"));
                    hero.setUser_id(result.getInt("user_id"));
                    hero.setDamage(result.getInt("dmg"));
                    hero.setHp(result.getInt("hp"));
                    hero.setHero_type(result.getInt("hero_type"));
                    hero.setLevel(result.getInt("level"));

                    result.close();
                    statement.close();
                    loger.info("Hero for " + user_name + " successfully found");
                } else {
                    hero = null;
                }
            }catch(SQLException e){
                throw e;
            }
            return hero;
        } else {
            throw new RuntimeException("First, create a connection!");
        }
    }

    private PreparedStatement findHeroPrepStatement(String user_name, int hero_type) throws SQLException{
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM heroes WHERE user_id = (SELECT id FROM users WHERE name = ? AND hero_type = ?)");
        statement.setString(1, user_name);
        statement.setInt(2, hero_type);
        return statement;
    }

    @Override
    public void updateHero(Heroes hero) throws SQLException {
        if (connection != null) {
            try(PreparedStatement upStatement = updateHeroPrepStatement(hero)) {
                if (heroExist(hero)) {
                    upStatement.executeUpdate();
                    loger.info("Hero " + hero.getId() + " updated successfully");
                } else {
                    throw new SQLException("Hero does not exist!");
                }
            }catch (SQLException e){
                throw e;
            }
        } else {
            throw new RuntimeException("First, create a connection!");
        }
    }

    private PreparedStatement updateHeroPrepStatement(Heroes hero) throws SQLException{
        PreparedStatement upStatement = connection.prepareStatement("UPDATE heroes SET user_id = ?, dmg = ?, hp = ?, hero_type = ?, level = ? WHERE id = ?");
        upStatement.setInt(1, hero.getUser_id());
        upStatement.setInt(2, hero.getDamage());
        upStatement.setInt(3, hero.getHp());
        upStatement.setInt(4, hero.getHero_type());
        upStatement.setInt(5, hero.getLevel());
        upStatement.setInt(6, hero.getId());

        return upStatement;
    }
    @Override
    public Collection<Heroes> selectAllHeroes() throws SQLException {
        if (connection != null) {
            ArrayList<Heroes> allHeroes = new ArrayList<>();
            try(Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery("SELECT * FROM heroes ORDER BY id")) {

                while (result.next()) {
                    if (!result.wasNull()) {
                        Heroes _hero = new Heroes();
                        _hero.setId(result.getInt("id"));
                        _hero.setUser_id(result.getInt("user_id"));
                        _hero.setDamage(result.getInt("dmg"));
                        _hero.setHp(result.getInt("hp"));
                        _hero.setHero_type(result.getInt("hero_type"));
                        _hero.setLevel(result.getInt("level"));

                        allHeroes.add(_hero);
                    } else {
                        throw new SQLException("Heroes does not exist!");
                    }
                }
            }catch(SQLException e){
                throw e;
            }
            return allHeroes;
        } else {
            throw new RuntimeException("First, create a connection!");
        }
    }

    public boolean userExist(Users user) throws SQLException {

        try(Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT id FROM users WHERE id = " + user.getId())) {

                return result.next();

        }catch(SQLException e){
            throw e;
        }
    }

    @Override
    public boolean heroExist(Heroes hero) throws SQLException {

        try(Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT id FROM heroes WHERE id = " + hero.getId())) {

            return result.next();

        }catch (SQLException e){
            throw e;
        }
    }
}
