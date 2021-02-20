package io.github.dnloop.inventorycom.controller.suppliers;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.CustomTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;

public class View {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuItem miNew;

    @FXML
    private MenuItem miExit;

    @FXML
    private Menu miHelp;

    @FXML
    private BarChart<?, ?> monthlyAmount;

    @FXML
    private LineChart<?, ?> monthlyTrend;

    @FXML
    private PieChart allTimeProducts;

    @FXML
    private LineChart<?, ?> allTimeBuys;

    @FXML
    private TableView<?> tvHistory;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colProduct;

    @FXML
    private TableColumn<?, ?> colAmmount;

    @FXML
    private Pagination pgHistory;

    @FXML
    private DatePicker historyFrom;

    @FXML
    private DatePicker historyTo;

    @FXML
    private Button btnDefault;

    @FXML
    private CustomTextField txtSearch;

    @FXML
    private Button btnClearSearch;

    @FXML
    private Button btnSearch;

    @FXML
    private TableView<?> tvClient;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private Pagination pgClient;

    @FXML
    private CustomTextField txtAddress;

    @FXML
    private CustomTextField txtCUIT;

    @FXML
    private CustomTextField txtLocality;

    @FXML
    private Tooltip ttProvince;

    @FXML
    private CustomTextField txtDescription;

    @FXML
    private CustomTextField txtMail;

    @FXML
    private TableView<?> tvPhone;

    @FXML
    private TableColumn<?, ?> colNumber;

    @FXML
    void clearSearch(ActionEvent event) {

    }

    @FXML
    void queryHistory(ActionEvent event) {

    }

    @FXML
    void resetHistory(ActionEvent event) {

    }

    @FXML
    void search(ActionEvent event) {

    }

    @FXML
    void initialize() {
	assert miNew != null : "fx:id=\"miNew\" was not injected: check your FXML file 'view.fxml'.";
	assert miExit != null : "fx:id=\"miExit\" was not injected: check your FXML file 'view.fxml'.";
	assert miHelp != null : "fx:id=\"miHelp\" was not injected: check your FXML file 'view.fxml'.";
	assert monthlyAmount != null : "fx:id=\"monthlyAmount\" was not injected: check your FXML file 'view.fxml'.";
	assert monthlyTrend != null : "fx:id=\"monthlyTrend\" was not injected: check your FXML file 'view.fxml'.";
	assert allTimeProducts != null : "fx:id=\"allTimeProducts\" was not injected: check your FXML file 'view.fxml'.";
	assert allTimeBuys != null : "fx:id=\"allTimeBuys\" was not injected: check your FXML file 'view.fxml'.";
	assert tvHistory != null : "fx:id=\"tvHistory\" was not injected: check your FXML file 'view.fxml'.";
	assert colDate != null : "fx:id=\"colDate\" was not injected: check your FXML file 'view.fxml'.";
	assert colProduct != null : "fx:id=\"colProduct\" was not injected: check your FXML file 'view.fxml'.";
	assert colAmmount != null : "fx:id=\"colAmmount\" was not injected: check your FXML file 'view.fxml'.";
	assert pgHistory != null : "fx:id=\"pgHistory\" was not injected: check your FXML file 'view.fxml'.";
	assert historyFrom != null : "fx:id=\"historyFrom\" was not injected: check your FXML file 'view.fxml'.";
	assert historyTo != null : "fx:id=\"historyTo\" was not injected: check your FXML file 'view.fxml'.";
	assert btnDefault != null : "fx:id=\"btnDefault\" was not injected: check your FXML file 'view.fxml'.";
	assert txtSearch != null : "fx:id=\"txtSearch\" was not injected: check your FXML file 'view.fxml'.";
	assert btnClearSearch != null : "fx:id=\"btnClearSearch\" was not injected: check your FXML file 'view.fxml'.";
	assert btnSearch != null : "fx:id=\"btnSearch\" was not injected: check your FXML file 'view.fxml'.";
	assert tvClient != null : "fx:id=\"tvClient\" was not injected: check your FXML file 'view.fxml'.";
	assert colName != null : "fx:id=\"colName\" was not injected: check your FXML file 'view.fxml'.";
	assert pgClient != null : "fx:id=\"pgClient\" was not injected: check your FXML file 'view.fxml'.";
	assert txtAddress != null : "fx:id=\"txtAddress\" was not injected: check your FXML file 'view.fxml'.";
	assert txtCUIT != null : "fx:id=\"txtCUIT\" was not injected: check your FXML file 'view.fxml'.";
	assert txtLocality != null : "fx:id=\"txtLocality\" was not injected: check your FXML file 'view.fxml'.";
	assert ttProvince != null : "fx:id=\"ttProvince\" was not injected: check your FXML file 'view.fxml'.";
	assert txtDescription != null : "fx:id=\"txtDescription\" was not injected: check your FXML file 'view.fxml'.";
	assert txtMail != null : "fx:id=\"txtMail\" was not injected: check your FXML file 'view.fxml'.";
	assert tvPhone != null : "fx:id=\"tvPhone\" was not injected: check your FXML file 'view.fxml'.";
	assert colNumber != null : "fx:id=\"colNumber\" was not injected: check your FXML file 'view.fxml'.";

    }
}
