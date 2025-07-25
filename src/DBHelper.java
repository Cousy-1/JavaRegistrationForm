import java.sql.*;

public class DBHelper {
    private static final String DB_URL = "jdbc:sqlite:registrtionform/users.db";

    public static void initDB() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT," +
                    "mobile TEXT," +
                    "gender TEXT," +
                    "dob TEXT," +
                    "address TEXT" +
                    ")";
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertUser(String name, String mobile, String gender, String dob, String address) {
        String sql = "INSERT INTO users(name, mobile, gender, dob, address) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, mobile);
            pstmt.setString(3, gender);
            pstmt.setString(4, dob);
            pstmt.setString(5, address);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String fetchUsers() {
        StringBuilder output = new StringBuilder();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {
            while (rs.next()) {
                output.append("Name: ").append(rs.getString("name")).append("\n");
                output.append("Mobile: ").append(rs.getString("mobile")).append("\n");
                output.append("Gender: ").append(rs.getString("gender")).append("\n");
                output.append("DOB: ").append(rs.getString("dob")).append("\n");
                output.append("Address: ").append(rs.getString("address")).append("\n\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}
