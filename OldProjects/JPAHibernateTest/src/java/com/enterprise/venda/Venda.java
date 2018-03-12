package com.enterprise.venda;
import com.enterprise.client.Client;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name="venda_Table")
public class Venda implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
//    @TableGenerator(name="TABLE_GEN2", table="GENERATOR_TABLE",pkColumnName="SEQ_NAME",pkColumnValue="VENDA_ID_SEQ",valueColumnName="SEQ_COUNT",allocationSize=1)
//    @GeneratedValue(strategy = GenerationType.TABLE,generator="TABLE_GEN2")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name="SEQUENCE_GEN2",allocationSize=1, sequenceName="VENDA_ID_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="SEQUENCE_GEN2")
    @Column(name="id_Venda")
    private Long id;
    
    @Column(name="itemVenda")
    private String itemVenda;
    
    @Column(name="periodoVenda", nullable=true)
    @Enumerated(EnumType.STRING)
    private EnumPeriodo periodoVenda;
    
    @ManyToOne
    @JoinColumn(name="client_Id",nullable=true)
    private Client client;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getItemVenda() {
        return itemVenda;
    }
    public void setItemVenda(String itemVenda) {
        this.itemVenda = itemVenda;
    }

    public EnumPeriodo getPeriodoVenda() {
        return periodoVenda;
    }

    public void setPeriodoVenda(EnumPeriodo periodoVenda) {
        this.periodoVenda = periodoVenda;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    
    @Override
    public String toString() {
        return "com.enterprise.venda.Venda[ id=" + id + " nomeVenda= "+itemVenda+" ]";
    }
}
