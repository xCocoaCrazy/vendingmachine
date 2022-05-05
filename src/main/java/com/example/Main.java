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
        VendingMachine vm = new VendingMachine("input.json");
        vm.showItems();
    }
}
