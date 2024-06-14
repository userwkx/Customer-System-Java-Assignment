package login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginService {

    private static final String URL = "jdbc:mysql://localhost:3306/administrator?serverTimezone=GMT%2B8&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";

    public String authenticateAndGetRole(String username, String password) {
        String role = null;
        String query = "SELECT r.role_name " +
                "FROM Users u " +
                "INNER JOIN Roles r ON u.role_id = r.role_id " +
                "WHERE u.username = ? AND u.password = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                role = rs.getString("role_name");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return role;
    }
}
