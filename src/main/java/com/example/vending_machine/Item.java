package com.example.vending_machine;

public class Item {
    private String name;
    private int amount;
    private double price;

    public Item(String name, int amount, double price) {
        this.name = name;
        this.amount = amount;
        this.price = price;
    }

    private boolean getItem() {
        if(amount > 0) {
            amount--;
            return true;
        }
        return false;
    }

    private double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name;
    }
}
