package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import model.Conta;
import view.MyJPanel;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory=null;
        EntityManager em1=null;
        EntityManager em2=null;
        EntityManager em3=null;
        try{
            factory = Persistence.createEntityManagerFactory("contabancoPU");
            em1 = factory.createEntityManager();
            em2 = factory.createEntityManager();
            em3 = factory.createEntityManager();
            popular(em1,em2);
            listarContaGUI(em3);
        } catch(Exception ex){
            ex.printStackTrace();
        } finally{
            if(em1!=null&&em1.isOpen())
                em1.close();
            if(em2!=null&&em2.isOpen())
                em2.close();
            if(factory!=null&&factory.isOpen())
                factory.close();
        }
    }
    public static void popular(EntityManager em1,EntityManager em2){
        Conta cc1 = new Conta("1651651919");
        cc1.setSaldoContaCorrente(new BigDecimal(100.00));
        em1.persist(cc1);
        em1.getTransaction().begin();
        em1.getTransaction().commit();
        
        em1.getTransaction().begin();
        em2.getTransaction().begin();
        
        //simulação de inconsistência de saldo em entidade Conta.
        Conta conta1 = em1.find(Conta.class, 1L);
        //em1.lock(conta1, LockModeType.PESSIMISTIC_WRITE);
        System.out.println("pre-set1: "+conta1);
        conta1.setSaldoContaCorrente(conta1.getSaldoContaCorrente().add(new BigDecimal(50)));
        System.out.println("post-set1: "+conta1);
        //set1 não atualizado ainda no banco, apenas no contexto do em1, não no em2 ou no bd.
        Conta conta2 = em2.find(Conta.class, 1L);
        //em2.lock(conta2, LockModeType.PESSIMISTIC_WRITE);
        System.out.println("pre-set2: "+conta2);
        //em2 obtém valor desatualizado do saldo, sem a operação não sincronizada ainda de em1, o que leva a um resultado errado do saldo
        //após operação em em2 devido ao valor inicial desatualizado no banco.
        conta2.setSaldoContaCorrente(conta2.getSaldoContaCorrente().subtract(new BigDecimal(50)));
        System.out.println("post-set2: "+conta2);
        //set2 não atualizado ainda no banco, apenas no contexto do em2, não no em1 ou no bd.
        
        
        em1.getTransaction().commit();
        //atualizado saldo para 150, valor do contexto em1.
        em2.getTransaction().commit();
        //atualizado saldo para 50, valor do contexto em2.
        //o valor do conexto em2 estava errado, pq a operação do em2 foi realizada sobre o saldo desatualizado de 100 e não 150,
        //o que levou à inconsistência do valor final, após as duas operações e sincronizações, o qual seria 100--->150--->100. E não 100--->50.
        
        conta1 = em1.find(Conta.class, 1L);
        System.out.println("postCommitEm1"+conta1);
        conta2 = em2.find(Conta.class, 1L);
        System.out.println("postCommitEm2"+conta2);
        //valores inconsistentes provenientes do cache primário dos dois em e não do bd.
        
        //não sei o motivo mas simplesmente fazer um refresh, neste caso, não dá certo. Não reconhece no banco a chave da instância mapeada.
//        em1.refresh(em1.find(Conta.class, 1L));
//        System.out.println(em1.find(Conta.class, 1L));
        
        /*
        Ocorrendo desatualização de valores de instâncias de objetos attached to entity managers, qualquer busca naquele em
        baseada na mesma chave, retornará valores do cache primário e não do bd. Se o refresh não funcionar, para que se obtenha 
        valores atualizados do bd que substituam aqueles valores desatualizados do cache, deve-se fazer um clear no em ou um detachment 
        daquele objeto, obtendo um novo objeto para aquele persistence context primário através de uma nova busca por ex. 
        Então, aquela instância estará atualizada com os valores do banco e seus valores, no cache primário, poderão ser usados e até 
        ser feito um eventual refresh, mesmo o refresh antes do reattachment não tendo funcionado. Ainda assim estes valores poderão
        estar inconsistentes devido ao commit errado e sem controle atômico em relação às atualizações a nível de instância antes da
        sincronização com o banco.
        */
        System.out.println(em1.contains(conta1)?"conta1 still attached to em1 with wrong value":"conta1 detached");
        em1.detach(conta1);
        System.out.println(em1.contains(conta1)?"conta1 still attached to em1 with wrong value":"conta1 detached");
        conta1 = em1.find(Conta.class, 1L);
        System.out.println(em1.contains(conta1)?"new conta1 instance attached to em1 with new right values":"conta1 detached");
        em1.refresh(conta1);
        System.out.println("postCommitEm1 and postDetachmentReattachment of new instance values: "+conta1);
        
        /*
        O importante em transações paralelas de dois entity managers sobre um mesmo db é portanto controlar de forma atômica o acesso 
        e modificação a cada tupla da tabela, realizando o commit após cada alteração de cada entity manager, e, caso isto não seja feito, 
        mesmo já estando os dados inconsistentes nos caches de cada entity manager paralelo e no próprio bd (relativo ao valor esperado para
        operações combinadas entre os dois entity managers mas comitadas de forma errada), que se atualize os valores no entity manager
        de cache desatualizado através de um refresh, ou, se este não reconhecer no banco a instância, que se faça um detachment
        ou clear desta naquele entity manager, obtendo-se nova instância, então com valores atualizados, à partir do bd e não mais do cache 
        desatualizado daquele em. Ainda assim os valores atualizados poderão estar inconsistentes pelo menos em relação a 1 dos entity managers
        envolvidos.
        */
        
        /*
        No caso do lock otimista, cria-se, na classe de entidade, um atributo de versionamento, que é valorado com um valor automático
        para cada entitity manager paralelo, sendo que este só consegue fazer a atualização do atributo na próxima sincronização caso
        a versão seja a mesma... caso contrário, a alteração não é feita, pois o valor foi modificado ou outro entitity manager paralelo,
        o que poderia levar a inconsistência em relação àquilo a ser modificado pelo primeiro.
        
        No caso do lock pessimista, cria-se um lock explícito que pode levar a deadlocks ou starving... e que bloqueia o acesso ou escrita
        por outros entity managers de um atributo cujo lock já esteja sob posse de um outro, anterior.
        
        Para o teste dos dois casos teriam que ser criados duas Main threads, e não uma thread só como neste exemplo, feito apenas para
        testar as excções lançadas e inconsistência de dados, mas sem ter como haver concorrência propriamente pois se trata de uma
        thread única.
        */
    }
    private static void listarContaGUI(EntityManager em){
        List<Conta> contaList = em.createQuery("select c from Conta c order by c.id").getResultList();
        JFrame frame = new JFrame("Lista de Contas - EntityManager - Concorrência");
        JTextArea textArea = new JTextArea(contaList.stream().map(x->x+"\n").reduce((x,y)->x+y).orElse("conta inexistente")+"\n");
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
}
