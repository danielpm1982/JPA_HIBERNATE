/*
Devido aos erros ddl intermitentes e decorrentes do drop errado das tables sobre as quais há 
dependência (joinTables), a política de criação foi trocada, no persistence.xml para value="create". 
Logo, antes de se rodar este projeto que se exclua manualmente as tabelas existentes, deixando o 
banco vazio para a criação das tabelas corretas. Inconsistência nos dados serão geradas se isso não 
for feito.
*/

package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.Month;
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
import model.pedidoCliente.Livro;
import model.pedidoCliente.Autor;
import view.MyJPanel;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory=null;
        EntityManager em=null;
        try{
            factory = Persistence.createEntityManagerFactory("livroAutorPU");
            em = factory.createEntityManager();
            popular(em);
            listarAutoresConsole(em);
            listarLivrosConsole(em);
            listarLivrosGUI(em);
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
        Autor a1 = new Autor("Daniel");
        Autor a2 = new Autor("Juliana");
        Autor a3 = new Autor("");
        Autor a4 = new Autor(null);
        Autor a5 = new Autor("Fulano");
        
        Livro l1 = new Livro("livro1", LocalDate.of(2000, Month.APRIL, 10),2);
        Livro l2 = new Livro("livro2", LocalDate.of(1990, Month.JANUARY, 1),3);
        Livro l3 = new Livro("livro3", LocalDate.of(1999, Month.DECEMBER, 5),7);
        Livro l4 = new Livro("livro4", LocalDate.of(2005, Month.SEPTEMBER, 20),9);
        
        a1.setLivroList(Arrays.asList(l1,l3));
        a2.setLivroList(Arrays.asList(l1,l2,l3));
        a3.setLivroList(Arrays.asList(l4));
        
        l1.setAutorList(Arrays.asList(a1,a2));
        l2.setAutorList(Arrays.asList(a2));
        l3.setAutorList(Arrays.asList(a1,a2));
        l4.setAutorList(Arrays.asList(a3));
        
        em.persist(a1);em.persist(a2);em.persist(a3);em.persist(a4);em.persist(a5);
        em.persist(l1);em.persist(l2);em.persist(l3);em.persist(l4);
        
        em.getTransaction().commit();
    }
    private static void listarAutoresConsole(EntityManager em){
        List<Autor> autorList = em.createQuery("select a from Autor a").getResultList();
        if(autorList!=null&&!autorList.isEmpty()){
            autorList.forEach(x->System.out.println(x+" "+x.getListLivrosThisString()));
        } else{
            System.out.println("Lista de autores VAZIA!");
        }
    }
    private static void listarLivrosConsole(EntityManager em){
        List<Livro> livroList = em.createQuery("select l from Livro l").getResultList();
        if(livroList!=null&&!livroList.isEmpty()){
            livroList.forEach(x->System.out.println(x+" "+x.getListAutorThisString()));
        } else{
            System.out.println("Lista de livros VAZIA!");
        }
    }
    private static void listarLivrosGUI(EntityManager em){
        List<Autor> autorList = em.createQuery("select a from Autor a").getResultList();
        List<Livro> livroList = em.createQuery("select l from Livro l").getResultList();
        JFrame frame = new JFrame("Lista de Livros - Relacionamento Livro N x N Autor (com relacionamento bidirecional)");
        JTextArea textArea = new JTextArea(getLivroStringView(livroList)+"\n"+"Autores sem livro:\n"+getAutorSemLivro(autorList,livroList));
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
    private static String getLivroStringView(List<Livro> list){
        return list.stream().map(x->x.toString()+" "+x.getListAutorThisString()+"\n").reduce((x,y)->x+y).get();
    }
    private static String getAutorSemLivro(List<Autor> listAutor, List<Livro> listLivro){
        listLivro.removeIf(x->x.getAutorList()==null);
        List<Autor> autorComLivro = new ArrayList<>();
        listLivro.forEach(x->{x.getAutorList().forEach(y->autorComLivro.add(y));});
//        for(Livro l:listLivro){
//            for(Autor a:l.getAutorList()){
//                autorComLivro.add(a);
//            }
//        }
        listAutor.removeAll(autorComLivro);
        return listAutor.stream().map(x->x.toString()+" "+x.getListLivrosThisString()+"\n").reduce((x,y)->x+y).orElse("Nenhum autor sem livro!");
    }
}


//sql command for full outer join:
/*
select * from (select * from livrotable l left join (select * from livro_autor_table la left join autortable a on la.autor_id=a.id_autor) r on l.id_livro=r.livro_id
union
select * from livrotable l right join (select * from livro_autor_table la right join autortable a on la.autor_id=a.id_autor) r on l.id_livro=r.livro_id)
x

ou

select * from (select * from livrotable l left join (select * from autor_livro_table al left join autortable a on al.autor_id=a.id_autor) r on l.id_livro=r.livro_id
union
select * from livrotable l right join (select * from autor_livro_table al right join autortable a on al.autor_id=a.id_autor) r on l.id_livro=r.livro_id)
x

Caso o mapped by não seja usado em nenhuma das tabelas, ambas as tabelas de relacionamento existirão
e a busca sql poderá ser feita por quaisquer das duas, já que ambas relacionam as mesmas relações,
entidades, atributos e possuem constraints equivalentes, seja pelo acesso A->B seja B->A.

Já, caso o mapped by seja usado em uma das duas classes de entidades (Autor ou Livro), definindo que o mapeamento
se dará pela jointable da outra classe (através do atributo que na outra classe representa a primeira para
fins de acesso), então apenas uma, e não duas, tabela de relacionamento será criada para a relação NxN,
assim como se houvesse atributo de relacionamento em apenas uma das classes e não nas duas, como nesse caso.
Ao invés de serem criadas duas joinTables apenas 1 delas é criada, e que servirá de mapeamento tanto para
a classe em que se encontra como para outra classe "mappedBy", sem que a outra tenha também que criar uma
segunda joinTable para o mesmo fim. Em outros relacionamentos isto tabmém pode ser usado para se reduzir
o número de tabelas criadas, caso se tenha atributos de relacionamento em mais de uma classe e se possa
mapear estes apenas por uma tabela ou por tabela extra nenhuma, apenas com as de entidade e joinColumns nestas
(por ex no relacionametno 1xN e Nx1 em que o atributo de relacionamento escolhido como mapeador seja um Objeto 
simples e não uma lista -> na classe N).
*/
