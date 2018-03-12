package view;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MyJPanel extends JPanel{
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(new ImageIcon(getClass().getResource("/view/library.png")).getImage(), -60, 0, this);
        repaint();
    }
}
