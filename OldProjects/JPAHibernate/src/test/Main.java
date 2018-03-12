package test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import model.Author;
import model.CountryDDIEnum;
import view.MyJPanel;
import model.Publisher;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        EntityManagerFactory factory=null;
        EntityManager em=null;
        try{
            factory = Persistence.createEntityManagerFactory("Bookstore_DB_PU");
            em = factory.createEntityManager();
            persist(em);
            modifyRename(em);
            modifyRemove(em);
            query(em);
        } finally{
            if(em!=null&&em.isOpen()){
                em.close();
            }
            if(factory!=null&&factory.isOpen()){
                factory.close();
            }
        }
    }
    
    public static void persist(EntityManager em){
        byte[] logo1 = getLogoType1bytes();
        byte[] logo2 = getLogoType2bytes();
        
        em.persist(new Author(null,"Daniel",LocalDateTime.of(1982, Month.APRIL, 22, 12, 00, 00)));
        em.persist(new Author(null,"Juliana",LocalDateTime.of(1986, Month.FEBRUARY, 24, 00, 00, 00)));
        em.persist(new Author(null,"Tina",LocalDateTime.of(1951, Month.OCTOBER, 16, 15, 00, 00)));
        em.persist(new Author(null,"Bosco",LocalDateTime.of(1947, Month.JUNE, 24, 16, 00, 00)));
        
        em.persist(new Publisher(null, "PineBooks", "pineBooks@gmail.com",logo1,CountryDDIEnum.ESTADOS_UNIDOS,Arrays.asList("555-0001","555-0002","555-0003","555-0004","555-0005")));
        em.persist(new Publisher(null, "PinheiroBooksLTDA", "pinheiroBooksLTDA@gmail.com",logo2,CountryDDIEnum.BRASIL,Arrays.asList("85-9874-0254","11-3433-0000")));

        em.persist(new Author(null,"Daniel",LocalDateTime.of(1982, Month.APRIL, 22, 12, 00, 00)));
        em.persist(new Author(null,"Juliana",LocalDateTime.of(1986, Month.FEBRUARY, 24, 00, 00, 00)));
        em.persist(new Author(null,"Tina",LocalDateTime.of(1951, Month.OCTOBER, 16, 15, 00, 00)));
        em.persist(new Author(null,"Bosco",LocalDateTime.of(1947, Month.JUNE, 24, 16, 00, 00)));
        
        em.persist(new Publisher(null, "PineBooks", "pineBooks@gmail.com",logo1,CountryDDIEnum.CHILE,Arrays.asList("2-4561-5555","2-555-0000")));
        em.persist(new Publisher(null, "PinheiroBooksLTDA", "pinheiroBooksLTDA@gmail.com",logo2,CountryDDIEnum.REINO_UNIDO,Arrays.asList("345-1245")));
        
        em.persist(new Author(null,"Daniel",LocalDateTime.of(1982, Month.APRIL, 22, 12, 00, 00)));
        em.persist(new Author(null,"Juliana",LocalDateTime.of(1986, Month.FEBRUARY, 24, 00, 00, 00)));
        em.persist(new Author(null,"Tina",LocalDateTime.of(1951, Month.OCTOBER, 16, 15, 00, 00)));
        em.persist(new Author(null,"Bosco",LocalDateTime.of(1947, Month.JUNE, 24, 16, 00, 00)));
        
        em.persist(new Publisher(null, "PineBooks", "pineBooks@gmail.com",logo1,CountryDDIEnum.CANADÁ,Arrays.asList("1234-1234","1234-9874")));
        em.persist(new Publisher(null, "PinheiroBooksLTDA", "pinheiroBooksLTDA@gmail.com",logo2,CountryDDIEnum.IRLANDA,Arrays.asList("9999-1234")));
        
        em.getTransaction().begin();
        em.getTransaction().commit();
        
    }
    
    //read a pic as byte[] to set as a blob attribute to an entity class to be persisted at the DB
    public static byte[] getLogoType1bytes(){
        try {
            return Files.readAllBytes(Paths.get(new Main().getClass().getResource("/view/books.png").toURI()));
        } catch (URISyntaxException|IOException ex) {
            ex.printStackTrace();
        }
        return new byte[]{};
    }
    
    //read another pic as byte[] to set as a blob attribute to an entity class to be persisted at the DB
    public static byte[] getLogoType2bytes(){
        try {
            return Files.readAllBytes(Paths.get(new Main().getClass().getResource("/view/library.png").toURI()));
        } catch (URISyntaxException|IOException ex) {
            ex.printStackTrace();
        }
        return new byte[]{};
    }
    
    public static void modifyRename(EntityManager em){
        Author a = em.createQuery("select a from Author a where a.name='Daniel'",Author.class).getResultList().get(0);
        JOptionPane.showMessageDialog(null, a);
        JOptionPane.showMessageDialog(null, "Modifying to Daniel Pinheiro");
        a.setName("Daniel Pinheiro");
        em.getTransaction().begin();
        em.getTransaction().commit();
//        a = em.createQuery("select a from Author a where a.name='Daniel Pinheiro'",Author.class).getSingleResult();
        a = em.createQuery("select a from Author a where a.name='Daniel Pinheiro'",Author.class).getResultList().get(0);
        if(a.getName().equals("Daniel Pinheiro")){
            JOptionPane.showMessageDialog(null, a+"\nSuccessfully modified!");
        } else{
            JOptionPane.showMessageDialog(null, a+"\nFailed modifying!");
        }
    }
    
    public static void modifyRemove(EntityManager em){
        Author a = em.createQuery("select a from Author a where a.name='Daniel Pinheiro'",Author.class).getResultList().get(0);
        JOptionPane.showMessageDialog(null, "Excluding "+a+"...");
        em.getTransaction().begin();
        em.remove(a);
        em.getTransaction().commit();
        if(em.createQuery("select a from Author a where a.name='Daniel Pinheiro'",Author.class).getResultList().isEmpty()){
            JOptionPane.showMessageDialog(null, a+"\nSuccessfully excluded!");
            JOptionPane.showMessageDialog(null, "Inserting Daniel again \n(as a new register, with a new key)...");
            em.persist(new Author(null, "Daniel",LocalDateTime.of(1982, Month.APRIL, 22, 12, 00, 00)));
            em.getTransaction().begin();
            em.getTransaction().commit();
//            a = em.createQuery("select a from Author a where a.name='Daniel'",Author.class).getSingleResult();
            a = em.createQuery("select a from Author a where a.name='Daniel'",Author.class).getResultList().get(0);
            if(a.getName().equals("Daniel")){
                JOptionPane.showMessageDialog(null, a+"\nSuccessfully reinserted\n(with different auto generated key)!");
            } else{
                JOptionPane.showMessageDialog(null, a+"\nFailed to be reinserted!");
            }
        } else{
            JOptionPane.showMessageDialog(null, a+"\nFailed excluding!");
        }
    }
    
    public static void query(EntityManager em){
//        Author author1 = em.find(Author.class, 1L);
//        Author author2 = em.getReference(Author.class, 2L);
//        System.out.println(author1);
//        System.out.println(author2);
        List<Author> listAuthor = em.createQuery("select e from Author e").getResultList();
        List<Publisher> listPublisher = em.createQuery("select e from Publisher e").getResultList();
        System.out.println("Authors:");
        listAuthor.forEach(System.out::println); System.out.println("");
        System.out.println("Publishers:");
        listPublisher.forEach(System.out::println); System.out.println("");
        printQueryResultsGUI(listAuthor,listPublisher);
        listPublisher.stream().distinct().forEach(x->printPublisherLogo(x));
    }
    
    public static void printQueryResultsGUI(List<Author> listAuthor, List<Publisher> listPublisher){
        String[][] dataRowAuthor = new String[listAuthor.size()][3];
        for(int i=0;i<listAuthor.size();i++){
            dataRowAuthor[i][0]=listAuthor.get(i).getId().toString();
            dataRowAuthor[i][1]=listAuthor.get(i).getName();
            dataRowAuthor[i][2]=listAuthor.get(i).getBirth().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
        }
        JTable tableAuthor = new JTable(dataRowAuthor,new String[]{"AuthorID","AuthorName","BirthDate"});
        tableAuthor.getTableHeader().setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        tableAuthor.setRowHeight(30);
        tableAuthor.setFont(new Font(Font.DIALOG, Font.BOLD+Font.PLAIN, 16));
        JScrollPane scrollPanelAuthor = new JScrollPane(tableAuthor,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //scrollPanelAuthor.setPreferredSize(new Dimension(400, 200));
        int tableActorHigh = (tableAuthor.getRowCount()+1)*tableAuthor.getRowHeight();
        scrollPanelAuthor.setPreferredSize(new Dimension(550,tableActorHigh+5>300?300:tableActorHigh+5));
        scrollPanelAuthor.setWheelScrollingEnabled(true);
        
        String[][] dataRowPublisher = new String[listPublisher.size()][4];
        for(int i=0;i<listPublisher.size();i++){
            dataRowPublisher[i][0]=listPublisher.get(i).getId().toString();
            dataRowPublisher[i][1]=listPublisher.get(i).getName();
            dataRowPublisher[i][2]=listPublisher.get(i).getEmail();
            dataRowPublisher[i][3]=listPublisher.get(i).getCountryDDI().toString()+" "+listPublisher.get(i).getTelefoneString();
        }
        JTable tablePublisher = new JTable(dataRowPublisher,new String[]{"PublisherID","PublisherName","PublisherEmail","Contact"});
        tablePublisher.getTableHeader().setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        tablePublisher.setRowHeight(30);
        tablePublisher.setFont(new Font(Font.DIALOG, Font.BOLD+Font.PLAIN, 14));
        JScrollPane scrollPanelPublisher = new JScrollPane(tablePublisher,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //scrollPanelPublisher.setPreferredSize(new Dimension(800, 200));
        int tablePublisherHigh = (tablePublisher.getRowCount()+1)*tablePublisher.getRowHeight();
        scrollPanelPublisher.setPreferredSize(new Dimension(1000,tablePublisherHigh+5>220?220:tablePublisherHigh+5));
        scrollPanelPublisher.setWheelScrollingEnabled(true);
        tablePublisher.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tableAuthor.clearSelection();
            }
        });
        tableAuthor.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tablePublisher.clearSelection();
            }
        });
        
        JFrame frame = new JFrame("JPA/Hibernate EntityManager Results GUI");
        frame.setIconImage(new ImageIcon(new Main().getClass().getResource("/view/books.png")).getImage());
        MyJPanel panel = new MyJPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 80));
        panel.add(scrollPanelAuthor);
        panel.add(scrollPanelPublisher);
        frame.getContentPane().add(panel,BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(1200, 750));
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    
    public static void printPublisherLogo(Publisher publisher){
        byte[] logoType = publisher.getLogoType();
        JLabel label = new JLabel(new ImageIcon(logoType));
        JFrame frame = new JFrame("JPA/Hibernate EntityManager Results GUI");
        frame.setIconImage(new ImageIcon(new Main().getClass().getResource("/view/books.png")).getImage());
        JPanel panel = new JPanel();
        panel.add(label);
        frame.getContentPane().add(panel,BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(new Dimension(600, 300));
        frame.setLocation(new SecureRandom().ints(500, 800).findAny().getAsInt(), new SecureRandom().ints(200, 500).findAny().getAsInt());
        frame.setResizable(true);
        frame.setVisible(true);
        
        //for printing to a file again after reading from the DB...
//        Path pathToWrite;
//        try {
//            pathToWrite = Paths.get("C:\\DANIEL\\Netbeans\\PersistenceJPAHibernate\\src\\view\\newLogo.png");
//            if(Files.notExists(pathToWrite)){
//                Files.createFile(pathToWrite);
//            }
//            Files.newOutputStream(pathToWrite).write(logoType);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
        
    }
}
