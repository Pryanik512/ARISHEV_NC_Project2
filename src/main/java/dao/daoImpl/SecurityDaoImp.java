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
            try( Statement statement = connection.createStatement()) {
                if (userExist(user)) {

                    String encoded = Base64                             // Encoding password through base-64
                            .getEncoder()
                            .encodeToString(password.getBytes(StandardCharsets.UTF_8));
                    statement.executeUpdate("INSERT INTO security(user_id,password) values(" + user.getId() + ", '" + encoded + "')");

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
    public void deleteSecurity(Users user) throws SQLException {

        if (connection != null) {
            try (Statement statement = connection.createStatement();){
                if (userExist(user)) {
                    statement.executeUpdate("DELETE FROM security WHERE user_id = " + user.getId());

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
    public boolean checkSecurity(Users user, String password) throws SQLException {

        if (connection != null) {

            try(PreparedStatement p_Statement = checkSecurityPrepStatement(user);
                ResultSet resultSecurity = p_Statement.executeQuery()) {
                if (userExist(user)) {

                    resultSecurity.next();
                    String passInDB = resultSecurity.getString("password");

                    String decodedPass = new String(                    // Decoding password from base-64
                            Base64.getDecoder().decode(passInDB),
                            StandardCharsets.UTF_8);

                    return password.compareTo(decodedPass) == 0;

                } else
                    throw new SQLException("User does not exist");
            }catch(SQLException e){
                throw e;
            }
        } else {
            throw new RuntimeException("First, create a connection!");
        }

    }

    private PreparedStatement checkSecurityPrepStatement(Users user) throws SQLException{
        PreparedStatement p_Statement = connection.prepareStatement("SELECT password FROM security WHERE user_id = ?");
        p_Statement.setInt(1,user.getId());
        return p_Statement;
    }

    @Override
    public boolean userExist(Users user) throws SQLException {

        try ( Statement statement = connection.createStatement();
              ResultSet result = statement.executeQuery("SELECT id FROM users WHERE id = " + user.getId())){

            return result.next();

        }catch (SQLException e){
            throw e;
        }
    }

}
