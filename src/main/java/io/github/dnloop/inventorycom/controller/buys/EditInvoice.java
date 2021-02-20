package io.github.dnloop.inventorycom.controller.buys;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.CustomTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;

public class EditInvoice {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private CustomTextField txtInvoiceNumber;

    @FXML
    private ChoiceBox<?> cbPaymentMethod;

    @FXML
    private Spinner<?> spShare;

    @FXML
    private ChoiceBox<?> saleInvoiceType;

    @FXML
    private DatePicker dpEmissionDate;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSave;

    @FXML
    private MenuItem miSave;

    @FXML
    private MenuItem miExit;

    @FXML
    private Menu miHelp;

    @FXML
    void initialize() {
	assert txtInvoiceNumber != null : "fx:id=\"txtInvoiceNumber\" was not injected: check your FXML file 'editInvoice.fxml'.";
	assert cbPaymentMethod != null : "fx:id=\"cbPaymentMethod\" was not injected: check your FXML file 'editInvoice.fxml'.";
	assert spShare != null : "fx:id=\"spShare\" was not injected: check your FXML file 'editInvoice.fxml'.";
	assert saleInvoiceType != null : "fx:id=\"saleInvoiceType\" was not injected: check your FXML file 'editInvoice.fxml'.";
	assert dpEmissionDate != null : "fx:id=\"dpEmissionDate\" was not injected: check your FXML file 'editInvoice.fxml'.";
	assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'editInvoice.fxml'.";
	assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'editInvoice.fxml'.";
	assert miSave != null : "fx:id=\"miSave\" was not injected: check your FXML file 'editInvoice.fxml'.";
	assert miExit != null : "fx:id=\"miExit\" was not injected: check your FXML file 'editInvoice.fxml'.";
	assert miHelp != null : "fx:id=\"miHelp\" was not injected: check your FXML file 'editInvoice.fxml'.";

    }
}
