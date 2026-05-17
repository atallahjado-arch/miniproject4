package com.miniproject;

import javafx.application.Application;
import javafx.stage.Stage;
import com.miniproject.util.SceneManager;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneManager.init(primaryStage);
        SceneManager.switchTo("Login");
        primaryStage.setTitle("Mini Project 2 - Business Manager");
        primaryStage.setWidth(900);
        primaryStage.setHeight(650);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
