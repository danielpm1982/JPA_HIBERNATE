package model.pedidoCliente;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity @Table(name="livroTable")
public class Livro implements Serializable{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id_livro")
    private Long id;
    @Column(name = "nome_livro")
    private String nome;
    @Column(name = "data_publicação")
    private LocalDate dataPublicação;
    @Column(name = "edição")
    private int edição;
    @ManyToMany @JoinTable(name = "livro_autor_table",joinColumns = @JoinColumn(name = "livro_id"),inverseJoinColumns = @JoinColumn(name = "autor_id"))
    private List<Autor> autorList;
    
    public Livro() {
    }

    public Livro(String nome, LocalDate dataPublicação, int edição) {
        this.nome = nome;
        this.dataPublicação = dataPublicação;
        this.edição = edição;
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

    public LocalDate getDataPublicação() {
        return dataPublicação;
    }

    public void setDataPublicação(LocalDate dataPublicação) {
        this.dataPublicação = dataPublicação;
    }

    public int getEdição() {
        return edição;
    }

    public void setEdição(int edição) {
        this.edição = edição;
    }

    public List<Autor> getAutorList() {
        return autorList;
    }

    public void setAutorList(List<Autor> autorList) {
        this.autorList = autorList;
    }
    
    @Override
    public String toString() {
        return id+" "+(nome!=null&&!nome.equals("")?nome:"sem nome");
    }
    
    public String getListAutorThisString(){
        if(autorList!=null&&!autorList.isEmpty())
            return autorList.stream().map(x->x+" ").reduce((x,y)->x+y).get();
        return "nenhum autor!";
    }
}
