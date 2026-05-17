package com.miniproject.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SceneManager {

    private static Stage stage;

    public static void init(Stage primaryStage) {
        stage = primaryStage;
    }

    public static void switchTo(String viewName) {
        try {
            String path = "/com/miniproject/views/" + viewName + "View.fxml";
            Parent root = FXMLLoader.load(
                Objects.requireNonNull(SceneManager.class.getResource(path))
            );
            if (stage.getScene() == null) {
                stage.setScene(new Scene(root));
            } else {
                stage.getScene().setRoot(root);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not load view: " + viewName, e);
        }
    }

    public static Stage getStage() {
        return stage;
    }
}
