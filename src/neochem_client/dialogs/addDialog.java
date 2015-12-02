/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neochem_client.dialogs;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
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
//        TextField NewCode = new TextField();
//        NewCode.setPromptText("New Code");
        ToggleGroup group = new ToggleGroup();
        FcheckBox = new RadioButton("Fragrance");
        LcheckBox = new RadioButton("Flavor");
        FcheckBox.setToggleGroup(group);
        LcheckBox.setToggleGroup(group);

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {

                RadioButton chk = (RadioButton) t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
                if (chk.getText().equals("Fragrance")) {
                    Code = "R";
                }
                if (chk.getText().equals("Flavor")) {
                    Code = "L";
                }
                System.out.println(Code);
            }
        });

        grid.add(new Label("Mane Code:"), 0, 0);
        grid.add(FormerCode, 2, 0);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));

        vbox.getChildren().addAll(grid, FcheckBox, LcheckBox);

// Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

// Do some validation (using the Java 8 lambda syntax).
        FormerCode.textProperty().addListener((observable, oldValue, newValue) -> {
            group.selectedToggleProperty().addListener((observable1, oldValue1, newValue1) -> {
                Boolean a = newValue.trim().isEmpty();
                Boolean b = newValue1.isSelected();
                Boolean f =  a && b;
                loginButton.setDisable(newValue.trim().isEmpty() && newValue1.isSelected());
                
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
                    return new Item(11, FormerCode.getText(), Code);
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
