package io.github.dnloop.inventorycom.controller.sales;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.CustomTextField;

import impl.com.calendarfx.view.NumericTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class EditDetails {

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
    private Button acceptSell;

    @FXML
    private Button cancelSell;

    @FXML
    private TableView<?> tvSProduct;

    @FXML
    private TableColumn<?, ?> colSDescription;

    @FXML
    private TableColumn<?, ?> colSPrice;

    @FXML
    private TableColumn<?, ?> colSStock;

    @FXML
    private CustomTextField txtSProduct;

    @FXML
    private Button clearSProductSearch;

    @FXML
    private Button searchSProduct;

    @FXML
    private Button addSCart;

    @FXML
    private SplitMenuButton mbSaleSurcharge;

    @FXML
    private MenuItem rvSinRecargo;

    @FXML
    private MenuItem rvPorcentual;

    @FXML
    private MenuItem rvNumerico;

    @FXML
    private SplitMenuButton mbSaleDiscount;

    @FXML
    private MenuItem dvSinDescuento;

    @FXML
    private MenuItem dvPorcentual;

    @FXML
    private MenuItem dvNumerico;

    @FXML
    private NumericTextField txtSaleDiscount;

    @FXML
    private NumericTextField txtSaleSurcharge;

    @FXML
    private TableView<?> tvSellCart;

    @FXML
    private TableColumn<?, ?> colSCDescription;

    @FXML
    private TableColumn<?, ?> colSCPrice;

    @FXML
    private TableColumn<?, ?> colSCQuantity;

    @FXML
    private TableColumn<?, ?> colSubtotal;

    @FXML
    private TableColumn<?, ?> colSCAction;

    @FXML
    private CustomTextField txtTotal;

    @FXML
    void initialize() {
	assert miSave != null : "fx:id=\"miSave\" was not injected: check your FXML file 'editDetail.fxml'.";
	assert miExit != null : "fx:id=\"miExit\" was not injected: check your FXML file 'editDetail.fxml'.";
	assert miHelp != null : "fx:id=\"miHelp\" was not injected: check your FXML file 'editDetail.fxml'.";
	assert acceptSell != null : "fx:id=\"acceptSell\" was not injected: check your FXML file 'editDetail.fxml'.";
	assert cancelSell != null : "fx:id=\"cancelSell\" was not injected: check your FXML file 'editDetail.fxml'.";
	assert tvSProduct != null : "fx:id=\"tvSProduct\" was not injected: check your FXML file 'editDetail.fxml'.";
	assert colSDescription != null : "fx:id=\"colSDescription\" was not injected: check your FXML file 'editDetail.fxml'.";
	assert colSPrice != null : "fx:id=\"colSPrice\" was not injected: check your FXML file 'editDetail.fxml'.";
	assert colSStock != null : "fx:id=\"colSStock\" was not injected: check your FXML file 'editDetail.fxml'.";
	assert txtSProduct != null : "fx:id=\"txtSProduct\" was not injected: check your FXML file 'editDetail.fxml'.";
	assert clearSProductSearch != null : "fx:id=\"clearSProductSearch\" was not injected: check your FXML file 'editDetail.fxml'.";
	assert searchSProduct != null : "fx:id=\"searchSProduct\" was not injected: check your FXML file 'editDetail.fxml'.";
	assert addSCart != null : "fx:id=\"addSCart\" was not injected: check your FXML file 'editDetail.fxml'.";
	assert mbSaleSurcharge != null : "fx:id=\"mbSaleSurcharge\" was not injected: check your FXML file 'editDetail.fxml'.";
	assert rvSinRecargo != null : "fx:id=\"rvSinRecargo\" was not injected: check your FXML file 'editDetail.fxml'.";
	assert rvPorcentual != null : "fx:id=\"rvPorcentual\" was not injected: check your FXML file 'editDetail.fxml'.";
	assert rvNumerico != null : "fx:id=\"rvNumerico\" was not injected: check your FXML file 'editDetail.fxml'.";
	assert mbSaleDiscount != null : "fx:id=\"mbSaleDiscount\" was not injected: check your FXML file 'editDetail.fxml'.";
	assert dvSinDescuento != null : "fx:id=\"dvSinDescuento\" was not injected: check your FXML file 'editDetail.fxml'.";
	assert dvPorcentual != null : "fx:id=\"dvPorcentual\" was not injected: check your FXML file 'editDetail.fxml'.";
	assert dvNumerico != null : "fx:id=\"dvNumerico\" was not injected: check your FXML file 'editDetail.fxml'.";
	assert txtSaleDiscount != null : "fx:id=\"txtSaleDiscount\" was not injected: check your FXML file 'editDetail.fxml'.";
	assert txtSaleSurcharge != null : "fx:id=\"txtSaleSurcharge\" was not injected: check your FXML file 'editDetail.fxml'.";
	assert tvSellCart != null : "fx:id=\"tvSellCart\" was not injected: check your FXML file 'editDetail.fxml'.";
	assert colSCDescription != null : "fx:id=\"colSCDescription\" was not injected: check your FXML file 'editDetail.fxml'.";
	assert colSCPrice != null : "fx:id=\"colSCPrice\" was not injected: check your FXML file 'editDetail.fxml'.";
	assert colSCQuantity != null : "fx:id=\"colSCQuantity\" was not injected: check your FXML file 'editDetail.fxml'.";
	assert colSubtotal != null : "fx:id=\"colSubtotal\" was not injected: check your FXML file 'editDetail.fxml'.";
	assert colSCAction != null : "fx:id=\"colSCAction\" was not injected: check your FXML file 'editDetail.fxml'.";
	assert txtTotal != null : "fx:id=\"txtTotal\" was not injected: check your FXML file 'editDetail.fxml'.";

    }
}
