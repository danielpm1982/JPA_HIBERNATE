package view;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MyJPanel extends JPanel{

    @Override
    protected void paintComponent(Graphics g) {
        ImageIcon img = new ImageIcon(new MyJPanel().getClass().getResource("/view/iot.png"));
        g.drawImage(img.getImage(), -60, 0, this);
    }
    
}
