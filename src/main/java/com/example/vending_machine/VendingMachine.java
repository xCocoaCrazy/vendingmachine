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
    private VendingMachinePOJO pojoVendingMachine; //Used to parse the data from a .json to the VendingMachine object
    private int rows;
    private int columns;
    private List<List<Item>> items = new ArrayList<List<Item>>();

    //Main constructor
    public VendingMachine(String directoryOfFile) {
        this.pojoVendingMachine = configureVendingMachine(directoryOfFile, true);

        //In case the .json file has more items than the vending machine has slots
        if(pojoVendingMachine.getItems().size() > rows * columns) {
            throw new IndexOutOfBoundsException("There are more items than slots!");
        }

        //Creating the rows of the table
        for(int i = 0; i < this.rows; i++) {
            items.add(new ArrayList<>());
        }

        int indexOfItemsFromPojo = 0;
        //Adding the items to the VendingMachine
        for(int i = 0; i < this.rows; i++) {
            if(indexOfItemsFromPojo == pojoVendingMachine.getItems().size()) {
                break;
            }
            for(int j = 0; j < this.columns; j++) {
                items.get(i).add(pojoVendingMachine.getItems().get(indexOfItemsFromPojo));
                indexOfItemsFromPojo++;
                if(indexOfItemsFromPojo == pojoVendingMachine.getItems().size()) {
                    break;
                }
            }
        }
    }

    //Configuring the POJO VendingMachine
    private VendingMachinePOJO configureVendingMachine(String directoryOfFile, boolean configureRowsAndColumns) {
        VendingMachinePOJO pojo;
        try {
            pojo = Json.fromJson(Json.parse(Files.readString(Paths.get(directoryOfFile), StandardCharsets.US_ASCII).replace("$", "")), VendingMachinePOJO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(configureRowsAndColumns) {
            rows = pojo.getConfig().getRows();
            columns = pojo.getConfig().getColumns();
        }
        return pojo;
    }

    //Resets all the
    public boolean updateAllItems(String fileFromWhichToGetItems) {
        this.pojoVendingMachine = configureVendingMachine(fileFromWhichToGetItems, false);

        //In case the .json file has more items than the vending machine has slots
        if(pojoVendingMachine.getItems().size() > rows * columns) {
            System.out.println("There are more items than slots!");
            return false;
        }
        int indexOfItemsFromPojo = 0;
        //Removes all items.
        for(int i = 0; i < this.rows; i++) {
            items.get(i).removeAll(items.get(i));
        }

        //Adding the items to the VendingMachine
        for(int i = 0; i < this.rows; i++) {
            if(indexOfItemsFromPojo == pojoVendingMachine.getItems().size()) {
                break;
            }
            for(int j = 0; j < this.columns; j++) {
                items.get(i).add(pojoVendingMachine.getItems().get(indexOfItemsFromPojo));
                indexOfItemsFromPojo++;
                if(indexOfItemsFromPojo == pojoVendingMachine.getItems().size()) {
                    break;
                }
            }
        }
        return true;
    }

    //Method that adds the items from a .json if they don't exist, or if they exist it updates the amount (oldAmount+newAmount) and updates the price of the item.
    public boolean addOrUpdateItems(String directoryOfFile) {
        VendingMachinePOJO tempPojo = configureVendingMachine(directoryOfFile, false);
        //In case the .json file has more items than the vending machine has slots
        if(tempPojo.getItems().size() > (rows * columns - getNumberOfItems())) {
            System.out.printf("There are only %d slots available, but you are trying to input %d items!", (rows * columns - getNumberOfItems()), tempPojo.getItems().size());
            return false;
        }

        //Firstly update the items that already exist
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < tempPojo.getItems().size(); j++) {
                Item item = tempPojo.getItems().get(j);
                if(items.get(i).contains(item)) {
                    item.setAmount(items.get(i).get(items.get(i).indexOf(item)).getAmount() + item.getAmount());
                    items.get(i).set(items.get(i).indexOf(item), item);
                    tempPojo.getItems().remove(item);
                    j--;
                }
            }
        }

        int indexOfPojoVendingMachine = 0;
        //Adding the items that are new.
        for(int i = getNumberOfItems() / columns; i < rows; i++) {
            if(indexOfPojoVendingMachine == tempPojo.getItems().size()) {
                break;
            }
            for(int j = getNumberOfItems() % columns; j < columns; j++) {
                items.get(i).add(tempPojo.getItems().get(indexOfPojoVendingMachine));
                indexOfPojoVendingMachine++;
                if(indexOfPojoVendingMachine == tempPojo.getItems().size()) {
                    break;
                }
            }
        }
        return true;
    }

    //Gets the number of slots used for items in the vending machine.
    public int getNumberOfItems() {
        int numberOfItems = 0;
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                if(items.get(i).size() < j + 1) {
                    break;
                }
                if(items.get(i).get(j) != null) {
                    numberOfItems++;
                }
            }
        }
        return numberOfItems;
    }

    //Getter for the items. Returns a copy of the items, so that the user won't have the ability to directly change the items.
    public List<List<Item>> getItems() {
        List<List<Item>> list = new ArrayList<List<Item>>();
        //Creating the rows of the table
        for(int i = 0; i < this.rows; i++) {
            list.add(new ArrayList<>());
        }

        //Copying the items.
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < items.get(i).size(); j++) {
                list.get(i).add(items.get(i).get(j));
            }
        }
        return list;
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
