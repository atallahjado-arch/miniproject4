package com.miniproject.model;

public class Client {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String address;

    private static int counter = 1;

    public Client(String name, String email, String phone, String address) {
        this.id = counter++;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public int getId()          { return id; }
    public String getName()     { return name; }
    public String getEmail()    { return email; }
    public String getPhone()    { return phone; }
    public String getAddress()  { return address; }

    public void setName(String name)       { this.name = name; }
    public void setEmail(String email)     { this.email = email; }
    public void setPhone(String phone)     { this.phone = phone; }
    public void setAddress(String address) { this.address = address; }

    @Override
    public String toString() {
        return name + " <" + email + ">";
    }
}
