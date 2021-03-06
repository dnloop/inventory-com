package io.github.dnloop.inventorycom.controller.main;

import io.github.dnloop.inventorycom.support.uiloader.RouteCrud;
import io.github.dnloop.inventorycom.support.uiloader.RouteInvoice;
import io.github.dnloop.inventorycom.support.uiloader.RouteMain;
import io.github.dnloop.inventorycom.support.uiloader.SceneManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class Main {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BorderPane mainPane;

    @FXML
    private MenuItem newClient;

    @FXML
    private MenuItem newSupplier;

    @FXML
    private MenuItem newProduct;

    @FXML
    private MenuItem newCategory;

    @FXML
    private MenuItem miExit;

    @FXML
    private Button btnMain;

    @FXML
    private MenuButton btnInvoice;

    @FXML
    private MenuItem miSales;

    @FXML
    private MenuItem miBuys;

    @FXML
    private Button btnClients;

    @FXML
    private Button btnSuppliers;

    @FXML
    private MenuButton btnProducts;

    @FXML
    private MenuItem miInventory;

    @FXML
    private MenuItem miCategory;

    @FXML
    void about(ActionEvent event) {
        SceneManager.loadView(RouteMain.MAIN.about());
    }

    @FXML
    void exit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void newCategory(ActionEvent event) {
        SceneManager.loadModal(RouteCrud.PRODUCT.extra(), "Categorías", true);
    }

    @FXML
    void newClient(ActionEvent event) {
        SceneManager.loadModal(RouteCrud.CLIENT.create(), "Nuevo Cliente", true);
    }

    @FXML
    void newProduct(ActionEvent event) {
        SceneManager.loadModal(RouteCrud.PRODUCT.create(), "Nuevo Producto", true);
    }

    @FXML
    void newSupplier(ActionEvent event) {
        SceneManager.loadModal(RouteCrud.SUPPLIER.create(), "Nuevo Proveedor", true);
    }

    @FXML
    void showBuys(ActionEvent event) {
        SceneManager.loadView(RouteInvoice.BUYS.display());
    }

    @FXML
    void showCategory(ActionEvent event) {
        SceneManager.loadModal(RouteCrud.PRODUCT.extra(), "Categorías", true);
    }

    @FXML
    void showClients(ActionEvent event) {
        SceneManager.loadView(RouteCrud.CLIENT.display());
    }

    @FXML
    void showInventory(ActionEvent event) {
        SceneManager.loadView(RouteCrud.PRODUCT.display());
    }

    @FXML
    void showMain(ActionEvent event) {
        SceneManager.loadView(RouteMain.MAIN.dataEntry());
    }

    @FXML
    void showSales(ActionEvent event) {
        SceneManager.loadView(RouteInvoice.SALES.display());
    }

    @FXML
    void showSuppliers(ActionEvent event) {
        SceneManager.loadView(RouteCrud.SUPPLIER.display());
    }

    @FXML
    void initialize() {
        Platform.runLater(() -> SceneManager.loadView(
                RouteMain.MAIN.dataEntry())
        );
    }

    @Async
    public void setView(Node node) {
        mainPane.setCenter(node);
    }
}
