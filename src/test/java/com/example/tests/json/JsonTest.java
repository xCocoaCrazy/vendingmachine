package com.example.tests.json;

import com.example.json.Json;
import com.example.pojos.VendingMachinePOJO;
import com.example.vending_machine.VendingMachine;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JsonTest {

    String testData = "{ \"title\": \"CoderFromScratch\"}";
    String vendingMachineTestData = "{\n" +
            "\t\"config\": {\n" +
            "\t\t\"rows\": 4,\n" +
            "\t\t\"columns\": \"8\"\n" +
            "\t},\n" +
            "\t\"items\": [{\n" +
            "\t\t\"name\": \"Snickers\",\n" +
            "\t\t\"amount\": 10,\n" +
            "\t\t\"price\": \"$1.35\"\n" +
            "\t}, {\n" +
            "\t\t\"name\": \"Hersheys\",\n" +
            "\t\t\"amount\": 10,\n" +
            "\t\t\"price\": \"$2.25\"\n" +
            "\t}, {\n" +
            "\t\t\"name\": \"Hersheys Almond\",\n" +
            "\t\t\"amount\": 10,\n" +
            "\t\t\"price\": \"$1.80\"\n" +
            "\t}, {\n" +
            "\t\t\"name\": \"Hersheys Special Dark\",\n" +
            "\t\t\"amount\": 10,\n" +
            "\t\t\"price\": \"$1.75\"\n" +
            "\t}, {\n" +
            "\t\t\"name\": \"Reese's\",\n" +
            "\t\t\"amount\": 10,\n" +
            "\t\t\"price\": \"$1.05\"\n" +
            "\t}, {\n" +
            "\t\t\"name\": \"Nutrageous\",\n" +
            "\t\t\"amount\": 10,\n" +
            "\t\t\"price\": \"$1.30\"\n" +
            "\t}, {\n" +
            "\t\t\"name\": \"Baby Ruth\",\n" +
            "\t\t\"amount\": 10,\n" +
            "\t\t\"price\": \"$2.50\"\n" +
            "\t}, {\n" +
            "\t\t\"name\": \"Milky Way\",\n" +
            "\t\t\"amount\": 10,\n" +
            "\t\t\"price\": \"$1.00\"\n" +
            "\t}, {\n" +
            "\t\t\"name\": \"M&M\",\n" +
            "\t\t\"amount\": 10,\n" +
            "\t\t\"price\": \"$1.25\"\n" +
            "\t}]\n" +
            "}\n";

    @Test
    void parse() throws JsonProcessingException {
        JsonNode node = Json.parse(testData);
        assertEquals("CoderFromScratch", node.get("title").asText());
    }

    @Test
    void fromJson() throws IOException {
        JsonNode node = Json.parse(testData);
        TestPOJO pojo = Json.fromJson(node, TestPOJO.class);

        assertEquals("CoderFromScratch", pojo.getTitle());
    }

    @Test
    void jsonScenario() throws IOException {
        JsonNode node = Json.parse(vendingMachineTestData);
        VendingMachinePOJO pojo = Json.fromJson(node, VendingMachinePOJO.class);

        System.out.println("config: " + pojo.getConfig().getRows() + "," + pojo.getConfig().getColumns());
        pojo.getItems().stream()
                .forEach(e -> System.out.println(e));
    }
}