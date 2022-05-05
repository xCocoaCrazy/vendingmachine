package com.example.pojos;

import com.example.vending_machine.Item;

import java.util.ArrayList;

//Class used to get the information from the .json file to then send to VendingMachine.java
public class VendingMachinePOJO {
    private ConfigPOJO config;
    private ArrayList<Item> items;

    public ConfigPOJO getConfig() {
        return config;
    }

    public void setConfig(ConfigPOJO config) {
        this.config = config;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    //Internal class only to parse the data from the .json file.
    public class ConfigPOJO {
        private int rows;
        private int columns;

        public int getRows() {
            return rows;
        }

        public void setRows(int rows) {
            this.rows = rows;
        }

        public int getColumns() {
            return columns;
        }

        public void setColumns(int columns) {
            this.columns = columns;
        }
    }
}
