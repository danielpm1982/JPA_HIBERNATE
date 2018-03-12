package model.departamentoFuncionario;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @Table(name="FuncionárioTable")
public class Funcionario implements Serializable{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id_Funcionário")
    private Long id;
    //@Column(name = "nome_funcionário")
    private String nome;

    public Funcionario() {
    }

    public Funcionario(String nome) {
        this.nome = nome;
    }    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return id+" "+(nome!=null&&(!nome.equals(""))?nome:"sem nome!");
    }
}
