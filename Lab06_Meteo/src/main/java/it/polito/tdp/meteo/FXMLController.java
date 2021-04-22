/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.meteo;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.meteo.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class FXMLController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxMese"
    private ChoiceBox<Integer> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="btnUmidita"
    private Button btnUmidita; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalcola"
    private Button btnCalcola; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    private Model model;

    @FXML
    void doCalcolaSequenza(ActionEvent event) {

    }

    @FXML
    void doCalcolaUmidita(ActionEvent event) {
    	if(boxMese.getValue() != null) {
    		int mese = (int) boxMese.getValue();
    		String stringFinale = model.getUmiditaMedia(mese);
    		txtResult.setText(stringFinale);
    	}
    	else {
    		txtResult.setText("Nessun mese inserito");
    	}

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnUmidita != null : "fx:id=\"btnUmidita\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCalcola != null : "fx:id=\"btnCalcola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
		this.model = model;	
		List<Integer> num_mesi = new ArrayList<Integer>();
		num_mesi.add(1);
		num_mesi.add(2);
		num_mesi.add(3);
		num_mesi.add(4);
		num_mesi.add(5);
		num_mesi.add(6);
		num_mesi.add(7);
		num_mesi.add(8);
		num_mesi.add(9);
		num_mesi.add(10);
		num_mesi.add(11);
		num_mesi.add(12);
		
		this.boxMese.getItems().addAll(num_mesi);
	}
}

