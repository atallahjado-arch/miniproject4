package com.miniproject.controller;

import com.miniproject.model.Order;
import com.miniproject.store.OrdersStore;
import com.miniproject.util.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class OrdersController implements Initializable {

    @FXML private TableView<Order>              tableView;
    @FXML private TableColumn<Order, Integer>   colId;
    @FXML private TableColumn<Order, String>    colClient;
    @FXML private TableColumn<Order, String>    colProduct;
    @FXML private TableColumn<Order, Integer>   colQty;
    @FXML private TableColumn<Order, Double>    colTotal;
    @FXML private TableColumn<Order, String>    colStatus;

    @FXML private TextField clientNameField;
    @FXML private TextField productField;
    @FXML private TextField quantityField;
    @FXML private TextField totalPriceField;
    @FXML private ComboBox<String> statusCombo;
    @FXML private Label statusLabel;

    private Order selectedOrder = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colClient.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        colProduct.setCellValueFactory(new PropertyValueFactory<>("product"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        statusCombo.getItems().addAll("Pending", "Processing", "Shipped", "Delivered", "Cancelled");
        statusCombo.setValue("Pending");

        tableView.setItems(OrdersStore.getAll());
        tableView.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldVal, newVal) -> populateForm(newVal)
        );
    }

    private void populateForm(Order o) {
        if (o == null) return;
        selectedOrder = o;
        clientNameField.setText(o.getClientName());
        productField.setText(o.getProduct());
        quantityField.setText(String.valueOf(o.getQuantity()));
        totalPriceField.setText(String.valueOf(o.getTotalPrice()));
        statusCombo.setValue(o.getStatus());
        statusLabel.setText("Editing order #" + o.getId());
    }

    @FXML
    private void handleAdd() {
        if (!validateFields()) return;
        Order o = new Order(
            clientNameField.getText().trim(),
            productField.getText().trim(),
            Integer.parseInt(quantityField.getText().trim()),
            Double.parseDouble(totalPriceField.getText().trim()),
            statusCombo.getValue()
        );
        OrdersStore.add(o);
        clearForm();
        statusLabel.setText("Order added successfully.");
    }

    @FXML
    private void handleUpdate() {
        if (selectedOrder == null) {
            statusLabel.setText("Select an order to update.");
            return;
        }
        if (!validateFields()) return;
        OrdersStore.update(selectedOrder,
            clientNameField.getText().trim(),
            productField.getText().trim(),
            Integer.parseInt(quantityField.getText().trim()),
            Double.parseDouble(totalPriceField.getText().trim()),
            statusCombo.getValue()
        );
        clearForm();
        statusLabel.setText("Order updated successfully.");
    }

    @FXML
    private void handleDelete() {
        Order o = tableView.getSelectionModel().getSelectedItem();
        if (o == null) {
            statusLabel.setText("Select an order to delete.");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
            "Delete order #" + o.getId() + "?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.YES) {
                OrdersStore.remove(o);
                clearForm();
                statusLabel.setText("Order deleted.");
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
        try {
            if (clientNameField.getText().trim().isEmpty() || productField.getText().trim().isEmpty()) {
                statusLabel.setText("Client name and product are required.");
                return false;
            }
            Integer.parseInt(quantityField.getText().trim());
            Double.parseDouble(totalPriceField.getText().trim());
        } catch (NumberFormatException e) {
            statusLabel.setText("Quantity must be integer, price must be number.");
            return false;
        }
        return true;
    }

    private void clearForm() {
        selectedOrder = null;
        clientNameField.clear();
        productField.clear();
        quantityField.clear();
        totalPriceField.clear();
        statusCombo.setValue("Pending");
        tableView.getSelectionModel().clearSelection();
    }
}
