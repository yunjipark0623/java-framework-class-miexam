package kr.ac.jejunu.userdao;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.*;

import static java.sql.DriverManager.getConnection;

public class UserDao {
    private final DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public User get(Long id) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = dataSource.getConnection();
            StatementStrategy statementStrategy = new GetStatementStrategy(id);
            preparedStatement = statementStrategy.makePreparedStatement(connection);
//            preparedStatement = connection.prepareStatement("select * from userinfo where id = ?");
//            preparedStatement.setLong(1, id);

            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
            }
        } finally {
            if(resultSet != null)
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if(preparedStatement != null)
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if(connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {

                }
        }
        return user;
    }

    public Long add(User user) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Long id;
        try {
            connection = dataSource.getConnection();
            StatementStrategy statementStrategy = new AddStatementStrategy(user);
            preparedStatement = statementStrategy.makePreparedStatement(connection);
//            preparedStatement = connection.prepareStatement("insert into userinfo(name, password) values (?, ?)");
//            preparedStatement.setString(1, user.getName());
//            preparedStatement.setString(2, user.getPassword());

            preparedStatement.executeUpdate();

            id = GetLastInsertId(connection);
        } finally {
            if(preparedStatement != null)
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if(connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return id;
    }

    public void update(User user) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            StatementStrategy statementStrategy = new UpdateStatementStrategy(user);
            preparedStatement = statementStrategy.makePreparedStatement(connection);
//            preparedStatement = connection.prepareStatement("update userinfo set name = ?, password = ? where id = ?");
//            preparedStatement.setString(1, user.getName());
//            preparedStatement.setString(2, user.getPassword());
//            preparedStatement.setLong(3, user.getId());

            preparedStatement.executeUpdate();
        } finally {
            if(preparedStatement != null)
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if(connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }

    public void delete(Long id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            StatementStrategy statementStrategy = new DeleteStatementStrategy(id);
            preparedStatement = statementStrategy.makePreparedStatement(connection);
//            preparedStatement = connection.prepareStatement("delete from userinfo where id = ?");
//            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
        } finally {
            if(preparedStatement != null)
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if(connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }

    public Long GetLastInsertId(Connection connection) throws SQLException {
        ResultSet resultSet = null;
        Long id;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select last_insert_id()");
            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            id = resultSet.getLong(1);
        } finally {
            if(resultSet != null)
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return id;
    }
}
