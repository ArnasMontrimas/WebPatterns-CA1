package DataAccessObjects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import Program.Program;
/**
 * @author grallm
 * @author Sam Ponik
 * @author Arnas Montrimas
 *
 * Principal DAO connecting to the MySQL DB with JDBC
 */
public class Dao 
{
    private final String databaseName;
    
    public Dao(String databaseName)
    {
        this.databaseName = databaseName;
    }
    
    public Connection getConnection()
    {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/" + databaseName;
        String username = "root";
        String password = "";
        Connection con = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException ex1) {
            System.out.println(Program.globalMessages.getString("dao_failedDriver") + ex1.getMessage());
            System.exit(1);
        } catch (SQLException ex2) {
            System.out.println(Program.globalMessages.getString("dao_ConFailed") + ex2.getMessage());
        }
        return con;
    }

    public void freeConnection(Connection con)
    {
        try {
            if (con != null) {
                con.close();
                con = null;
            }
        } catch (SQLException e) {
            System.out.println(Program.globalMessages.getString("dao_FreeConFailed") + e.getMessage());
            System.exit(1);
        }
    }

}