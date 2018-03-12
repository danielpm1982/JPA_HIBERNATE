import com.enterprise.autor.Autor;
import com.enterprise.autor.Endereco;
import com.enterprise.livro.Livro;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Test {
    public static void main (String[] args) throws IOException{
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPAHibernateTest2PU");
        EntityManager em = factory.createEntityManager();

        Livro l1 = new Livro();
        Livro l2 = new Livro();
        Livro l3 = new Livro();

        File file = new File("..\\JPAHibernateTest2\\src\\java\\com\\enterprise\\livro\\capa1.jpg");
        byte[] arrayBytes = Files.readAllBytes(file.toPath());
        l1.setCapaLivro(arrayBytes);
        file = new File("..\\JPAHibernateTest2\\src\\java\\com\\enterprise\\livro\\capa2.jpg");
        arrayBytes = Files.readAllBytes(file.toPath());
        l2.setCapaLivro(arrayBytes);
        file = new File("..\\JPAHibernateTest2\\src\\java\\com\\enterprise\\livro\\capa3.jpg");
        arrayBytes = Files.readAllBytes(file.toPath());
        l3.setCapaLivro(arrayBytes);

        Autor a1 = new Autor();
        Collection<Livro> list = new  ArrayList<Livro>();
        list.add(l1);
        list.add(l2);
        list.add(l3);
        a1.setLivros(list);
        Endereco endereco = new Endereco();
        endereco.setRua("Dom Manuel de Medeiros");
        endereco.setNumRua(Short.valueOf("2785"));
        endereco.setCep("60455-305");
        endereco.setCidade("Fortaleza");
        endereco.setEstado("Ceara");
        endereco.setPais("Brasil");
        a1.setEndereco(endereco);

        Autor a2 = new Autor();
        list = new  ArrayList<Livro>();
        list.add(l1);
        list.add(l3);
        a2.setLivros(list);
        endereco = new Endereco();
        endereco.setRua("Abilio Martins");
        endereco.setNumRua(Short.valueOf("117"));
        endereco.setCep("60455-470");
        endereco.setCidade("Fortaleza");
        endereco.setEstado("Ceara");
        endereco.setPais("Brasil");
        a2.setEndereco(endereco);

        Collection<Autor> list2 = new ArrayList<Autor>();
        list2.add(a1);
        list2.add(a2);
        l1.setAutor(list2);

        list2 = new ArrayList<Autor>();
        list2.add(a1);
        l2.setAutor(list2);

        list2 = new ArrayList<Autor>();
        list2.add(a1);
        list2.add(a2);
        l3.setAutor(list2);

        em.getTransaction().begin();
        em.persist(l1);
        em.persist(l2);
        em.persist(l3);
        em.persist(a1);
        em.persist(a2);
        em.getTransaction().commit();
    }
}
