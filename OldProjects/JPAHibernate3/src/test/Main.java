package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import model.departamentoFuncionario.Departamento;
import model.departamentoFuncionario.Funcionario;
import view.MyJPanel;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory=null;
        EntityManager em=null;
        try{
            factory = Persistence.createEntityManagerFactory("departamentofuncionarioPU");
            em = factory.createEntityManager();
            popular(em);
            listarFuncionariosConsole(em);
            listarDepartamentosConsole(em);
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
        Funcionario f1 = new Funcionario("Daniel");
        Funcionario f2 = new Funcionario("Juliana");
        Funcionario f3 = new Funcionario("Tina");
        Funcionario f4 = new Funcionario("Bosco");
        Funcionario f5 = new Funcionario("Luke");
        Funcionario f6 = new Funcionario("Apollo");
        Funcionario f7 = new Funcionario("");
        em.persist(f1);em.persist(f2);em.persist(f3);em.persist(f4);em.persist(f5);em.persist(f6);em.persist(f7);
        Departamento d1 = new Departamento("departamento1", Arrays.asList(f1,f2,f3));
        Departamento d2 = new Departamento("departamento2", Arrays.asList(f4));
        Departamento d3 = new Departamento("departamento3", Arrays.asList(f5));
        Departamento d4 = new Departamento("departamento4", null);
        Departamento d5 = new Departamento("departamento5", new ArrayList<>());
        Departamento d6 = new Departamento(null, null);
        em.persist(d1);em.persist(d2);em.persist(d3);em.persist(d4);em.persist(d5);em.persist(d6);
        em.getTransaction().commit();
    }
    private static void listarFuncionariosConsole(EntityManager em){
        List<Funcionario> funcionarioList = em.createQuery("select f from Funcionario f").getResultList();
        if(!funcionarioList.isEmpty()){
            funcionarioList.forEach(System.out::println);
        } else{
            System.out.println("Lista de funcionários VAZIA!");
        }
    }
    private static void listarDepartamentosConsole(EntityManager em){
        List<Departamento> departamentoList = em.createQuery("select d from Departamento d").getResultList();
        if(!departamentoList.isEmpty()){
            departamentoList.forEach(System.out::println);
        } else{
            System.out.println("Lista de departamentos VAZIA!");
        }
    }
    private static void listarDepartamentosGUI(EntityManager em){
        List<Departamento> departamentoList = em.createQuery("select d from Departamento d").getResultList();
        List<Funcionario> funcionarioList = em.createQuery("select f from Funcionario f").getResultList();
        JFrame frame = new JFrame("Lista de Departamentos - Relacionamento Departamento 1 x N Funcionários");
        JTextArea textArea = new JTextArea(getDepartamentoStringView(departamentoList)+"\n"+"Funcionários sem departamento:\n"+getFuncionariosSemDepartamento(departamentoList, funcionarioList));
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
    private static String getDepartamentoStringView(List<Departamento> list){
        return list.stream().map(x->x.toString()+"\n").reduce((x,y)->x+y).get();
    }
    private static String getFuncionariosSemDepartamento(List<Departamento> listDep, List<Funcionario> listFunc){
        List<Funcionario> listFuncAlocados = new ArrayList<>();
        
//        for(Departamento dep:listDep){
//            if(dep.getFuncionarioList()!=null)
//                listFuncAlocados.addAll(dep.getFuncionarioList());
//        }
        
//        listDep.forEach(new Consumer<Departamento>() {
//            @Override
//            public void accept(Departamento dep) {
//                if(dep.getFuncionarioList()!=null){
//                    listFuncAlocados.addAll(dep.getFuncionarioList());
//                }
//            }
//        });
        
        listDep.forEach(dep -> {if(dep.getFuncionarioList()!=null){
                listFuncAlocados.addAll(dep.getFuncionarioList());}});
        
        listFunc.removeIf(x->listFuncAlocados.contains(x));
        return listFunc.stream().map(x->x.toString()+"\n").reduce((x,y)->x+y).get();
    }
}

//sql command for viewing the three tables joined:
//select * from departamentotable d join (select * from dep_func_jointable df join funcionáriotable f on df.func_Id=f.id_Funcionário) x on d.id_Departamento=x.depto_Id;
//sql command for full outer join:
//select * from 
/*
(select * from departamentotable d left join (select * from dep_func_jointable df left join funcionáriotable f on df.func_Id=f.id_Funcionário) x on d.id_Departamento=x.depto_Id)
union
(select * from departamentotable d right join (select * from dep_func_jointable df right join funcionáriotable f on df.func_Id=f.id_Funcionário) x on d.id_Departamento=x.depto_Id);
*/