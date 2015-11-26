/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neochem_client.http;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import javafx.scene.control.Alert;
import neochem_client.models.Item;
import org.json.simple.JSONObject;

/**
 *
 * @author JanithaT
 */
public class UPDATE {
    
    private final String USER_AGENT = "Mozilla/5.0";

    public void sendUpdate(Item item) throws Exception {
        int id = item.getID();
        
        String url = "http://192.168.1.130:8080/neochem/webapi/items/" +id;
       //  String url = "http://localhost:8080/neochem/webapi/items/"+id;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        con.setRequestProperty("Accept", "application/json");
        
         con.setDoOutput(true);
        
        
        con.setRequestMethod("PUT");
        OutputStreamWriter out = new OutputStreamWriter(
                con.getOutputStream());

        
        JSONObject cred = new JSONObject();
        cred.put("formerCode",item.getFormerCode());
        cred.put("newCode", item.getNewCode());
        cred.put("id", item.getID());
        
       
        
        out.write(cred.toString());
        out.flush();
        out.close();
        
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'PUT' request to URL : " + url);
        
        System.out.println("Response Code : " + responseCode);
        con.getInputStream();
        
        if (responseCode == 200 || responseCode == 204) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");

            alert.setContentText("Item Updated Successfully");

            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");

            alert.setContentText("CODE: " + Integer.toString(responseCode));

            alert.showAndWait();
        }
    }
}
