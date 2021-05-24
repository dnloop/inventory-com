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

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class DialogBox {

    private static String header;

    private static String content;

    public DialogBox() {
    }

    public static boolean confirmDialog(String content) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("Confirmar acción.");
        alert.setContentText(content);
        alert.setResizable(true);

        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    public static void displayWarning() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Advertencia.");
        alert.setHeaderText("Elemento vacío.");
        alert.setContentText("No se seleccionó ningún elemento de la lista. Elija un ítem e intente nuevamente.");
        alert.setResizable(true);

        alert.showAndWait();
    }

    public static void displayCustomWarning() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Advertencia.");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.setResizable(true);

        alert.showAndWait();
    }

    public static void displaySuccess() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Aviso.");
        alert.setHeaderText("Éxito.");
        alert.setContentText("La operación se concretó en forma satisfactoria.");
        alert.setResizable(true);

        alert.showAndWait();
    }

    public static void displayError() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error.");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.setResizable(true);

        alert.showAndWait();
    }

    public static String getHeader() {
        return header;
    }

    public static void setHeader(String header) {
        DialogBox.header = header;
    }

    public static String getContent() {
        return content;
    }

    public static void setContent(String content) {
        DialogBox.content = content;
    }

}
