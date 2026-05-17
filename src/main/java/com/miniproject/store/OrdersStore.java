package com.miniproject.store;

import com.miniproject.model.Order;
import com.miniproject.util.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class OrdersStore {

    public static ObservableList<Order> getAll() {
        ObservableList<Order> orders = FXCollections.observableArrayList();
        String sql = "SELECT id, client_name, product, quantity, total_price, status, order_date FROM orders";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Order o = new Order(
                        rs.getString("client_name"),
                        rs.getString("product"),
                        rs.getInt("quantity"),
                        rs.getDouble("total_price"),
                        rs.getString("status")
                );
                o.setId(rs.getInt("id"));
                o.setOrderDate(rs.getDate("order_date").toLocalDate());
                orders.add(o);
            }

        } catch (SQLException e) {
            System.err.println("[OrdersStore] getAll() failed: " + e.getMessage());
        }

        return orders;
    }

    public static void add(Order o) {
        String sql = "INSERT INTO orders (client_name, product, quantity, total_price, status, order_date) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, o.getClientName());
            ps.setString(2, o.getProduct());
            ps.setInt(3,    o.getQuantity());
            ps.setDouble(4, o.getTotalPrice());
            ps.setString(5, o.getStatus());
            ps.setDate(6,   Date.valueOf(o.getOrderDate()));
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) o.setId(keys.getInt(1));
            }

        } catch (SQLException e) {
            System.err.println("[OrdersStore] add() failed: " + e.getMessage());
        }
    }

    public static void update(Order o, String clientName, String product,
                              int quantity, double totalPrice, String status) {
        String sql = "UPDATE orders SET client_name=?, product=?, quantity=?, total_price=?, status=? WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, clientName);
            ps.setString(2, product);
            ps.setInt(3,    quantity);
            ps.setDouble(4, totalPrice);
            ps.setString(5, status);
            ps.setInt(6,    o.getId());
            ps.executeUpdate();

            o.setClientName(clientName);
            o.setProduct(product);
            o.setQuantity(quantity);
            o.setTotalPrice(totalPrice);
            o.setStatus(status);

        } catch (SQLException e) {
            System.err.println("[OrdersStore] update() failed: " + e.getMessage());
        }
    }

    public static void remove(Order o) {
        String sql = "DELETE FROM orders WHERE id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, o.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("[OrdersStore] remove() failed: " + e.getMessage());
        }
    }
}