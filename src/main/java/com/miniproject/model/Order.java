package com.miniproject.model;

import java.time.LocalDate;

public class Order {
    private int id;
    private String clientName;
    private String product;
    private int quantity;
    private double totalPrice;
    private String status;
    private LocalDate orderDate;

    public Order(String clientName, String product, int quantity, double totalPrice, String status) {
        this.clientName = clientName;
        this.product    = product;
        this.quantity   = quantity;
        this.totalPrice = totalPrice;
        this.status     = status;
        this.orderDate  = LocalDate.now();
    }

    public int       getId()          { return id; }
    public String    getClientName()  { return clientName; }
    public String    getProduct()     { return product; }
    public int       getQuantity()    { return quantity; }
    public double    getTotalPrice()  { return totalPrice; }
    public String    getStatus()      { return status; }
    public LocalDate getOrderDate()   { return orderDate; }

    public void setId(int id)                    { this.id = id; }
    public void setClientName(String clientName) { this.clientName = clientName; }
    public void setProduct(String product)       { this.product = product; }
    public void setQuantity(int quantity)        { this.quantity = quantity; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    public void setStatus(String status)         { this.status = status; }
    public void setOrderDate(LocalDate date)     { this.orderDate = date; }
}