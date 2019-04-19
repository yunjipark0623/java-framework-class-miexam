package kr.ac.jejunu.userdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteStatementStrategy implements StatementStrategy {
    private Long id;
    public DeleteStatementStrategy(Long id) {
        this.id = id;
    }

    @Override
    public PreparedStatement makePreparedStatement(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = null;
        preparedStatement = connection.prepareStatement("delete from userinfo where id = ?");
        preparedStatement.setLong(1, id);

        return preparedStatement;
    }
}
