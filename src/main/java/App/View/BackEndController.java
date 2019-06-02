package App.View;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import App.Model.DatabaseManager;
import javafx.fxml.FXML;

public class BackEndController {
	@FXML
	private JFXListView<String> listCommandes;

	@FXML
	private JFXListView<String> listVehiculesDispo;

	@FXML
	private JFXListView<String> listLivreursNonDispo;

	@FXML
	private JFXListView<String> listLivreursDispo;

	@FXML
	private JFXListView<String> listVehiculesNonDispo;
	
	@FXML
	private JFXButton btnMAJ;
	
	@FXML
	public void initialize() {
		fillLists();
		fillCommandeHistory();
		btnMAJ.setOnAction(e->{
			listVehiculesDispo.getItems().clear();
			listLivreursDispo.getItems().clear();
			listLivreursNonDispo.getItems().clear();
			listVehiculesNonDispo.getItems().clear();
			listCommandes.getItems().clear();
			fillLists();
			fillCommandeHistory();
		});
	}

	
	private void fillLists() {
		try {
			ResultSet resultLivreurLibre = DatabaseManager.executeQuerry("SELECT * FROM livreur WHERE statut = 0");
			ResultSet resultVehiculeAndLivreurNonDispo = DatabaseManager.executeQuerry("SELECT v.nom, l.nom FROM vehicule v , livreur l WHERE l.statut = 1 and l.vehicule = v.idVehicule");
			ResultSet resultVehiculeDispo = DatabaseManager.executeQuerry("SELECT * FROM vehicule v where not exists (SELECT * FROM vehicule x , livreur l WHERE v.nom = x.nom and l.statut = 1 and l.vehicule = v.idVehicule)");
			
			while(resultLivreurLibre.next()) {//Fill listLivreursDispo
				listLivreursDispo.getItems().add(resultLivreurLibre.getObject("nom").toString());
			}
			
			while(resultVehiculeAndLivreurNonDispo.next()) {
				listVehiculesNonDispo.getItems().add(resultVehiculeAndLivreurNonDispo.getObject(1).toString());
				String s = resultVehiculeAndLivreurNonDispo.getObject(2).toString() + " utilisant le véhicule "+resultVehiculeAndLivreurNonDispo.getObject(1).toString();
				listLivreursNonDispo.getItems().add(s);
			}
			
			while(resultVehiculeDispo.next()) {
				listVehiculesDispo.getItems().add(resultVehiculeDispo.getObject("nom").toString());
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void fillCommandeHistory() {
		
		ResultSet resultCommandes = DatabaseManager.executeQuerry("SELECT * FROM commande");
		try {
			while(resultCommandes.next()) {
				System.out.println(resultCommandes.getObject("client").toString());
				ResultSet resultNomClient = DatabaseManager.executeQuerry("SELECT nom FROM client WHERE idClient = "+resultCommandes.getObject("client").toString());
				String nomClient = "";
				while(resultNomClient.next()) {
					nomClient = resultNomClient.getObject("nom").toString();
					System.out.println(nomClient);
					break;
				}
				
				ResultSet resultPizzaCommande = DatabaseManager.executeQuerry("SELECT nom FROM pizza p, pizzaparcommande ppc WHERE p.idPizza = ppc.idPizza and ppc.idCommande ="+resultCommandes.getObject("idCommande").toString());
				String pizzas = "";
				
				while(resultPizzaCommande.next()) {
					pizzas+=" "+resultPizzaCommande.getObject(1).toString();
				}
				
				ResultSet resultLivraison = DatabaseManager.executeQuerry("SELECT * FROM livraison WHERE commande = "+resultCommandes.getObject("idCommande").toString());
				String timeStampDeb = "";
				String timeStampFin ="";
				while(resultLivraison.next()) {
					timeStampDeb = resultLivraison.getObject("ts_commande").toString();
					try {
						timeStampFin = resultLivraison.getObject("ts_livraison").toString();
					}catch(NullPointerException e) {
						timeStampFin = "NULL";
					}
					break;
				}
				
				ResultSet resultLivreur = DatabaseManager.executeQuerry("SELECT nom FROM livreur WHERE idLivreur="+resultCommandes.getObject("livreur").toString());
				String nomLivreur = "";
				while(resultLivreur.next()) {
					nomLivreur = resultLivreur.getObject(1).toString();
					break;
				}
				
				String text = "Commande par "+nomClient+" contenant "+ pizzas +" commandée le " + timeStampDeb +" livré par "+ nomLivreur +" a " + timeStampFin;
				listCommandes.getItems().add(text);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
