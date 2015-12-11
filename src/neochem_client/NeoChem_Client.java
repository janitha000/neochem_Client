/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neochem_client;

import com.sun.javaws.jnl.InformationDesc;
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
import javafx.geometry.Pos;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import neochem_client.dialogs.WarningE;
import neochem_client.barcode.barcodeGenerator;
import neochem_client.barcode.barcodeLogic;
import neochem_client.dialogs.InfoDialog;
import neochem_client.dialogs.addDialog;
import neochem_client.dialogs.confirmation;
import neochem_client.dialogs.updateDialog;
import neochem_client.http.DELETE;
import neochem_client.http.GET;
import neochem_client.http.POST;
import neochem_client.http.UPDATE;
import neochem_client.models.Item;
import neochem_client.models.Table;
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
    private TextField filterField = new TextField();

    ArrayList<Item> items = new ArrayList<>();
    private TableView<Item> table = new TableView<Item>();
    final HBox hb = new HBox();
    GET get = new GET();
    VBox vbox = new VBox();
    Table tables = new Table();
    ObservableList<Item> data;

    @Override
    public void start(Stage stage) throws Exception {
        filterField.setPromptText("Search");
        filterField.setMinWidth(700);

        items = get.sendGet();
        //generateTable(items);

        data = FXCollections.observableArrayList(items);
        FilteredList<Item> filteredData = new FilteredList<>(data, p -> true);

//        TableColumn firstNameCol = new TableColumn("ID");
//        firstNameCol.setMinWidth(100);
//        firstNameCol.setCellValueFactory(
//                new PropertyValueFactory<Item, String>("ID"));
        TableColumn maneCodeCol = new TableColumn("Mane Code");
        maneCodeCol.setMinWidth(100);
        maneCodeCol.setCellValueFactory(
                new PropertyValueFactory<Item, String>("ManeCode"));

        TableColumn maneNameCol = new TableColumn("Mane Name");
        maneNameCol.setMinWidth(100);
        maneNameCol.setCellValueFactory(
                new PropertyValueFactory<Item, String>("ManeName"));

        TableColumn TypeCol = new TableColumn("Type");
        TypeCol.setMinWidth(100);
        TypeCol.setCellValueFactory(
                new PropertyValueFactory<Item, String>("Type"));

        TableColumn flavorFormatCol = new TableColumn("Flavor Format");
        flavorFormatCol.setMinWidth(100);
        flavorFormatCol.setCellValueFactory(
                new PropertyValueFactory<Item, String>("FlavorFormat"));

        TableColumn flavorTypeCol = new TableColumn("Flavor Type");
        flavorTypeCol.setMinWidth(100);
        flavorTypeCol.setCellValueFactory(
                new PropertyValueFactory<Item, String>("FlavorType"));

        TableColumn yearCol = new TableColumn("Year");
        yearCol.setMinWidth(100);
        yearCol.setCellValueFactory(
                new PropertyValueFactory<Item, String>("Year"));
        yearCol.setStyle("-fx-alignment: CENTER;");
        

        TableColumn countryCol = new TableColumn("Country");
        countryCol.setMinWidth(100);
        countryCol.setCellValueFactory(
                new PropertyValueFactory<Item, String>("Country"));

        TableColumn neoNameCol = new TableColumn("NeoChemName");
        neoNameCol.setMinWidth(100);
        neoNameCol.setCellValueFactory(
                new PropertyValueFactory<Item, String>("NeoChemName"));

        TableColumn neoChemCol = new TableColumn("NeoChem Code");
        neoChemCol.setMinWidth(100);
        neoChemCol.setCellValueFactory(
                new PropertyValueFactory<Item, String>("NeoChemCode"));
        

        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(item -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (item.getManeCode().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (item.getNeoChemCode().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }  else if (item.getManeName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (item.getType().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (item.getFlavorFormat().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (item.getFlavorType().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (Integer.toString(item.getYear()).toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (item.getCountry().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (item.getNeoChemName().toLowerCase().contains(lowerCaseFilter)) {
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
        //addLastName.setMaxWidth(lastNameCol.getPrefWidth());
        addLastName.setPromptText("Mane Code");
        final TextField addEmail = new TextField();
        //addEmail.setMaxWidth(emailCol.getPrefWidth());
        addEmail.setPromptText("New Code");

        final Button refresh = new Button("Refresh");
        refresh.setMinWidth(100);
        refresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    generateTable(get.sendGet());
                } catch (Exception ex) {
                    Logger.getLogger(NeoChem_Client.class.getName()).log(Level.SEVERE, null, ex);
                    Alert alert = alerts.getWarningException(ex);
                    alert.showAndWait();
                }

            }

        });

        final Button addButton = new Button("Add");
        addButton.setMinWidth(150);
        addButton.setMinHeight(50);
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
                        generateTable(get.sendGet());
                    } catch (Exception ex) {
                        Logger.getLogger(NeoChem_Client.class.getName()).log(Level.SEVERE, null, ex);
                        Alert alert = alerts.getWarningException(ex);
                        alert.showAndWait();
                    }
                }

            }

        });
        final Button generateButton = new Button("Print Bar Code");

        generateButton.setMinWidth(150);
        generateButton.setMinHeight(50);
        generateButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Item selectedItem = table.getSelectionModel().getSelectedItem();
                if (selectedItem == null) {
                    Alert alert = alerts.getWarning("Please select an Item from the table");

                    alert.showAndWait();

                } else {

                    try {
                        barcodeGenerator.generateBarCode(selectedItem.getNeoChemCode());
                    } catch (BarcodeException ex) {
                        Logger.getLogger(NeoChem_Client.class.getName()).log(Level.SEVERE, null, ex);
                        Alert alert = alerts.getWarningException(ex);
                        alert.showAndWait();
                    }

                }
            }
        });

        final Button infoButton = new Button("Info");
        infoButton.setMinWidth(100);
//        updateButton.setMinWidth(150);
//        updateButton.setMinHeight(50);

        infoButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                InfoDialog ID = new InfoDialog();
                Dialog dialog = ID.getInfoDialog();
                dialog.showAndWait();
        
            }
        });

        final Button deleteButton = new Button("Delete");
        deleteButton.setMinWidth(150);
        deleteButton.setMinHeight(50);

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
                        int id = (int) selectedItem.getId();
                        DELETE delete = new DELETE();
                        try {
                            delete.sendDelete(id);
                            generateTable(get.sendGet());
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

        hb.getChildren().addAll(addButton, generateButton); //remove delete button for normal user
        hb.setSpacing(3);

        Scene scene = new Scene(new Group());
        stage.setTitle("Item Generator");

        stage.setWidth(930);
        stage.setHeight(580);
        stage.resizableProperty().setValue(Boolean.FALSE);

        final Label label = new Label("NeoChem International (Pvt)Ltd");
        label.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        label.setAlignment(Pos.CENTER);
        //label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        table.getColumns().addAll(maneCodeCol, maneNameCol, TypeCol, flavorFormatCol, flavorTypeCol, yearCol, countryCol, neoNameCol, neoChemCol);

        HBox hbox2 = new HBox();
        hbox2.setSpacing(3);
        hbox2.getChildren().addAll(filterField, infoButton, refresh);

        final VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 20, 30, 10));

        vbox.getChildren().addAll(label, hbox2, table, hb);
        vbox.setAlignment(Pos.CENTER);

        //vbox.getChildren().add(hb);
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

    public void generateTable(ArrayList<Item> items) {
        ObservableList<Item> newdata = FXCollections.observableArrayList(items);
        data.removeAll(data);

        for (Item newdata1 : newdata) {
            data.add(newdata1);
        }
        FilteredList<Item> filteredData = new FilteredList<>(data, p -> true);

//        TableColumn firstNameCol = new TableColumn("ID");
//        firstNameCol.setMinWidth(100);
//        firstNameCol.setCellValueFactory(
//                new PropertyValueFactory<Item, String>("ID"));
//
//        TableColumn lastNameCol = new TableColumn("Former Code");
//        lastNameCol.setMinWidth(100);
//        lastNameCol.setCellValueFactory(
//                new PropertyValueFactory<Item, String>("formerCode"));
//
//        TableColumn emailCol = new TableColumn("New Code");
//        emailCol.setMinWidth(200);
//        emailCol.setCellValueFactory(
//                new PropertyValueFactory<Item, String>("newCode"));
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(item -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (item.getManeCode().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (item.getNeoChemCode().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }  else if (item.getManeName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (item.getType().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (item.getFlavorFormat().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (item.getFlavorType().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (Integer.toString(item.getYear()).toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (item.getCountry().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (item.getNeoChemName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } 
                return false; // Does not match.
            });
        });

        SortedList<Item> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);

        table.setEditable(true);

        //table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);
//        vbox.setSpacing(5);
//        vbox.setPadding(new Insets(10, 0, 0, 10));
//        vbox.getChildren().addAll( filterField, table,hb);
    }
}
