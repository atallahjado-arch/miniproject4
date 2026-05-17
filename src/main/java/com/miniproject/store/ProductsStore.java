package com.miniproject.store;

import com.miniproject.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductsStore {

    private static final ObservableList<Product> products =
        FXCollections.observableArrayList();

    static {
        products.add(new Product("Laptop Pro",    "Electronics", 1200.0, 15, "High-performance laptop"));
        products.add(new Product("Office Chair",  "Furniture",    350.0, 40, "Ergonomic office chair"));
        products.add(new Product("Wireless Mouse","Electronics",   45.0, 100,"Bluetooth mouse"));
    }

    public static ObservableList<Product> getAll() {
        return products;
    }

    public static void add(Product p) {
        products.add(p);
    }

    public static void remove(Product p) {
        products.remove(p);
    }

    public static void update(Product p, String name, String category,
                              double price, int stock, String description) {
        p.setName(name);
        p.setCategory(category);
        p.setPrice(price);
        p.setStock(stock);
        p.setDescription(description);
        int idx = products.indexOf(p);
        products.set(idx, p);
    }
}
