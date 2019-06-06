package App.View;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;

import App.Model.DatabaseManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

public class PayWindowController {

    @FXML
    private StackPane stackPane;

    @FXML
    private JFXListView<Label> recapList;

    @FXML
    private JFXButton btnPay;

    @FXML
    private Label soldeAmount;

    @FXML
    private Label payAmount;
    
    private double payAmountNb;
    
    private double soldeAmountNb;
    
    public static String amountTotal = "0 €";
    
    private JFXDialog dialogPayOK;
    private JFXDialog dialogPayNotOK;
    
    @FXML
    public void initialize() {
    	initDialog();
    	
    	soldeAmount.setText(String.valueOf(MainController.currentUser.getSolde()));
    	soldeAmountNb = MainController.currentUser.getSolde();
    	
    	payAmount.setText(String.valueOf(MainController.amountbasket) + " €");
    	payAmountNb = MainController.amountbasket;
    	
    	btnPay.setOnAction(e->{
    		if(soldeAmountNb < payAmountNb) {
    			dialogPayNotOK.show();
    		}else {
    			registerCommand();
    		}
    	});
    	
    	
    	for(String s : MainController.basketContent) {
    		Label displayLab = new Label(s);
			displayLab.setStyle("-fx-font-weight: bold;"
					+ "-fx-font-size: 15px;");
			recapList.getItems().add(displayLab);
    	}
    }
    
    private void registerCommand() {
    	dialogPayOK.show();
		MainController.currentUser.increaseNbPizzaCommande(MainController.basketContent.size());
		MainController.currentUser.decreaseSolde(payAmountNb);
		MainController.updateClientInformations();
		
		
		//Create command
		ResultSet resultLivreurLibre = DatabaseManager.executeQuerry("SELECT idLivreur FROM livreur WHERE statut = 0");
		ResultSet resultVehiculeDispo = DatabaseManager.executeQuerry("SELECT idVehicule FROM vehicule v where not exists (SELECT * FROM vehicule x , livreur l WHERE v.nom = x.nom and l.statut = 1 and l.vehicule = v.idVehicule)");
		
		
		try {
			String idLivreur = null;
			while(resultLivreurLibre.next()) {
				idLivreur = resultLivreurLibre.getObject("idLivreur").toString();				
				break;
			}
			DatabaseManager.executeUpdate("INSERT INTO commande (livreur,client,prix) VALUES("+idLivreur+","+MainController.currentUser.getId()+","+payAmountNb+")");
			ResultSet resultIdCommande = DatabaseManager.executeQuerry("SELECT idCommande FROM commande WHERE livreur="+idLivreur+" and client="+MainController.currentUser.getId()+" and prix="+payAmountNb);
			
			String idCommande = null ;
			while(resultIdCommande.next()) {
				idCommande = resultIdCommande.getObject("idCommande").toString();
				break;
			}	
			System.out.println(idCommande);
			DatabaseManager.executeUpdate("INSERT INTO livraison (commande) VALUES ("+idCommande+")"); 
			
			for(String s : MainController.basketContent) {
				String nomPizza = s.split(" en taille ")[0];
				ResultSet resultIdPizza = DatabaseManager.executeQuerry("SELECT idPizza FROM pizza WHERE nom ='"+nomPizza+"'");
				
				while(resultIdPizza.next()) {
					DatabaseManager.executeUpdate("INSERT INTO pizzaparcommande (idCommande,idPizza) VALUES ("+idCommande+","+resultIdPizza.getObject(1).toString()+")"); 
					break;
				}
			}
			
			//Update du livreur 
			DatabaseManager.executeUpdate("UPDATE livreur SET statut = '1' WHERE livreur.idLivreur = "+idLivreur);
			while(resultVehiculeDispo.next()) {
				DatabaseManager.executeUpdate("UPDATE livreur SET vehicule = "+ resultVehiculeDispo.getObject("idVehicule").toString() +" WHERE livreur.idLivreur = "+idLivreur);
				break;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    private void initDialog() {
    	/*First dialog : OK*/
    	JFXDialogLayout layoutOk = new JFXDialogLayout();
    	layoutOk.setHeading(new Text("Succès !"));
    	layoutOk.setBody(new Text("La commande a été passé avec succès !"));
    	dialogPayOK = new JFXDialog(stackPane,layoutOk,JFXDialog.DialogTransition.CENTER);
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
    		dialogPayOK.close();
    	});
    	
    	layoutOk.setActions(btnOk);
    	dialogPayOK.setOnDialogClosed(e->{
    		MainController.payStage.close();
    	});
    	
    	/*Second dialog : not ok*/
    	JFXDialogLayout layoutNotOk = new JFXDialogLayout();
    	layoutNotOk.setHeading(new Text("Echec..."));
    	layoutNotOk.setBody(new Text("La commande a échouée. Veuillez vérifier votre solde."));
    	dialogPayNotOK = new JFXDialog(stackPane,layoutNotOk,JFXDialog.DialogTransition.CENTER);
    	JFXButton btnOk2= new JFXButton("Ok");
    	btnOk2.setRipplerFill(Paint.valueOf("#80e27e"));
    	btnOk2.setStyle("-fx-background-color:#4caf50;");
    	btnOk2.setOnMouseEntered(i->{
    		btnOk2.setStyle("-fx-background-color:#087f23;"
					+ "-fx-text-fill:white;");
		});

    	btnOk2.setOnMouseExited(i->{
    		btnOk2.setStyle("-fx-background-color:#4caf50;"
					+ "-fx-text-fill:black;");
		});
    	
    	btnOk2.setOnAction(i->{
    		dialogPayNotOK.close();
    	});
    	layoutNotOk.setActions(btnOk2);  	
    }

	public String getSoldeAmountText() {
		return soldeAmount.getText();
	}

	public void setSoldeAmountText(String soldeAmount) {
		this.soldeAmount.setText(soldeAmount);
	}

	public String getPayAmountText() {
		return payAmount.getText();
	}

	public void setPayAmountText(String payAmount) {
		this.payAmount.setText(payAmount);
	}
}
