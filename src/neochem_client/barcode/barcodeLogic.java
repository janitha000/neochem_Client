/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neochem_client.barcode;

import neochem_client.http.GET;
import neochem_client.models.Item;

/**
 *
 * @author JanithaT
 */
public class barcodeLogic {
    public GET get = new GET();

    public String generateBarCode(Item item) throws Exception {
        String code = "";

        String type = item.getType();
        switch (type) {
            case "Fragrance":
                code = "R";
                break;

            case "Flavor":
                code = "V";
                break;
        }

        String FlavorFormat = item.getFlavorFormat();
        String c = "";
        switch (FlavorFormat) {
            case "Liquid":
                c = "L";
                break;

            case "Powder":
                c = "P";
                break;

            case "Granual":
                c = "G";
                break;
                
            case "":
                c="";
                break;
        }
        code = code + c;

        String flavorType = item.getFlavorType();
        String a = "";
        switch (flavorType) {
            case "Artificial":
                a = "A";
                break;

            case "Nature Identical":
                a = "E";
                break;

            case "Natural":
                a = "N";
                break;
                
            case "":
                a="";
                break;
        }
        code = code + a;

        String country = item.getCountry();
        String b = "";
        switch (country) {
            case "India":
                b = "I";
                break;

            case "Indonesia":
                b = "D";
                break;

            case "France":
                b = "F";
                break;
                
            case "":
                c="";
                break;
        }

        code = code + b;

        int year = item.getYear();
        int yCode = year % 100;

        code = code + Integer.toString(yCode);
        
        int number =0;
        
        if (type.equals("Fragrance")) {
            number = get.getCode("R")  + 1;
        }
        if (type.equals("Flavor")) {
            number = get.getCode("L")  + 1;
        }
        
        
        
        code = code + Integer.toString(number).substring(2);

        return code;

    }
}
