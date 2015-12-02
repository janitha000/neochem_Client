/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neochem_client.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
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
public class POST {

    private final String USER_AGENT = "Mozilla/5.0";
    public GET get = new GET();
    public String newCode = "";
    public int code = 0;

    public void sendPost(Item item) throws Exception {

        String url = "http://192.168.1.130:8080/neochem/webapi/items";
        //String url = "http://localhost:8080/neochem/webapi/items";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();  //https????

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        con.setRequestProperty("Accept", "application/json");

        String charValue = item.getNewCode();
        if (charValue.equals("R")) {
            code = get.getCode("R") + 1;
        }
        if (charValue.equals("L")) {
            code = get.getCode("L") + 1;
        }

        newCode = charValue + Integer.toString(code);
        JSONObject cred = new JSONObject();
        cred.put("formerCode", item.getFormerCode());
        cred.put("newCode", newCode);

        // Send post request
        con.setDoOutput(true);
//        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
//        wr.writeBytes(urlParameters);
//        wr.flush();
//        wr.close();

        OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
        wr.write(cred.toString());
        wr.flush();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);

        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

        if (responseCode == 200 || responseCode == 204) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");

            alert.setContentText("Item Added Successfully");

            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");

            alert.setContentText("CODE: " + Integer.toString(responseCode));

            alert.showAndWait();
        }

    }

}
