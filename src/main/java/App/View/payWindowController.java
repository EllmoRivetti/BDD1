package App.View;

import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class payWindowController {

    @FXML
    private StackPane stackPane;

    @FXML
    private JFXListView<?> recapList;

    @FXML
    private JFXButton btnPay;

    @FXML
    private Label soldeAmount;

    @FXML
    private Label payAmount;
    
    public static String amountTotal = "0 €";
    
    public static ArrayList<Label> arrayPizza= new ArrayList<Label>();//TODO utiliser pour passer les données des pizzas du recap
    
    private JFXDialog dialogPayOK;
    private JFXDialog dialogPayNotOK;
    
    @FXML
    public void initialize() {
    	initDialog();
    	payAmount.setText(amountTotal);
    	btnPay.setOnAction(e->{
    		dialogPayNotOK.show();
    	});
    }
    
    private void initDialog() {
    	/*First dialog : OK*/
    	JFXDialogLayout layoutOk = new JFXDialogLayout();
    	layoutOk.setHeading(new Text("Succès !"));
    	layoutOk.setBody(new Text("La commande a été passé avec succès !"));
    	dialogPayOK = new JFXDialog(stackPane,layoutOk,JFXDialog.DialogTransition.CENTER);
    	
    	/*Second dialog : not ok*/
    	JFXDialogLayout layoutNotOk = new JFXDialogLayout();
    	layoutNotOk.setHeading(new Text("Echec..."));
    	layoutNotOk.setBody(new Text("La commande a échouée. Veuillez vérifier votre solde."));
    	dialogPayNotOK = new JFXDialog(stackPane,layoutNotOk,JFXDialog.DialogTransition.CENTER);
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
