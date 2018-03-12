package view;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MyJPanel extends JPanel{

    @Override
    protected void paintComponent(Graphics g) {
        ImageIcon img = new ImageIcon(new MyJPanel().getClass().getResource("/view/bandeira.jpg"));
        g.drawImage(img.getImage(), -170, -100, this);
        ImageIcon img2 = new ImageIcon(new MyJPanel().getClass().getResource("/view/cloud.gif"));
        g.drawImage(img2.getImage(), 400, 250, this);
    }
    
}
