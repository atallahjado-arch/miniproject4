package com.miniproject.controller;

import com.miniproject.model.User;
import com.miniproject.store.UserStore;
import com.miniproject.util.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please enter both username and password.");
            return;
        }

        User user = UserStore.authenticate(username, password);
        if (user != null) {
            SceneManager.switchTo("Home");
        } else {
            errorLabel.setText("Invalid credentials. Try again.");
            passwordField.clear();
        }
    }
}
