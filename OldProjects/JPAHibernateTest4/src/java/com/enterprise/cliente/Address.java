package com.enterprise.cliente;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="address_gen")
    @SequenceGenerator(name="address_gen",sequenceName="address_seq")
    private Long id;
    
    @Column(name="Street")
    private String street;
    
    @Column(name="Street_Number")
    private String streetNumber;
    
    @Column(name="Neighborhood")
    private String neighborhood;
    
    @Column(name="City")
    private String city;
    
    @Column(name="State")
    private String state;
    
    @Column(name="Country")
    private String country;
    
    @OneToOne(mappedBy="address",fetch= FetchType.LAZY,cascade= CascadeType.ALL,orphanRemoval=true)
    private Client client;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "idAddress: "+getId()+" address: "+getStreet()+", "+getStreetNumber()+", "+getNeighborhood()+", "+getCity()+" - "+getState()+", "+getCountry();
    }
}
