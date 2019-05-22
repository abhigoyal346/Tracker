package Database;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Abhishek Goyal
 */
public class DBConnection {
    public static Connection connect() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql:///db";
            conn = DriverManager.getConnection(url, "root", "");

        } catch (Exception e) {
            System.out.println("Exception in connect()" + e);
        }
        return conn;
    }
}