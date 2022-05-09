package com.example.vending_machine;

import com.example.json.Json;
import com.example.pojos.VendingMachinePOJO;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    //Method to show items from the vending machine
    public void showItems() {
        char c = 65;
        int numberOfElements = getNumberOfItems();
        for(int i = 0; i < rows; i++) {
            System.out.print((char)(c + i));
            for(int j = 0; j < columns; j++) {
                if(numberOfElements > 0) {
                    System.out.print( " | (" + (j + 1) % 9 + ")" + items.get(i).get(j));
                    numberOfElements--;
                } else {
                    System.out.print(" | (" + (j + 1) % 9 + ")" + "Empty");
                }
            }
            System.out.println();
        }
    }

    private int[] getPositionOfItemFromString(String position) {
        char[] pos = position.toCharArray();
        //Gets the position of the item for row by transforming the Char to the int
        int chosenRow = pos[0] - 65;
        int chosenColumn = 0;
        int multiplyer = 1;
        for(int i = pos.length - 1; i > 0; i--) {
            chosenColumn += Character.getNumericValue(pos[i]) * multiplyer;
            multiplyer *= 10;
        }
        //The final value in int for chosenColumn for the item.
        chosenColumn--;
        int positions[] = new int[2];
        positions[0] = chosenRow;
        positions[1] = chosenColumn;
        return positions;
    }

    //Method to pick the item from Vending Machine.
    protected boolean checkIfItemIsValid(String position) {
        int chosenRow = getPositionOfItemFromString(position)[0];
        int chosenColumn = getPositionOfItemFromString(position)[1];

        //Checking if the entered data by the user is valid.
        if(chosenRow >= rows) {
            System.out.println("The row is invalid!");
            return false;
        } else if(chosenColumn >= columns) {
            System.out.println("The column is invalid!");
            return false;
        } else if (chosenRow * 8 + chosenColumn > getNumberOfItems()) {
            System.out.println("The cell has no item!");
            return false;
        }
        return true;
    }

    private boolean proceedPayment(String position) {
        int chosenRow = getPositionOfItemFromString(position)[0];
        int chosenColumn = getPositionOfItemFromString(position)[1];

        double amountLeftToPay = items.get(chosenRow).get(chosenColumn).getPrice();
        System.out.println("Attention! The Vending Machine does not return change!");
        while(amountLeftToPay > 0) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("\nAmount left to enter: " + amountLeftToPay + "\n");
            System.out.print("Insert the money: ");
            double amount = scanner.nextDouble();
            amountLeftToPay -= amount;
        }
        return true;
    }

    protected boolean giveItemToUser(String position) {
        int chosenRow = getPositionOfItemFromString(position)[0];
        int chosenColumn = getPositionOfItemFromString(position)[1];

        items.get(chosenRow).get(chosenColumn).setAmount(items.get(chosenRow).get(chosenColumn).getAmount() - 1);
        return true;
    }

    public void startVendingMachine() {
        System.out.println();
        showItems();
        String position;
        while(true) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("\nEnter the item you want to get (example for writing: A1) :");
            position = scanner.next();
            if(!Character.isAlphabetic(getPositionOfItemFromString(position)[0] + 65)) {
                System.out.println("\nInvalid form of selecting item! (example for writing: A1)");
            } else if(!checkIfItemIsValid(position)) {
                System.out.println("Try again!");
            } else if(checkIfItemIsValid(position)) {
                break;
            }
        }
        if(proceedPayment(position)) {
            System.out.println("Here you go! Have a nice day!");
            giveItemToUser(position);
        }
    }
}
