package io.github.dnloop.inventorycom.controller.main;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextFlow;

public class AboutApp {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView ivLogo;

    @FXML
    private TextFlow tfAbout;

    @FXML
    void initialize() {
	assert ivLogo != null : "fx:id=\"ivLogo\" was not injected: check your FXML file 'Untitled'.";
	assert tfAbout != null : "fx:id=\"tfAbout\" was not injected: check your FXML file 'Untitled'.";

    }
}
