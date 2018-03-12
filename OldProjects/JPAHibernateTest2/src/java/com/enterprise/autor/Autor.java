package com.enterprise.autor;
import com.enterprise.livro.Livro;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.TableGenerator;

@Entity
public class Autor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator="autorGen")
    @TableGenerator(name="autorGen",allocationSize=1)
    //GenerationType.SEQUENCE ou GenerationType.IDENTITY não funcionariam para @ManyToMany a não ser que se colocase uma tabela extra para o gerador
    
    private Long id;
    
    @ManyToMany(mappedBy="autor")
    @JoinTable(name="Autor_Livro",joinColumns=@JoinColumn(name="Autor_Id"),inverseJoinColumns=@JoinColumn(name="Livro_Id"))
    private Collection<Livro> livros=new ArrayList<Livro>();
    
    @Embedded
    private Endereco endereco;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection<Livro> getLivros() {
        return livros;
    }

    public void setLivros(Collection<Livro> livros) {
        this.livros = livros;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
