/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neochem_client.dialogs;

import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import neochem_client.models.Item;

/**
 *
 * @author JanithaT
 */
public class InfoDialog {
    
    public Dialog getInfoDialog(){
        Dialog<Item> dialog = new Dialog<>();
        dialog.setTitle("Info");
        dialog.setHeaderText("Barcode Information");
        
        ButtonType loginButtonType = new ButtonType("Close", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 100, 10, 10));
        
        Label Frag = new Label("Fragrance (F)");
        Frag.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        Label coun = new Label("Country");
        coun.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        Label ye = new Label("Year");
        ye.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        
        grid.add(Frag, 0, 0);
        grid.add(coun, 0, 1);
        grid.add(new Label("I - India"), 1, 1);
        grid.add(new Label("D - Indonesia"), 2, 1);
        grid.add(new Label("F - France"), 3, 1);
        grid.add(ye, 0, 2);
        grid.add(new Label("(2015 --> 15)"),1,2);
        
        
        Label Flv = new Label("Flavor (L)");
        Flv.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        Label FlvF = new Label("Flavor Format");
        FlvF.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        Label FlvT = new Label("Flavor Type");
        FlvT.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        Label coun2 = new Label("Country");
        coun2.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        Label ye2 = new Label("Year");
        ye2.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        
        grid.add(Flv, 0, 4);
        grid.add(FlvF, 0, 5);
        grid.add(new Label("L - Liquid"), 1, 5);
        grid.add(new Label("P - Powder"), 2, 5);
        grid.add(new Label("G - Granual"), 3, 5);
        grid.add(FlvT, 0, 6);
        grid.add(new Label("A - Artificial"), 1, 6);
        grid.add(new Label("N - Natural"), 2, 6);
        grid.add(new Label("I - Natural Identical"), 3, 6);
        
        grid.add(coun2, 0, 7);
        grid.add(new Label("I - India"), 1, 7);
        grid.add(new Label("D - Indonesia"), 2, 7);
        grid.add(new Label("F - France"), 3, 7);
        grid.add(ye2, 0, 8);
        grid.add(new Label("(2015 --> 15)"),1,8);
        
        
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));

        vbox.getChildren().addAll(grid);
        dialog.getDialogPane().setContent(vbox);
        
        return dialog;
    }
}
