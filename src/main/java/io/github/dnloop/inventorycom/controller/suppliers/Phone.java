package io.github.dnloop.inventorycom.controller.suppliers;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.CustomTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;

public class Phone {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuItem miSave;

    @FXML
    private MenuItem miExit;

    @FXML
    private Menu miHelp;

    @FXML
    private TableView<?> tvPhone;

    @FXML
    private CustomTextField txtAdd;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDel;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelAll;

    @FXML
    void initialize() {
	assert miSave != null : "fx:id=\"miSave\" was not injected: check your FXML file 'tel.fxml'.";
	assert miExit != null : "fx:id=\"miExit\" was not injected: check your FXML file 'tel.fxml'.";
	assert miHelp != null : "fx:id=\"miHelp\" was not injected: check your FXML file 'tel.fxml'.";
	assert tvPhone != null : "fx:id=\"tvPhone\" was not injected: check your FXML file 'tel.fxml'.";
	assert txtAdd != null : "fx:id=\"txtAdd\" was not injected: check your FXML file 'tel.fxml'.";
	assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'tel.fxml'.";
	assert btnDel != null : "fx:id=\"btnDel\" was not injected: check your FXML file 'tel.fxml'.";
	assert btnEdit != null : "fx:id=\"btnEdit\" was not injected: check your FXML file 'tel.fxml'.";
	assert btnDelAll != null : "fx:id=\"btnDelAll\" was not injected: check your FXML file 'tel.fxml'.";

    }
}
