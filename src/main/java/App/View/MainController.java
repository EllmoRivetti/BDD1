package App.View;

import java.io.IOException;
import java.util.Random;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXMasonryPane;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

public class MainController {
	@FXML
    private JFXListView<Label> panierList;

    @FXML
    private JFXMasonryPane pizzaDisplay;
    
    @FXML
    public void initialize() {
    	System.out.println("Slt");
    	
    	try {
			initMasonryPane();
		} catch (IOException e) {
			e.printStackTrace();
		} 	
    	System.out.println("Fin");
    }
    
    private void initMasonryPane() throws IOException {
    	for (int i = 0; i < 100; i++) {
    		GridPane pizzCell = FXMLLoader.load(getClass().getResource("../pizzaCell.fxml"));
            pizzaDisplay.getChildren().add(pizzCell);
        }
    }
}
