package io.github.dnloop.inventorycom.controller.products;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.CustomTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;

public class View {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private CustomTextField txtSearch;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnSearch;

    @FXML
    private ListView<?> lvProducts;

    @FXML
    private CustomTextField txtStock;

    @FXML
    private CustomTextField txtPrice;

    @FXML
    private CustomTextField txtCategory;

    @FXML
    private BarChart<?, ?> bcSells;

    @FXML
    private LineChart<?, ?> lcSells;

    @FXML
    private BarChart<?, ?> bcBuys;

    @FXML
    private LineChart<?, ?> lcBuys;

    @FXML
    private PieChart totalPieChart;

    @FXML
    private StackedBarChart<?, ?> totalBarChart;

    @FXML
    private MenuItem miNew;

    @FXML
    private MenuItem miEdit;

    @FXML
    private Button btnHelp;

    @FXML
    void clear(ActionEvent event) {

    }

    @FXML
    void editProduct(ActionEvent event) {

    }

    @FXML
    void newProduct(ActionEvent event) {

    }

    @FXML
    void search(ActionEvent event) {

    }

    @FXML
    void showHelp(ActionEvent event) {

    }

    @FXML
    void initialize() {
	assert txtSearch != null : "fx:id=\"txtSearch\" was not injected: check your FXML file 'view.fxml'.";
	assert btnClear != null : "fx:id=\"btnClear\" was not injected: check your FXML file 'view.fxml'.";
	assert btnSearch != null : "fx:id=\"btnSearch\" was not injected: check your FXML file 'view.fxml'.";
	assert lvProducts != null : "fx:id=\"lvProducts\" was not injected: check your FXML file 'view.fxml'.";
	assert txtStock != null : "fx:id=\"txtStock\" was not injected: check your FXML file 'view.fxml'.";
	assert txtPrice != null : "fx:id=\"txtPrice\" was not injected: check your FXML file 'view.fxml'.";
	assert txtCategory != null : "fx:id=\"txtCategory\" was not injected: check your FXML file 'view.fxml'.";
	assert bcSells != null : "fx:id=\"bcSells\" was not injected: check your FXML file 'view.fxml'.";
	assert lcSells != null : "fx:id=\"lcSells\" was not injected: check your FXML file 'view.fxml'.";
	assert bcBuys != null : "fx:id=\"bcBuys\" was not injected: check your FXML file 'view.fxml'.";
	assert lcBuys != null : "fx:id=\"lcBuys\" was not injected: check your FXML file 'view.fxml'.";
	assert totalPieChart != null : "fx:id=\"totalPieChart\" was not injected: check your FXML file 'view.fxml'.";
	assert totalBarChart != null : "fx:id=\"totalBarChart\" was not injected: check your FXML file 'view.fxml'.";
	assert miNew != null : "fx:id=\"miNew\" was not injected: check your FXML file 'view.fxml'.";
	assert miEdit != null : "fx:id=\"miEdit\" was not injected: check your FXML file 'view.fxml'.";
	assert btnHelp != null : "fx:id=\"btnHelp\" was not injected: check your FXML file 'view.fxml'.";

    }
}
