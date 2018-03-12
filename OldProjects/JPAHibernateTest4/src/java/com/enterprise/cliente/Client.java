package com.enterprise.cliente;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;
import javax.swing.JOptionPane;

@Entity
@NamedQueries({
    @NamedQuery(name="Client.findClientByName1",query="select c from Client c where c.name=:name"),
    @NamedQuery(name="Client.findClientByName2",query="select c from Client c where c.name=?1")
})

public class Client implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="aluno_gen")
    @SequenceGenerator(name="aluno_gen",sequenceName="aluno_seq")
    private Long id;
    
    @Column(name="Name_Client",unique=true,nullable=false)
    private String name;
    
    @OneToOne(optional=true, cascade= CascadeType.ALL,fetch= FetchType.LAZY)
    private Address address;
    
//    @PrePersist
//    public void prePersist(){
//        JOptionPane.showMessageDialog(null, "Inserting Client...", "Client Insertion", JOptionPane.INFORMATION_MESSAGE);
//    }
    
    @Version
    private Long versionID;
    
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Long getVersionID() {
        return versionID;
    }

    public void setVersionID(Long versionID) {
        this.versionID = versionID;
    }

    @Override
    public String toString() {
        return "idClient: "+getId()+" name: "+getName();
    }
}
