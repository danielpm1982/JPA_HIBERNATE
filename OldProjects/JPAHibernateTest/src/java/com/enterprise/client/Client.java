package com.enterprise.client;
import com.enterprise.venda.Venda;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "client_Table")

public class Client implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name="id_Client")
//    @TableGenerator(name="TABLE_GEN", table="GENERATOR_TABLE",pkColumnName="SEQ_NAME",pkColumnValue="CLIENT_ID_SEQ",valueColumnName="SEQ_COUNT",allocationSize=1)
//    @GeneratedValue(strategy = GenerationType.TABLE,generator="TABLE_GEN")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name="SEQUENCE_GEN",allocationSize=1, sequenceName="CLIENT_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="SEQUENCE_GEN")
    private Long id;

    @Column(name = "name_Client")
    private String name;

    @ElementCollection
    @CollectionTable(name="Tels_Table",joinColumns=@JoinColumn(name="client_Id"))
    @Column(name="telefone")
    private Collection<String> tel;
    
//    @OneToMany
//    @JoinTable(name="cliente_venda",joinColumns=@JoinColumn(name="cliente_Id"),inverseJoinColumns=@JoinColumn(name="venda_Id"))
//    private List<Venda> venda;
    
    public Long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Collection<String> getTel() {
        return tel;
    }

    public void setTel(Collection<String> tel) {
        this.tel = tel;
    }

//    public List<Venda> getVenda() {
//        return venda;
//    }
//
//    public void setVenda(List<Venda> venda) {
//        this.venda = venda;
//    }

    @Override
    public String toString() {
        return "[ id=" +id+ " name=" +name+" ]";
    }
}
