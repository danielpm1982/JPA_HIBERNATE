import com.enterprise.client.Client;
import com.enterprise.venda.EnumPeriodo;
import com.enterprise.venda.Venda;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Test {
    public static void main (String[] args){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPAHibernateTestPU");
        EntityManager em = factory.createEntityManager();
        
        for (int i=1;i<1001;i++){
            Client c = new Client();
            c.setName("Client-"+i);
            Venda v = new Venda();
            v.setItemVenda("ItemVenda-"+i);
            em.getTransaction().begin();
            em.persist(c);
            em.persist(v);
            em.getTransaction().commit();
        }
        
//        Venda v1 = new Venda();
//        v1.setItemVenda("ItemVenda-1001");
//        v1.setPeriodoVenda(EnumPeriodo.MATUTINO);
//        Venda v2 = new Venda();
//        v2.setItemVenda("ItemVenda-1002");
//        v2.setPeriodoVenda(EnumPeriodo.NOTURNO);
//        Client c = new Client();
//        c.setName("Client-1001");
//        Collection<String> telList = new ArrayList<String>();
//        telList.add("3046-4623");
//        telList.add("8874-1001");
//        c.setTel(telList);
//        List<Venda> list = new ArrayList<Venda>();
//        list.add(v1);
//        list.add(v2);
//        c.setVenda(list);
        
                
        Client c = new Client();
        c.setName("Daniel");
        ArrayList<String> tels = new ArrayList<String>();
        tels.add("88741001");
        tels.add("30464623");
        c.setTel(tels);
        
        Venda v1 = new Venda();
        v1.setItemVenda("sushi");
        v1.setPeriodoVenda(EnumPeriodo.MATUTINO);
        v1.setClient(c);
        Venda v2 = new Venda();
        v2.setItemVenda("pizza");
        v2.setPeriodoVenda(EnumPeriodo.NOTURNO);
        v2.setClient(c);
        
        em.getTransaction().begin();
        em.persist(v1);
        em.persist(v2);
        em.persist(c);
        em.getTransaction().commit();
    }
}
