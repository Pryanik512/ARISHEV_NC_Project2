package dao;


import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class DBFactoryTest {
    private static DBFactory dbFactory;

    @BeforeClass
    public static void create_object_DBFactory(){
         dbFactory = new DBFactory();
    }


    @Test
    public void test_get_connection_to_DB(){
        Connection connect = null;
        try {
           connect = dbFactory.getConnection();
           if (connect == null){
               throw new AssertionError();
           }

        }catch(SQLException | ClassNotFoundException ex){
            throw new AssertionError(ex.getMessage());
        }
    }

    @Test
    public void test_close_connection_to_DB() throws SQLException, ClassNotFoundException{

        try{
            dbFactory.getConnection();
            dbFactory.closeConnection();
        }catch(RuntimeException ex){
            throw new AssertionError(ex.getMessage());
        }
    }


}
