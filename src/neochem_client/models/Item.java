/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neochem_client.models;

/**
 *
 * @author JanithaT
 */
public class Item {
    private  int ID;
    private  String formerCode;
    private  String newCode;
 
    public Item(int id, String fCode, String NCode) {
        this.ID = id;
        this.formerCode = fCode;
        this.newCode = NCode;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFormerCode() {
        return formerCode;
    }

    public void setFormerCode(String formerCode) {
        this.formerCode = formerCode;
    }

    public String getNewCode() {
        return newCode;
    }

    public void setNewCode(String newCode) {
        this.newCode = newCode;
    }
    
    
}
