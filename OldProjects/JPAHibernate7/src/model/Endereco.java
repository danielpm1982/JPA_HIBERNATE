package model;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Endereco implements Serializable{
    @Id @Column(name = "id_endereco") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "logradouro")
    private String logradouro;
    @Column(name = "numero")
    private String numero;
    @Column(name = "cep")
    private String cep;
    @Column(name = "cidade")
    private String cidade;
    @Column(name = "estado")
    private String estado;
    @Column(name = "pais")
    private String pais;
    @OneToOne(mappedBy = "endereco", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Pessoa pessoa;

    public Endereco() {
    }

    public Endereco(String logradouro, String numero, String cep, String cidade, String estado, String pais) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.cep = cep;
        this.cidade = cidade;
        this.estado = estado;
        this.pais = pais;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCep() {
        return cep;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public String getPais() {
        return pais;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
    
    @Override
    public String toString() {
        return (id+" "+
                logradouro!=null&&!logradouro.equals("")?logradouro:"sem logradouro")+" "+
                (numero!=null&&!numero.equals("")?numero:"sem numero")+" "+
                (cep!=null&&!cep.equals("")?cep:"sem cep")+" "+
                (cidade!=null&&!cidade.equals("")?cidade:"sem cidade")+" "+
                (estado!=null&&!estado.equals("")?estado:"sem estado")+" "+
                (pais!=null&&!pais.equals("")?pais:"sem país")
                ;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Endereco){
            Endereco e =((Endereco)o);
            if(this.getLogradouro().equals(e.getLogradouro())&&this.getNumero().equals(e.getNumero())){
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
    
}
