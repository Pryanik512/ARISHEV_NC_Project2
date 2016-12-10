package dao;


import dao.Entity.Heroes;
import dao.Entity.Users;

import java.sql.SQLException;
import java.util.Collection;

public interface HeroDAO {

    public void createHero(Heroes hero, Users user)throws SQLException;
    public void deleteHero(Heroes hero)throws SQLException;
    public Heroes findHero(String user_name, int hero_type)throws SQLException;
    public void updateHero(Heroes hero)throws SQLException;
    public Collection<Heroes> selectAllHeroes()throws SQLException;

    public boolean userExist(Users user) throws SQLException;
    public boolean heroExist(Heroes hero) throws SQLException;
}
