package model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity @Table(name="ContaTable")
public class Conta implements Serializable{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id_conta")
    private Long id;
    @Column(name = "numero_cc")
    private String numeroContaCorrente;
    @Column(name = "saldo")
    private BigDecimal saldoContaCorrente;
//    @Version
//    private Long versão;
    
    public Conta() {
    }

    public Conta(String numeroContaCorrente) {
        this.numeroContaCorrente = numeroContaCorrente;
    }
    
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroContaCorrente() {
        return numeroContaCorrente;
    }

    public void setNumeroContaCorrente(String numeroContaCorrente) {
        this.numeroContaCorrente = numeroContaCorrente;
    }

    public void setSaldoContaCorrente(BigDecimal saldoContaCorrente) {
        this.saldoContaCorrente = saldoContaCorrente;
    }

    public BigDecimal getSaldoContaCorrente() {
        return saldoContaCorrente;
    }

    @Override
    public String toString() {
        return "id:"+id+" "+
                (numeroContaCorrente!=null&&!numeroContaCorrente.equals("")?"cc:"+numeroContaCorrente:"sem numero")+" "+
                (saldoContaCorrente!=null?saldoContaCorrente.setScale(2)+" reais":"sem saldo")
                ;
    }
    
}
