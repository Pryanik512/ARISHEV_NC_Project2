package dao.daoImpl;


import dao.Entity.Users;
import dao.SecurityDAO;
import java.sql.*;
import java.util.Base64;
import java.nio.charset.StandardCharsets;


public class SecurityDaoImp implements SecurityDAO {

    private Connection connection;


    public SecurityDaoImp(Connection connect) {
        this.connection = connect;
    }


    @Override
    public void createSecurity(Users user, String password) throws SQLException {

        if (connection != null) {

            if (!userExist(user)) {
                Statement statement = connection.createStatement();
                String encoded = Base64                             // Encoding password through base-64
                        .getEncoder()
                        .encodeToString( password.getBytes( StandardCharsets.UTF_8 ) );
                statement.executeUpdate("INSERT INTO security(user_id,password) values(" + user.getId() + ", '" + encoded + "')");

                statement.close();
            }
            else
                throw new RuntimeException("User does not exist");



        } else {
            throw new RuntimeException("First, create a connection!");
        }
    }

    @Override
    public void deleteSecurity(Users user) throws SQLException {

        if (connection != null) {


            if (! userExist(user)) {
                Statement statement = connection.createStatement();
                statement.executeUpdate("DELETE FROM security WHERE user_id = " + user.getId());
                System.out.println("DELETE OK!!");

                statement.close();
            }
            else
                throw new RuntimeException("User does not exist");

        } else {
            throw new RuntimeException("First, create a connection!");
        }
    }

    @Override
    public boolean checkSecurity(Users user, String password) throws SQLException {

        if (connection != null) {


            if (! userExist(user)) {
                Statement statement = connection.createStatement();
                PreparedStatement p_Statement = connection.prepareStatement("SELECT password FROM security WHERE user_id = ?");
                p_Statement.setInt(1,user.getId());

                ResultSet resultSecurity = p_Statement.executeQuery();
                resultSecurity.next();
                String passInDB = resultSecurity.getString("password");

                String decodedPass = new String(                    // Decoding password from base-64
                        Base64.getDecoder().decode( passInDB ),
                        StandardCharsets.UTF_8 );


                if(password.compareTo(decodedPass) == 0) {
                    resultSecurity.close();
                    statement.close();
                    return true;
                }
                else {

                    resultSecurity.close();
                    statement.close();
                    return false;
                }

            }
            else
                throw new RuntimeException("User does not exist");

        } else {
            throw new RuntimeException("First, create a connection!");
        }

    }

    @Override
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

}
