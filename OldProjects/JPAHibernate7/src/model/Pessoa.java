package model;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity @Table(name="PessoaTable")
@EntityListeners(CallbackClassPessoa.class)
public class Pessoa implements Serializable{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id_pessoa")
    private Long id;
    @Column(name = "nome_pessoa")
    private String nome;
    @OneToOne(cascade = CascadeType.PERSIST, orphanRemoval = true) @JoinColumn(name = "endereço")
    private Endereco endereco;
    
    public Pessoa() {
    }

    public Pessoa(String nome, Endereco endereco) {
        this.nome = nome;
        this.endereco = endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return id+" "+(nome!=null&&!nome.equals("")?nome:"sem nome");
    }
    
}
