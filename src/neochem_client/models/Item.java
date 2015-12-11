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
    private long id;
	private String ManeCode;
	private String ManeName;
	private String Type;
	private String FlavorFormat;
	private String FlavorType;
	private int Year;
	private String Country;
	private String NeoChemName;
        private String NeoChemCode;
 
    public Item(long id, String formerCode, String mname, String type, String fFormat, String FType, int year, String country, String neoName, String newCode) {

		this.id = id;
		this.ManeCode = formerCode;
		this.ManeName = mname;
		this.Type = type;
		this.FlavorFormat = fFormat;
		this.FlavorType = FType;
		this.Year = year;
		this.Country = country;
		this.NeoChemName = neoName;
		this.NeoChemCode = newCode;
	}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getManeCode() {
        return ManeCode;
    }

    public void setManeCode(String ManeCode) {
        this.ManeCode = ManeCode;
    }

    public String getManeName() {
        return ManeName;
    }

    public void setManeName(String ManeName) {
        this.ManeName = ManeName;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getFlavorFormat() {
        return FlavorFormat;
    }

    public void setFlavorFormat(String FlavorFormat) {
        this.FlavorFormat = FlavorFormat;
    }

    public String getFlavorType() {
        return FlavorType;
    }

    public void setFlavorType(String FlavorType) {
        this.FlavorType = FlavorType;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int Year) {
        this.Year = Year;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

    public String getNeoChemName() {
        return NeoChemName;
    }

    public void setNeoChemName(String NeoChemName) {
        this.NeoChemName = NeoChemName;
    }

    public String getNeoChemCode() {
        return NeoChemCode;
    }

    public void setNeoChemCode(String NeoChemCode) {
        this.NeoChemCode = NeoChemCode;
    }

    
    
    
}
