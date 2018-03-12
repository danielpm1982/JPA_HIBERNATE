package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

//@Embeddable
public class Endereco implements Serializable{
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
    
    @Override
    public String toString() {
        return (logradouro!=null&&!logradouro.equals("")?logradouro:"sem logradouro")+" "+
                (numero!=null&&!numero.equals("")?numero:"sem numero")+" "+
                (cep!=null&&!cep.equals("")?cep:"sem numero")+" "+
                (cidade!=null&&!cidade.equals("")?cidade:"sem numero")+" "+
                (estado!=null&&!estado.equals("")?estado:"sem numero")+" "+
                (pais!=null&&!pais.equals("")?pais:"sem numero")
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
    
}
