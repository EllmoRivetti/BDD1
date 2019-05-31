package App.View;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import App.Model.DatabaseManager;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

public class RegisterController {
	@FXML
	private StackPane stackPane;

	@FXML
	private JFXButton btnInscrire;

	@FXML
	private JFXTextField fldNom;

	@FXML
	private JFXTextField fldLogin;

	@FXML
	private JFXPasswordField fldMdp;

	@FXML
	public void initialize() {
		btnInscrire.setOnAction(e->{
			System.out.println("go");
			inscription();
		});
	}

	private void inscription() {
		ResultSet result = DatabaseManager.executeQuerry("SELECT * FROM client WHERE login='"+fldLogin.getText()+"'");
		try {
			if(!result.next()) {//Check if login already exists
				System.out.println("login doesn't exist");
				DatabaseManager.executeUpdate("INSERT INTO `client` (`nom`, `login`, `mdp`) VALUES ('"+fldNom.getText()+"', '"+fldLogin.getText()+"', '"+fldMdp.getText()+"')");
				
				JFXDialogLayout layout = new JFXDialogLayout();
		    	layout.setHeading(new Text("Information"));
		    	layout.setBody(new Text("Votre compte a bien été créé ! Vous pouvez vous connecter en revenant sur la page principale de l'application."));
		    	JFXDialog dialog = new JFXDialog(stackPane,layout,JFXDialog.DialogTransition.CENTER);
		    	
		    	JFXButton btnOk= new JFXButton("Ok");
		    	btnOk.setRipplerFill(Paint.valueOf("#80e27e"));
		    	btnOk.setStyle("-fx-background-color:#4caf50;");
		    	btnOk.setOnMouseEntered(i->{
		    		btnOk.setStyle("-fx-background-color:#087f23;"
							+ "-fx-text-fill:white;");
				});

		    	btnOk.setOnMouseExited(i->{
		    		btnOk.setStyle("-fx-background-color:#4caf50;"
							+ "-fx-text-fill:black;");
				});
		    	
		    	btnOk.setOnAction(i->{
		    		dialog.close();
		    	});
		    	
		    	layout.setActions(btnOk);
		    	dialog.show();
		    	dialog.setOnDialogClosed(e->{
		    		MainController.registerStage.close();
		    	});
				
			}else {
				System.out.println("login exists");
				JFXDialogLayout layout = new JFXDialogLayout();
		    	layout.setHeading(new Text("Information"));
		    	layout.setBody(new Text("Ce login est déjà utilisé. Veuillez changer de login ou bien vous connecter s'il s'agit de vous."));
		    	JFXDialog dialog = new JFXDialog(stackPane,layout,JFXDialog.DialogTransition.CENTER);
		    	
		    	JFXButton btnOk= new JFXButton("Ok");
		    	btnOk.setRipplerFill(Paint.valueOf("#80e27e"));
		    	btnOk.setStyle("-fx-background-color:#4caf50;");
		    	btnOk.setOnMouseEntered(i->{
		    		btnOk.setStyle("-fx-background-color:#087f23;"
							+ "-fx-text-fill:white;");
				});

		    	btnOk.setOnMouseExited(i->{
		    		btnOk.setStyle("-fx-background-color:#4caf50;"
							+ "-fx-text-fill:black;");
				});
		    	
		    	btnOk.setOnAction(i->{
		    		dialog.close();
		    	});
		    	
		    	layout.setActions(btnOk);
		    	
		    	dialog.show();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
