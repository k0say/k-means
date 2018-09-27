package database;
import java.sql.*;

/**
 * Classe che realizza l'accesso alla base di dati.
 * 
 * @author Giuseppe Pio Maffia, Giovanni Vittore
 *
 */
public class DbAccess {

	private static final String DBMS = "jdbc:mysql";
	private static final String SERVER = "localhost";
	private static final String DATABASE = "MapDB";
	private static final String PORT = "3306";
	private static final String USER_ID = "MapUser";
	private static final String PASSWORD = "map";
	private static Connection conn;
	
	public static void initConnection() throws DatabaseConnectionException {
		

		
		try {
			conn = DriverManager.getConnection(DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",USER_ID, PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}
	
	public static Connection getConnection() {
		return conn;
	}
	
	public static void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
