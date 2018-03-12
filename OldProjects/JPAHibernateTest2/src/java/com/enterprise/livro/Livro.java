package com.enterprise.livro;
import com.enterprise.autor.Autor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.TableGenerator;

@Entity
public class Livro implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator="livroGen")
    @TableGenerator(name="livroGen",allocationSize=1)
    //GenerationType.SEQUENCE ou GenerationType.IDENTITY não funcionariam para @ManyToMany a não ser que se colocase uma tabela extra para o gerador
    
    private Long id;

    @ManyToMany
    @JoinTable(name="livro_autor",joinColumns=@JoinColumn(name="livro_id"),inverseJoinColumns=@JoinColumn(name="autor_id"))
    private Collection<Autor> autor=new ArrayList<Autor>();
    
    @Lob
    private byte[] capaLivro;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection<Autor> getAutor() {
        return autor;
    }

    public void setAutor(Collection<Autor> autor) {
        this.autor = autor;
    }

    public byte[] getCapaLivro() {
        return capaLivro;
    }

    public void setCapaLivro(byte[] capaLivro) {
        this.capaLivro = capaLivro;
    }
}
