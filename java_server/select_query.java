import java.io.IOException;
import java.lang.ClassNotFoundException;
import java.sql.*;

/**
 * insert_data.java - Demonstrates how to INSERT data into an SQL
 *                    database using Java JDBC.
 */
public class insert_data { 
  
    public static void main (String[] args) throws IOException, ClassNotFoundException {
        try { 
            Class.forName("oracle.jdbc.driver.OracleDriver"); 
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:32769/ORCLCDB.localdomain","oracle","oracle"); 
            Statement st = conn.createStatement(); 
            ResultSet rs = st.executeQuery("select * from agro");

            while (rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getInt(2) + " " + rs.getInt(3) + " " + rs.getTimestamp(4));
            }

            rs.close();
            st.close();
            conn.close(); 
        } catch (Exception e) { 
            System.err.println("Got an exception! "); 
            System.err.println(e.getMessage());
        } 
  
    }
} 