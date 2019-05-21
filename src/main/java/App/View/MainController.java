package App.View;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXMasonryPane;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class MainController {
	@FXML
    private JFXListView<Label> panierList;
    
    @FXML
    private BorderPane mainPane;
    
    private JFXMasonryPane pizzaDisplay;
    
    @FXML
    public void initialize() {
    	System.out.println("Slt");
    	try {
			initMasonryPane();
			mainPane.setCenter(pizzaDisplay);
		} catch (IOException e) {
			e.printStackTrace();
		} 	
    	System.out.println("Fin");
    }
    
    private void initMasonryPane() throws IOException {
    	pizzaDisplay = new JFXMasonryPane();
    	
    	for (int i = 0; i < 10; i++) {
    		VBox v = new VBox();
    		v.setStyle("-fx-background-color:red");
        	v.setPrefSize(200, 290);
        	v.setMinSize(200, 290);
        	v.setMaxSize(200, 290);
        	v.setAlignment(Pos.CENTER);

        	ImageView imageView = new ImageView("file:src/resources/pizzas/pizza.png");
        	
        	Label pizzaName = new Label("Pizza jolie");
        	
        	Label pizzaIng = new Label("Tomates,  poivrons, viande hachée, sauce dégueu");
        	
        	Label pizzaTailleLabel = new Label("Séléctionner la taille de la pizza :");
        	
        	ChoiceBox<String> cbPizzaTaille = new ChoiceBox<String>();
        	cbPizzaTaille.getItems().add("Naine");
        	cbPizzaTaille.getItems().add("Humaine");
        	cbPizzaTaille.getItems().add("Ogresse");
        	
        	JFXButton buttonAdd = new JFXButton("Ajouter au panier");
        	buttonAdd.setRipplerFill(Paint.valueOf("#12f230"));
        	buttonAdd.setStyle("-fx-background-color:#2ecc71;");
        	
        	v.getChildren().add(imageView);
        	v.getChildren().add(pizzaName);
        	v.getChildren().add(pizzaIng);
        	v.getChildren().add(pizzaTailleLabel);
        	v.getChildren().add(cbPizzaTaille);
        	v.getChildren().add(buttonAdd);
        	
        	v.setStyle("-fx-border-width:1;"
        			+ "-fx-border-radius:10;"
        			+ "-fx-border-color:#e0e0e0;");
        	
    		pizzaDisplay.getChildren().add(v);
        }
    	pizzaDisplay.setCellHeight(200);
    	pizzaDisplay.setCellWidth(290);
    }
 
}
