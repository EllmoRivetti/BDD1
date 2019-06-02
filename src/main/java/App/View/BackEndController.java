package App.View;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jfoenix.controls.JFXListView;

import App.Model.DatabaseManager;
import javafx.fxml.FXML;

public class BackEndController {
	@FXML
	private JFXListView<?> listCommandes;

	@FXML
	private JFXListView<String> listVehiculesDispo;

	@FXML
	private JFXListView<String> listLivreursNonDispo;

	@FXML
	private JFXListView<String> listLivreursDispo;

	@FXML
	private JFXListView<String> listVehiculesNonDispo;
	
	
	@FXML
	public void initialize() {
		fillLists();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
