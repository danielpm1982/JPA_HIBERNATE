package model;

import java.awt.Font;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class CallbackClassPessoa {
    Font originalFont = (Font)UIManager.get("OptionPane.messageFont");
    @PrePersist
    public void prePersist(Pessoa p){
        UIManager.put("OptionPane.messageFont", new Font(Font.MONOSPACED, Font.BOLD, 30));
        JOptionPane.showMessageDialog(null, "prePersist event!\nObjeto persistido no EntityManager:\n"+p+" "+p.getEndereco());
        UIManager.put("OptionPane.messageFont", originalFont);
    }
    @PostPersist
    public void postPersist(Pessoa p){
        UIManager.put("OptionPane.messageFont", new Font(Font.MONOSPACED, Font.BOLD, 30));
        JOptionPane.showMessageDialog(null, "postPersist event!\nObjeto inserido no Banco de Dados:\n"+p+" "+p.getEndereco());
        UIManager.put("OptionPane.messageFont", originalFont);
    }
    @PreUpdate
    public void preUpdate(Pessoa p){
        UIManager.put("OptionPane.messageFont", new Font(Font.MONOSPACED, Font.BOLD, 30));
        JOptionPane.showMessageDialog(null, "preUpdate event!\nObjeto updated no EntityManager:\n"+p+" "+p.getEndereco());
        UIManager.put("OptionPane.messageFont", originalFont);
    }
    @PostUpdate
    public void postUpdate(Pessoa p){
        UIManager.put("OptionPane.messageFont", new Font(Font.MONOSPACED, Font.BOLD, 30));
        JOptionPane.showMessageDialog(null, "postUpdate event!\nObjeto updated no Banco de Dados:\n"+p+" "+p.getEndereco());
        UIManager.put("OptionPane.messageFont", originalFont);
    }
    @PreRemove
    public void preRemove(Pessoa p){
        UIManager.put("OptionPane.messageFont", new Font(Font.MONOSPACED, Font.BOLD, 30));
        JOptionPane.showMessageDialog(null, "preRemove event!\nObjeto removido do EntityManager:\n"+p+" "+p.getEndereco());
        UIManager.put("OptionPane.messageFont", originalFont);
    }
    @PostRemove
    public void postRemove(Pessoa p){
        UIManager.put("OptionPane.messageFont", new Font(Font.MONOSPACED, Font.BOLD, 30));
        JOptionPane.showMessageDialog(null, "postRemove event!\nObjeto removido do Banco de Dados:\n"+p+" "+p.getEndereco());  
        UIManager.put("OptionPane.messageFont", originalFont);
    }
    @PostLoad
    public void postLoad(Pessoa p){
        UIManager.put("OptionPane.messageFont", new Font(Font.MONOSPACED, Font.BOLD, 30));
        JOptionPane.showMessageDialog(null, "postLoad event!\nObjeto carregado no EntityManager à partir do Banco de Dados:\n"+p+" "+p.getEndereco());
        UIManager.put("OptionPane.messageFont", originalFont);
    }
}
