package io.github.dnloop.inventorycom.controller.clients;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.textfield.CustomTextField;

import java.net.URL;
import java.util.ResourceBundle;

public class View {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private StackedBarChart<?, ?> monthlyAmmount;

    @FXML
    private LineChart<?, ?> monthlyTrend;

    @FXML
    private PieChart allTimeProducts;

    @FXML
    private LineChart<?, ?> allTimeSells;

    @FXML
    private TableView<?> tvHistory;

    @FXML
    private TableView indexPay;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colProduct;

    @FXML
    private TableColumn<?, ?> colAmmount;

    @FXML
    private TableColumn tcUnitPrice;

    @FXML
    private TableColumn tcDate;

    @FXML
    private TableColumn tcDescripcion;

    @FXML
    private TableColumn tcQuantity;

    @FXML
    private TableColumn tcPayAmount;

    @FXML
    private TableColumn tcPayDate;

    @FXML
    private TableView indexCA;

    @FXML
    private Pagination pgHistory;

    @FXML
    private Pagination tablePagination;

    @FXML
    private Button btnDefault;

    @FXML
    private AnchorPane currentAccount;

    @FXML
    private TitledPane tpCA;

    @FXML
    private TitledPane tpPay;

    @FXML
    private CustomTextField txtSearch;

    @FXML
    private JFXTextField txtFilter;

    @FXML
    private JFXTextField txtPay;

    @FXML
    private JFXTextField txtTotal;

    @FXML
    private JFXTextField txtSubCA;

    @FXML
    private JFXTextField txtSubPay;

    @FXML
    private Button btnClearSearch;

    @FXML
    private Button btnSearch;

    @FXML
    private JFXButton btnEdit;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnPay;

    @FXML
    private JFXButton btnCancelDebt;

    @FXML
    private TableView<?> tvClient;

    @FXML
    private TableColumn<?, ?> colLatName;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private Pagination pgClient;

    @FXML
    private CustomTextField txtAddress;

    @FXML
    private CustomTextField txtDNI;

    @FXML
    private CustomTextField txtCUIT;

    @FXML
    private CustomTextField txtLocality;


    @FXML
    private Tooltip ttProvince;

    @FXML
    private CustomTextField txtMail;

    @FXML
    private TableView<?> tvPhone;

    @FXML
    private TableColumn<?, ?> colNumber;

    @FXML
    private MenuItem miNew;

    @FXML
    private MenuItem miEdit;

    @FXML
    private Button btnHelp;

    @FXML
    private JFXComboBox comboType;

    @FXML
    private Accordion details;

    @FXML
    void clearSearch(ActionEvent event) {

    }

    @FXML
    void editClient(ActionEvent event) {

    }

    @FXML
    void newClient(ActionEvent event) {

    }

    @FXML
    void queryDate(ActionEvent event) {

    }

    @FXML
    void resetDate(ActionEvent event) {

    }

    @FXML
    void search(ActionEvent event) {

    }

    @FXML
    void showHelp(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert monthlyAmmount != null : "fx:id=\"monthlyAmmount\" was not injected: check your FXML file 'view.fxml'.";
        assert monthlyTrend != null : "fx:id=\"monthlyTrend\" was not injected: check your FXML file 'view.fxml'.";
        assert allTimeProducts !=
               null : "fx:id=\"allTimeProducts\" was not injected: check your FXML file 'view.fxml'.";
        assert allTimeSells != null : "fx:id=\"allTimeSells\" was not injected: check your FXML file 'view.fxml'.";
        assert tvHistory != null : "fx:id=\"tvHistory\" was not injected: check your FXML file 'view.fxml'.";
        assert colDate != null : "fx:id=\"colDate\" was not injected: check your FXML file 'view.fxml'.";
        assert colProduct != null : "fx:id=\"colProduct\" was not injected: check your FXML file 'view.fxml'.";
        assert colAmmount != null : "fx:id=\"colAmmount\" was not injected: check your FXML file 'view.fxml'.";
        assert pgHistory != null : "fx:id=\"pgHistory\" was not injected: check your FXML file 'view.fxml'.";
        assert btnDefault != null : "fx:id=\"btnDefault\" was not injected: check your FXML file 'view.fxml'.";
        assert currentAccount != null : "fx:id=\"currentAccount\" was not injected: check your FXML file 'view.fxml'.";
        assert txtSearch != null : "fx:id=\"txtSearch\" was not injected: check your FXML file 'view.fxml'.";
        assert btnClearSearch != null : "fx:id=\"btnClearSearch\" was not injected: check your FXML file 'view.fxml'.";
        assert btnSearch != null : "fx:id=\"btnSearch\" was not injected: check your FXML file 'view.fxml'.";
        assert tvClient != null : "fx:id=\"tvClient\" was not injected: check your FXML file 'view.fxml'.";
        assert colLatName != null : "fx:id=\"colLatName\" was not injected: check your FXML file 'view.fxml'.";
        assert colName != null : "fx:id=\"colName\" was not injected: check your FXML file 'view.fxml'.";
        assert pgClient != null : "fx:id=\"pgClient\" was not injected: check your FXML file 'view.fxml'.";
        assert txtAddress != null : "fx:id=\"txtAddress\" was not injected: check your FXML file 'view.fxml'.";
        assert txtDNI != null : "fx:id=\"txtDNI\" was not injected: check your FXML file 'view.fxml'.";
        assert txtCUIT != null : "fx:id=\"txtCUIT\" was not injected: check your FXML file 'view.fxml'.";
        assert txtLocality != null : "fx:id=\"txtLocality\" was not injected: check your FXML file 'view.fxml'.";
        assert ttProvince != null : "fx:id=\"ttProvince\" was not injected: check your FXML file 'view.fxml'.";
        assert txtMail != null : "fx:id=\"txtMail\" was not injected: check your FXML file 'view.fxml'.";
        assert tvPhone != null : "fx:id=\"tvPhone\" was not injected: check your FXML file 'view.fxml'.";
        assert colNumber != null : "fx:id=\"colNumber\" was not injected: check your FXML file 'view.fxml'.";
        assert miNew != null : "fx:id=\"miNew\" was not injected: check your FXML file 'view.fxml'.";
        assert miEdit != null : "fx:id=\"miEdit\" was not injected: check your FXML file 'view.fxml'.";
        assert btnHelp != null : "fx:id=\"btnHelp\" was not injected: check your FXML file 'view.fxml'.";

    }
}
