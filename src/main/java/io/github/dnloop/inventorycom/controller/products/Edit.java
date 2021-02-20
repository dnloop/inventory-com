package io.github.dnloop.inventorycom.controller.products;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.CustomTextField;

import impl.com.calendarfx.view.NumericTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class Edit {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private CustomTextField txtDescription;

    @FXML
    private NumericTextField txtStock;

    @FXML
    private NumericTextField txtPrice;

    @FXML
    private ComboBox<?> cbCategory;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSave;

    @FXML
    void cancel(ActionEvent event) {

    }

    @FXML
    void save(ActionEvent event) {

    }

    @FXML
    void initialize() {
	assert txtDescription != null : "fx:id=\"txtDescription\" was not injected: check your FXML file 'edit.fxml'.";
	assert txtStock != null : "fx:id=\"txtStock\" was not injected: check your FXML file 'edit.fxml'.";
	assert txtPrice != null : "fx:id=\"txtPrice\" was not injected: check your FXML file 'edit.fxml'.";
	assert cbCategory != null : "fx:id=\"cbCategory\" was not injected: check your FXML file 'edit.fxml'.";
	assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'edit.fxml'.";
	assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'edit.fxml'.";

    }
}
