package com.example.vaadin.main;

import com.example.vending_machine.VendingMachine;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import elemental.css.CSSStyleDeclaration;

import javax.swing.*;
import java.io.*;

@PageTitle("Vending Machine")
@Route(value = "")
public class MainView extends VerticalLayout {
    private VendingMachine vendingMachine;
    private final TextField insertItemToBuyTextField;
    private final Button insertItemToBuyButton;
    private int numberOfTimesPressedGetItem = 0;
    private final MemoryBuffer memoryBuffer;
    private Html html;

    public MainView() {
        //Adding the refresh button to the UI
        Button refreshButton = new Button("Refresh the items");
        refreshButton.addClickListener(e -> refresh());
        add(refreshButton);

        //Adding the searching for item text field and button to the UI
        insertItemToBuyTextField = new TextField();
        insertItemToBuyTextField.setPlaceholder("Enter Item (ex. A1)");
        insertItemToBuyButton = new Button("Get Item");
        insertItemToBuyButton.addClickListener(e -> Notification.show(startVendingMachine(insertItemToBuyTextField.getValue())));

        //The default file for userInput
        File userInputFile = new File("FileResources/userInput.json");

        //The file Uploader for user
        memoryBuffer = new MemoryBuffer();
        Upload uploadJsonFile = new Upload(memoryBuffer);
        uploadJsonFile.addSucceededListener(event -> {
            // Get information about the uploaded file
            InputStream fileData = memoryBuffer.getInputStream();
            //Copies the user input to the directory of userInputFile
            try {
                copyInputStreamToFileJava9(fileData, userInputFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        //Selection what to do with the json file
        Button updateAllVendingMachine = new Button("Reset Items");
        Button addOrUpdateVendingMachine = new Button("Add or Update Items");

        updateAllVendingMachine.addClickListener(e -> {
            vendingMachine.updateAllItems(userInputFile.getAbsolutePath());
            refresh();
            userInputFile.delete();
        });

        addOrUpdateVendingMachine.addClickListener(e -> {
            vendingMachine.addOrUpdateItems(userInputFile.getAbsolutePath());
            refresh();
            userInputFile.delete();
        });

        uploadJsonFile.setAcceptedFileTypes(".json");
        uploadJsonFile.setMaxFiles(1);

        uploadJsonFile.addFileRejectedListener(event -> {
            String errorMessage = event.getErrorMessage();

            Notification notification = Notification.show(
                    errorMessage,
                    5000,
                    Notification.Position.MIDDLE
            );
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        });

        insertItemToBuyButton.setWidth(120, Unit.PIXELS);
        HorizontalLayout getItem = new HorizontalLayout(insertItemToBuyTextField, insertItemToBuyButton, uploadJsonFile, updateAllVendingMachine, addOrUpdateVendingMachine);
        getItem.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        add(getItem);

        vendingMachine = new VendingMachine("FileResources/input.json");
        html = new Html(vendingMachine.generateTableToShowItems());
        add(html);
    }

    //Method to refresh the table
    private void refresh() {
        remove(html);
        html = new Html(vendingMachine.generateTableToShowItems());
        add(html);
    }

    //Method to start the Vending Machine when pressing the button to search for Item
    private String startVendingMachine(String position) {
        if(!vendingMachine.startVendingMachine(position).equals("")) {
            return vendingMachine.startVendingMachine(position);
        }
        Button addMoney = new Button("Insert Money");

        NumberField money = new NumberField();
        money.setLabel("Insert here!");
        money.setValue(0.0);
        Div dollarPrefix = new Div();
        dollarPrefix.setText("$");
        money.setPrefixComponent(dollarPrefix);

        NumberField balanceInVendingMachine = new NumberField("Balance");
        balanceInVendingMachine.setValue(0.0);
        Div dollarPrefixForBalance = new Div();
        dollarPrefixForBalance.setText("$");
        balanceInVendingMachine.setPrefixComponent(dollarPrefixForBalance);
        balanceInVendingMachine.setReadOnly(true);

        HorizontalLayout hl = new HorizontalLayout(money, addMoney);
        hl.setDefaultVerticalComponentAlignment(Alignment.END);

        //Refreshing the table
        if(numberOfTimesPressedGetItem == 0) {
            remove(html);
            add(hl);
            add(balanceInVendingMachine);
            add(html);
            numberOfTimesPressedGetItem++;
        }

        boolean[] payed = {false};
        double[] amountLeftToPay = {vendingMachine.getItems().get(vendingMachine.getPositionOfItemFromString(position)[0]).get(vendingMachine.getPositionOfItemFromString(position)[1]).getPrice()};
        addMoney.addClickListener(e -> {
            insertItemToBuyButton.setEnabled(false);
            insertItemToBuyTextField.setEnabled(false);
            amountLeftToPay[0] -= money.getValue();
            if(amountLeftToPay[0] <= 0) {
                Notification.show("Here you go, take your " + vendingMachine.getItems().get(vendingMachine.getPositionOfItemFromString(position)[0]).get(vendingMachine.getPositionOfItemFromString(position)[1]).getName());
                vendingMachine.giveItemToUser(position);
                refresh();
                insertItemToBuyButton.setEnabled(true);
                insertItemToBuyTextField.setEnabled(true);
                money.setValue(0.0);
                balanceInVendingMachine.setValue(0.0);
                numberOfTimesPressedGetItem--;
                payed[0] = true;
            } else {
                Notification.show("$" + money.getValue().toString() + " added to balance!");
                balanceInVendingMachine.setValue(balanceInVendingMachine.getValue() + money.getValue());
            }
            if(payed[0]) {
                remove(hl, balanceInVendingMachine);
            }
        });
        return "";
    }

    //Transform and InputStream to a file
    private static void copyInputStreamToFileJava9(InputStream input, File file)
            throws IOException {

        // append = false
        try (OutputStream output = new FileOutputStream(file, false)) {
            input.transferTo(output);
        }

    }

}