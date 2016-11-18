package dao.daoImpl;

import dao.Entity.Users;
import dao.HeroDAO;
import dao.Entity.Heroes;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;


public class HeroDaoImp implements HeroDAO{
    private Connection connection;

    private static Logger loger = Logger.getLogger(HeroDaoImp.class.getName());
    public HeroDaoImp(Connection connect) {
        this.connection = connect;
    }

    // DBFactory.connection будет использоваться здесь для создания запросов
    @Override
    public void createHero(Heroes hero, Users user) throws SQLException {
        if (connection != null) {

            if (!userExist(user)) {
                Statement statement = connection.createStatement();

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

                statement.close();
                loger.info("Hero for " + user.getName() + " created successfully");
            }
            else
                throw new RuntimeException("User does not exist");



        } else {
            throw new RuntimeException("First, create a connection!");
        }
    }

    @Override
    public void deleteHero(Heroes hero)  throws SQLException {

        if (connection != null) {

            if (heroExist(hero)){
                Statement statement = connection.createStatement();
                statement.executeUpdate("DELETE FROM heroes WHERE id = " + hero.getId());
                statement.close();
                loger.info("Hero  " + hero.getHero_type() + " deleted successfully");
            }

            else{
                throw new RuntimeException("Hero does not exist");

            }
        } else {
            throw new RuntimeException("First, create a connection!");
        }

    }

    @Override
    public Heroes findHero(String user_name) throws SQLException {
        if (connection != null) {
            Heroes hero = new Heroes();

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM heroes WHERE user_id = (SELECT id FROM users WHERE name = ?)");
            statement.setString(1,user_name);
            ResultSet result = statement.executeQuery();

            result.next();
            if (!  result.wasNull()) {

                hero.setId(result.getInt("id"));
                hero.setUser_id(result.getInt("user_id"));
                hero.setDamage(result.getInt("dmg"));
                hero.setHp(result.getInt("hp"));
                hero.setHero_type(result.getInt("hero_type"));
                hero.setLevel(result.getInt("level"));

                result.close();
                statement.close();
                loger.info("Hero for " +user_name + "successfully found");
            } else {
                result.close();
                statement.close();
                throw new RuntimeException("Heroes for that user does not exist!");
            }

            return hero;
        } else {
            throw new RuntimeException("First, create a connection!");
        }
    }

    @Override
    public void updateHero(Heroes hero) throws SQLException {
        if (connection != null) {

            if (!  heroExist(hero)) {
                PreparedStatement upStatement = connection.prepareStatement("UPDATE heroes SET user_id = ?, dmg = ?, hp = ?, hero_type = ?, level = ? WHERE id = ?");
                upStatement.setInt(1, hero.getUser_id());
                upStatement.setInt(2, hero.getDamage());
                upStatement.setInt(3, hero.getHp());
                upStatement.setInt(4, hero.getHero_type());
                upStatement.setInt(5, hero.getLevel());
                upStatement.setInt(5, hero.getId());


                upStatement.executeUpdate();

                upStatement.close();
                loger.info("Hero " +hero.getId() + " updated successfully");
            }
            else{
                throw new RuntimeException("Hero does not exist!");
            }
        } else {
            throw new RuntimeException("First, create a connection!");
        }

    }

    @Override
    public Collection<Heroes> selectAllHeroes() throws SQLException {
        if (connection != null) {

            ArrayList<Heroes> allHeroes = new ArrayList<>();
            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery("SELECT * FROM heroes ORDER BY id");


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
                    result.close();
                    statement.close();
                }
                else {
                    result.close();
                    statement.close();
                    throw new RuntimeException("Heroes does not exist!");
                }
            }
            return allHeroes;
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
    public boolean heroExist(Heroes hero) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT id FROM heroes WHERE id = " + hero.getId());
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
