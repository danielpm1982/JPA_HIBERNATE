package test;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import modelo.Cliente;
import modelo.Cliente_;
import modelo.NomeDataCliente;
import modelo.Pedido;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import view.MyJPanel;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pedidoclientePU");
        EntityManager em = factory.createEntityManager();
        try{
            popular(em);
            //queriesJPQL(em);
            queriesCriteria(em);
        } finally{
            em.close();
            factory.close();
        }
    }
    public static void popular(EntityManager em){
        Cliente c1 = new Cliente("Daniel", LocalDate.of(1982, Month.APRIL, 22));
        Pedido p1 = new Pedido("PlayStationVR", LocalDateTime.now());
        Pedido p2 = new Pedido("PlayStation5", LocalDateTime.now());
        Pedido p3 = new Pedido("VRGames", LocalDateTime.now());
        List<Pedido> listC1 = c1.getPedido();
        listC1.add(p1); listC1.add(p2); listC1.add(p3);
        c1.setPedido(listC1);
        p1.setCliente(c1); p2.setCliente(c1); p3.setCliente(c1);
        
        em.persist(c1); em.persist(p1); em.persist(p2); em.persist(p3);
        
        Cliente c2 = new Cliente("Juliana", LocalDate.of(1986, Month.FEBRUARY, 24));
        Pedido p4 = new Pedido("Vestido", LocalDateTime.now());
        Pedido p5 = new Pedido("Sapato", LocalDateTime.now());
        List<Pedido> listC2 = c2.getPedido();
        listC2.add(p4); listC2.add(p5);
        c2.setPedido(listC2);
        p4.setCliente(c2); p5.setCliente(c2);
        
        em.persist(c2); em.persist(p4); em.persist(p5);
        
        em.persist(new Cliente("ClienteX", LocalDate.of(1900, Month.MARCH, 01))); em.persist(new Cliente("ClienteY", LocalDate.of(1810, Month.MARCH, 01))); em.persist(new Cliente("ClienteZ", LocalDate.of(2100, Month.DECEMBER, 01)));
        em.persist(new Pedido("PedidoX", LocalDateTime.now())); em.persist(new Pedido("PedidoY", LocalDateTime.now()));
        
        em.getTransaction().begin();
        em.getTransaction().commit();
    }
    public static void queriesJPQL(EntityManager em){
        
//        String stringToShowAtGUI="";
//        stringToShowAtGUI+="Clientes:\n\n";
//        stringToShowAtGUI+=em.createQuery("select c from Cliente c",Cliente.class).getResultList().stream().map(x->x+" "+x.getPedido()+"\n").reduce((x,y)->x+y).get();
//        stringToShowAtGUI+="\nPedidos:\n\n";
//        stringToShowAtGUI+=em.createQuery("select p from Pedido p",Pedido.class).getResultList().stream().map(x->x+" "+x.getCliente()+"\n").reduce((x,y)->x+y).get();
//        showStringGUI(stringToShowAtGUI);
            
        String stringToShowAtGUI="";
        
//        stringToShowAtGUI+="ByFirstLetter:\n\n";
//        String option = JOptionPane.showInputDialog("Type a letter to search:");
//        TypedQuery<Cliente> query = em.createNamedQuery("Cliente.findFirstLetter",Cliente.class);
//        query.setParameter("firstLetter", option+"%");
//        List<Cliente> list = query.getResultList();
//        stringToShowAtGUI+=list.stream().map(x->x+" "+x.getPedido()+"\n").reduce((x,y)->x+y).orElse("None.");
//        
//        stringToShowAtGUI+="\nByOrdinal:\n\n";
//        Long idToSearch = Long.valueOf(JOptionPane.showInputDialog("Type an ordinal number to search:"));
//        TypedQuery<Cliente> query2 = em.createNamedQuery("Cliente.findOrdinal",Cliente.class);
//        query2.setParameter(1, idToSearch);
//        Cliente cliente = query2.getSingleResult();
//        stringToShowAtGUI+=(cliente+" "+cliente.getPedido());
//        stringToShowAtGUI+="\n\n";
        
        stringToShowAtGUI+="Clientes (nomes):\n\n";
        TypedQuery<String> query3 = em.createNamedQuery("Cliente.findAllName", String.class);
        List<String> nameList = query3.getResultList();
        stringToShowAtGUI+=nameList.stream().map(x->x+"\n").reduce((x,y)->x+y).orElse("");
        stringToShowAtGUI+="\n";
        
        stringToShowAtGUI+="Clientes (estatística):\n\n";
        TypedQuery<Long> query4 = em.createNamedQuery("Cliente.findStatistics1", Long.class);
        TypedQuery<LocalDate> query5 = em.createNamedQuery("Cliente.findStatistics2", LocalDate.class);
        TypedQuery<LocalDate> query6 = em.createNamedQuery("Cliente.findStatistics3", LocalDate.class);
        TypedQuery<Double> query7 = em.createNamedQuery("Cliente.findStatistics4", Double.class);
        TypedQuery<Long> query8 = em.createNamedQuery("Cliente.findStatistics5", Long.class);
        stringToShowAtGUI+="Numero de clientes: "+query4.getSingleResult()+"\n"+"Maior dataNasc: "+query5.getSingleResult()+"\n"+"Menor dataNasc: "+query6.getSingleResult()+"\n"+"Média IDs: "+query7.getSingleResult()+"\n"+"Soma IDs: "+query8.getSingleResult();
        stringToShowAtGUI+="\n\n";
        
        stringToShowAtGUI+="Clientes (nome/dataNasc):\n\n";
        TypedQuery<Object[]> query9 = em.createNamedQuery("Cliente.findNomeDataNasc",Object[].class);
        List<Object[]> nameBirthDate = query9.getResultList();
        stringToShowAtGUI+=nameBirthDate.stream().map(x->x[0]+" "+((LocalDate)x[1]).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))+"\n").reduce((x,y)->x+y).orElse("");
        stringToShowAtGUI+="\n";
        
        stringToShowAtGUI+="Clientes (nome/dataNasc (2)):\n\n";
        TypedQuery<NomeDataCliente> query10 = em.createNamedQuery("Cliente.findNomeDataCliente",NomeDataCliente.class);
        List<NomeDataCliente> nomeDataCliente = query10.getResultList();
        stringToShowAtGUI+=nomeDataCliente.stream().map(x->x+"\n").reduce((x,y)->x+y).orElse("");
        stringToShowAtGUI+="\n";
        
        stringToShowAtGUI+="Clientes (nome/dataNasc (3) - paginação: max 3 clientes):\n\n";
        TypedQuery<NomeDataCliente> query11 = em.createNamedQuery("Cliente.findNomeDataCliente",NomeDataCliente.class);
        query11.setFirstResult(0); query11.setMaxResults(3);
        List<NomeDataCliente> nomeDataCliente2 = query11.getResultList();
        stringToShowAtGUI+=nomeDataCliente2.stream().map(x->x+"\n").reduce((x,y)->x+y).orElse("");
        stringToShowAtGUI+="\n";
        
        stringToShowAtGUI+="Clientes (left join fetch Pedidos):\n\n";
        TypedQuery<Cliente> query12 = em.createNamedQuery("Cliente.findAllFetchPedido",Cliente.class);
        List<Cliente> clienteListFetchPedido = query12.getResultList();
        stringToShowAtGUI+=clienteListFetchPedido.stream().map(x->x+" "+(!x.getPedido().isEmpty()?x.getPedido():"")+"\n").reduce((x,y)->x+y).orElse("");
        stringToShowAtGUI+="\n";
        
//        //dissociando pedidos
//        em.getTransaction().begin();
//        Query queryBulk = em.createNamedQuery("Cliente.bulkUpdate");
//        queryBulk.executeUpdate();
//        em.getTransaction().commit();
//        em.clear();
//        
//        stringToShowAtGUI+="Clientes (After bulk operation: pedidos dissociados):\n\n";
//        TypedQuery<Cliente> query13 = em.createNamedQuery("Cliente.findAllFetchPedido",Cliente.class);
//        clienteListFetchPedido = query13.getResultList();
//        stringToShowAtGUI+=clienteListFetchPedido.stream().map(x->x+" "+(!x.getPedido().isEmpty()?x.getPedido():"")+"\n").reduce((x,y)->x+y).orElse("");
//        stringToShowAtGUI+="\n";
//        
//        //excluindo pedidos
//        em.getTransaction().begin();
//        Query queryBulk2 = em.createNamedQuery("Cliente.bulkUpdate2");
//        queryBulk2.executeUpdate();
//        em.getTransaction().commit();
//        em.clear();
//        
//        stringToShowAtGUI+="Clientes (After bulk operation: pedidos excluídos):\n\n";
//        TypedQuery<Cliente> query14 = em.createNamedQuery("Cliente.findAllFetchPedido",Cliente.class);
//        clienteListFetchPedido = query14.getResultList();
//        stringToShowAtGUI+=clienteListFetchPedido.stream().map(x->x+" "+(!x.getPedido().isEmpty()?x.getPedido():"")+"\n").reduce((x,y)->x+y).orElse("");
//        stringToShowAtGUI+="\n";
        
        stringToShowAtGUI+="Cliente com mais pedidos:\n\n";
        TypedQuery<Cliente> query15 = em.createQuery("select c1 FROM Cliente c1 where not exists(select c2 from Cliente c2 where size(c2.pedido)>size(c1.pedido))",Cliente.class);
        List<Cliente> clienteMaisPedidos = query15.getResultList();
        if(!clienteMaisPedidos.isEmpty()){
            stringToShowAtGUI+=clienteMaisPedidos.stream().map(x->x+" "+(!x.getPedido().isEmpty()?x.getPedido()+" "+x.getPedido().size()+" pedidos.":"nenhum pedido.")+"\n").reduce((x,y)->x+y).orElse("");
        } else{
            stringToShowAtGUI+="nenhum cliente"+"\n";
        }
        stringToShowAtGUI+="\n";
        
        stringToShowAtGUI+="Cliente com menos pedidos:\n\n";
        TypedQuery<Cliente> query16 = em.createQuery("select c1 FROM Cliente c1 where not exists(select c2 from Cliente c2 where size(c2.pedido)<size(c1.pedido))",Cliente.class);
        List<Cliente> clienteMenosPedidos = query16.getResultList();
        stringToShowAtGUI+=clienteMenosPedidos.stream().map(x->x+" "+(!x.getPedido().isEmpty()?x.getPedido():"nenhum pedido")+"\n").reduce((x,y)->x+y).orElse("");
        stringToShowAtGUI+="\n";
        
        stringToShowAtGUI+="Clientes (native query):\n\n";
        Query query17 = em.createNativeQuery("select * from clientetable", Cliente.class);
        List<Cliente> clienteListNativeQuery = query17.getResultList();
        stringToShowAtGUI+=clienteListNativeQuery.stream().map(x->x+" "+(!x.getPedido().isEmpty()?x.getPedido():"nenhum pedido")+"\n").reduce((x,y)->x+y).orElse("");
        stringToShowAtGUI+="\n";
        
        stringToShowAtGUI+="Clientes com maior numero de pedidos (stored procedure call):\n\n";
        StoredProcedureQuery storedProcedureQuery1 = em.createNamedStoredProcedureQuery("buscaMaiorNumeroPedidos");
        Cliente busca1 = (Cliente)storedProcedureQuery1.getSingleResult();
        stringToShowAtGUI+=busca1+" "+(!busca1.getPedido().isEmpty()?busca1.getPedido():"nenhum pedido");
        stringToShowAtGUI+="\n";
        
        stringToShowAtGUI+="\nClientes com numero minimo de 2 pedidos (stored procedure call):\n\n";
        StoredProcedureQuery storedProcedureQuery2 = em.createNamedStoredProcedureQuery("buscaNumeroMinimoPedidos");
        storedProcedureQuery2.setParameter("MINIMO_PEDIDOS", 2);
        List<Cliente> busca2 = storedProcedureQuery2.getResultList();
        stringToShowAtGUI+=busca2.stream().map(x->x+" "+(!x.getPedido().isEmpty()?x.getPedido():"nenhum pedido")+"\n").reduce((x,y)->x+y).orElse("");
        stringToShowAtGUI+="\n";
        
        stringToShowAtGUI+="Clientes:\n\n";
        stringToShowAtGUI+=em.createNamedQuery("Cliente.findAll",Cliente.class).getResultList().stream().map(x->x+" "+(!x.getPedido().isEmpty()?x.getPedido():"nenhum pedido")+"\n").reduce((x,y)->x+y).orElse("nenhum registro");
        
        stringToShowAtGUI+="\nPedidos:\n\n";
        stringToShowAtGUI+=em.createNamedQuery("Pedido.findAll",Pedido.class).getResultList().stream().map(x->x+" "+(x.getCliente()!=null?x.getCliente():"nenhum cliente")+"\n").reduce((x,y)->x+y).orElse("nenhum registro");
        
        showStringGUI(stringToShowAtGUI);
    }
    public static void queriesCriteria(EntityManager em){
        String stringToShowAtGUI="";
        CriteriaBuilder cb = em.getCriteriaBuilder();
        
        CriteriaQuery<Cliente> c1 = cb.createQuery(Cliente.class);
        Root<Cliente> root1 = c1.from(Cliente.class);
        c1.select(root1);
        stringToShowAtGUI+="Clientes:\n\n";
        TypedQuery<Cliente> query1 = em.createQuery(c1);
        stringToShowAtGUI+=query1.getResultList().stream().map(x->x+" "+(!x.getPedido().isEmpty()?x.getPedido():"nenhum pedido.")+"\n").reduce((x,y)->x+y).orElse("nenhum cliente.");
        stringToShowAtGUI+="\n";
        
        CriteriaQuery<Pedido> c2 = cb.createQuery(Pedido.class);
        Root<Pedido> root2 = c2.from(Pedido.class);
        c2.select(root2);
        stringToShowAtGUI+="Pedidos:\n\n";
        TypedQuery<Pedido> query2 = em.createQuery(c2);
        stringToShowAtGUI+=query2.getResultList().stream().map(x->x+" "+(x.getCliente()!=null?x.getCliente():"nenhum cliente.")+"\n").reduce((x,y)->x+y).orElse("nenhum pedido.");
        stringToShowAtGUI+="\n";
        
        CriteriaQuery<Object[]> c3 = cb.createQuery(Object[].class);
        Root<Cliente> root3 = c3.from(Cliente.class);
        c3.multiselect(root3.<String>get("nome").alias("nomeCliente"), root3.<LocalDate>get("dataNascimento").alias("dataNascimento"));
        stringToShowAtGUI+="Cliente (nome/dataNascimento - multiselect Object[] result):\n\n";
        TypedQuery<Object[]> query3 = em.createQuery(c3);
        stringToShowAtGUI+=query3.getResultList().stream().map(x->x[0]+" "+x[1]+"\n").reduce((x,y)->x+y).orElse("nenhum cliente");
        stringToShowAtGUI+="\n";
        
        CriteriaQuery<Tuple> c4 = cb.createQuery(Tuple.class);
        Root<Cliente> root4 = c4.from(Cliente.class);
        c4.multiselect(root4.<String>get("nome").alias("nomeCliente"), root4.<LocalDate>get("dataNascimento").alias("dataNascimento"));
        stringToShowAtGUI+="Cliente (nome/dataNascimento - multiselect Tuple result):\n\n";
        TypedQuery<Tuple> query4 = em.createQuery(c4);
        stringToShowAtGUI+=query4.getResultList().stream().map(x->x.get("nomeCliente")+" "+x.get("dataNascimento")+"\n").reduce((x,y)->x+y).orElse("nenhum cliente");
        stringToShowAtGUI+="\n";
        
        CriteriaQuery<Tuple> c5 = cb.createQuery(Tuple.class);
        Root<Cliente> root5 = c5.from(Cliente.class);
        c5.multiselect(root5.<Integer>get("id").alias("cliente.id"),root5.<String>get("nome").alias("cliente.nome"),root5.<LocalDate>get("dataNascimento").alias("cliente.dataNasc"));
        CriteriaQuery<Number> c6 = cb.createQuery(Number.class);
        Root<Cliente> root6 = c6.from(Cliente.class);
        c6.select(cb.sum(root6.<Integer>get("id")));
        stringToShowAtGUI+="Cliente (id/nome/dataNascimento - multiselect triple Tuple result):\n\n";
        TypedQuery<Tuple> query5 = em.createQuery(c5);
        TypedQuery<Number> query6 = em.createQuery(c6);
        stringToShowAtGUI+=query5.getResultList().stream().map(x->x.get("cliente.id")+" "+x.get("cliente.nome")+" "+((LocalDate)x.get("cliente.dataNasc")).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))+"\n").reduce((x,y)->x+y).orElse("nenhum cliente.");
        stringToShowAtGUI+="-------------------------\n";
        stringToShowAtGUI+=query5.getResultList().size()+" registros. Sum(Ids): ";
        stringToShowAtGUI+=query6.getSingleResult()+".";
        stringToShowAtGUI+="\n\n";
        
        stringToShowAtGUI+="Clientes (Predicate e where()):\n\n";
        CriteriaQuery<String> c7 = cb.createQuery(String.class);
        Root<Cliente> root7 = c7.from(Cliente.class);
        c7.select(root7.<String>get("nome"));
        Predicate predicate1 = cb.like(root7.<String>get("nome"), "c%");
        Predicate predicate2 = cb.notLike(root7.<String>get("nome"), "c%");
        c7.where(predicate1);
        TypedQuery<String> query7 = em.createQuery(c7);
        stringToShowAtGUI+="like \"c%\":\n";
        stringToShowAtGUI+=query7.getResultList().stream().map(x->x+" ").reduce((x,y)->x+y).orElse("nenhum valor encontrado.");
        c7.where(predicate2);
        query7 = em.createQuery(c7);
        stringToShowAtGUI+="\nnot like \"c%\":\n";
        stringToShowAtGUI+=query7.getResultList().stream().map(x->x+" ").reduce((x,y)->x+y).orElse("nenhum valor encontrado.");
        stringToShowAtGUI+="\n\n";
        
        stringToShowAtGUI+="Clientes (Predicate (id between 3-5; nome='c%'; dataNascimento ano>2000) e where()):\n\n";
        CriteriaQuery<Cliente> c8 = cb.createQuery(Cliente.class);
        Root<Cliente> root8 = c8.from(Cliente.class);
        c8.select(root8);
        Predicate predicate3 = cb.and(cb.between(root8.<Integer>get("id"),3,5),cb.like(root8.<String>get("nome"),"c%"),cb.greaterThanOrEqualTo(root8.<LocalDate>get("dataNascimento"), LocalDate.of(2000, Month.JANUARY, 1)));
        c8.where(predicate3);
        TypedQuery<Cliente> query8 = em.createQuery(c8);
        stringToShowAtGUI+=query8.getResultList().stream().map(x->x+" ").reduce((x,y)->x+y).orElse("nenhum valor encontrado.");
        stringToShowAtGUI+="\n\n";
        
        stringToShowAtGUI+="Clientes (Predicate e where() - Funções - Ordering - Subqueries):\n\n";
        CriteriaQuery<String> c9 = cb.createQuery(String.class);
        Root<Cliente> root9 = c9.from(Cliente.class);
        c9.select(cb.substring(cb.upper(root9.<String>get("nome")),1,3));
        Subquery<String> c10 = c9.subquery(String.class);
        Root<Cliente> root10 = c10.from(Cliente.class);
        c10.select(root10.<String>get("nome"));
        c10.where(cb.ge(cb.length(root10.<String>get("nome")),0));
        //Predicate predicate4 = cb.and(cb.ge(cb.abs(root9.<Integer>get("id")),0),cb.lessThanOrEqualTo(root9.<LocalDate>get("dataNascimento"), LocalDate.now()),cb.ge(cb.length(root9.<String>get("nome")),0));
        Predicate predicate4 = cb.and(cb.ge(cb.abs(root9.<Integer>get("id")),0),cb.lessThanOrEqualTo(root9.<LocalDate>get("dataNascimento"), LocalDate.now()),cb.exists(c10));
        c9.where(predicate4);
        c9.orderBy(cb.desc(root9.<String>get("nome")));
        TypedQuery<String> query9 = em.createQuery(c9);
        stringToShowAtGUI+=query9.getResultList().stream().map(x->x+"\n").reduce((x,y)->x+y).orElse("nenhum valor encontrado.");
        stringToShowAtGUI+="\n\n";
        
        stringToShowAtGUI+="Clientes (cliente com menor nome):\n\n";
        CriteriaQuery<Cliente> c11 = cb.createQuery(Cliente.class);
        Root<Cliente> root11 = c11.from(Cliente.class);
        c11.select(root11);
        Subquery<Cliente> c12 = c11.subquery(Cliente.class);
        Root<Cliente> root12 = c12.from(Cliente.class);
        c12.select(root12);
        Predicate p12 = cb.gt(cb.length(root11.<String>get("nome")),cb.length(root12.<String>get("nome")));
        c12.where(p12);
        Predicate p11 = cb.not(cb.exists(c12));
        c11.where(p11);
        TypedQuery<Cliente> query11 = em.createQuery(c11);
        stringToShowAtGUI+=query11.getResultList().stream().map(x->x+"\n").reduce((x,y)->x+y).orElse("nenhum valor encontrado.");
        stringToShowAtGUI+="\n\n";
        
        stringToShowAtGUI+="Clientes (problema N+1, left join fetch):\n\n";
        CriteriaQuery<Cliente> c13 = cb.createQuery(Cliente.class);
        Root<Cliente> root13 = c13.from(Cliente.class);
        root13.fetch("pedido",JoinType.LEFT);
        c13.select(root13).distinct(true);
        TypedQuery<Cliente> query13 = em.createQuery(c13);
        stringToShowAtGUI+=query13.getResultList().stream().map(x->x+" "+(!x.getPedido().isEmpty()?x.getPedido():"nenhum pedido.")+"\n").reduce((x,y)->x+y).orElse("nenhum valor encontrado.");
        stringToShowAtGUI+="\n\n";
        
        em.getTransaction().begin();
        stringToShowAtGUI+="Clientes (Bulk operations update):\n\n";
        CriteriaUpdate<Cliente> c14 = cb.createCriteriaUpdate(Cliente.class);
        Root<Cliente> root14 = c14.from(Cliente.class);
        c14.set(root14.<String>get("nome"), cb.concat(root14.<String>get("nome"), "bulkedAltered"));
        Query query14 = em.createQuery(c14);
        query14.executeUpdate();
        em.getTransaction().commit();
        
        em.clear();
        cb = em.getCriteriaBuilder();
        c13 = cb.createQuery(Cliente.class);
        root13 = c13.from(Cliente.class);
        root13.fetch("pedido",JoinType.LEFT);
        c13.select(root13).distinct(true);
        query13 = em.createQuery(c13);
        stringToShowAtGUI+=query13.getResultList().stream().map(x->x+" "+(!x.getPedido().isEmpty()?x.getPedido():"nenhum pedido.")+"\n").reduce((x,y)->x+y).orElse("nenhum valor encontrado.");
        stringToShowAtGUI+="\n\n";
        
        em.getTransaction().begin();
        stringToShowAtGUI+="Clientes (Bulk operations delete - deleted 'c%'):\n\n";
        CriteriaDelete<Cliente> c15 = cb.createCriteriaDelete(Cliente.class);
        Root<Cliente> root15 = c15.from(Cliente.class);
        c15.where(cb.like(root15.<String>get("nome"), "c%"));
        Query query15 = em.createQuery(c15);
        query15.executeUpdate();
        em.getTransaction().commit();
        
        em.clear();
        cb = em.getCriteriaBuilder();
        c13 = cb.createQuery(Cliente.class);
        root13 = c13.from(Cliente.class);
        root13.fetch("pedido",JoinType.LEFT);
        c13.select(root13).distinct(true);
        query13 = em.createQuery(c13);
        stringToShowAtGUI+=query13.getResultList().stream().map(x->x+" "+(!x.getPedido().isEmpty()?x.getPedido():"nenhum pedido.")+"\n").reduce((x,y)->x+y).orElse("nenhum valor encontrado.");
        stringToShowAtGUI+="\n\n";
        
        stringToShowAtGUI+="Cliente (id/nome/dataNascimento - multiselect triple Tuple result with MetaModel Cliente_):\n\n";
        CriteriaQuery<Tuple> c16 = cb.createQuery(Tuple.class);
        Root<Cliente> root16 = c16.from(Cliente.class);
        c16.multiselect(root16.get(Cliente_.id).alias("cliente.id"),root16.get(Cliente_.nome).alias("cliente.nome"),root16.get(Cliente_.dataNascimento).alias("cliente.dataNasc"));
        TypedQuery<Tuple> query16 = em.createQuery(c16);
        stringToShowAtGUI+=query16.getResultList().stream().map(x->x.get("cliente.id")+" "+x.get("cliente.nome")+" "+((LocalDate)x.get("cliente.dataNasc")).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))+"\n").reduce((x,y)->x+y).orElse("nenhum cliente.");
        stringToShowAtGUI+="\n\n";
        
        stringToShowAtGUI+="Enver versioned queries:\n\n";
        AuditReader ar = AuditReaderFactory.get(em);
        AuditQuery aq = ar.createQuery().forRevisionsOfEntity(Cliente.class, true,true);
        List<Cliente> resultClienteVersion1 = new ArrayList<>();
        resultClienteVersion1.add((Cliente)(aq.add(AuditEntity.id().eq(1L)).getSingleResult()));
        stringToShowAtGUI+=resultClienteVersion1.stream().map(x->x+" "+(!x.getPedido().isEmpty()?x.getPedido():"nenhum pedido.\n")).reduce((x,y)->x+y).orElse("nenhum cliente.");
        stringToShowAtGUI+="\n\n";
        
        showStringGUI(stringToShowAtGUI);
    }
    public static void showStringGUI(String s){
        JFrame frame = new JFrame("JPQL");
        JTextArea textArea = new JTextArea(s);
        textArea.setLineWrap(true); textArea.setWrapStyleWord(true);textArea.setColumns(50); textArea.setRows(16); textArea.setMargin(new Insets(10, 10, 10, 10));
        textArea.setFont(new Font(Font.MONOSPACED, Font.BOLD, 17));
        JScrollPane scrollPanel = new JScrollPane(textArea);
        MyJPanel panel = new MyJPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 50));
        panel.add(scrollPanel);
        frame.getContentPane().add(panel);
        frame.setIconImage(new ImageIcon(new Main().getClass().getResource("/view/java.png")).getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(800, 600));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
