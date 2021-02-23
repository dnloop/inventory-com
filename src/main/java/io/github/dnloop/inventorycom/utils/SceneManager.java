package io.github.dnloop.inventorycom.utils;

import io.github.dnloop.inventorycom.controller.main.Main;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;

/**
 * Utility class for controlling navigation between Scene Nodes.
 *
 * All methods on the navigator are static to facilitate simple access from
 * anywhere in the application.
 */
public class SceneManager {

	private static final Log log = LogFactory.getLog(SceneManager.class);

	/** The main application layout controller. */
	private static Main mainController;

	/** The Low level view methods */
	public static UILoader uiLoader;

	/* The stage used by the main application layout. */
	public static Stage mainStage;

	/* The stage used by modals */
	public static Stage modalStage;

	/** Loading dialog for concurrent tasks */
	public static LoadingDialog loadingDialog;

	/**
	 * Stores the main controller for later use in navigation tasks.
	 *
	 * @param mainController the main application layout controller.
	 */
	public static void setMainController(Main mainController) {
		SceneManager.mainController = mainController;
	}

	/**
	 * Loads a node into the Pane of the main application layout. The node is
	 * previously defined by the FXMLoader.
	 *
	 * The path is the key value of a cached map object from UILoader. If The view
	 * is not in the cache it must be created.
	 *
	 * @param path - The path to the fxml layout.
	 */
	public static void loadView(String path) {
		log.info(path);
		Node node = uiLoader.getNode(path);
		if (node == null) {
			uiLoader.buildNode(path);
			node = uiLoader.getNode(path);
		}
		mainController.setView(node);
	}

	/**
	 * Loads a node into the Pane of the main application layout. The node is
	 * previously defined by the FXMLoader.
	 *
	 * The path is the key value of a cached map object from UILoader. If The view
	 * is not in the cache it must be created.
	 *
	 * @param path - The path to the fxml layout.
	 * @return node - The selected node.
	 */
	public static Node getView(String path) {
		log.info(path);
		Node node = uiLoader.getNode(path);
		if (node == null) {
			uiLoader.buildNode(path);
			node = uiLoader.getNode(path);
		}
		return node;
	}

	/**
	 * Loads a node into the Pane of the main application layout. The node is
	 * previously defined by the FXMLoader.
	 *
	 * The path is the key value of a cached map object from UILoader.
	 *
	 * @param node - The path to the fxml layout.
	 */
	public static void loadView(Node node) {
		mainController.setView(node);
	}

	/**
	 * Retrieves the node containing the modal dialog and loads it on a different
	 * stage. The boolean parameter checks wheather the modal must be assigned an
	 * owner or it is an independent window.
	 * 
	 * @param route - The path to the FXML layout.
	 * @param title - The title used on the modal.
	 * @param owner - Check if the modal has an owner.
	 */
	public static void loadModal(String route, String title, Boolean owner) {
		Parent node = (Parent) uiLoader.getNode(route);

		if (node == null) {
			uiLoader.buildNode(route);
			node = (Parent) uiLoader.getNode(route);
		}

		if (owner) {
			if (modalStage != null) {
				modalStage.setTitle(title);
				modalStage.getScene().setRoot(node);
				if (modalStage.getOwner() == null)
					modalStage.initOwner(mainStage);
			} else {
				modalStage = uiLoader.buildStage(title, node, mainStage);
			}
		} else {
			if (modalStage != null) {
				modalStage.setTitle(title);
				modalStage.getScene().setRoot(node);
			} else {
				modalStage = uiLoader.buildStage(title, node);
			}
		}
		modalStage.showAndWait();
		log.debug("Modal dialog loaded.");
	}

	/**
	 * @return The cached HashMap.
	 */
	public static HashMap<String, Pair<?, Node>> getStoredViews() {
		return uiLoader.getStoredViews();
	}

	/**
	 * @return The graphic node of the view.
	 */
	public static Node getNode(String path) {
		return uiLoader.getNode(path);
	}

	/**
	 * @param <T>  - The type of the controller for the FXML layout.
	 * @param path - The path to the FXML layout.
	 * @return The controller for the FXML layout.
	 */
	public static <T> T getController(String path) {
		return uiLoader.getController(path);
	}
}