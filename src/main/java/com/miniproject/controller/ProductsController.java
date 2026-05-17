package com.miniproject.controller;

import com.miniproject.model.Product;
import com.miniproject.store.ProductsStore;
import com.miniproject.util.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductsController implements Initializable {

    @FXML private TableView<Product>             tableView;
    @FXML private TableColumn<Product, Integer>  colId;
    @FXML private TableColumn<Product, String>   colName;
    @FXML private TableColumn<Product, String>   colCategory;
    @FXML private TableColumn<Product, Double>   colPrice;
    @FXML private TableColumn<Product, Integer>  colStock;
    @FXML private TableColumn<Product, String>   colDescription;

    @FXML private TextField nameField;
    @FXML private TextField categoryField;
    @FXML private TextField priceField;
    @FXML private TextField stockField;
    @FXML private TextField descriptionField;
    @FXML private Label     statusLabel;

    private Product selectedProduct = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        tableView.setItems(ProductsStore.getAll());
        tableView.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldVal, newVal) -> populateForm(newVal)
        );
    }

    private void populateForm(Product p) {
        if (p == null) return;
        selectedProduct = p;
        nameField.setText(p.getName());
        categoryField.setText(p.getCategory());
        priceField.setText(String.valueOf(p.getPrice()));
        stockField.setText(String.valueOf(p.getStock()));
        descriptionField.setText(p.getDescription());
        statusLabel.setText("Editing: " + p.getName());
    }

    @FXML
    private void handleAdd() {
        if (!validateFields()) return;
        Product p = new Product(
            nameField.getText().trim(),
            categoryField.getText().trim(),
            Double.parseDouble(priceField.getText().trim()),
            Integer.parseInt(stockField.getText().trim()),
            descriptionField.getText().trim()
        );
        ProductsStore.add(p);
        clearForm();
        statusLabel.setText("Product added successfully.");
    }

    @FXML
    private void handleUpdate() {
        if (selectedProduct == null) {
            statusLabel.setText("Select a product to update.");
            return;
        }
        if (!validateFields()) return;
        ProductsStore.update(selectedProduct,
            nameField.getText().trim(),
            categoryField.getText().trim(),
            Double.parseDouble(priceField.getText().trim()),
            Integer.parseInt(stockField.getText().trim()),
            descriptionField.getText().trim()
        );
        clearForm();
        statusLabel.setText("Product updated successfully.");
    }

    @FXML
    private void handleDelete() {
        Product p = tableView.getSelectionModel().getSelectedItem();
        if (p == null) {
            statusLabel.setText("Select a product to delete.");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
            "Delete " + p.getName() + "?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.YES) {
                ProductsStore.remove(p);
                clearForm();
                statusLabel.setText("Product deleted.");
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
            if (nameField.getText().trim().isEmpty()) {
                statusLabel.setText("Product name is required.");
                return false;
            }
            Double.parseDouble(priceField.getText().trim());
            Integer.parseInt(stockField.getText().trim());
        } catch (NumberFormatException e) {
            statusLabel.setText("Price must be a number, stock must be an integer.");
            return false;
        }
        return true;
    }

    private void clearForm() {
        selectedProduct = null;
        nameField.clear();
        categoryField.clear();
        priceField.clear();
        stockField.clear();
        descriptionField.clear();
        tableView.getSelectionModel().clearSelection();
    }
}
