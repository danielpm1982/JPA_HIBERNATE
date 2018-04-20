package model;

public class Address { // this will be embbeded at other entity classes / tables (Patient and Pharmacist).
	private String street;
	private int streetNumber;
	private String city;
	private String state;
	private String country;
	private String email;
	public Address() {
	}
	public Address(String street, int streetNumber, String city, String state, String country, String email) {
		this.street = street;
		this.streetNumber = streetNumber;
		this.city = city;
		this.state = state;
		this.country = country;
		this.email = email;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public int getStreetNumber() {
		return streetNumber;
	}
	public void setStreetNumber(int streetNumber) {
		this.streetNumber = streetNumber;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return street+", "+streetNumber+", "+city+" - "+state+", "+country+" email: "+email; 
	}
}
