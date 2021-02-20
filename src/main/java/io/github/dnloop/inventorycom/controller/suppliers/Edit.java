package io.github.dnloop.inventorycom.controller.suppliers;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.CustomTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

public class Edit {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private CustomTextField txtName;

    @FXML
    private TextArea txtDescription;

    @FXML
    private CustomTextField txtAddress;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSave;

    @FXML
    private CustomTextField txtCUIT;

    @FXML
    private TableView<?> tvTel;

    @FXML
    private Button btnDelTel;

    @FXML
    private Button btnEditTel;

    @FXML
    private Button btnDelAllTel;

    @FXML
    private Button btnAddTel;

    @FXML
    private CustomTextField txtMail;

    @FXML
    private ChoiceBox<?> province;

    @FXML
    private ComboBox<?> locality;

    @FXML
    private MenuItem miSave;

    @FXML
    private MenuItem miExit;

    @FXML
    private Menu miHelp;

    @FXML
    void initialize() {
	assert txtName != null : "fx:id=\"txtName\" was not injected: check your FXML file 'edit.fxml'.";
	assert txtDescription != null : "fx:id=\"txtDescription\" was not injected: check your FXML file 'edit.fxml'.";
	assert txtAddress != null : "fx:id=\"txtAddress\" was not injected: check your FXML file 'edit.fxml'.";
	assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'edit.fxml'.";
	assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'edit.fxml'.";
	assert txtCUIT != null : "fx:id=\"txtCUIT\" was not injected: check your FXML file 'edit.fxml'.";
	assert tvTel != null : "fx:id=\"tvTel\" was not injected: check your FXML file 'edit.fxml'.";
	assert btnDelTel != null : "fx:id=\"btnDelTel\" was not injected: check your FXML file 'edit.fxml'.";
	assert btnEditTel != null : "fx:id=\"btnEditTel\" was not injected: check your FXML file 'edit.fxml'.";
	assert btnDelAllTel != null : "fx:id=\"btnDelAllTel\" was not injected: check your FXML file 'edit.fxml'.";
	assert btnAddTel != null : "fx:id=\"btnAddTel\" was not injected: check your FXML file 'edit.fxml'.";
	assert txtMail != null : "fx:id=\"txtMail\" was not injected: check your FXML file 'edit.fxml'.";
	assert province != null : "fx:id=\"province\" was not injected: check your FXML file 'edit.fxml'.";
	assert locality != null : "fx:id=\"locality\" was not injected: check your FXML file 'edit.fxml'.";
	assert miSave != null : "fx:id=\"miSave\" was not injected: check your FXML file 'edit.fxml'.";
	assert miExit != null : "fx:id=\"miExit\" was not injected: check your FXML file 'edit.fxml'.";
	assert miHelp != null : "fx:id=\"miHelp\" was not injected: check your FXML file 'edit.fxml'.";

    }
}
