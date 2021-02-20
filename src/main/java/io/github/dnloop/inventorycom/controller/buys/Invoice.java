package io.github.dnloop.inventorycom.controller.buys;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.CustomTextField;

import impl.com.calendarfx.view.NumericTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class Invoice {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtClient;

    @FXML
    private TextField txtNumber;

    @FXML
    private TextField txtIssueDate;

    @FXML
    private TextField txtSurcharge;

    @FXML
    private TextField txtDiscount;

    @FXML
    private TextField txtType;

    @FXML
    private TableView<?> tvDetail;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colQuantity;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colIVA;

    @FXML
    private TableColumn<?, ?> colSubtotal;

    @FXML
    private TableView<?> tvFee;

    @FXML
    private TableColumn<?, ?> colNum;

    @FXML
    private TableColumn<?, ?> colExpiry;

    @FXML
    private TableColumn<?, ?> colPay;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private Button btnFirst;

    @FXML
    private Button btnPrev;

    @FXML
    private Button btnNext;

    @FXML
    private Button btnLast;

    @FXML
    private NumericTextField ntxtShare;

    @FXML
    private NumericTextField ntxtShareTotal;

    @FXML
    private ChoiceBox<?> cbPaymentMethod;

    @FXML
    private Button btnAccept;

    @FXML
    private MenuButton mbOption;

    @FXML
    private MenuItem miSaleDetail;

    @FXML
    private MenuItem miInvoiceDetail;

    @FXML
    private MenuItem miShare;

    @FXML
    private CustomTextField txtTotal;

    @FXML
    private CustomTextField txtSearch;

    @FXML
    private Button btnClearSearch;

    @FXML
    private Button btnSearch;

    @FXML
    private TableView<?> tvClient;

    @FXML
    private TableColumn<?, ?> colLatName;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private Pagination pgClient;

    @FXML
    void initialize() {
	assert txtClient != null : "fx:id=\"txtClient\" was not injected: check your FXML file 'invoice.fxml'.";
	assert txtNumber != null : "fx:id=\"txtNumber\" was not injected: check your FXML file 'invoice.fxml'.";
	assert txtIssueDate != null : "fx:id=\"txtIssueDate\" was not injected: check your FXML file 'invoice.fxml'.";
	assert txtSurcharge != null : "fx:id=\"txtSurcharge\" was not injected: check your FXML file 'invoice.fxml'.";
	assert txtDiscount != null : "fx:id=\"txtDiscount\" was not injected: check your FXML file 'invoice.fxml'.";
	assert txtType != null : "fx:id=\"txtType\" was not injected: check your FXML file 'invoice.fxml'.";
	assert tvDetail != null : "fx:id=\"tvDetail\" was not injected: check your FXML file 'invoice.fxml'.";
	assert colDescription != null : "fx:id=\"colDescription\" was not injected: check your FXML file 'invoice.fxml'.";
	assert colQuantity != null : "fx:id=\"colQuantity\" was not injected: check your FXML file 'invoice.fxml'.";
	assert colPrice != null : "fx:id=\"colPrice\" was not injected: check your FXML file 'invoice.fxml'.";
	assert colIVA != null : "fx:id=\"colIVA\" was not injected: check your FXML file 'invoice.fxml'.";
	assert colSubtotal != null : "fx:id=\"colSubtotal\" was not injected: check your FXML file 'invoice.fxml'.";
	assert tvFee != null : "fx:id=\"tvFee\" was not injected: check your FXML file 'invoice.fxml'.";
	assert colNum != null : "fx:id=\"colNum\" was not injected: check your FXML file 'invoice.fxml'.";
	assert colExpiry != null : "fx:id=\"colExpiry\" was not injected: check your FXML file 'invoice.fxml'.";
	assert colPay != null : "fx:id=\"colPay\" was not injected: check your FXML file 'invoice.fxml'.";
	assert colAmount != null : "fx:id=\"colAmount\" was not injected: check your FXML file 'invoice.fxml'.";
	assert colAction != null : "fx:id=\"colAction\" was not injected: check your FXML file 'invoice.fxml'.";
	assert btnFirst != null : "fx:id=\"btnFirst\" was not injected: check your FXML file 'invoice.fxml'.";
	assert btnPrev != null : "fx:id=\"btnPrev\" was not injected: check your FXML file 'invoice.fxml'.";
	assert btnNext != null : "fx:id=\"btnNext\" was not injected: check your FXML file 'invoice.fxml'.";
	assert btnLast != null : "fx:id=\"btnLast\" was not injected: check your FXML file 'invoice.fxml'.";
	assert ntxtShare != null : "fx:id=\"ntxtShare\" was not injected: check your FXML file 'invoice.fxml'.";
	assert ntxtShareTotal != null : "fx:id=\"ntxtShareTotal\" was not injected: check your FXML file 'invoice.fxml'.";
	assert cbPaymentMethod != null : "fx:id=\"cbPaymentMethod\" was not injected: check your FXML file 'invoice.fxml'.";
	assert btnAccept != null : "fx:id=\"btnAccept\" was not injected: check your FXML file 'invoice.fxml'.";
	assert mbOption != null : "fx:id=\"mbOption\" was not injected: check your FXML file 'invoice.fxml'.";
	assert miSaleDetail != null : "fx:id=\"miSaleDetail\" was not injected: check your FXML file 'invoice.fxml'.";
	assert miInvoiceDetail != null : "fx:id=\"miInvoiceDetail\" was not injected: check your FXML file 'invoice.fxml'.";
	assert miShare != null : "fx:id=\"miShare\" was not injected: check your FXML file 'invoice.fxml'.";
	assert txtTotal != null : "fx:id=\"txtTotal\" was not injected: check your FXML file 'invoice.fxml'.";
	assert txtSearch != null : "fx:id=\"txtSearch\" was not injected: check your FXML file 'invoice.fxml'.";
	assert btnClearSearch != null : "fx:id=\"btnClearSearch\" was not injected: check your FXML file 'invoice.fxml'.";
	assert btnSearch != null : "fx:id=\"btnSearch\" was not injected: check your FXML file 'invoice.fxml'.";
	assert tvClient != null : "fx:id=\"tvClient\" was not injected: check your FXML file 'invoice.fxml'.";
	assert colLatName != null : "fx:id=\"colLatName\" was not injected: check your FXML file 'invoice.fxml'.";
	assert colName != null : "fx:id=\"colName\" was not injected: check your FXML file 'invoice.fxml'.";
	assert pgClient != null : "fx:id=\"pgClient\" was not injected: check your FXML file 'invoice.fxml'.";

    }
}
