

/**
 * Sample Skeleton for 'ExtFlightDelays.fxml' Controller Class
 */

package it.polito.tdp.extflightdelays;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.extflightdelays.model.Airport;
import it.polito.tdp.extflightdelays.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ExtFlightDelaysController {

	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="distanzaMinima"
    private TextField distanzaMinima; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalizza"
    private Button btnAnalizza; // Value injected by FXMLLoader

    @FXML // fx:id="cmbBoxAeroportoPartenza"
    private ComboBox<Airport> cmbBoxAeroportoPartenza; // Value injected by FXMLLoader

    @FXML // fx:id="btnAeroportiConnessi"
    private Button btnAeroportiConnessi; // Value injected by FXMLLoader

    @FXML // fx:id="numeroVoliTxtInput"
    private TextField numeroVoliTxtInput; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaItinerario"
    private Button btnCercaItinerario; // Value injected by FXMLLoader

    @FXML
    void doAnalizzaAeroporti(ActionEvent event) {

    	txtResult.clear();
    	cmbBoxAeroportoPartenza.getItems().clear();
    	
    	try {
    		
    		int x = Integer.parseInt(distanzaMinima.getText());
    		
    		this.model.creaGrafo(x);
    		
    		List<Airport> aeroporti = this.model.getAeroporti();
    		
    		cmbBoxAeroportoPartenza.getItems().addAll(aeroporti);
    		
    		
	} 
    	catch (NumberFormatException e ) {
    		txtResult.appendText("Inserire un numero nel formato corretto");
    	} 
    	catch( RuntimeException e ) 
    	{
    		txtResult.appendText("\nERRORE");
    	}
    }

    @FXML
    void doCalcolaAeroportiConnessi(ActionEvent event) {
    	txtResult.clear();
    	try {
    		
    		Airport a = cmbBoxAeroportoPartenza.getValue();
    		
    		if (a == null) {
    			txtResult.appendText("Seleziona un aeroporto!");
    		}
    		
    		List<Airport> connessi = this.model.getConnessi(a);
    		
    		for (Airport a1: connessi) {
    			txtResult.appendText(a1+"\n");
    		}
    		
    	} catch (RuntimeException e) {
    		txtResult.appendText("ERRORE");
    	}
    }

    @FXML
    void doCercaItinerario(ActionEvent event) {

 
    	try {
    		 
    		int miglia = Integer.parseInt(numeroVoliTxtInput.getText());
    		Airport a = cmbBoxAeroportoPartenza.getValue();
    		
    		if (numeroVoliTxtInput.getText()=="") {
    			txtResult.appendText("Inserire un numero");
    		}
    		
    		if (a==null) {
    			txtResult.appendText("Selezionare un aeroporto");
    		}
    		
    		txtResult.appendText("Percorso:\n");
    		List<Airport> percorso = this.model.getPercorso(a,miglia);
    		
    		for (Airport a1: percorso) {
    			txtResult.appendText(a1+"\n");
    		}
    		
    		txtResult.appendText("\nTotMiglia:");
    		txtResult.appendText("\n"+this.model.getTotMiglia());
    			
    		
    		
    	} catch (NumberFormatException e ) {
    		txtResult.appendText("Inserire il numero nel formato corretto");
    	} catch( RuntimeException e ) {
    		txtResult.appendText("ERRORE");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert distanzaMinima != null : "fx:id=\"distanzaMinima\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnAnalizza != null : "fx:id=\"btnAnalizza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert cmbBoxAeroportoPartenza != null : "fx:id=\"cmbBoxAeroportoPartenza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnAeroportiConnessi != null : "fx:id=\"btnAeroportiConnessi\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert numeroVoliTxtInput != null : "fx:id=\"numeroVoliTxtInput\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnCercaItinerario != null : "fx:id=\"btnCercaItinerario\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";

    }
    
    public void setModel(Model model) {
		this.model = model;
		
	}
}

