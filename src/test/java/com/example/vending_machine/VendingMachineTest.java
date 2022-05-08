package com.example.vending_machine;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VendingMachineTest {
    private VendingMachine vm = new VendingMachine("FileResources/input.json");

    @Test
    void updateAllItems() {
        vm.updateAllItems("FileResources/updateOrAdd.json");
        assertEquals(4, vm.getNumberOfItems());

        ArrayList<Item> items = new ArrayList<>();
        Item item1 = new Item();
        item1.setName("Snickers");
        item1.setAmount(10);
        item1.setPrice(1.55);
        Item item2 = new Item();
        item2.setName("Nutrageous");
        item2.setAmount(4);
        item2.setPrice(1.10);
        Item item3 = new Item();
        item3.setName("Oreo");
        item3.setAmount(10);
        item3.setPrice(1.65);
        Item item4 = new Item();
        item4.setName("Nefis");
        item4.setAmount(6);
        item4.setPrice(1.05);
        items.add(item1);
        items.add(item2);
        items.add(item3);
        items.add(item4);

        //Iterating through the items to check
        for(int i = 0; i < vm.getItems().size(); i++) {
            for(int j = 0; j < vm.getItems().get(i).size(); j++) {
                assertEquals(items.get(j).getName(), vm.getItems().get(i).get(j).getName());
                assertEquals(items.get(j).getAmount(), vm.getItems().get(i).get(j).getAmount());
                assertEquals(items.get(j).getPrice(), vm.getItems().get(i).get(j).getPrice());
            }
        }
    }

    @Test
    void addOrUpdateItems() {
        List<List<Item>> originalItems = vm.getItems();
        vm.addOrUpdateItems("FileResources/updateOrAdd.json");
        assertEquals(originalItems.get(0).get(0).getName(), vm.getItems().get(0).get(0).getName());
        assertEquals(1.55, vm.getItems().get(0).get(0).getPrice());
        assertEquals(originalItems.get(0).get(0).getAmount() + 10, vm.getItems().get(0).get(0).getAmount());
        assertEquals(originalItems.get(0).get(5).getName(), vm.getItems().get(0).get(5).getName());
        assertEquals(1.10, vm.getItems().get(0).get(5).getPrice());
        assertEquals(originalItems.get(0).get(5).getAmount() + 4, vm.getItems().get(0).get(5).getAmount());
        assertEquals("Oreo", vm.getItems().get(1).get(1).getName());
        assertEquals(1.65, vm.getItems().get(1).get(1).getPrice());
        assertEquals(10, vm.getItems().get(1).get(1).getAmount());
        assertEquals("Nefis", vm.getItems().get(1).get(2).getName());
        assertEquals(1.05, vm.getItems().get(1).get(2).getPrice());
        assertEquals(6, vm.getItems().get(1).get(2).getAmount());


    }

    @Test
    void getNumberOfItems() {
        assertEquals(9, vm.getNumberOfItems());
    }
}