package model;

import javax.persistence.Convert;
import javax.persistence.Entity;

@Entity
public class PessoaFisica extends Pessoa{
    @Convert(converter = StringLongConverter.class)
    private String cpf;

    public PessoaFisica() {
    }

    public PessoaFisica(String nome, Endereco endereco, String cpf) {
        super(nome, endereco);
        this.cpf=cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return super.toString()+" "+(cpf!=null?cpf:"sem cpf");
    }
}
