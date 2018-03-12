package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import model.Endereco;
import model.Pessoa;
import view.MyJPanel;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory=null;
        EntityManager em=null;
        try{
            factory = Persistence.createEntityManagerFactory("enderecopessoaPU");
            em = factory.createEntityManager();
            popular(em);
            listarPessoaGUI(em);
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
        Endereco e1 = new Endereco("Dom Manuel de Medeiros", "2785", "60455305", "Fortaleza", "CE", "Brasil");
        Endereco e2 = new Endereco("Abilio Martins", "117", "60455470", "Fortaleza", "CE", "Brasil");
        Endereco e3 = new Endereco("Jovino Guedes", "60", "60000000", "Fortaleza", "CE", "Brasil");
        Endereco e4 = new Endereco("Fiúsa de Pontes", "191", "60000000", "Fortaleza", "CE", "Brasil");
        Endereco e5 = new Endereco("", "", "", "", "", "");
        
        Pessoa p1 = new Pessoa("Daniel", e1);
        Pessoa p2 = new Pessoa("Juliana", e2);
        Pessoa p3 = new Pessoa("Zuleida", e3);
        Pessoa p4 = new Pessoa("Eneida", e4);
        Pessoa p5 = new Pessoa("Tina", e5);
        Pessoa p6 = new Pessoa("Bosco", null);
        
        p1.setEndereco(e1); e1.setPessoa(p1);
        p2.setEndereco(e2); e2.setPessoa(p2);
        p3.setEndereco(e3); e3.setPessoa(p3);
        p4.setEndereco(e4); e4.setPessoa(p4);
        p5.setEndereco(e5); e5.setPessoa(p5);
        p6.setEndereco(null);
        //Persist of some of the Endereco objects below, as well as of some of the Pessoa objects below, is done by cascading, by @OneToOne(cascade = CascadeType.PERSIST) at Pessoa class and at Endereco class - cascading is a unidirectional property that must be set at both classes if it is to be valued from both sides of the attribute relation
        //em.persist(e4);em.persist(e5);
        em.persist(e1);em.persist(e2);em.persist(e3);
        em.persist(p4);em.persist(p5);em.persist(p6);
        
        em.getTransaction().begin();
        em.getTransaction().commit();
        
        //Orphan objects, from both entities, automatically removed (after the mapped other object is programatically removed) through "orphanRemoval = true" option at both classes, also an unidirectional property
        em.remove(p6); em.remove(p5);
        em.getTransaction().begin();
        em.getTransaction().commit();
        System.out.println("Endereços:");
        em.createQuery("select e from Endereco e",Endereco.class).getResultList().forEach(e->System.out.println(e+" "+e.getPessoa()));
        
        
    }
    private static void listarPessoaGUI(EntityManager em){
        List<Pessoa> pessoaList = em.createQuery("select p from Pessoa p order by p.id").getResultList();
        JFrame frame = new JFrame("Lista de Pessoas - EntityManager - Cascading / OrphanRemoval / Callbacks");
        JTextArea textArea = new JTextArea(pessoaList.stream().map(x->x+" "+(x.getEndereco()!=null&&!x.getEndereco().getLogradouro().equals("")?x.getEndereco().toString():"sem endereço")+"\n").reduce((x,y)->x+y).orElse("pessoa inexistente")+"\nPessoas sem Endereços:\n"+getPessoaSemEndereco(pessoaList));
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
        frame.setSize(new Dimension(700, 600));
        frame.setLocationRelativeTo(null);
        frame.setIconImage(new ImageIcon(new Main().getClass().getResource("/test/brasil.png")).getImage());
        frame.setVisible(true);
    }
    
    public static String getPessoaSemEndereco(List<Pessoa> listPessoa){
        List<Pessoa> listaPessoaSemEndereco = new ArrayList<>();
        listPessoa.forEach(x->{
            if(x.getEndereco()==null||x.getEndereco().getLogradouro().equals("")||x.getEndereco().getNumero().equals("")){
                listaPessoaSemEndereco.add(x);
            }
        });
        return listaPessoaSemEndereco.stream().map(x->x+"\n").reduce((x,y)->x+y).orElse("nenhuma pessoa sem endereço!");
    }
}
