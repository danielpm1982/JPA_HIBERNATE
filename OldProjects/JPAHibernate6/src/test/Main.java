package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import model.Endereco;
import model.Pessoa;
import model.PessoaFisica;
import model.PessoaJuridica;
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
        em.getTransaction().begin();
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
        Pessoa p7 = new PessoaFisica("Bosco", null,"06060358349");
        Pessoa p8 = new PessoaJuridica("DPMCompany", null,"06.999.111/0000-99");
        
        em.persist(p1);em.persist(p2);em.persist(p3);em.persist(p4);em.persist(p5);em.persist(p6);em.persist(p7);em.persist(p8);
        
        List <Integer> intList = new SecureRandom().ints(1000, 1000000000, Integer.MAX_VALUE).boxed().collect(Collectors.toList());
        List <Integer> intCharList = new SecureRandom().ints(58, 65,122).boxed().collect(Collectors.toList());
        System.out.println();
        for (int i=0;i<1000;i++){
            Collections.shuffle(intCharList);
            String nome=String.valueOf(Character.toChars(intCharList.get(0)))+String.valueOf(Character.toChars(intCharList.get(10)))+String.valueOf(Character.toChars(intCharList.get(20)))+String.valueOf(Character.toChars(intCharList.get(30)))+String.valueOf(Character.toChars(intCharList.get(40)))+String.valueOf(Character.toChars(intCharList.get(50)))+String.valueOf(Character.toChars(intCharList.get(15)))+String.valueOf(Character.toChars(intCharList.get(25)))+String.valueOf(Character.toChars(intCharList.get(57)));
            em.persist(new Pessoa(nome,null));
            Collections.shuffle(intList);
            Collections.shuffle(intCharList);
            String nome2=String.valueOf(Character.toChars(intCharList.get(0)))+String.valueOf(Character.toChars(intCharList.get(10)))+String.valueOf(Character.toChars(intCharList.get(20)))+String.valueOf(Character.toChars(intCharList.get(30)))+String.valueOf(Character.toChars(intCharList.get(40)))+String.valueOf(Character.toChars(intCharList.get(50)))+String.valueOf(Character.toChars(intCharList.get(15)))+String.valueOf(Character.toChars(intCharList.get(25)))+String.valueOf(Character.toChars(intCharList.get(57)));
            em.persist(new PessoaFisica(nome2, null, intList.stream().findAny().get().toString()));
            Collections.shuffle(intList);
            Collections.shuffle(intCharList);
            String nome3=String.valueOf(Character.toChars(intCharList.get(0)))+String.valueOf(Character.toChars(intCharList.get(10)))+String.valueOf(Character.toChars(intCharList.get(20)))+String.valueOf(Character.toChars(intCharList.get(30)))+String.valueOf(Character.toChars(intCharList.get(40)))+String.valueOf(Character.toChars(intCharList.get(50)))+String.valueOf(Character.toChars(intCharList.get(15)))+String.valueOf(Character.toChars(intCharList.get(25)))+String.valueOf(Character.toChars(intCharList.get(57)));
            em.persist(new PessoaJuridica(nome3, null, intList.stream().findAny().get().toString()));
        }
        
        em.getTransaction().commit();
    }
    private static void listarPessoaGUI(EntityManager em){
        List<Pessoa> pessoaList = em.createQuery("select p from Pessoa p order by p.id").getResultList();
        JFrame frame = new JFrame("Lista de Pessoas - Embedded Endereço - Three Inheritance Types - AttributeConverter");
        JTextArea textArea = new JTextArea(pessoaList.stream().map(x->x+"\n").reduce((x,y)->x+y).orElse("pessoa inexistente")+"\nPessoas sem Endereços:\n"+getPessoaSemEndereco(pessoaList));
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

/*
sql search for single-table inheritance construction:
select `DTYPE`,id_pessoa,nome_pessoa,cpf,cnpj,logradouro,numero,cep,cidade,estado,pais from pessoatable;

sql search for joined inheritance construction:
select * from 
(
select pt.id_pessoa,nome_pessoa, null as cpf, null as cnpj,logradouro, numero, cep, cidade, estado, pais from pessoatable pt
where id_pessoa not in (select id_pessoa from pessoafisica) and id_pessoa not in (select id_pessoa from pessoajuridica)
union all
select pt.id_pessoa,nome_pessoa, cpf, null as cnpj, logradouro, numero, cep, cidade, estado, pais from pessoatable pt
join
pessoafisica pf on pt.id_pessoa=pf.id_pessoa
union all
select pt.id_pessoa,nome_pessoa, null as cpf, cnpj, logradouro, numero, cep, cidade, estado, pais from pessoatable pt
join
pessoajuridica pj on pt.id_pessoa=pj.id_pessoa
) x
order by id_pessoa

sql search for table_per_class inheritance construction:
select * from 
(
select id_pessoa,nome_pessoa, null as cpf, null as cnpj,logradouro, numero, cep, cidade, estado, pais from pessoatable
union all
select id_pessoa,nome_pessoa, cpf, null as cnpj, logradouro, numero, cep, cidade, estado, pais from pessoafisica
union all
select id_pessoa,nome_pessoa, null as cpf, cnpj, logradouro, numero, cep, cidade, estado, pais from pessoajuridica
) x
order by id_pessoa
*/