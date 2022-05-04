package com.example.vending_machine;

import java.util.ArrayList;
import java.util.List;

public class VendingMachine {
    private int rows;
    private int columns;
    private List<List<Item>> items = new ArrayList<List<Item>>();

    public VendingMachine(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        for(int i = 0; i < this.rows; i++) {
            items.add(new ArrayList<>());
        }
    }

    public VendingMachine(int rows, int columns, ArrayList<Item> toBeAdded) {
        this.rows = rows;
        this.columns = columns;
        if(toBeAdded.size() > rows * columns) {
            throw new IndexOutOfBoundsException("There aren't enought slots in the Vending Machine for all the itmes.");
        }
        int indexOfToBeAddedArray = 0;
        for(int i = 0; i < this.rows; i++) {
            items.add(new ArrayList<>());
            for(int j = 0; j < this.columns; j++) {
                items.get(i).add(toBeAdded.get(indexOfToBeAddedArray));
                indexOfToBeAddedArray++;
            }
        }
    }

    public boolean updateAllItems(ArrayList<Item> toBeAdded) {

        return true;
    }

    public boolean addItems(ArrayList<Item> toBeAdded) {

        return true;
    }

    public boolean resetMachine(int rows, int columns, ArrayList<Item> toBeAdded) {

        return true;
    }

    public void showItems() {
        items.stream()
                .forEach(row -> {
                    row.stream()
                            .forEach(item -> System.out.print(item + " "));
                    System.out.println();
                });
    }
}
