package App.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

	//JDBC driver name and database URL
	private String JDBC_DRIVER;  
	private String DB_URL;
	//Database credentials
	private String USER;
	private String PASS;
	private Connection conn;

	public DatabaseConnection(){
		JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
		DB_URL = "jdbc:mysql://localhost/rapizz?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC&useUnicode=yes&characterEncoding=UTF-8";
		USER = "root";
		PASS = "";
		conn = null;
	}
	
	public void openConnection(){
		try{
			//Register JDBC driver
			Class.forName(JDBC_DRIVER);         
			//Open a connection
			System.out.print("Connecting to a selected database... ");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Success!");     
		}catch(Exception e){
			//Handle errors for JDBC
			e.printStackTrace();
		}
		test();
	}
	
	public void closeConnection(){
		try{
			if(conn!=null)
				conn.close();
		}catch(SQLException se){
			se.printStackTrace();
		}
		System.out.println("Connection closed");
	}
	
	public void test() {
		try {
		      //Création d'un objet Statement
		      Statement state = conn.createStatement();
		      //L'objet ResultSet contient le résultat de la requête SQL
		      ResultSet result = state.executeQuery("SELECT * FROM pizza");
		      //On récupère les MetaData
		      ResultSetMetaData resultMeta = result.getMetaData();
		         
		      System.out.println("\n**********************************");
		      //On affiche le nom des colonnes
		      for(int i = 1; i <= resultMeta.getColumnCount(); i++)
		        System.out.print("\t" + resultMeta.getColumnName(i).toUpperCase() + "\t *");
		         
		      System.out.println("\n**********************************");
		         
		      while(result.next()){         
		        for(int i = 1; i <= resultMeta.getColumnCount(); i++)
		          System.out.print("\t" + result.getObject(i).toString() + "\t |");
		            
		        System.out.println("\n---------------------------------");

		      }

		      result.close();
		      state.close();
		         
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
	}
	
	public Connection getConnection(){
		return conn;
	}
}
