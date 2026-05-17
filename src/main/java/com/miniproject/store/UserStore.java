package com.miniproject.store;

import com.miniproject.model.User;
import com.miniproject.util.DBConnection;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class UserStore {

    public static User authenticate(String username, String password) {
        String hashedPassword = sha256(password);
        if (hashedPassword == null) return null;

        String sql = "SELECT username, display_name FROM accounts WHERE username=? AND password_hash=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, hashedPassword);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getString("username"), password, rs.getString("display_name"));
                }
            }

        } catch (SQLException e) {
            System.err.println("[UserStore] authenticate() failed: " + e.getMessage());
        }

        return null;
    }

    private static String sha256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}