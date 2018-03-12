package view;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MyJPanel extends JPanel{

    @Override
    protected void paintComponent(Graphics g) {
        ImageIcon img = new ImageIcon(new MyJPanel().getClass().getResource("/view/praia.jpg"));
        g.drawImage(img.getImage(), -1040, -500, this);
        ImageIcon img2 = new ImageIcon(new MyJPanel().getClass().getResource("/view/nemo.gif"));
        g.drawImage(img2.getImage(), 420, 405, this);
    }
    
}
