package com.enterprise.autor;

import javax.persistence.Embeddable;

@Embeddable
public class Endereco {
    private String rua;
    private short numRua;
    private String cep;
    private String cidade;
    private String estado;
    private String pais;

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public short getNumRua() {
        return numRua;
    }

    public void setNumRua(short numRua) {
        this.numRua = numRua;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}
