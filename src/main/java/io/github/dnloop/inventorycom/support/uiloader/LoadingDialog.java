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

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.List;

@Component
@Controller
public class LoadingDialog {

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private Label lblPendingTasks;

    @FXML
    private Label lblMessage;

    private Stage stage;

    private final BooleanProperty state = new SimpleBooleanProperty(false);

    private AppReadyCallback appCallback;

    private final TaskReadyCallback taskCallback = ready -> {
        if (ready)
            state.setValue(Boolean.TRUE);
    };

    /**
     * The TaskManager must be previously loaded to use in this class.
     */
    private TaskManager taskManager = new TaskManager();

    @FXML
    void initialize() {
        taskManager.setCallback(taskCallback);
    }

    public void showDialog() {
        lblPendingTasks.textProperty()
                       .bind(taskManager.getPendingTasks().asString("%d de " + taskManager.getNumTasks()));
        taskManager.runList();

        state.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                appCallback.appState(true);
                stage.hide();
            }
        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setTaskList(List<Task<?>> taskList) {
        this.taskManager.setTaskList(taskList);
    }

    public void setAppCallback(AppReadyCallback appCallback) {
        this.appCallback = appCallback;
    }
}
