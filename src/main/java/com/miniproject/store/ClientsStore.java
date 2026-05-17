package com.miniproject.store;

import com.miniproject.model.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ClientsStore {

    private static final ObservableList<Client> clients =
        FXCollections.observableArrayList();

    static {
        // Sample data
        clients.add(new Client("John Smith",   "john@example.com",  "70123456", "Beirut, Hamra"));
        clients.add(new Client("Sara Hassan",  "sara@example.com",  "71234567", "Beirut, Achrafieh"));
        clients.add(new Client("Omar Khalil",  "omar@example.com",  "76345678", "Tripoli, Downtown"));
    }

    public static ObservableList<Client> getAll() {
        return clients;
    }

    public static void add(Client c) {
        clients.add(c);
    }

    public static void remove(Client c) {
        clients.remove(c);
    }

    public static void update(Client c, String name, String email, String phone, String address) {
        c.setName(name);
        c.setEmail(email);
        c.setPhone(phone);
        c.setAddress(address);
        // Trigger TableView refresh
        int idx = clients.indexOf(c);
        clients.set(idx, c);
    }
}
