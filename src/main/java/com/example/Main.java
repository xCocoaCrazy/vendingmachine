package com.example;

import com.example.json.Json;
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
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item("name1", 12, 12));
        items.add(new Item("name2", 12, 12));
        items.add(new Item("name3", 12, 12));
        items.add(new Item("name4", 12, 12));
        items.add(new Item("name5", 12, 12));
        items.add(new Item("name6", 12, 12));

        VendingMachine vm = new VendingMachine(2, 3, items);
        String jsonFile = Files.readString(Paths.get("input.json"), StandardCharsets.US_ASCII);
        System.out.println(jsonFile);

        JsonNode node = Json.parse(jsonFile);
    }
}
