package App;
import App.Model.DatabaseManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//Loading database connection
			DatabaseManager dbManager = new DatabaseManager();
			DatabaseManager.launchDatabaseConnection();
			
			//Create UI
			AnchorPane root = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/View.fxml"));
			primaryStage.setTitle("RaPizz");
			primaryStage.getIcons().add(new Image("/pizzas/pizza.png"));
			Scene scene = new Scene(root,1200,720);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			//Closing database connection when exiting the app
			primaryStage.setOnCloseRequest(e->{
				DatabaseManager.closeDatabaseConnection();
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
