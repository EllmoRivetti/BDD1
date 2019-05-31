package App.View;

import java.io.IOException;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.sun.security.auth.UserPrincipal;

import App.Model.DatabaseConnection;
import App.Model.DatabaseManager;
import App.Model.Pizza;
import App.Model.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {


	/**Singleton**/
	public static MainController instance;


	/**FXML Object **/

	@FXML
	private JFXListView<Label> panierList;

	@FXML
	private BorderPane mainPane;

	@FXML
	private Label totalPanier;

	@FXML
	private BorderPane panePizzaDisplay;

	@FXML
	private TabPane tabPaneCompte;
	
	@FXML
	private JFXTabPane mainTabPane;

	@FXML
	private JFXButton btnCompteConnexion;
	@FXML
	private JFXButton btnCompteDeco;

	@FXML
	private JFXButton btnCompteInscription;

	@FXML
	private Label lblCompteNom;

	@FXML
	private Label lblCompteSolde;
	@FXML
	private Label lblCompteNbPizza;

	@FXML
	private StackPane stackPaneCompte;

	@FXML
	private StackPane stackPanePizzaDisplay;



	/**Class Object **/

	private JFXMasonryPane pizzaDisplay;

	private ScrollPane mainScrollPane;

	private Label defaultPanierLabel;

	public static Utilisateur currentUser = null;

	public static double amountbasket = 0;

	public static ArrayList<String> basketContent = new ArrayList<String>();

	public static Stage payStage;

	public static Stage registerStage;

	private boolean isUserLoggedIn;

	private ArrayList<Pizza> pizzaList;

	private Tab tabBackEnd;


	@FXML
	public void initialize() {
		instance = this;//Instantiate singleton
		
		setEvents();//instantiate events
		initTabBackEnd();
		pizzaList = new ArrayList<Pizza>();
		fillPizzaList();

		defaultPanierLabel = new Label("Votre panier est vide.");
		defaultPanierLabel.setStyle("-fx-font-size: 15px;");

		try {
			panierList.getItems().add(defaultPanierLabel);
			initMasonryPane();
			mainScrollPane = new ScrollPane();
			mainScrollPane.setStyle("-fx-background-color:#e0e0e0;");
			mainScrollPane.setContent(pizzaDisplay);
			mainScrollPane.setFitToWidth(true);
			panePizzaDisplay.setCenter(mainScrollPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initTabBackEnd() {
		tabBackEnd = new Tab();
		tabBackEnd.setText("Administration");
		BorderPane root;

		FXMLLoader fxmlLoader = new FXMLLoader();
		try {
			root = fxmlLoader.load(getClass().getClassLoader().getResource("FXML/backEndView.fxml"));
			tabBackEnd.setContent(root);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setEvents() {
		btnCompteConnexion.setOnAction(e->{
			connection(true);
		});

		btnCompteDeco.setOnAction(e->{
			mainTabPane.getSelectionModel().select(1);
			mainTabPane.getTabs().remove(2);
			tabPaneCompte.getSelectionModel().select(0);
			JFXDialogLayout layout = new JFXDialogLayout();
			layout.setHeading(new Text("Information"));
			layout.setBody(new Text("Vous avez été déconnecté"));
			JFXDialog dialog = new JFXDialog(stackPaneCompte,layout,JFXDialog.DialogTransition.CENTER);

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
			currentUser = null;
			isUserLoggedIn = false;
		});

		btnCompteInscription.setOnAction(e->{
			register();
		});
	}

	private void fillPizzaList() {
		try {
			ResultSet result = DatabaseManager.executeQuerry("SELECT * FROM pizza");
			while(result.next()) {

				ArrayList<String> ing = new ArrayList<String>();
				ResultSet resultIng = DatabaseManager.executeQuerry("SELECT i.nom from ingredient as i , ingredientsparpizza as ing WHERE i.idIngredient = ing.idIngredient AND ing.idPizza = "+result.getObject("idPizza").toString());
				while(resultIng.next())
					ing.add(resultIng.getObject("nom").toString());


				pizzaList.add(new Pizza(Integer.parseInt(result.getObject("idPizza").toString()), result.getObject("nom").toString(), ing, Double.parseDouble(result.getObject("prix_normal").toString())));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void initMasonryPane() throws IOException {
		pizzaDisplay = new JFXMasonryPane();

		for (Pizza p : pizzaList) {
			VBox v = new VBox();
			v.setSpacing(15);
			v.setPadding(new Insets(0, 5, 10, 5));
			v.setPrefSize(200, 350);
			v.setMinSize(200, 350);
			v.setMaxSize(200, 350);
			v.setAlignment(Pos.CENTER);

			ImageView imageView = new ImageView("/pizzas/"+p.getNomPizza().toLowerCase()+".jpg");

			//imageView.resize(100, 100);

			Label pizzaName = new Label(p.getNomPizza());
			pizzaName.setStyle("-fx-font-weight: bold;"
					+ "-fx-font-size: 20px;");

			Label pizzaIng = new Label("");
			for(String s : p.getListIngredients()) {
				if(p.getListIngredients().get(0) == s)
					pizzaIng.setText(pizzaIng.getText()+s);
				else
					pizzaIng.setText(pizzaIng.getText()+", "+s);
			}
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

			Label price = new Label(String.valueOf(p.getPrix())+" €");
			price.setStyle("-fx-font-weight: bold");

			cbPizzaTaille.getSelectionModel().selectedIndexProperty().addListener(e->{
				double calculus;
				if(cbPizzaTaille.getSelectionModel().getSelectedIndex() == 0)
					calculus = p.getPrix() - (p.getPrix() * 0.33);
				else if(cbPizzaTaille.getSelectionModel().getSelectedIndex() == 2)
					calculus = p.getPrix() + (p.getPrix() * 0.33);
				else
					calculus = p.getPrix();

				DecimalFormat df = new DecimalFormat("##.##");
				price.setText(String.valueOf(df.format(calculus))+" €");
			});


			JFXButton buttonAdd = new JFXButton("Ajouter au panier");
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

	void register() {
		registerStage = new Stage();
		registerStage.setAlwaysOnTop(true);
		registerStage.initModality(Modality.APPLICATION_MODAL);
		BorderPane root;
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			root = fxmlLoader.load(getClass().getClassLoader().getResource("FXML/registerView.fxml"));
			registerStage.setTitle("RaPizz - Création d'un compte");
			registerStage.getIcons().add(new Image("/pizzas/pizza.png"));
			Scene scene = new Scene(root);
			registerStage.setScene(scene);
			registerStage.setResizable(false);
			registerStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void commander(ActionEvent event) {

		if(!isUserLoggedIn) {
			JFXDialogLayout layout = new JFXDialogLayout();
			layout.setHeading(new Text("Information"));
			layout.setBody(new Text("Veuillez vous connecter avant de commander"));
			JFXDialog dialog = new JFXDialog(stackPanePizzaDisplay,layout,JFXDialog.DialogTransition.CENTER);
			dialog.setOnDialogClosed(e->{
				connection(false);
			});

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
		else {
			payStage = new Stage();
			payStage.setAlwaysOnTop(true);
			payStage.initModality(Modality.APPLICATION_MODAL);
			BorderPane root;
			try {
				PayWindowController.amountTotal = totalPanier.getText();
				FXMLLoader fxmlLoader = new FXMLLoader();
				root = fxmlLoader.load(getClass().getClassLoader().getResource("FXML/payWindow.fxml"));
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
			amountbasket = calculus;

			String taille = "Humaine";
			if(size == 0)
				taille = "Naine";
			else if (size == 2)
				taille = "Ogresse";


			Label displayLab = new Label(nom+" en taille "+taille +" : "+prix);
			displayLab.setStyle("-fx-font-weight: bold;"
					+ "-fx-font-size: 15px;");
			panierList.getItems().add(displayLab);
			basketContent.add(displayLab.getText());
		}	
	}


	private void connection(boolean accountView) {
		JFXDialog dialogConnection;
		JFXDialogLayout layout = new JFXDialogLayout();
		layout.setHeading(new Text("Entrez vos identifiants"));


		GridPane body = new GridPane();
		body.setHgap(15);

		Label lblLogin = new Label("Login :");
		lblLogin.setStyle("-fx-font-size: 15px;");
		GridPane.setConstraints(lblLogin, 0, 0);

		Label lblpwd = new Label("Mot de passe :");
		lblpwd.setStyle("-fx-font-size: 15px;");
		GridPane.setConstraints(lblpwd, 0, 1);

		JFXTextField loginField = new JFXTextField();
		loginField.setFocusColor(Paint.valueOf("#f44336"));
		GridPane.setConstraints(loginField, 1, 0);


		JFXPasswordField passwordField = new JFXPasswordField();
		passwordField.setFocusColor(Paint.valueOf("#f44336"));
		GridPane.setConstraints(passwordField, 1, 1);

		body.getChildren().addAll(lblLogin,lblpwd,loginField,passwordField);

		layout.setBody(body);
		JFXButton btnCo= new JFXButton("Connexion");
		btnCo.setOnMouseEntered(e->{
			btnCo.setStyle("-fx-background-color:#087f23;"
					+ "-fx-text-fill:white;");
		});

		btnCo.setOnMouseExited(e->{
			btnCo.setStyle("-fx-background-color:#4caf50;"
					+ "-fx-text-fill:black;");
		});

		layout.setBody(body);

		if(accountView)
			dialogConnection = new JFXDialog(stackPaneCompte,layout,JFXDialog.DialogTransition.CENTER);
		else
			dialogConnection = new JFXDialog(stackPanePizzaDisplay,layout,JFXDialog.DialogTransition.CENTER);

		btnCo.setOnAction(e->{
			dialogConnection.close();
			ResultSet result = DatabaseManager.executeQuerry("SELECT * FROM client WHERE login='"+loginField.getText()+"' AND mdp='"+passwordField.getText()+"'");
			try {
				if(!result.next()) {//Check if user exists
					System.out.println("User doesn't exist");
				}else {
					System.out.println("User exists");

					boolean isAbo = false;
					if(result.getObject("abonne").toString().equals("1"))
						isAbo = true;

					boolean isAdmin = false;
					if(result.getObject("isAdmin").toString().equals("1"))
						isAdmin = true;
					
					currentUser = new Utilisateur(Integer.valueOf(result.getObject("idClient").toString()), result.getObject("nom").toString(), result.getObject("mdp").toString(), result.getObject("login").toString(), 
							isAbo, Double.valueOf(result.getObject("solde").toString()) , Integer.valueOf(result.getObject("nb_pizza_commande").toString()),isAdmin);

					isUserLoggedIn = true; //Set to logged in

					tabPaneCompte.getSelectionModel().select(1);//Change to connected account view
					lblCompteNom.setText(currentUser.getNom());
					lblCompteSolde.setText(String.valueOf(currentUser.getSolde()));
					lblCompteNbPizza.setText(String.valueOf(currentUser.getNbPizzaCommandees()));

					if(currentUser.isAdmin())
					{
						System.out.println("Adding pane");
						mainTabPane.getTabs().add(2, tabBackEnd);
					}

				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});

		btnCo.setRipplerFill(Paint.valueOf("#80e27e"));
		btnCo.setStyle("-fx-background-color:#4caf50;");
		layout.setActions(btnCo);

		dialogConnection.show();
	}

	public static void updateClientInformations() {
		instance.lblCompteSolde.setText(String.valueOf(currentUser.getSolde()));
		instance.lblCompteNbPizza.setText(String.valueOf(currentUser.getNbPizzaCommandees()));
	}

}
