/*
 *     Inventory-Com: Inventory and Commerce Management Application.
 *     Copyright (C) 2021. dnloop.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package io.github.dnloop.inventorycom.support.uiloader;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;

/**
 * The UILoader class builds the fxml views to be used in the
 * {@link SceneManager}.
 */
public class UILoader {
    private static final Log log = LogFactory.getLog(UILoader.class);

    private FXMLLoader loader;

    private Node node;

    private Stage stage;

    private Scene scene;

    private Pair<?, Node> pair;

    private final HashMap<String, Pair<?, Node>> storedViews = new HashMap<>();

    public UILoader() {
        super();
    }

    /**
     * Constructs a pair of a controller and a node to be used in the stored map of
     * views.
     *
     * @param route - The path to the FXML layout.
     */
    public <T> void buildNode(String route) {
        loader = new FXMLLoader(getClass().getResource(route));
        try {
            node = loader.load();
            pair = new Pair<T, Node>(loader.getController(), node);
            storedViews.put(route, pair);
        } catch (IOException e) {
            log.error("Node building failed: " + e.getCause());
            e.printStackTrace();
        }
    }

    /**
     * Builds an independent modal dialog, without an owner.
     */
    public Stage buildStage(String title, Parent node) {
        stage = new Stage();
        stage.initStyle(StageStyle.UTILITY);
        stage.initModality(Modality.APPLICATION_MODAL);
        scene = new Scene(node);
        stage.setScene(scene);
        return stage;
    } // used on application start

    /**
     * Builds a modal dialog with an initializer owner.
     *
     * @param node  - The path to the FXML layout.
     * @param title - The modal window title.
     * @param owner - The source stage that called the modal.
     */
    public Stage buildStage(String title, Parent node, Stage owner) {
        stage = new Stage();
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(owner);
        scene = new Scene(node);
        stage.setScene(scene);
        return stage;

    } // used on edit/new view

    /**
     * Creates the main application scene.
     *
     * @return the created scene.
     */
    public <T> Scene createMainPane() {
        String route = RouteMain.MAIN.mainGUI();
        loader = new FXMLLoader(getClass().getResource(route));
        try {
            Parent node = loader.load();
            scene = new Scene(node, 1024, 700);
            pair = new Pair<T, Node>(loader.getController(), node);
            storedViews.put(route, pair);
        } catch (IOException e) {
            log.error("Main Application Node building failed: " + e.getCause());
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            log.debug(sw.toString());
        }
        return scene;
    }

    /**
     * Creates the main application LoadingDialog.
     *
     * @return The created loading dialog.
     */
    public <T> T createLoadingDialog() {
        String route = Route.LOADING.display();
        loader = new FXMLLoader(getClass().getResource(route));
        T controller = null;
        try {
            Node node = loader.load();
            controller = loader.getController();
            pair = new Pair<>(controller, node);
            storedViews.put(route, pair);
        } catch (IOException e) {
            log.error("Loading Dialog Node building failed: " + e.getCause());
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            log.debug(sw.toString());
        }
        return controller;
    }

    /**
     * @return The graphic node of the view.
     */
    public Node getNode(String path) {
        Pair<?, Node> pair = storedViews.get(path);
        if (pair == null)
            return null;
        else
            return storedViews.get(path).getValue();
    }

    /**
     * @param <T>  - The type of the controller for the FXML layout.
     * @param path - The path to the FXML layout.
     * @return The controller for the FXML layout.
     */
    @SuppressWarnings("unchecked")
    public <T> T getController(String path) {
        return (T) storedViews.get(path).getKey();
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public HashMap<String, Pair<?, Node>> getStoredViews() {
        return storedViews;
    }
}
