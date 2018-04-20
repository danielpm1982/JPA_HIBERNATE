package model;

public class Address {
	private String street;
	private int streetNumber;
	private String city;
	private String state;
	private String country;
	public Address() {
	}
	public Address(String street, int streetNumber, String city, String state, String country) {
		super();
		this.street = street;
		this.streetNumber = streetNumber;
		this.city = city;
		this.state = state;
		this.country = country;
	}
	@Override
	public String toString() {
		return street+", "+streetNumber+", "+city+" - "+state+", "+country; 
	}
}
