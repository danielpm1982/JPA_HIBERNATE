import com.enterprise.cliente.Address;
import com.enterprise.cliente.Address_;
import com.enterprise.cliente.Client;
import com.enterprise.cliente.Client_;
import com.enterprise.result.IdName;
import com.enterprise.result.IdNameAddress;
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
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class TestListClients {
    public static void main(String[] args){
        
//        List<Client> list = DAOClient.findAllClients();
//        for(Client c : list){
//            System.out.println(c+" "+c.getAddress());
//        }
        
//        List<Tuple> list = DAOClient.findAllClients5(); 
//        for(Tuple t : list){
//            System.out.println(t.get("client.id")+" - "+t.get("client.name"));
//        }
        
        //Teste abaixo não tem nada a ver com o DAO.
        //buscando resultados complexos com ou sem uso de classes modelos para evitar 
        //lidar com posicionamento de itens de resultado. E ex. de singlequeries de funções sql.
        // Paginação da query. Usando fetch eager com left join fetch.
        
//        EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPAHibernateTest4PU");
//        EntityManager em = factory.createEntityManager();
       
//        Query query = em.createQuery("select c.id,c.name from Client c");
//        List<Object[]> list = query.getResultList();
//        for (Object[] tupla:list){
//            System.out.println("ID: "+tupla[0]+" - Name: "+tupla[1]);
//        }
//        Query query2 = em.createQuery("select COUNT(c) from Client c");
//        System.out.println(query2.getSingleResult()+" clients.");
        
//        Query query3 = em.createQuery("SELECT NEW com.enterprise.result.IdName(c.id,c.name) FROM Client c order by c.id");
//        List<IdName> listIdName = query3.getResultList();
//        for (IdName n:listIdName){
//            System.out.println(n.getId()+": "+n.getName());
//        }
//        Query query4 = em.createQuery("select COUNT(c) from Client c");
//        System.out.println("Total: "+query4.getSingleResult()+" clients.");
        
//        Query query5 = em.createQuery("SELECT NEW com.enterprise.result.IdNameAddress(c.id, c.name, c.address) FROM Client c order by c.id");
//        query5.setFirstResult(0);
//        query5.setMaxResults(3);
//        List<IdNameAddress> listIdNameAddress = query5.getResultList();
//        for (IdNameAddress m:listIdNameAddress){
//            System.out.println(m.getId()+": "+m.getName()+" "+m.getAddress());
//        }

//        Ao invés de fazer várias buscas lazy na tabela endereço para cada cliente, 
// traz tudo de uma vez (eager) só com o left join fetch:
//        Query query6 = em.createQuery("SELECT distinct(c) FROM Client c left join fetch c.address order by c.id");
//        List<Client> listClient = query6.getResultList();
//        for (Client c:listClient){
//            System.out.println(c.getName()+" - "+c.getAddress());
//        }
        
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPAHibernateTest4PU");
        EntityManager em = factory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Tuple> criteria = cb.createQuery(Tuple.class);
        CriteriaQuery<String> criteria = cb.createQuery(String.class);
        Root <Client> client = criteria.from(Client.class);
//        evitando o problema com o lazy e obtendo os endereços com o left join, de uma vez só:
//        client.fetch("address",JoinType.LEFT);
//        criteria.multiselect(client.<Long>get(Client_.id).alias("client.id"),cb.lower(client.<String>get(Client_.name)).alias("client.name"),client.<Address>get(Client_.address).alias("client.address"));
        criteria.select(client.get(Client_.address).<String>get("neighborhood").alias("client.neighborhood"));
        criteria.orderBy(cb.asc(client.<Long>get("id")));
        criteria.groupBy(client.get(Client_.address).get(Address_.neighborhood));
//        criteria.having(cb.in(client.get(Client_.address).get(Address_.neighborhood)).value("Parquelandia"));
//        Predicate predicate = cb.like(client.<String>get("name"), "d%");
//        criteria.where(predicate);
//        TypedQuery <Tuple> query = em.createQuery(criteria);
//        for (Tuple t: query.getResultList()){
//            System.out.println(t.get("client.id")+" "+t.get("client.name")+" "+t.get("client.address"));
//        }
        TypedQuery<String> query2 = em.createQuery(criteria);
        for (String s : query2.getResultList()){
            System.out.println(s);
        }
    }
}
