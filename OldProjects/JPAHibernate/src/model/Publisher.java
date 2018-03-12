package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity @Table(name = "Publisher")
public class Publisher implements Serializable{
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Lob
    private byte[] logoType;
    @Enumerated(EnumType.STRING)
    private CountryDDIEnum countryDDI;
    @ElementCollection @CollectionTable(name = "contact",joinColumns = @JoinColumn(name = "publisherID")) @Column(name = "telephone")
    private List<String> telephone;
    
    public Publisher(Long id, String name, String email, byte[] logoType, CountryDDIEnum countryDDI, List<String> telephone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.logoType=logoType;
        this.countryDDI=countryDDI;
        this.telephone=telephone;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getLogoType() {
        return logoType;
    }

    public void setLogoType(byte[] logoType) {
        this.logoType = logoType;
    }

    public CountryDDIEnum getCountryDDI() {
        return countryDDI;
    }

    public void setCountryDDI(CountryDDIEnum countryDDI) {
        this.countryDDI = countryDDI;
    }

    public void setTelephone(List<String> telephone) {
        this.telephone = telephone;
    }

    public List<String> getTelephone() {
        return telephone;
    }
    
    public String getTelefoneString(){
        String telefoneResult="";
        for(int i=0;i<telephone.size();i++){
            if(i==0){
                telefoneResult+=telephone.get(i);
            } else{
                telefoneResult+="/"+telephone.get(i);
            }
        }
        return telefoneResult;
    }
    
    @Override
    public String toString() {
        return id+" "+name+" "+email+" "+countryDDI+" "+getTelefoneString();
    }
    
    @Override
    public boolean equals(Object obj) {
        return this.getName().equals(((Publisher)obj).getName());
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
  