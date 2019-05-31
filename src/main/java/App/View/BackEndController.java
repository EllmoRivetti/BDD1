package App.View;

import com.jfoenix.controls.JFXListView;

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
}
