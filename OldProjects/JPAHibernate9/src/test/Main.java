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
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import modelo.Cliente;
import modelo.NomeDataCliente;
import modelo.Pedido;
import view.MyJPanel;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pedidoclientePU");
        EntityManager em = factory.createEntityManager();
        try{
            popular(em);
            queries(em);
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
    public static void queries(EntityManager em){
        
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
