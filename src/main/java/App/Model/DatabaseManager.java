package App.Model;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

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

	public static ResultSet executeQuerry(String querry) {
		ResultSet result = null;
		try {
			Statement state = dbConnection.getConnection().createStatement();
			result = state.executeQuery(querry);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
