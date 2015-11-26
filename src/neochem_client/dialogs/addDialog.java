/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neochem_client.dialogs;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import neochem_client.models.Item;

/**
 *
 * @author JanithaT
 */
public class addDialog {

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
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField FormerCode = new TextField();
        FormerCode.setPromptText("Former Code");
        TextField NewCode = new TextField();
        NewCode.setPromptText("New Code");

        grid.add(new Label("Former Code:"), 0, 0);
        grid.add(FormerCode, 1, 0);
        grid.add(new Label("New Code"), 0, 1);
        grid.add(NewCode, 1, 1);

// Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

// Do some validation (using the Java 8 lambda syntax).
        FormerCode.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

// Request focus on the username field by default.
        Platform.runLater(() -> FormerCode.requestFocus());
        


 //Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            
            if (dialogButton == loginButtonType) {
                return new Item(11, FormerCode.getText(), NewCode.getText());
            }
            return null;
        });
     return dialog;

    }
}
