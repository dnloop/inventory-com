package io.github.dnloop.inventorycom.utils;

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
