package com.example.tests;

import com.example.vending_machine.Item;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
    private Item item;

    @BeforeAll
    void setup() {
        item.setName("name");
        item.setAmount(10);
    }
    @Test
    void getName() {

    }

    @Test
    void setName() {
    }

    @Test
    void getAmount() {
    }

    @Test
    void setAmount() {
    }

    @Test
    void getPrice() {
    }

    @Test
    void setPrice() {
    }
}