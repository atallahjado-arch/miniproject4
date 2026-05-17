package com.miniproject.controller;

import com.miniproject.model.Client;
import com.miniproject.store.ClientsStore;
import com.miniproject.util.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientsController implements Initializable {

    @FXML private TableView<Client>          tableView;
    @FXML private TableColumn<Client, Integer> colId;
    @FXML private TableColumn<Client, String>  colName;
    @FXML private TableColumn<Client, String>  colEmail;
    @FXML private TableColumn<Client, String>  colPhone;
    @FXML private TableColumn<Client, String>  colAddress;

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextField addressField;
    @FXML private Label     statusLabel;

    private Client selectedClient = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        tableView.setItems(ClientsStore.getAll());

        tableView.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldVal, newVal) -> populateForm(newVal)
        );
    }

    private void populateForm(Client c) {
        if (c == null) return;
        selectedClient = c;
        nameField.setText(c.getName());
        emailField.setText(c.getEmail());
        phoneField.setText(c.getPhone());
        addressField.setText(c.getAddress());
        statusLabel.setText("Editing: " + c.getName());
    }

    @FXML
    private void handleAdd() {
        if (!validateFields()) return;
        Client c = new Client(
            nameField.getText().trim(),
            emailField.getText().trim(),
            phoneField.getText().trim(),
            addressField.getText().trim()
        );
        ClientsStore.add(c);
        clearForm();
        statusLabel.setText("Client added successfully.");
    }

    @FXML
    private void handleUpdate() {
        if (selectedClient == null) {
            statusLabel.setText("Select a client to update.");
            return;
        }
        if (!validateFields()) return;
        ClientsStore.update(selectedClient,
            nameField.getText().trim(),
            emailField.getText().trim(),
            phoneField.getText().trim(),
            addressField.getText().trim()
        );
        clearForm();
        statusLabel.setText("Client updated successfully.");
    }

    @FXML
    private void handleDelete() {
        Client c = tableView.getSelectionModel().getSelectedItem();
        if (c == null) {
            statusLabel.setText("Select a client to delete.");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
            "Delete " + c.getName() + "?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.YES) {
                ClientsStore.remove(c);
                clearForm();
                statusLabel.setText("Client deleted.");
            }
        });
    }

    @FXML
    private void handleClear() {
        clearForm();
        statusLabel.setText("");
    }

    @FXML
    private void goBack() {
        SceneManager.switchTo("Home");
    }

    private boolean validateFields() {
        if (nameField.getText().trim().isEmpty() ||
            emailField.getText().trim().isEmpty()) {
            statusLabel.setText("Name and Email are required.");
            return false;
        }
        return true;
    }

    private void clearForm() {
        selectedClient = null;
        nameField.clear();
        emailField.clear();
        phoneField.clear();
        addressField.clear();
        tableView.getSelectionModel().clearSelection();
    }
}
