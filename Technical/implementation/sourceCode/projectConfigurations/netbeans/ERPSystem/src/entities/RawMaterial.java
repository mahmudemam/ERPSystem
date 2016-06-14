/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author memam
 */
public class RawMaterial {

    private final int id;
    private final String name;
    private final String description;
    private int qty;
    private double price;

    public RawMaterial(int id, String name, String description, int qty, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.qty = qty;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getQty() {
        return qty;
    }

    public void addQty(int qty) {
        this.qty += qty;
    }

    public boolean withdrawQty(int qty) {
        boolean status = false;

        if (qty <= this.qty) {
            this.qty -= qty;
            status = true;
        }

        return status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "[Code=" + id + ", Name=" + name + ", Description=" + description + ", Qty=" + qty + ", Price=" + price + "]";
    }
}
