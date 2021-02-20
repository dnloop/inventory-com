package io.github.dnloop.inventorycom.controller.products;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.CustomTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Category {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private CustomTextField txtSearch;

    @FXML
    private Button btnSearch;

    @FXML
    private CustomTextField txtAdd;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDel;

    @FXML
    private Button btnSave;

    @FXML
    void add(ActionEvent event) {

    }

    @FXML
    void remove(ActionEvent event) {

    }

    @FXML
    void save(ActionEvent event) {

    }

    @FXML
    void search(ActionEvent event) {

    }

    @FXML
    void initialize() {
	assert txtSearch != null : "fx:id=\"txtSearch\" was not injected: check your FXML file 'category.fxml'.";
	assert btnSearch != null : "fx:id=\"btnSearch\" was not injected: check your FXML file 'category.fxml'.";
	assert txtAdd != null : "fx:id=\"txtAdd\" was not injected: check your FXML file 'category.fxml'.";
	assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'category.fxml'.";
	assert btnDel != null : "fx:id=\"btnDel\" was not injected: check your FXML file 'category.fxml'.";
	assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'category.fxml'.";

    }
}
