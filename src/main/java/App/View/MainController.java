package App.View;

import java.io.IOException;
import java.text.DecimalFormat;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXMasonryPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {
	@FXML
	private JFXListView<Label> panierList;

	@FXML
	private BorderPane mainPane;
	
	@FXML
	private Label totalPanier;

	private JFXMasonryPane pizzaDisplay;

	private ScrollPane mainScrollPane;

	private Label defaultPanierLabel;
	

	@FXML
	public void initialize() {
		System.out.println("Slt");

		defaultPanierLabel = new Label("Votre panier est vide.");
		defaultPanierLabel.setStyle("-fx-font-size: 15px;");

		try {
			panierList.getItems().add(defaultPanierLabel);
			initMasonryPane();
			mainScrollPane = new ScrollPane();
			mainScrollPane.setStyle("-fx-background-color:#e0e0e0;");
			mainScrollPane.setContent(pizzaDisplay);
			mainScrollPane.setFitToWidth(true);
			mainPane.setCenter(mainScrollPane);
		} catch (IOException e) {
			e.printStackTrace();
		} 	
		System.out.println("Fin");
	}

	private void initMasonryPane() throws IOException {
		pizzaDisplay = new JFXMasonryPane();

		for (int i = 0; i < 25; i++) {
			VBox v = new VBox();
			v.setSpacing(15);
			v.setPadding(new Insets(0, 5, 10, 5));
			v.setPrefSize(200, 290);
			v.setMinSize(200, 290);
			v.setMaxSize(200, 290);
			v.setAlignment(Pos.CENTER);

			ImageView imageView = new ImageView("/pizzas/pizza.png");
			imageView.resize(100, 100);

			Label pizzaName = new Label("Pizza jolie");
			pizzaName.setStyle("-fx-font-weight: bold;"
					+ "-fx-font-size: 20px;");

			Label pizzaIng = new Label("Tomates,  poivrons, viande hachée, sauce dégueu");
			pizzaIng.setWrapText(true);
			pizzaIng.setStyle("-fx-text-fill:#546e7a;");

			Label pizzaTailleLabel = new Label("Séléctionner la taille de la pizza :");
			pizzaTailleLabel.setAlignment(Pos.CENTER_LEFT);
			pizzaTailleLabel.setStyle("-fx-font-weight: bold");

			ChoiceBox<String> cbPizzaTaille = new ChoiceBox<String>();
			cbPizzaTaille.getItems().add("Naine");
			cbPizzaTaille.getItems().add("Humaine");
			cbPizzaTaille.getItems().add("Ogresse");
			cbPizzaTaille.getSelectionModel().select(1);
			cbPizzaTaille.setPrefWidth(110);
			cbPizzaTaille.getStylesheets().add("/css/cb.css");

			Label price = new Label("17.50"+" €");
			price.setStyle("-fx-font-weight: bold");

			cbPizzaTaille.getSelectionModel().selectedIndexProperty().addListener(e->{
				double calculus;
				if(cbPizzaTaille.getSelectionModel().getSelectedIndex() == 0)
					calculus = 17.5 - (17.5 * 0.33); //TODO get data from db
				else if(cbPizzaTaille.getSelectionModel().getSelectedIndex() == 2)
					calculus = 17.5 + (17.5 * 0.33); //TODO get data from db
				else
					calculus = 17.5; //TODO get data from db
				
				DecimalFormat df = new DecimalFormat("##.##");
				price.setText(String.valueOf(df.format(calculus))+" €");
			});


			JFXButton buttonAdd = new JFXButton("Ajouter au panier");
			//buttonAdd.getStylesheets().addAll(getClass().getResource("/css/outline.css").toExternalForm());
			buttonAdd.setOnMouseEntered(e->{
				buttonAdd.setStyle("-fx-background-color:#087f23;"
						+ "-fx-text-fill:white;");
			});

			buttonAdd.setOnMouseExited(e->{
				buttonAdd.setStyle("-fx-background-color:#4caf50;"
						+ "-fx-text-fill:black;");
			});
			
			buttonAdd.setOnAction(e->{
				addToBasket(pizzaName.getText(), cbPizzaTaille.getSelectionModel().getSelectedIndex(), price.getText());
			});

			buttonAdd.setRipplerFill(Paint.valueOf("#80e27e"));
			buttonAdd.setStyle("-fx-background-color:#4caf50;");

			v.getChildren().add(imageView);
			v.getChildren().add(pizzaName);
			v.getChildren().add(pizzaIng);
			v.getChildren().add(pizzaTailleLabel);
			v.getChildren().add(cbPizzaTaille);
			v.getChildren().add(price);
			v.getChildren().add(buttonAdd);

			v.setStyle("-fx-border-width:1;"
					+ "-fx-border-radius:10;"
					+ "-fx-border-color:#e0e0e0;"
					+ "-fx-background-color:#ffffff;"
					+ "-fx-background-radius:10;");

			pizzaDisplay.getChildren().add(v);
		}
		pizzaDisplay.setCellHeight(190);
		pizzaDisplay.setCellWidth(210);
		pizzaDisplay.setPadding(new Insets(10, 10, 10, 10));
	}

	@FXML
	void commander(ActionEvent event) {
		Stage payStage = new Stage();
		payStage.setAlwaysOnTop(true);
		payStage.initModality(Modality.APPLICATION_MODAL);
		BorderPane root;
		try {
			payWindowController.amountTotal = totalPanier.getText();
			FXMLLoader fxmlLoader = new FXMLLoader();
			root = fxmlLoader.load(getClass().getClassLoader().getResource("payWindow.fxml"));
			payWindowController cont = fxmlLoader.getController();
			payStage.setTitle("RaPizz - Payement de la commande");
			payStage.getIcons().add(new Image("/pizzas/pizza.png"));
			Scene scene = new Scene(root);
			payStage.setScene(scene);
			payStage.setResizable(false);
			payStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	void addToBasket(String nom, int size, String prix) {
		if(panierList.getItems().get(0) != null) {
			if(panierList.getItems().get(0).equals(defaultPanierLabel)) {
				panierList.getItems().remove(0);
			}
			
			prix = prix.replace(",", ".");
			String temp = totalPanier.getText().split(" ")[0].replace(",",".");
			double old = Double.parseDouble(temp);
			
			double calculus =  old + Double.parseDouble(prix.split(" ")[0]);
			DecimalFormat df = new DecimalFormat("##.##");
			totalPanier.setText(String.valueOf(df.format(calculus))+" €");
			
			String taille = "Humaine";
			if(size == 0)
				taille = "Naine";
			else if (size == 2)
				taille = "Ogresse";
				
			
			Label displayLab = new Label(nom+" en taille "+taille +" : "+prix);
			displayLab.setStyle("-fx-font-weight: bold;"
					+ "-fx-font-size: 15px;");
			panierList.getItems().add(displayLab);
		}	
	}
	
	


}
