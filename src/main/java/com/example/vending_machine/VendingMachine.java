package com.example.vending_machine;

import com.example.json.Json;
import com.example.pojos.VendingMachinePOJO;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class VendingMachine {
    private int rows;
    private int columns;
    private List<List<Item>> items = new ArrayList<List<Item>>();

    public VendingMachine(String fileName) {
        VendingMachinePOJO pojo;
        try {

            pojo = Json.fromJson(Json.parse(Files.readString(Paths.get(fileName), StandardCharsets.US_ASCII).replace("$", "")), VendingMachinePOJO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        rows = pojo.getConfig().getRows();
        columns = pojo.getConfig().getColumns();

        if(pojo.getItems().size() > rows * columns) {
            throw new IndexOutOfBoundsException("There are more items than slots!");
        }

        int indexOfItemsFromPojo = 0;
        for(int i = 0; i < this.rows; i++) {
            if(indexOfItemsFromPojo == pojo.getItems().size()) {
                break;
            }
            items.add(new ArrayList<>());
            for(int j = 0; j < this.columns; j++) {
                items.get(i).add(pojo.getItems().get(indexOfItemsFromPojo));
                indexOfItemsFromPojo++;
                if(indexOfItemsFromPojo == pojo.getItems().size()) {
                    break;
                }
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
