package App.Model;

public class DatabaseManager {

	
	private static DatabaseConnection dbConnection;
	
	public DatabaseManager() {
		dbConnection = new DatabaseConnection();
	}
	
	public static void launchDatabaseConnection() {
		dbConnection.openConnection();
	}
	
	public static void closeDatabaseConnection() {
		dbConnection.closeConnection();
	}
	
}
