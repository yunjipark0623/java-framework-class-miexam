package kr.ac.jejunu.userdao;

import java.sql.*;

public abstract class UserDao {
    public User get(Long id) throws ClassNotFoundException, SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement =
                connection.prepareStatement("select * from userinfo where id = ?");
        preparedStatement.setLong(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setName(resultSet.getString("name"));
        user.setPassword(resultSet.getString("password"));

        resultSet.close();
        preparedStatement.close();
        connection.close();
        //리턴
        return user;
    }

    public Long add(User user) throws ClassNotFoundException, SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement =
                connection.prepareStatement("insert into userinfo(name, password) values (?, ?)");
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.executeUpdate();

        preparedStatement = connection.prepareStatement("select last_insert_id()");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        Long id = resultSet.getLong(1);

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return id;
    }

    protected abstract Connection getConnection() throws ClassNotFoundException, SQLException;
}
