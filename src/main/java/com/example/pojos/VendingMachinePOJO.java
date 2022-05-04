package com.example.pojos;

import java.util.ArrayList;

public class VendingMachinePOJO {
    private ConfigPOJO config;
    private ArrayList<ItemPOJO> items;

    public ConfigPOJO getConfig() {
        return config;
    }

    public void setConfig(ConfigPOJO config) {
        this.config = config;
    }

    public ArrayList<ItemPOJO> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemPOJO> items) {
        this.items = items;
    }
}
