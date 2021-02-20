package io.github.dnloop.inventorycom.controller.main;

import impl.com.calendarfx.view.NumericTextField;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.controlsfx.control.textfield.CustomTextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DataEntry {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TabPane mainView;

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
    private CustomTextField ctf_search_client;

    @FXML
    private Button clearSProductSearch;

    @FXML
    private Button searchSProduct;

    @FXML
    private Button addSCart;

    @FXML
    private Button btn_search_client;

    @FXML
    private TableView<?> tvClient;

    @FXML
    private TableColumn<?, ?> colLastName;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn colSubtotal;

    @FXML
    private Button searchSClient;

    @FXML
    private Button clearSearchSClient;

    @FXML
    private Button delClient;

    @FXML
    private Button addClient;

    @FXML
    private NumericTextField saleInvoiceNumber;

    @FXML
    private DatePicker saleEmisionDate;

    @FXML
    private ChoiceBox<?> saleInvoiceType;

    @FXML
    private ChoiceBox<?> salePaymentMethod;

    @FXML
    private Spinner<?> saleShare;

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
    private TableColumn<?, ?> colSCAction;

    @FXML
    private TextField txtClientName;

    @FXML
    private NumericTextField sellTotalShares;

    @FXML
    private NumericTextField sellShareAmount;

    @FXML
    private NumericTextField sellSubtotal;

    @FXML
    private NumericTextField sellTotal;

    @FXML
    private Button acceptSell;

    @FXML
    private Button cancelSell;

    @FXML
    private TableView<?> tvBProduct;

    @FXML
    private TableColumn<?, ?> colBDescription;

    @FXML
    private TableColumn<?, ?> colBPrice;

    @FXML
    private TableColumn<?, ?> colBStock;

    @FXML
    private CustomTextField txtBProduct;

    @FXML
    private Button clearBProductSearch;

    @FXML
    private Button searchBProduct;

    @FXML
    private Button addBCart;

    @FXML
    private TableView<?> tvSupplier;

    @FXML
    private TableColumn<?, ?> colSupplierName;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private CustomTextField txtSupplier;

    @FXML
    private Button clearBProductSearch1;

    @FXML
    private Button searchBProduct1;

    @FXML
    private Button delSupplier;

    @FXML
    private Button addSupplier;

    @FXML
    private NumericTextField buyInvoiceNumber;

    @FXML
    private DatePicker buyDate;

    @FXML
    private ChoiceBox<?> buyInvoiceType;

    @FXML
    private ChoiceBox<?> buyInvoiceMethod;

    @FXML
    private Spinner<?> buyShares;

    @FXML
    private SplitMenuButton buySurcharge;

    @FXML
    private MenuItem bcNoSurcharge;

    @FXML
    private MenuItem bcPercentage;

    @FXML
    private MenuItem bcNumeric;

    @FXML
    private SplitMenuButton buyDiscount;

    @FXML
    private MenuItem dcSinRecargo;

    @FXML
    private MenuItem dcPorcentual;

    @FXML
    private MenuItem dcNumerico;

    @FXML
    private NumericTextField txtBuySurcharge;

    @FXML
    private NumericTextField txtBuyDiscount;

    @FXML
    private TableView<?> tvBCart;

    @FXML
    private TableColumn<?, ?> colBCDescription;

    @FXML
    private TableColumn<?, ?> colBCPrice;

    @FXML
    private TableColumn<?, ?> colBCQuantity;

    @FXML
    private TableColumn<?, ?> colBCAction;

    @FXML
    private TextField txtSupplierName;

    @FXML
    private NumericTextField txtBTotalShares;

    @FXML
    private NumericTextField txtBShareAmount;

    @FXML
    private NumericTextField txtBSubtotal;

    @FXML
    private NumericTextField txtBTotal;

    @FXML
    private Button acceptBuy;

    @FXML
    private Button cancelBuy;

    @FXML
    void initialize() {
        assert mainView != null : "fx:id=\"mainView\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert tvSProduct != null : "fx:id=\"tvSProduct\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert colSDescription !=
               null : "fx:id=\"colSDescription\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert colSPrice != null : "fx:id=\"colSPrice\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert colSStock != null : "fx:id=\"colSStock\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert txtSProduct != null : "fx:id=\"txtSProduct\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert clearSProductSearch !=
               null : "fx:id=\"clearSProductSearch\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert searchSProduct !=
               null : "fx:id=\"searchSProduct\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert addSCart != null : "fx:id=\"addSCart\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert tvClient != null : "fx:id=\"tvClient\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert colLastName != null : "fx:id=\"colLastName\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert colName != null : "fx:id=\"colName\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert searchSClient !=
               null : "fx:id=\"searchSClient\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert clearSearchSClient !=
               null : "fx:id=\"clearSearchSClient\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert delClient != null : "fx:id=\"delClient\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert addClient != null : "fx:id=\"addClient\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert saleInvoiceNumber !=
               null : "fx:id=\"saleInvoiceNumber\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert saleEmisionDate !=
               null : "fx:id=\"saleEmisionDate\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert saleInvoiceType !=
               null : "fx:id=\"saleInvoiceType\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert salePaymentMethod !=
               null : "fx:id=\"salePaymentMethod\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert saleShare != null : "fx:id=\"saleShare\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert mbSaleSurcharge !=
               null : "fx:id=\"mbSaleSurcharge\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert rvSinRecargo != null : "fx:id=\"rvSinRecargo\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert rvPorcentual != null : "fx:id=\"rvPorcentual\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert rvNumerico != null : "fx:id=\"rvNumerico\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert mbSaleDiscount !=
               null : "fx:id=\"mbSaleDiscount\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert dvSinDescuento !=
               null : "fx:id=\"dvSinDescuento\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert dvPorcentual != null : "fx:id=\"dvPorcentual\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert dvNumerico != null : "fx:id=\"dvNumerico\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert txtSaleDiscount !=
               null : "fx:id=\"txtSaleDiscount\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert txtSaleSurcharge !=
               null : "fx:id=\"txtSaleSurcharge\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert tvSellCart != null : "fx:id=\"tvSellCart\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert colSCDescription !=
               null : "fx:id=\"colSCDescription\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert colSCPrice != null : "fx:id=\"colSCPrice\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert colSCQuantity !=
               null : "fx:id=\"colSCQuantity\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert colSCAction != null : "fx:id=\"colSCAction\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert txtClientName !=
               null : "fx:id=\"txtClientName\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert sellTotalShares !=
               null : "fx:id=\"sellTotalShares\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert sellShareAmount !=
               null : "fx:id=\"sellShareAmount\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert sellSubtotal != null : "fx:id=\"sellSubtotal\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert sellTotal != null : "fx:id=\"sellTotal\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert acceptSell != null : "fx:id=\"acceptSell\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert cancelSell != null : "fx:id=\"cancelSell\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert tvBProduct != null : "fx:id=\"tvBProduct\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert colBDescription !=
               null : "fx:id=\"colBDescription\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert colBPrice != null : "fx:id=\"colBPrice\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert colBStock != null : "fx:id=\"colBStock\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert txtBProduct != null : "fx:id=\"txtBProduct\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert clearBProductSearch !=
               null : "fx:id=\"clearBProductSearch\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert searchBProduct !=
               null : "fx:id=\"searchBProduct\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert addBCart != null : "fx:id=\"addBCart\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert tvSupplier != null : "fx:id=\"tvSupplier\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert colSupplierName !=
               null : "fx:id=\"colSupplierName\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert colDescription !=
               null : "fx:id=\"colDescription\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert txtSupplier != null : "fx:id=\"txtSupplier\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert clearBProductSearch1 !=
               null : "fx:id=\"clearBProductSearch1\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert searchBProduct1 !=
               null : "fx:id=\"searchBProduct1\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert delSupplier != null : "fx:id=\"delSupplier\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert addSupplier != null : "fx:id=\"addSupplier\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert buyInvoiceNumber !=
               null : "fx:id=\"buyInvoiceNumber\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert buyDate != null : "fx:id=\"buyDate\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert buyInvoiceType !=
               null : "fx:id=\"buyInvoiceType\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert buyInvoiceMethod !=
               null : "fx:id=\"buyInvoiceMethod\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert buyShares != null : "fx:id=\"buyShares\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert buySurcharge != null : "fx:id=\"buySurcharge\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert bcNoSurcharge !=
               null : "fx:id=\"bcNoSurcharge\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert bcPercentage != null : "fx:id=\"bcPercentage\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert bcNumeric != null : "fx:id=\"bcNumeric\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert buyDiscount != null : "fx:id=\"buyDiscount\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert dcSinRecargo != null : "fx:id=\"dcSinRecargo\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert dcPorcentual != null : "fx:id=\"dcPorcentual\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert dcNumerico != null : "fx:id=\"dcNumerico\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert txtBuySurcharge !=
               null : "fx:id=\"txtBuySurcharge\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert txtBuyDiscount !=
               null : "fx:id=\"txtBuyDiscount\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert tvBCart != null : "fx:id=\"tvBCart\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert colBCDescription !=
               null : "fx:id=\"colBCDescription\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert colBCPrice != null : "fx:id=\"colBCPrice\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert colBCQuantity !=
               null : "fx:id=\"colBCQuantity\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert colBCAction != null : "fx:id=\"colBCAction\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert txtSupplierName !=
               null : "fx:id=\"txtSupplierName\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert txtBTotalShares !=
               null : "fx:id=\"txtBTotalShares\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert txtBShareAmount !=
               null : "fx:id=\"txtBShareAmount\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert txtBSubtotal != null : "fx:id=\"txtBSubtotal\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert txtBTotal != null : "fx:id=\"txtBTotal\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert acceptBuy != null : "fx:id=\"acceptBuy\" was not injected: check your FXML file 'dataEntry.fxml'.";
        assert cancelBuy != null : "fx:id=\"cancelBuy\" was not injected: check your FXML file 'dataEntry.fxml'.";

    }
}
