package io.github.dnloop.inventorycom.utils;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Script containing all available tasks to be loaded in the background.
 *
 * @author dnloop
 */
public class TaskManager {

    private static final Log log = LogFactory.getLog(TaskManager.class);

    /**
     * The total number of tasks in the list.
     */
    private int numTasks = 0;

    /**
     * The current number of pending tasks to be executed.
     */
    private final IntegerProperty pendingTasks = new SimpleIntegerProperty(0);

    /**
     * Callback method to inform the state of the task.
     */
    private TaskReadyCallback callback;

    /**
     * Background daemon service to process concurrent tasks.
     */
    private final ExecutorService exec = Executors.newFixedThreadPool(10, r -> {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    });

    /**
     * List of concurrent tasks.
     */
    private List<Task<?>> taskList = new ArrayList<>();

    /**
     * To call this method list must be previously loaded, the properties are used
     * to display the current background tasks that the module is working on.
     */
    public void initVars() {
        numTasks = taskList.size();
        pendingTasks.set(numTasks);
    }

    /**
     * Execute the tasks previously loaded on the list.
     */
    public void runList() {
        if (pendingTasks.get() > 0) {
            taskList.forEach(task -> task.stateProperty().addListener((obs, oldState, newState) -> {
                log.debug("Task " + newState);
                // update lblPendingTasks if task moves out of running state:
                if (oldState == Worker.State.RUNNING)
                    pendingTasks.set(pendingTasks.get() - 1);

                if (pendingTasks.get() == 0) {
                    log.info("Job finished.");
                    callback.taskState(Boolean.TRUE);
                }
            }));

            taskList.forEach(exec::execute);
        }
        taskList.clear();

    }

    /**
     * This is a standard template to be used in future releases of the application,
     * currently unsupported.
     */
    public static final Task<Void> template = new Task<>() {
        @Override
        protected Void call() {
            log.info("Loading Database");
            return null;
        }

        @Override
        protected void cancelled() {
            updateMessage("Carga cancelada.");
            log.debug("Canceled module loading.");
        }

        @Override
        protected void failed() {
            updateMessage("Carga fallida.");
            log.error("Module loading failed.");
        }

    };

    /*
     * Getters and Setters
     */

    public Task<?> getTask(int index) {
        return taskList.get(index);
    }

    public void addTask(Task<?> task) {
        this.taskList.add(task);
    }

    public int getNumTasks() {
        return numTasks;
    }

    public void setNumTasks(int numTasks) {
        this.numTasks = numTasks;
    }

    public List<Task<?>> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task<?>> taskList) {
        this.taskList = taskList;
    }

    public IntegerProperty getPendingTasks() {
        return pendingTasks;
    }

    public void setCallback(TaskReadyCallback callback) {
        this.callback = callback;
    }

}
