package dao;

import beans.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection _connection;

    public UserDAO(Connection connection) {
        _connection = connection;
    }

    public User login(User user) throws SQLException {
        String query = "SELECT password FROM Users WHERE username = ?";
        ResultSet result = null;
        PreparedStatement pstatement = null;
        boolean valid = false;
        try {
            pstatement = _connection.prepareStatement(query);
            pstatement.setString(1, user.getUserId());
            result = pstatement.executeQuery();
            if (result.next()) {
                valid = user.getPassword().equals(result.getString(1));
            }
        } catch (SQLException e) {
            throw new SQLException(e);

        } finally {
            try {
                result.close();
                pstatement.close();
            } catch (SQLException e1) {
                throw new SQLException("Cannot close");
            }
        }

        user.setValid(valid);


        return user;
    }
}
