package com.example.vending_machine;

import java.util.Objects;

public class Item {
    private String name;
    private int amount;
    private Double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return name + ", amount = " + amount + ", price = $" + price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return this.getName().equals(item.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name) + 67;
    }
}
