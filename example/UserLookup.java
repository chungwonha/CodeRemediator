import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserLookup {
    public void getUserInfo(String username) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT * FROM users WHERE username = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                System.out.println("User ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}