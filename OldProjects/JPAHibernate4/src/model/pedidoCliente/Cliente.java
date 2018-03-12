package model.pedidoCliente;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @Table(name="ClienteTable")
public class Cliente implements Serializable{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id_cliente")
    private Long id;
    @Column(name = "nome_cliente")
    private String nome;
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;
    
    public Cliente() {
    }

    public Cliente(String nome, LocalDate dataNascimento) {
        this.nome = nome;
        this.dataNascimento=dataNascimento;
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

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    
    @Override
    public String toString() {
        return id+" "+(nome!=null&&(!nome.equals(""))?nome:"sem nome!")+" "+((dataNascimento!=null)?dataNascimento:"sem data de nascimento!");
    }
}
