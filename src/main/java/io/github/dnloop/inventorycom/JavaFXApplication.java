package io.github.dnloop.inventorycom;

import io.github.dnloop.inventorycom.controller.main.Main;
import io.github.dnloop.inventorycom.utils.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

public class JavaFXApplication extends Application implements AppReadyCallback {

    private static final Log log = LogFactory.getLog(JavaFXApplication.class);

    private ConfigurableApplicationContext applicationContext;

    private final BooleanProperty state = new SimpleBooleanProperty(false);

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);

        this.applicationContext = new SpringApplicationBuilder().sources(InventoryComApplication.class).run(args);

        SceneManager.uiLoader = new UILoader();
    }

    @Override
    public void start(Stage primaryStage) {
        log.info("[ Starting CommerceInventory application ]");
        initLoadingDialog();
        SceneManager.mainStage = primaryStage;

        Platform.runLater(() -> {
            Scene scene = initMainPane();
            primaryStage.setTitle(" -·=[ Inventario Comercial ]=·-");
            primaryStage.setScene(scene);
            log.info("[ Application running ]");
            primaryStage.show();
        });


    }

    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
    }

    /**
     * First the LoadingDialog is created to act as a splash screen providing
     * feedback to the state of application initialization.
     */
    private void initLoadingDialog() {
        log.info("[ Setting Loading Dialog ]");
        SceneManager.loadingDialog = SceneManager.uiLoader.createLoadingDialog();
        Parent node = (Parent) SceneManager.uiLoader.getNode(Route.LOADING.display());
        Stage loading = SceneManager.uiLoader.buildStage("Diálogo de carga.", node);
        SceneManager.loadingDialog.setStage(loading);
        SceneManager.loadingDialog.setAppCallback(this);
    }

    /**
     * Loads the main fxml layout. Sets up SceneManager. Loads the first view into
     * the FXML layout. Creates the LoadingDialog.
     *
     * @return the main application scene.
     */
    private Scene initMainPane() {
        log.info("[ Setting Main Pane ]");
        SceneManager.uiLoader = new UILoader();
        Scene scene = SceneManager.uiLoader.createMainPane();
        Main mainController = SceneManager.getController(RouteMain.MAIN.mainGUI());
        SceneManager.setMainController(mainController);

        return scene;
    }

    @Override
    public void appState(Boolean ready) {
        if (ready)
            state.setValue(Boolean.TRUE);
    }


    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("DatabaseInitializer-");
        executor.initialize();
        return executor;
    }
}
