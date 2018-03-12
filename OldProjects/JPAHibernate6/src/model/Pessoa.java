/*For table_per_class Inheritance, generationType.identity does not work! The Ids must
be shared amongst the different tables, which therefore can't have their own Ids' continuous sequence.*/

package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity @Table(name="PessoaTable") @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Pessoa implements Serializable{
    @Id @GeneratedValue(strategy = GenerationType.TABLE) @Column(name = "id_pessoa")
    private Long id;
    @Column(name = "nome_pessoa")
    private String nome;
    @Embedded
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
        return id+" "+(nome!=null&&!nome.equals("")?nome:"sem nome")+" "+(endereco!=null&&!endereco.getLogradouro().equals("")?endereco.toString():"sem endereço");
    }
    
}
