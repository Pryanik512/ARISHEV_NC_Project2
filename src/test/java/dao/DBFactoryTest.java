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

    @Test(expected = SQLException.class)
    public void test_fail_create_connection_to_DB() throws SQLException, ClassNotFoundException{


            String url = "";
            String name = "";
            String pass = "";

            dbFactory.getConnection();
            throw new AssertionError();

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

    @Test(expected = RuntimeException.class)
    public void test_fail_close_connection_to_DB() throws SQLException, ClassNotFoundException{
        dbFactory.closeConnection();
        throw new AssertionError();
    }
}
