package kr.ac.jejunu.userdao;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.*;

import static java.sql.DriverManager.getConnection;

public class UserDao {
    private final JdbcContext jdbcContext;

    public UserDao(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }

    public User get(Long id) throws ClassNotFoundException, SQLException {
        String sql = "select * from userinfo where id = ?";
        Object[] params = new Object[] {id};
        return jdbcContext.get(sql, params);
    }

    public Long add(User user) throws ClassNotFoundException, SQLException {
        String sql = "insert into userinfo(name, password) values (?, ?)";
        Object[] params = new Object[] {user.getName(), user.getPassword()};
        return jdbcContext.add(sql, params);
    }

    public void update(User user) throws SQLException {
        String sql = "update userinfo set name = ?, password = ? where id = ?";
        Object[] params = new Object[]{user.getName(), user.getPassword(), user.getId()};
        jdbcContext.update(sql, params);
    }

    public void delete(Long id) throws SQLException {
        String sql = "delete from userinfo where id = ?";
        Object[] params = new Object[]{id};
        jdbcContext.update(sql, params);
    }
}
