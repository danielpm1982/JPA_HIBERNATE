package model.pedidoCliente;

import java.io.Serializable;
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

@Entity @Table(name="AutorTable")
public class Autor implements Serializable{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id_autor")
    private Long id;
    @Column(name = "nome_autor")
    private String nome;
    @ManyToMany(mappedBy = "autorList")
//    @JoinTable(name = "autor_livro_table",joinColumns = @JoinColumn(name = "autor_id"),inverseJoinColumns = @JoinColumn(name = "livro_id"))
    private List<Livro> livroList;
    
    public Autor() {
    }

    public Autor(String nome) {
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

    public List<Livro> getLivroList() {
        return livroList;
    }

    public void setLivroList(List<Livro> livroList) {
        this.livroList = livroList;
    }

    @Override
    public String toString() {
        return id+" "+(nome!=null&&!nome.equals("")?nome:"sem nome");
    }
    
    public String getListLivrosThisString(){
        if(livroList!=null&&!livroList.isEmpty())
            return livroList.stream().map(x->x+" ").reduce((x,y)->x+y).get();
        return "nenhum livro!";
    }
    
}
