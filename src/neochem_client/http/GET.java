/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neochem_client.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import neochem_client.dialogs.WarningE;
import neochem_client.models.Item;
import org.json.*;

/**
 *
 * @author JanithaT
 */
public class GET {

    private final String USER_AGENT = "Mozilla/5.0";
    public WarningE alerts = new WarningE();

    public ArrayList<Item> sendGet() throws Exception {

        String url = "http://192.168.1.4:8080/neochem/webapi/items";
        //String url = "http://localhost:8080/neochem/webapi/items";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = 0;

        try {
            responseCode = con.getResponseCode();
        } catch (Exception ex) {
            Alert alert = alerts.getWarningException(ex);
            alert.showAndWait();
        }
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        String jsonString = response.toString();
        jsonString = "{ Items: " + jsonString;
        jsonString = jsonString + "}";
        System.out.println(jsonString);

        JSONObject jsonObj = new JSONObject(jsonString);
        JSONArray jsonArray = jsonObj.getJSONArray("Items");

        ArrayList<Item> items = new ArrayList<Item>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonItem = (JSONObject) jsonArray.get(i);
            Item item = new Item(jsonItem.getInt("id"), jsonItem.getString("maneCode"), jsonItem.getString("maneName"),
            jsonItem.getString("type"),jsonItem.getString("flavorFormat"),jsonItem.getString("flavorType"),jsonItem.getInt("year"),jsonItem.getString("country"),
            jsonItem.getString("neoChemName"),jsonItem.getString("neoChemCode"));
            items.add(item);

        }

        return items;

    }
    


    public int getCode(String code) throws Exception{
        //String url = "http://localhost:8080/neochem/webapi/code/" + code;
        String url = "http://192.168.1.4:8080/neochem/webapi/code/" + code;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = 0;

        try {
            responseCode = con.getResponseCode();
        } catch (Exception ex) {
            Alert alert = alerts.getWarningException(ex);
            alert.showAndWait();
        }
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        
        String lastCode = response.toString();
        lastCode = lastCode.replaceAll("[^\\d.]", "");
//        lastCode = lastCode.replace("R", "");
//        lastCode = lastCode.replace("L", "");
//        lastCode = lastCode.replace("P", "");
//        lastCode = lastCode.replace("G", "");
//        lastCode = lastCode.replace("I", "");
//        lastCode = lastCode.replace("D", "");
//        lastCode = lastCode.replace("F", "");
//        lastCode = lastCode.replace("F", "");
//        lastCode = lastCode.replace("F", "");
        int lastintCode = Integer.parseInt(lastCode);
       
        return lastintCode;
    }
    
    
}
