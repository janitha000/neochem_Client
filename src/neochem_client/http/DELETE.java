/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neochem_client.http;

import java.net.HttpURLConnection;
import java.net.URL;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author JanithaT
 */
public class DELETE {

    public String sendDelete(int id) throws Exception {

        String url = "http://192.168.1.130:8080/neochem/webapi/items/" + id;
        //String url = "http://localhost:8080/neochem/webapi/items/" + id;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setDoOutput(true);
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestMethod("DELETE");
        con.connect();
        int responseCode = con.getResponseCode();

        System.out.println("\nSending 'PUT' request to URL : " + url);

        System.out.println("Response Code : " + responseCode);
        if (responseCode == 200 || responseCode == 204) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");

            alert.setContentText("Item Deleted Successfully");

            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");

            alert.setContentText("CODE: " + Integer.toString(responseCode));

            alert.showAndWait();
        }

        return null;

    }
}
