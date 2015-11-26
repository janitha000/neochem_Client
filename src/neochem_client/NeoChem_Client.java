/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neochem_client;

import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import neochem_client.dialogs.WarningE;
import neochem_client.barcode.barcodeGenerator;
import neochem_client.dialogs.addDialog;
import neochem_client.dialogs.confirmation;
import neochem_client.dialogs.updateDialog;
import neochem_client.http.DELETE;
import neochem_client.http.GET;
import neochem_client.http.POST;
import neochem_client.http.UPDATE;
import neochem_client.models.Item;
import net.sourceforge.barbecue.BarcodeException;

/**
 *
 * @author JanithaT
 */
public class NeoChem_Client extends Application {

    public WarningE alerts = new WarningE();
    public addDialog addD = new addDialog();
    public confirmation con = new confirmation();

    @FXML
    private TextField filterField = new TextField("Search");
    ArrayList<Item> items = new ArrayList<>();
    private TableView<Item> table = new TableView<Item>();
    final HBox hb = new HBox();
    GET get = new GET();

    @Override
    public void start(Stage stage) throws Exception {

        items = get.sendGet();
        ObservableList<Item> data
                = FXCollections.observableArrayList(items);
        FilteredList<Item> filteredData = new FilteredList<>(data, p -> true);

        TableColumn firstNameCol = new TableColumn("ID");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<Item, String>("ID"));

        TableColumn lastNameCol = new TableColumn("Former Code");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<Item, String>("formerCode"));

        TableColumn emailCol = new TableColumn("New Code");
        emailCol.setMinWidth(200);
        emailCol.setCellValueFactory(
                new PropertyValueFactory<Item, String>("newCode"));

        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(item -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (item.getFormerCode().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (item.getNewCode().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });

        SortedList<Item> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);

        //table.setItems(data);
        final TextField addLastName = new TextField();
        addLastName.setMaxWidth(lastNameCol.getPrefWidth());
        addLastName.setPromptText("Former Code");
        final TextField addEmail = new TextField();
        addEmail.setMaxWidth(emailCol.getPrefWidth());
        addEmail.setPromptText("New Code");

        final Button addButton = new Button("Add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Dialog dialog = addD.addD();
                Optional<Item> result = dialog.showAndWait();

                if (result.isPresent()) {
                    Item item = result.get();
                    POST post = new POST();
                    try {

                        post.sendPost(item);
                    } catch (Exception ex) {
                        Logger.getLogger(NeoChem_Client.class.getName()).log(Level.SEVERE, null, ex);
                        Alert alert = alerts.getWarningException(ex);
                        alert.showAndWait();
                    }
                }

            }

        });
        final Button generateButton = new Button("Generate");

        generateButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Item selectedItem = table.getSelectionModel().getSelectedItem();
                if (selectedItem == null) {
                    Alert alert = alerts.getWarning("Please select an Item from the table");

                    alert.showAndWait();

                } else {

                    try {
                        barcodeGenerator.generateBarCode(selectedItem.getNewCode());
                    } catch (BarcodeException ex) {
                        Logger.getLogger(NeoChem_Client.class.getName()).log(Level.SEVERE, null, ex);
                        Alert alert = alerts.getWarningException(ex);
                        alert.showAndWait();
                    }

                }
            }
        });

        final Button updateButton = new Button("Update");

        updateButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Item selectedItem = table.getSelectionModel().getSelectedItem();
                if (selectedItem == null) {
                    Alert alert = alerts.getWarning("Please select an Item from the table");

                    alert.showAndWait();

                } else {
                    
                    updateDialog upd = new updateDialog();
                    Dialog dialog = upd.updateD(selectedItem);
                    Optional<Item> result = dialog.showAndWait();
                    
                    if (result.isPresent()) {
                    Item item = result.get();
                    UPDATE update = new UPDATE();
                    try {

                        update.sendUpdate(item);
                    } catch (Exception ex) {
                        Logger.getLogger(NeoChem_Client.class.getName()).log(Level.SEVERE, null, ex);
                        Alert alert = alerts.getWarningException(ex);
                        alert.showAndWait();
                    }
                }


                }
            }
        });

        final Button deleteButton = new Button("Delete");

        deleteButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Alert conAlert = con.con();
                Optional<ButtonType> result = conAlert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Item selectedItem = table.getSelectionModel().getSelectedItem();
                    if (selectedItem == null) {
                        Alert alert = alerts.getWarning("Please select an Item from the table");

                        alert.showAndWait();

                    } else {
                        int id = selectedItem.getID();
                        DELETE delete = new DELETE();
                        try {
                            delete.sendDelete(id);
                        } catch (Exception ex) {
                            Logger.getLogger(NeoChem_Client.class.getName()).log(Level.SEVERE, null, ex);
                            Alert alert = alerts.getWarningException(ex);
                            alert.showAndWait();
                        }

                    }
                } else {

                }

            }

        });

        hb.getChildren().addAll(addButton, updateButton, deleteButton, generateButton);
        hb.setSpacing(3);

        Scene scene = new Scene(new Group());
        stage.setTitle("Table View Sample");
        stage.setWidth(500);
        stage.setHeight(550);

        final Label label = new Label("NeoChem");
        //label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));

        vbox.getChildren().addAll(label, filterField, table, hb);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
