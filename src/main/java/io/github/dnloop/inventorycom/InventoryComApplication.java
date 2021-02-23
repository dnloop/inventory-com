package io.github.dnloop.inventorycom;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableJpaRepositories("io.github.dnloop.inventorycom.repository")
@EntityScan("io.github.dnloop.inventorycom.model")
public class InventoryComApplication {

    public static void main(String[] args) {
        Application.launch(JavaFXApplication.class, args);
    }

}
