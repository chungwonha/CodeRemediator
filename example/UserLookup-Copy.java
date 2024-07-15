import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

public class UserLookup {
    public void getUserInfo(String username) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM users WHERE username = '" + username + "'";
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                System.out.println("User ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
