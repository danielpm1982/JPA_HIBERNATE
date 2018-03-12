package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import model.estadoGovernador.Estado;
import model.estadoGovernador.EstadoCodigoEnum;
import model.estadoGovernador.EstadoNomeGovernadorEnum;
import model.estadoGovernador.Governador;
import view.MyJPanel;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory=null;
        EntityManager em=null;
        try{
            factory = Persistence.createEntityManagerFactory("estadogovernadorPU");
            em = factory.createEntityManager();
            popular(em);
            listarGovernadoresConsole(em);
            listarEstadosConsole(em);
            listarEstadosGUI(em);
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
        for (EstadoNomeGovernadorEnum ee:EstadoNomeGovernadorEnum.values()){
            Governador g = new Governador(ee.getNomeGovernador());
            Estado e = new Estado(ee.name(), EstadoCodigoEnum.valueOf(ee.name()).getCodigo(), g);
            em.persist(g);
            em.persist(e);
        }
        em.getTransaction().commit();
    }
    private static void listarGovernadoresConsole(EntityManager em){
        List<Governador> governadorList = em.createQuery("select g from Governador g").getResultList();
        if(!governadorList.isEmpty()){
            governadorList.forEach(System.out::println);
        } else{
            System.out.println("Lista de governadores VAZIA!");
        }
    }
    private static void listarEstadosConsole(EntityManager em){
        List<Estado> estadoList = em.createQuery("select e from Estado e").getResultList();
        if(!estadoList.isEmpty()){
            estadoList.forEach(System.out::println);
        } else{
            System.out.println("Lista de estados VAZIA!");
        }
    }
    private static void listarEstadosGUI(EntityManager em){
        List<Estado> estadoList = em.createQuery("select e from Estado e").getResultList();
        JFrame frame = new JFrame("Lista de Estados da República Federativa do Brasil");
        JTextArea textArea = new JTextArea(getEstadoStringView(estadoList));
        //textArea.setLineWrap(true); textArea.setEditable(false); textArea.setWrapStyleWord(true); 
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
    private static String getEstadoStringView(List<Estado> list){
        return list.stream().map(x->x.toString()+"\n").reduce((x,y)->x+y).get();
    }    
}
