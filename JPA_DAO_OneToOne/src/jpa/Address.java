package jpa;
import java.io.Serializable;

public class Address implements Serializable{
    private String street;
    private int number;
    private String city;
    private String state;
    private String country;
    public Address() {
        this(null, -1, null, null, null);
    }
    public Address(String street, int number, String city, String state, String country) {
        this.street = street;
        this.number = number;
        this.city = city;
        this.state = state;
        this.country = country;
    }
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    @Override
    public String toString() {
        return "Street: "+street+", "+number+" city: "+city+" state: "+state+" country: "+country;
    }
    public String toStringForConvertion() {
        return "Street: /"+street+"/, /"+number+"/ city: /"+city+"/ state: /"+state+"/ country: /"+country+"/";
    }
}
