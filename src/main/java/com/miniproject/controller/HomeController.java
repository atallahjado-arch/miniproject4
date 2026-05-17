package com.miniproject.controller;

import com.miniproject.util.SceneManager;
import javafx.fxml.FXML;

public class HomeController {

    @FXML
    private void goToClients() {
        SceneManager.switchTo("Clients");
    }

    @FXML
    private void goToOrders() {
        SceneManager.switchTo("Orders");
    }

    @FXML
    private void goToProducts() {
        SceneManager.switchTo("Products");
    }

    @FXML
    private void handleLogout() {
        SceneManager.switchTo("Login");
    }
}
