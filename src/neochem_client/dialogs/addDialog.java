/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neochem_client.dialogs;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import neochem_client.models.Item;

/**
 *
 * @author JanithaT
 */
public class addDialog {

    public RadioButton FcheckBox;
    public RadioButton LcheckBox;
    public String Code = "";
    WarningE alerts = new WarningE();

    public Dialog addD() {

        // Create the custom dialog.
        Dialog<Item> dialog = new Dialog<>();
        dialog.setTitle("Add Items");
        dialog.setHeaderText("Add New Items");

// Set the button types.
        ButtonType loginButtonType = new ButtonType("Add", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

// Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 100, 10, 10));

        TextField FormerCode = new TextField();
        FormerCode.setMinWidth(300);
        FormerCode.setPromptText("Mane Code");

        TextField Manename = new TextField();
        Manename.setMinWidth(300);
        Manename.setPromptText("Mane Name");
        Label mName = new Label("Mane Name:");

        ToggleGroup group = new ToggleGroup();
        FcheckBox = new RadioButton("Fragrance");
        LcheckBox = new RadioButton("Flavor");
        FcheckBox.setToggleGroup(group);
        LcheckBox.setToggleGroup(group);

        ObservableList<String> FlavorFormats = FXCollections.observableArrayList(
                "Liquid",
                "Powder",
                "Granual");
        final ComboBox FlavorBox = new ComboBox(FlavorFormats);
        final Label flavorFormatLabel = new Label("Flavor Format: ");
        FlavorBox.setValue("");

        ObservableList<String> FlavorTypes = FXCollections.observableArrayList(
                "Artificial",
                "Nature Identical",
                "Natural");
        final ComboBox FlavorTypesBox = new ComboBox(FlavorTypes);
        final Label flavorTyprLabel = new Label("Flavor Type: ");
        FlavorTypesBox.setValue("");

        TextField year = new TextField();
        year.setMinWidth(50);
        year.setPromptText("Year");
        Label yearLabel = new Label("Year:");

        ObservableList<String> Country = FXCollections.observableArrayList(
                "India",
                "Indonesia",
                "France");
        final ComboBox countryBox = new ComboBox(Country);
        final Label CountryLabel = new Label("Country: ");
        countryBox.setValue("");

        TextField neoName = new TextField();
        neoName.setMinWidth(100);
        neoName.setPromptText("NeoChem Name");
        Label neoNameLabel = new Label("NoeChem Name:");

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {

                RadioButton chk = (RadioButton) t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
                if (chk.getText().equals("Fragrance")) {
                    Code = "Fragrance";
                }
                if (chk.getText().equals("Flavor")) {
                    Code = "Flavor";
                }
                System.out.println(Code);
            }
        });

        grid.add(new Label("Mane Code:"), 0, 0);
        grid.add(FormerCode, 1, 0);
        grid.add(mName, 0, 1);
        grid.add(Manename, 1, 1);
        grid.add(FcheckBox, 0, 2);
        grid.add(LcheckBox, 1, 2);
        grid.add(flavorFormatLabel, 0, 3);
        grid.add(FlavorBox, 1, 3);
        grid.add(flavorTyprLabel, 2, 3);
        grid.add(FlavorTypesBox, 3, 3);
        grid.add(yearLabel, 0, 4);
        grid.add(year, 1, 4);
        grid.add(CountryLabel, 2, 4);
        grid.add(countryBox, 3, 4);
        grid.add(neoNameLabel, 0, 6);
        grid.add(neoName, 1, 6);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));

        vbox.getChildren().addAll(grid);

// Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

// Do some validation (using the Java 8 lambda syntax).
        FormerCode.textProperty().addListener((observable, oldValue, newValue) -> {
            group.selectedToggleProperty().addListener((observable1, oldValue1, newValue1) -> {
                year.textProperty().addListener((observable2, oldValue2, newValue2) -> {
                    Boolean a = newValue.trim().isEmpty();
                    Boolean b = newValue1.isSelected();
                    Boolean c = newValue2.trim().isEmpty();
                    Boolean f = a && b;
                    loginButton.setDisable(newValue.trim().isEmpty() && newValue1.isSelected() && newValue2.trim().isEmpty());

                });

            });
        });

        dialog.getDialogPane().setContent(vbox);

// Request focus on the username field by default.
        Platform.runLater(() -> FormerCode.requestFocus());

        //Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {

            if (dialogButton == loginButtonType) {
                if (Code.equals("")) {
                    Alert alert = alerts.getWarning("Please select from Fragrance or Flavour");
                    alert.showAndWait();
                    return null;
                } else {

                    String flavorFormat = FlavorBox.getValue().toString();
                    String flavorType = FlavorTypesBox.getValue().toString();
                    return new Item(11, FormerCode.getText(), Manename.getText(), Code, flavorFormat, flavorType, Integer.parseInt(year.getText()), countryBox.getValue().toString(), neoName.getText(), "");
                    //return null;
                }
            }
            return null;

        });

        return dialog;

    }

    private void handleButtonAction(ActionEvent e) {
        String code = "";

        if (FcheckBox.isSelected()) {
            Code = "R";
        }
        if (LcheckBox.isSelected()) {
            Code = "L";
        }

        System.out.println(code);

    }
}
