package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import model.pedidoCliente.Cliente;
import model.pedidoCliente.Pedido;
import view.MyJPanel;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory=null;
        EntityManager em=null;
        try{
            factory = Persistence.createEntityManagerFactory("pedidoclientePU");
            em = factory.createEntityManager();
            popular(em);
            listarClientesConsole(em);
            listarPedidosConsole(em);
            listarDepartamentosGUI(em);
        } catch(Exception ex){
            ex.printStackTrace();
        } finally{
            if(em!=null&&em.isOpen())
                em.close();
            if(factory!=null&&factory.isOpen())
                factory.close();
        }
    }
    public static void popular(EntityManager em){
        em.getTransaction().begin();
        Cliente c1 = new Cliente("Daniel",LocalDate.of(1982, Month.APRIL, 22));
        Cliente c2 = new Cliente("Juliana",LocalDate.of(1986, Month.FEBRUARY, 24));
        Cliente c3 = new Cliente("Tina",LocalDate.of(1951, Month.OCTOBER, 16));
        Cliente c4 = new Cliente("Bosco",LocalDate.of(1947, Month.JUNE, 24));
        Cliente c5 = new Cliente("Luke",null);
        Cliente c6 = new Cliente("",null);
        Cliente c7 = new Cliente(null,null);
        em.persist(c1);em.persist(c2);em.persist(c3);em.persist(c4);em.persist(c5);em.persist(c6);em.persist(c7);
        Pedido p1 = new Pedido("pedido1", LocalDateTime.now(),c2);
        Pedido p2 = new Pedido("pedido2", LocalDateTime.now(),c3);
        Pedido p3 = new Pedido("pedido3", LocalDateTime.now(),c4);
        Pedido p4 = new Pedido("pedido4", LocalDateTime.now(),c5);
        Pedido p5 = new Pedido("", LocalDateTime.now(),c6);
        Pedido p6 = new Pedido(null, null,null);
        em.persist(p1);em.persist(p2);em.persist(p3);em.persist(p4);em.persist(p5);em.persist(p6);
        em.getTransaction().commit();
    }
    private static void listarClientesConsole(EntityManager em){
        List<Cliente> clienteList = em.createQuery("select c from Cliente c").getResultList();
        if(!clienteList.isEmpty()){
            clienteList.forEach(System.out::println);
        } else{
            System.out.println("Lista de clientes VAZIA!");
        }
    }
    private static void listarPedidosConsole(EntityManager em){
        List<Pedido> pedidoList = em.createQuery("select p from Pedido p").getResultList();
        if(!pedidoList.isEmpty()){
            pedidoList.forEach(System.out::println);
        } else{
            System.out.println("Lista de pedidos VAZIA!");
        }
    }
    private static void listarDepartamentosGUI(EntityManager em){
        List<Pedido> pedidoList = em.createQuery("select p from Pedido p").getResultList();
        List<Cliente> clienteList = em.createQuery("select c from Cliente c").getResultList();
        JFrame frame = new JFrame("Lista de Pedidos - Relacionamento Pedido N x 1 Cliente");
        JTextArea textArea = new JTextArea(getPedidoStringView(pedidoList)+"\n"+"Clientes sem pedido:\n"+getClienteSemPedido(pedidoList, clienteList));
//        textArea.setLineWrap(true); textArea.setEditable(false); textArea.setWrapStyleWord(true); 
        textArea.setFont(new Font(Font.MONOSPACED,Font.BOLD, 18)); textArea.setMargin(new Insets(10, 10, 0, 10));
        textArea.setRows(8); textArea.setColumns(45);textArea.setBackground(Color.BLACK); textArea.setForeground(Color.WHITE);
        textArea.setToolTipText("Scroll up/down to see all results");
        JScrollPane scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setPreferredSize(new Dimension(450, 230));
        MyJPanel panel = new MyJPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 30));
        panel.add(scroll,BorderLayout.CENTER);
        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(new Dimension(800, 700));
        frame.setLocationRelativeTo(null);
        frame.setIconImage(new ImageIcon(new Main().getClass().getResource("/test/brasil.png")).getImage());
        frame.setVisible(true);
    }
    private static String getPedidoStringView(List<Pedido> list){
        return list.stream().map(x->x.toString()+"\n").reduce((x,y)->x+y).get();
    }
    private static String getClienteSemPedido(List<Pedido> listPedido, List<Cliente> listCliente){
        listPedido.removeIf(x->x.getCliente()==null);
        List<Cliente> clienteComPedido = listPedido.stream().map(x->x.getCliente()).collect(Collectors.toList());
        listCliente.removeAll(clienteComPedido);
        return listCliente.stream().map(x->x.toString()+"\n").reduce((x,y)->x+y).get();
    }
}


//sql command for full outer join:
/*
select * from
(select * from pedidotable p left join clientetable c on p.cliente_id=c.id_cliente
union
select * from pedidotable p right join clientetable c on p.cliente_id=c.id_cliente) x
*/