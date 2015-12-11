/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neochem_client.models;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

/**
 *
 * @author JanithaT
 */
public class Table {
    private TextField filterField = new TextField("Search");
    private TableView<Item> table = new TableView<Item>();
    final VBox vbox = new VBox();
    
    public VBox getTable(ArrayList<Item> items){
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

                if (item.getManeCode().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (item.getNeoChemCode().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });
        
        SortedList<Item> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
        
        table.setEditable(true);

        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);
        
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll( filterField, table);
        
        return vbox;
    }
}
