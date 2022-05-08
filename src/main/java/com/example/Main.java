package com.example;

import com.example.json.Json;
import com.example.pojos.VendingMachinePOJO;
import com.example.vending_machine.Item;
import com.example.vending_machine.VendingMachine;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        VendingMachine vm = new VendingMachine("FileResources/input.json");
        System.out.println(vm.addOrUpdateItems("FileResources/updateOrAdd.json"));
        /*
        Item item = new Item();
        item.setName("Snickers");;
        VendingMachinePOJO pojo;
        try {
            pojo = Json.fromJson(Json.parse(Files.readString(Paths.get("FileResources/input.json"), StandardCharsets.US_ASCII).replace("$", "")), VendingMachinePOJO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(pojo.getItems().contains(item));
        */
        vm.showItems();
    }
}
