import com.enterprise.cliente.Address;
import com.enterprise.cliente.Client;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Criteria;

public class DAOClient {
    public static boolean addClient(Client c, Address a){
        try{
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPAHibernateTest4PU");
            EntityManager em = factory.createEntityManager();
            em.getTransaction().begin();
// Address a não necessário ser persistido pois, por cascade, já está sendo persistido via Cliente c.
//            em.persist(a);
            em.persist(c);
            em.getTransaction().commit();
            em.close();
            return true;
        } catch (Exception e){
            e.getStackTrace();
            return false;
        }
    }
    public static boolean removeClient(Client c){
        try{
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPAHibernateTest4PU");
            EntityManager em = factory.createEntityManager();
            em.getTransaction().begin();
            Client client = em.merge(c);
            em.detach(c.getAddress());
            em.remove(client);
//            Address address = em.merge(c.getAddress());
//            em.remove(address);
//endereço é excluído em cascade pela sua referência ao cliente.
            em.getTransaction().commit();
            em.close();
            return true;
        } catch (Exception e){
            return false;
        }
//para bulk operations de delete ou update é mais eficiente usar a alteração direto no banco,
//através de uma query com delete ou update explícitos, ao invés de encher
//a memória do servidor de aplicação com muitos objetos usando o find ou o merge.
//de forma que não sejam feitos selects nem criados objetos para receber resultados.
    }
    public static Client findClientById(long id){
        try{
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPAHibernateTest4PU");
            EntityManager em = factory.createEntityManager();
            Client c = em.find(Client.class, id);
            return c;
        } catch (Exception e){
            return null;
        }
    }
    public static Client findClientById2(long id){
        try{
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPAHibernateTest4PU");
            EntityManager em = factory.createEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Client> criteria = cb.createQuery(Client.class);
            Root <Client> client = criteria.from(Client.class);
            criteria.select(client);
            Predicate predicate = cb.equal(client.get("id"), id);
            criteria.where(predicate);
            TypedQuery<Client> query = em.createQuery(criteria);
            Client c = query.getSingleResult();
            return c;
        } catch (Exception e){
            return null;
        }
    }
    public static List<Client> findAllClients(){
        try{
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPAHibernateTest4PU");
            EntityManager em = factory.createEntityManager();
            em.getEntityManagerFactory().getCache().evictAll();
            String q = "select c from Client c";
            //Query query = em.createQuery(q);
            TypedQuery<Client> query = em.createQuery(q, Client.class);
            return query.getResultList(); 
        } catch (Exception e){
            return null;
        }
    }
    public static List<Client> findAllClients2(){
        try{
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPAHibernateTest4PU");
            EntityManager em = factory.createEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery <Client> criteria = cb.createQuery(Client.class);
            Root<Client> client = criteria.from(Client.class);
            criteria.select(client);
            TypedQuery<Client> query = em.createQuery(criteria);
            return query.getResultList(); 
        } catch (Exception e){
            return null;
        }
    }
    public static List<String> findAllClients3(){
        try{
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPAHibernateTest4PU");
            EntityManager em = factory.createEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery <String> criteria = cb.createQuery(String.class);
            Root<Client> client = criteria.from(Client.class);
            criteria.select(client.<String>get("name"));
            TypedQuery<String> query = em.createQuery(criteria);
            return query.getResultList(); 
        } catch (Exception e){
            return null;
        }
    }
    public static List<Object[]> findAllClients4(){
        try{
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPAHibernateTest4PU");
            EntityManager em = factory.createEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery <Object[]> criteria = cb.createQuery(Object[].class);
            Root<Client> client = criteria.from(Client.class);
            criteria.multiselect(client.<Long>get("id"),client.<String>get("name"));
            TypedQuery<Object[]> query = em.createQuery(criteria);
            return query.getResultList(); 
        } catch (Exception e){
            return null;
        }
    }
    public static List<Tuple> findAllClients5(){
        try{
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPAHibernateTest4PU");
            EntityManager em = factory.createEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery <Tuple> criteria = cb.createQuery(Tuple.class);
            Root<Client> client = criteria.from(Client.class);
            criteria.multiselect(client.<Long>get("id").alias("client.id"),client.<String>get("name").alias("client.name"));
            TypedQuery<Tuple> query = em.createQuery(criteria);
            return query.getResultList(); 
        } catch (Exception e){
            return null;
        }
    }
    public static Client findClientByName(String name){
        try{
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPAHibernateTest4PU");
            EntityManager em = factory.createEntityManager();
            Query query = em.createNamedQuery("Client.findClientByName2");
            //query.setParameter("name", name);
            query.setParameter(1, name);
            return (Client)query.getSingleResult(); 
        } catch (Exception e){
            return null;
        }
    }
    public static List<Client> findClientByName2(String name){
        try{
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPAHibernateTest4PU");
            EntityManager em = factory.createEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Client> criteria = cb.createQuery(Client.class);
            Root <Client> client = criteria.from(Client.class);
            criteria.select(client);
            Predicate predicate = cb.like(client.<String>get("name"), name+"%");
            criteria.where(predicate);
            TypedQuery<Client> query = em.createQuery(criteria);
            return query.getResultList();
        } catch (Exception e){
            return null;
        }
    }
    public static boolean updateClient(Client client1, Client client2){
        try{
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPAHibernateTest4PU");
            EntityManager em = factory.createEntityManager();
            em.getTransaction().begin();
            client1.setName(client2.getName());
            client1.setAddress(client2.getAddress());
            client1.setVersionID(client2.getVersionID());
            em.merge(client1);
            em.getTransaction().commit();
            em.close();
            return true; 
        } catch (Exception e){
            return false;
        }
    }
}
