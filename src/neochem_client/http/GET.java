/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neochem_client.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import neochem_client.models.Item;
import org.json.*;

/**
 *
 * @author JanithaT
 */
public class GET {

    private final String USER_AGENT = "Mozilla/5.0";

    public ArrayList<Item> sendGet() throws Exception {

        String url = "http://192.168.1.130:8080/neochem/webapi/items";
        //String url = "http://localhost:8080/neochem/webapi/items";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
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
            Item item = new Item(jsonItem.getInt("id"), jsonItem.getString("formerCode"), jsonItem.getString("newCode"));
            items.add(item);
            
            
        }
        
      return items;

    }
}
