package model.estadoGovernador;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Estado implements Serializable{
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id_estado")
    private long id;
    @Column(name = "nome_estado")
    private String nome;
    @Column(name = "código_estado")
    private String codigo;
    @OneToOne(optional = false)
    private Governador governador;

    public Estado() {
    }
    
    public Estado(String nome, String codigo, Governador governador) {
        this.nome = nome;
        this.codigo = codigo;
        this.governador=governador;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setGovernador(Governador governador) {
        this.governador = governador;
    }
    
    public Governador getGovernador() {
        return governador;
    }
    
    @Override
    public String toString() {
        if(id>=1&&id<=9){
            return "0"+id+" "+nome+" "+codigo+" "+governador;
        }
        return id+" "+nome+" "+codigo+" "+governador;
    }
}
